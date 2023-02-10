package podsistem1;

import entiteti.Grad;
import entiteti.Korisnik;
import java.util.ArrayList;
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
import utility.Zahtev;

public class Main extends Thread{

    @Resource(lookup="projConnFactory")
    private static ConnectionFactory connectionFactory;
    
    @Resource(lookup="topicServer")
    private static Topic myTopic;
    
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("Podsistem1PU");
    EntityManager em = emf.createEntityManager();
    //@PersistenceContext(unitName = "Podsistem1PU")
    //EntityManager em;
    
    private static final int KREIRAJ_GRAD = 1;
    private static final int KREIRAJ_KORISNIKA = 2;
    private static final int DODAJ_NOVAC = 3;
    private static final int PROMENA_ADRESA_GRAD = 4;
    private static final int SVI_GRADOVI = 12;
    private static final int SVI_KORISNICI = 13;
    
     //zahtev 1
    private Odgovor kreirajGrad(String naziv)
    {
        List<Grad> gradovi = em.createNamedQuery("Grad.findByNaziv").setParameter("naziv", naziv).getResultList();
        if(!gradovi.isEmpty())
            return new Odgovor(-1, "VEC POSTOJI GRAD SA ZADATIM NAZIVOM");
        
        Grad g = new Grad();
        g.setNaziv(naziv);
        g.setIDGrad(0);
        em.joinTransaction();
        em.persist(g);
        em.flush();
        return new Odgovor(0, "USPESNO KREIRAN GRAD");
    }
    
    //zahtev2
    private Odgovor kreirajKorisnika(String username, String ime, String prezime, String sifra, String adresa,double novac, String nazivGrada)
    {
        List<Grad> gradovi = em.createNamedQuery("Grad.findByNaziv").setParameter("naziv", nazivGrada).getResultList();
        if(gradovi.isEmpty())
            return new Odgovor(-1, "NE POSTOJI GRAD SA ZADATIM NAZIVOM");
        Grad g = gradovi.get(0);
        
        List<Korisnik> korisnici = em.createNamedQuery("Korisnik.findByUsername").setParameter("username", username).getResultList();
        if(!korisnici.isEmpty())
            return new Odgovor(-1, "VEC POSTOJI KORISNIK SA ZADATIM USERNAME");
        
        Korisnik k = new Korisnik(0, username, novac);
        k.setIme(ime);
        k.setPrezime(prezime);
        k.setSifra(sifra);
        k.setAdresa(adresa);
        k.setIDGrad(g);
        em.joinTransaction();
        em.persist(k);
        em.flush();
        return new Odgovor(0, "USPESNO KREIRAN KORISNIK");
    }
    
    //zahtev3
    private Odgovor dodajNovac(String username, double novac)
    {
        List<Korisnik> korisnici = em.createNamedQuery("Korisnik.findByUsername").setParameter("username", username).getResultList();
        if(korisnici.isEmpty())
            return new Odgovor(-1, "NE POSTOJI KORISNIK " + username);
        
        Korisnik k = korisnici.get(0);
        k.setNovac(k.getNovac() + novac);
        em.joinTransaction();
        em.persist(k);
        em.flush();
        return new Odgovor(0, "USPESNO DODAT NOVAC KORISNIKU");
    }
    
    //zahtev4
    private Odgovor promeniAdresuGrad(String username, String adresa, String nazivGrada)
    {
        List<Korisnik> korisnici = em.createNamedQuery("Korisnik.findByUsername").setParameter("username", username).getResultList();
        if(korisnici.isEmpty())
            return new Odgovor(-1, "NE POSTOJI KORISNIK SA ZADATIM USERNAME");
        Korisnik k = korisnici.get(0);
        
        List<Grad> gradovi = em.createNamedQuery("Grad.findByNaziv").setParameter("naziv", nazivGrada).getResultList();
        if(gradovi.isEmpty())
            return new Odgovor(-1, "NE POSTOJI GRAD SA DATIM NAZIVOM");
        Grad g = gradovi.get(0);
        
        k.setAdresa(adresa);
        k.setIDGrad(g);
        em.joinTransaction();
        em.persist(k);
        em.flush();
        return new Odgovor(0, "USPESNO PROMENJENI ADRESA I GRAD");
    }
    
    //zahtev 12
    private List<Grad> getSviGradovi()
    {
        List<Grad> gradovi =  em.createNamedQuery("Grad.findAll").getResultList();
        for(Grad g : gradovi)
            g.setKorisnikList(null);
        return gradovi;
    }
    
    //zahtev 13
    private List<Korisnik> getSviKorisnici()
    {
        List<Korisnik> korisnici = em.createNamedQuery("Korisnik.findAll").getResultList();
        return korisnici;
    }
    
    @Override
    public void run() {
        System.out.println("Started podsistem1...");
        JMSContext context=connectionFactory.createContext();
        JMSConsumer consumer=context.createConsumer(myTopic, "id=1");
        JMSProducer producer = context.createProducer();
        ObjectMessage objMsgSend = context.createObjectMessage();
        Odgovor odgovor = null;
        ArrayList<Object> params = null;
        String username = null;
        String adresa = null;
        double novac = 0;
        String nazivGrada = null;
        
        while(true)
        {
            System.out.println("Cekam na zahtev od servera...");
            try {
                
                ObjectMessage objMsg = (ObjectMessage)consumer.receive();
                Zahtev zahtev = (Zahtev)objMsg.getObject();
                System.out.println("Primio zahtev od servera");
                switch(zahtev.getBrZahteva())
                {
                    case KREIRAJ_GRAD:
                        nazivGrada = (String)zahtev.getParametri().get(0);
                        odgovor = kreirajGrad(nazivGrada);
                        objMsgSend.setObject(odgovor);
                        break;
                    case KREIRAJ_KORISNIKA: 
                        params = zahtev.getParametri();
                        username = (String)params.get(0);
                        String ime = (String)params.get(1);
                        String prezime = (String)params.get(2);
                        String sifra = (String)params.get(3);
                        adresa = (String)params.get(4);
                        novac = (double)params.get(5);
                        nazivGrada = (String)params.get(6);
                        odgovor = kreirajKorisnika(username, ime, prezime, sifra, adresa, novac, nazivGrada);
                        objMsgSend.setObject(odgovor);
                        break;
                    case DODAJ_NOVAC:
                        params = zahtev.getParametri();
                        username = (String)params.get(0);
                        novac = (double)params.get(1);
                        odgovor = dodajNovac(username, novac);
                        objMsgSend.setObject(odgovor);
                        break;
                    case PROMENA_ADRESA_GRAD:
                        params = zahtev.getParametri();
                        username = (String)params.get(0);
                        adresa = (String)params.get(1);
                        nazivGrada = (String)params.get(2);
                        odgovor = promeniAdresuGrad(username, adresa, nazivGrada);
                        objMsgSend.setObject(odgovor);
                        break;
                    case SVI_GRADOVI:
                        List<Grad> gradovi = getSviGradovi();
                        odgovor = new Odgovor(0, "SVE OK", gradovi);
                        objMsgSend.setObject(odgovor);
                        break;
                    case SVI_KORISNICI:
                        List<Korisnik> sviKorisnici = getSviKorisnici();
                        odgovor = new Odgovor(0, "SVE OK", sviKorisnici);
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
        new Main().start();
        new Komunikacija13(connectionFactory, myTopic).start();
        while(true){}
    }
}
