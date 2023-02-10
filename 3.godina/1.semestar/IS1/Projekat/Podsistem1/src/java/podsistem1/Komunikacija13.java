package podsistem1;

import entiteti.Korisnik;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import utility.Zahtev;

public class Komunikacija13 extends Thread{

    private  ConnectionFactory connectionFactory;
    private Topic myTopic;
    
    JMSContext context = null;
    JMSConsumer consumer=null;
    JMSProducer producer = null;
    EntityManager em = null;
    
    public Komunikacija13(ConnectionFactory cf, Topic t, EntityManager em)
    {
        connectionFactory = cf;
        myTopic = t;
        this.em = em;
    }
    
    //EntityManagerFactory emf = Persistence.createEntityManagerFactory("Podsistem1PU");
    
    //@PersistenceContext(unitName = "Podsistem1PU")
    //EntityManager em;
    
    private static final int GET_GRAD_ADRESA = 101;
    private static final int SMANJI_NOVAC = 111; 
    
    private void persistObject(Object o)
    {
        em.getTransaction().begin();
        em.persist(o);
        em.flush();
        em.clear();
        em.getTransaction().commit();
    }
    
    private Zahtev getAdresaGradNovac(int idKor)
    {
        List<Korisnik> korisnici = em.createNamedQuery("Korisnik.findByIDKor").setParameter("iDKor", idKor).getResultList();
        Korisnik k = korisnici.get(0);
        Zahtev z = new Zahtev();
        z.postaviBrZahteva(0);
        z.dodajParam(k.getAdresa());
        z.dodajParam(k.getIDGrad().getIDGrad());
        z.dodajParam(k.getNovac());
        return z;
    }
    
    private Zahtev smanjiNovac(int idKor, double novac)
    {
        List<Korisnik> korisnici = em.createNamedQuery("Korisnik.findByIDKor").setParameter("iDKor", idKor).getResultList();
        Korisnik k = korisnici.get(0);
        k.setNovac(k.getNovac() - novac);
        persistObject(k);
        Zahtev z = new Zahtev();
        z.postaviBrZahteva(0);
        return z;
    }
    
    @Override
    public void run() {
        System.out.println("Started podsistem1 komunikacija 13...");
        if(context == null)
        {
            context=connectionFactory.createContext();
            consumer=context.createConsumer(myTopic, "id=11");
            producer = context.createProducer();
        }
        ObjectMessage objMsgSend = context.createObjectMessage();
        int idKor = 0;
        double novac = 0;
        Zahtev zahtevOdg = null;
        
        while(true)
        {
            System.out.println("Cekam na zahtev od podsistema 3...");
            try {
                
                ObjectMessage objMsg = (ObjectMessage)consumer.receive();
                Zahtev zahtev = (Zahtev)objMsg.getObject();
                System.out.println("Primio zahtev od podsistema3 ");
                switch(zahtev.getBrZahteva())
                {
                    case GET_GRAD_ADRESA:
                        idKor = (int)zahtev.getParametri().get(0);
                        zahtevOdg = getAdresaGradNovac(idKor);
                        objMsgSend.setObject(zahtevOdg);
                        break;
                    case SMANJI_NOVAC:
                        idKor = (int)zahtev.getParametri().get(0);
                        novac = (double)zahtev.getParametri().get(1);
                        zahtevOdg = smanjiNovac(idKor, novac);
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
}