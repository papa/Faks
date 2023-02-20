package podsistem3;

import entiteti.Narudzbina;
import entiteti.Stavka;
import entiteti.Transakcija;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.ObjectMessage;
import javax.jms.Topic;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import utility.Odgovor;
import utility.PaketArtikl;
import utility.Zahtev;

public class Main {

    @Resource(lookup="projConnFactory")
    private static ConnectionFactory connectionFactory;
    
    @Resource(lookup="topicServer")
    private static Topic myTopic;
    
    private static JMSConsumer consumer = null;
    private static JMSProducer producer = null;
    private static JMSContext context = null;
    
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("Podsistem3PU");
    private static EntityManager em = emf.createEntityManager();
  
    private static final int PLACANJE = 11;
    private static final int KORISNIK_NARUDZBINE = 17;
    private static final int SVE_NARUDZBINE = 18;
    private static final int SVE_TRANSKACIJE = 19;
    
    private static final int GET_GRAD_ADRESA = 101;
    private static final int SMANJI_NOVAC = 111;
    private static final int CISTI_KORPA_GET_ARTIKLI = 102;
    private static final int ISPRAZNI_KORPU = 122;
    
    private static final int DODAJ_NOVAC_PLACANJE = 1111;
    
    private static void persistObject(Object o)
    {
        em.getTransaction().begin();
        em.persist(o);
        em.flush();
        em.getTransaction().commit();
    }
    
    private static double izvrsiPlacanje(int idKor, double novacKorisnik, String adresa, int idGrad, ArrayList<Object> artikliPaketi)
    {
        double totCena = 0;
        for(Object o : artikliPaketi)
        {
            PaketArtikl pArt = (PaketArtikl)o;
            totCena+=pArt.getCena();
        }
        if(novacKorisnik < totCena)
            return -1;
        
        Narudzbina nar = new Narudzbina(0,totCena,new Date(),adresa,idGrad,idKor);
        persistObject(nar);
        
        Transakcija t = new Transakcija(0,totCena,new Date());
        t.setIDNar(nar);
        persistObject(t);
        
        for(Object o : artikliPaketi)
        {
            PaketArtikl pArt = (PaketArtikl)o;
            Stavka s = new Stavka(0, pArt.getIdArt(), pArt.getKolicina(), pArt.getCena());
            s.setIDNar(nar);
            persistObject(s);
        }
        
        return totCena;
    }
    
    //id 13 da tu prima poruke na topicu
    //zahtev 11
    private static Odgovor placanje(int idKor)
    {
        try {
            ObjectMessage objMsgSend = context.createObjectMessage();
            
            //----------------------------ka podsistemu 1
            objMsgSend.setIntProperty("id", 1);
            Zahtev zahtev = new Zahtev();
            zahtev.postaviBrZahteva(GET_GRAD_ADRESA);
            zahtev.dodajParam(idKor);
            objMsgSend.setObject(zahtev);
            
            producer.send(myTopic, objMsgSend);
            System.out.println("Poslao zahtev podsistemu 1");
            
            ObjectMessage objMsgRcv = (ObjectMessage)consumer.receive();
            System.out.println("Primio odgovor od podsistema 1");
            Zahtev z = (Zahtev)objMsgRcv.getObject();
            String adresa = (String)z.getParametri().get(0);
            int idGrad = (int)z.getParametri().get(1);
            double novac = (double)z.getParametri().get(2);
            //----------------------------- ka podsistemu 2
            ObjectMessage objMsgSend2 = context.createObjectMessage();
            objMsgSend2.setIntProperty("id", 2);
            Zahtev zahtev2 = new Zahtev();
            zahtev2.postaviBrZahteva(CISTI_KORPA_GET_ARTIKLI);
            zahtev2.dodajParam(idKor);
            objMsgSend2.setObject(zahtev2);
            
            producer.send(myTopic, objMsgSend2);
            System.out.println("Poslao zahtev podsistemu 2");
            
            ObjectMessage objMsgRcv2 = (ObjectMessage)consumer.receive();
            System.out.println("Primio odgovor od podsistema 2");
            Zahtev z2 = (Zahtev)objMsgRcv2.getObject();
            if(z2.getBrZahteva() == -1)
                return new Odgovor(-1, "KORISNIKU JE PRAZNA KORPA");
            
            double ok = izvrsiPlacanje(idKor, novac, adresa, idGrad, z2.getParametri());
            if(ok == -1)
                return new Odgovor(-1, "KORISNIK NEMA DOVOLJNO NOVCA");
            
            //------------------------------ka podsistemu 1
            objMsgSend = context.createObjectMessage();
            objMsgSend.setIntProperty("id", 1);
            zahtev = new Zahtev();
            zahtev.postaviBrZahteva(SMANJI_NOVAC);
            zahtev.dodajParam(idKor);
            zahtev.dodajParam(ok);
            objMsgSend.setObject(zahtev);
            
            producer.send(myTopic, objMsgSend);
            System.out.println("Poslao zahtev podsistemu 1");
            consumer.receive();
            System.out.println("Primio zahtev od podsistema 1");
            
            ArrayList<Object> pArtList = z2.getParametri();
            for(Object obj : pArtList)
            {
                PaketArtikl pArt = (PaketArtikl)obj;
                zahtev = new Zahtev();
                zahtev.postaviBrZahteva(DODAJ_NOVAC_PLACANJE);
                zahtev.dodajParam(pArt.getIdKor());
                zahtev.dodajParam(pArt.getCena()*pArt.getKolicina());
                objMsgSend = context.createObjectMessage();
                objMsgSend.setIntProperty("id", 1);
                objMsgSend.setObject(zahtev);
                producer.send(myTopic, objMsgSend);
                System.out.println("Poslao zahtev podsistemu 1 za uvecanje");
                consumer.receive();
                System.out.println("Primio zahtev od podsistema 1");
            }
            
            //-----------------------------ka podsistemu 2
            objMsgSend2 = context.createObjectMessage();
            objMsgSend2.setIntProperty("id", 2);
            zahtev2 = new Zahtev();
            zahtev2.postaviBrZahteva(ISPRAZNI_KORPU);
            zahtev2.dodajParam(idKor);
            objMsgSend2.setObject(zahtev2);
            
            producer.send(myTopic, objMsgSend2);
            
            consumer.receive();
            System.out.println("Primi zahtev od podsistema 2");
        } catch (JMSException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return new Odgovor(0, "USPESNO IZVRSENO PLACANJE");
    }
    
    //zahtev 17
    private static Odgovor getKorisnikNarudzbine(int idKor)
    {
        List<Narudzbina> narudzbine = em.createNamedQuery("Narudzbina.findByIDKor").setParameter("iDKor", idKor).getResultList();
        for(Narudzbina n : narudzbine)
        {
            n.setStavkaList(null);
            n.setTransakcijaList(null);
        }
        return new Odgovor(0, "SVE OK", narudzbine);
    }
    
    //zahtev 18
    private static Odgovor getSveNarudzbine()
    {
        List<Narudzbina> narudzbine = em.createNamedQuery("Narudzbina.findAll").getResultList();
        for(Narudzbina n : narudzbine)
        {
            n.setStavkaList(null);
            n.setTransakcijaList(null);
        }
        return new Odgovor(0, "SVE OK", narudzbine);
    }
    
    //zahtev 19
    private static Odgovor getSveTranskacije()
    {
        List<Transakcija> transakcije = em.createNamedQuery("Transakcija.findAll").getResultList();
        for(Transakcija t : transakcije)
        {
            Narudzbina n = t.getIDNar();
            n.setStavkaList(null);
            n.setTransakcijaList(null);
        }
        return new Odgovor(0, "SVE OK", transakcije);
    }
    
    public static void run() {
        System.out.println("Started podsistem3...");
        if(context == null)
        {
            context=connectionFactory.createContext();
            consumer=context.createConsumer(myTopic, "id=3");
            producer = context.createProducer();
        }
        ObjectMessage objMsgSend = context.createObjectMessage();
        
        while(true)
        {
            System.out.println("Cekam na zahtev od servera...");
            try {
                
                ObjectMessage objMsg = (ObjectMessage)consumer.receive();
                Zahtev zahtev = (Zahtev)objMsg.getObject();
                System.out.println("Primio zahtev od servera");
                switch(zahtev.getBrZahteva())
                {
                    case PLACANJE:
                        ArrayList<Object> params = zahtev.getParametri();
                        int idKor = (int)params.get(0);
                        Odgovor odgovor = placanje(idKor);
                        objMsgSend.setObject(odgovor);
                        break;
                    case KORISNIK_NARUDZBINE: 
                        params = zahtev.getParametri();
                        idKor = (int)params.get(0);
                        odgovor = getKorisnikNarudzbine(idKor);
                        objMsgSend.setObject(odgovor);
                        break;
                    case SVE_NARUDZBINE:
                        params = zahtev.getParametri();
                        odgovor = getSveNarudzbine();
                        objMsgSend.setObject(odgovor);
                        break;
                    case SVE_TRANSKACIJE:
                        params = zahtev.getParametri();
                        odgovor = getSveTranskacije();
                        objMsgSend.setObject(odgovor);
                        break;
                }

                objMsgSend.setIntProperty("id", 0);
                producer.send(myTopic, objMsgSend);
                System.out.println("Poslao serveru");
            } catch (JMSException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public static void main(String[] args) {
        run();
    }
}
