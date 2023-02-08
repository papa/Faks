package podsistem2;

import entiteti.Korpa;
import entiteti.Sadrzi;
import java.io.Serializable;
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
import utility.PaketArtikl;
import utility.Zahtev;

public class Komunikacija23 extends Thread{
    
    private static ConnectionFactory connectionFactory;
    private Topic myTopic;
    
    public Komunikacija23(ConnectionFactory cf, Topic t)
    {
        connectionFactory = cf;
        myTopic = t;
    }
    
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("Podsistem2PU");
    EntityManager em = emf.createEntityManager();
    //@PersistenceContext(unitName = "Podsistem1PU")
    //EntityManager em;
    private static final int CISTI_KORPA_GET_ARTIKLI = 102;
    private static final int ISPRAZNI_KORPU = 122;
    
    private Zahtev formirajZahtev(Korpa k)
    {
        Zahtev z = new Zahtev();
        z.postaviBrZahteva(0);
        List<Sadrzi> sadrziList = em.createNamedQuery("Sadrzi.findByIDKorpa").setParameter("iDKorpa", k.getIDKorpa()).getResultList();
        for(Sadrzi s : sadrziList)
        {
            PaketArtikl pArt = new PaketArtikl(s.getArtikl().getIDArt(), s.getKolicina(), s.getCena()*s.getKolicina());
            z.dodajParam(pArt);
        }
//        for(Sadrzi s : sadrziList)
//        {
//            em.joinTransaction();
//            em.remove(s);
//            em.flush();
//        }
        
        return z;
    }
    
    private Zahtev isprazniKorpu(int idKor)
    {
        List<Korpa> korpe = em.createNamedQuery("Korpa.findByIDKorpa").setParameter("iDKorpa", idKor).getResultList();
        Korpa k = korpe.get(0);
        Zahtev z = new Zahtev();
        z.postaviBrZahteva(0);
        List<Sadrzi> sadrziList = em.createNamedQuery("Sadrzi.findByIDKorpa").setParameter("iDKorpa", k.getIDKorpa()).getResultList();
        for(Sadrzi s : sadrziList)
        {
            em.joinTransaction();
            em.remove(s);
            em.flush();
        }
        k.setUkupnaCena(0);
        em.joinTransaction();
        em.persist(k);
        em.flush();
        return z;
    }
    
    private Zahtev getArtikliKorpa(int idKor)
    {
        List<Korpa> korpe = em.createNamedQuery("Korpa.findByIDKorpa").setParameter("iDKorpa", idKor).getResultList();
        if(korpe.isEmpty() || korpe.get(0).getUkupnaCena() == 0)
        {
            Zahtev z = new Zahtev();
            z.postaviBrZahteva(-1);
            return z;
        }
        return formirajZahtev(korpe.get(0));
    }
    
    @Override
    public void run() {
        System.out.println("Started podsistem1 komunikacija 23...");
        JMSContext context=connectionFactory.createContext();
        JMSConsumer consumer=context.createConsumer(myTopic, "id=12");
        JMSProducer producer = context.createProducer();
        ObjectMessage objMsgSend = context.createObjectMessage();
        int idKor = 0;
        Zahtev zahtevOdg = null;
        
        while(true)
        {
            System.out.println("Cekam na zahtev od podsistema3...");
            try {
                
                ObjectMessage objMsg = (ObjectMessage)consumer.receive();
                Zahtev zahtev = (Zahtev)objMsg.getObject();
                System.out.println("Primio zahtev od podsistema3 ");
                switch(zahtev.getBrZahteva())
                {
                    case CISTI_KORPA_GET_ARTIKLI:
                        idKor = (int)zahtev.getParametri().get(0);
                        zahtevOdg = getArtikliKorpa(idKor);
                        objMsgSend.setObject(zahtevOdg);
                        break;
                    case ISPRAZNI_KORPU:
                        idKor = (int)zahtev.getParametri().get(0);
                        zahtevOdg = isprazniKorpu(idKor);
                        objMsgSend.setObject(zahtevOdg);
                        break;
                }

                objMsgSend.setIntProperty("id", 13);
                producer.send(myTopic, objMsgSend);
                System.out.println("Poslao podsistemu 3");
            } catch (JMSException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public static void main(String[] args) {
        
        while(true){}
    }
}