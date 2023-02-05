/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package podsistem2;

import entiteti.Kategorija;
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
    
    private static final int KREIRAJ_KATEGORIJU = 5;
    private static final int KREIRAJ_ARTIKL = 6;
    private static final int MENJAJ_CENU = 7;
    private static final int POSTAVI_POPUST = 8;
    private static final int DODAJ_ARTIKL_KORPA = 9;
    private static final int BRISI_ARTIKL_KORPA = 10;
    private static final int SVE_KATEGORIJE = 14;
    private static final int SVI_ARTIKLI_KORISNIK = 15;
    private static final int KORISNIK_KORPA = 16;
    
    @Resource(lookup="projConnFactory")
    private static ConnectionFactory connectionFactory;
    
    @Resource(lookup="topicServer")
    private static Topic myTopic;
    
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("Podsistem2PU");
    EntityManager em = emf.createEntityManager();
    
    private List<Kategorija> getSveKategorije()
    {
        List<Kategorija> kategorije = em.createNamedQuery("Kategorija.findAll").getResultList();        
        for(Kategorija k : kategorije)
        {
            k.setArtiklList(null);
            k.setKategorijaList(null);
        }
        return kategorije;
    }
    
    //TODO da li je ok da postoji kategorija istog naziva sa istom nadkategorijom
    private Odgovor kreirajKategoriju(String naziv, String nazivNad)
    {
        List<Kategorija> kategorije = em.createNamedQuery("Kategorija.findByNaziv").setParameter("naziv", nazivNad).getResultList();
        if(kategorije.isEmpty() && nazivNad != null)
            return new Odgovor(-1, "NE POSTOJI KATEGORIJA SA DATIM NAZIVOM NADKATEGORIJE");
        Kategorija nad = null;
        if(nazivNad != null)
            nad = kategorije.get(0);
        
        kategorije = em.createNamedQuery("Kategorija.findByNaziv").setParameter("naziv", naziv).getResultList();
        if(!kategorije.isEmpty() && ((nad == null && kategorije.get(0).getNadKat() == null) ||  (nad != null && kategorije.get(0).getNadKat().equals(nad))))
            return new Odgovor(-1, "VEC POSTOJI KATEGORIJA SA NAZIVOM I NADKATEGORIJOM");
        
        Kategorija k = new Kategorija();
        k.setNaziv(naziv);
        k.setNadKat(nad);
        k.setIDKat(0);
        em.joinTransaction();
        em.persist(k);
        em.flush();
        return new Odgovor(0, "KATEGORIJA USPESNO KREIRANA");
    }
    
    @Override
    public void run() {
        
        System.out.println("Started podsistem2...");
        JMSContext context=connectionFactory.createContext();
        JMSConsumer consumer=context.createConsumer(myTopic, "id=2");
        JMSProducer producer = context.createProducer();
        ObjectMessage objMsgSend = context.createObjectMessage();
        Odgovor odgovor = null;
        ArrayList<Object> params = null;
        
        while(true)
        {
            System.out.println("Cekam na zahtev od servera...");
            try {
                
                ObjectMessage objMsg = (ObjectMessage)consumer.receive();
                Zahtev zahtev = (Zahtev)objMsg.getObject();
                System.out.println("Primio zahtev od servera");
                switch(zahtev.getBrZahteva())
                {
                    case Main.KREIRAJ_KATEGORIJU:
                        params = zahtev.getParametri();
                        String nazivKategorije = (String)params.get(0);
                        String nazivNadKat = (String)params.get(1);
                        odgovor = kreirajKategoriju(nazivKategorije, nazivNadKat);
                        objMsgSend.setObject(odgovor);
                        break;
                    case Main.KREIRAJ_ARTIKL: 
                        
                        break;
                    case Main.MENJAJ_CENU:
                        
                        break;
                    case Main.POSTAVI_POPUST:
                        
                        break;
                    case Main.DODAJ_ARTIKL_KORPA:
                        
                        break;
                    case Main.BRISI_ARTIKL_KORPA:
                        
                        break;
                    case Main.SVE_KATEGORIJE:
                        List<Kategorija> kategorije = getSveKategorije();
                        odgovor = new Odgovor(0, "SVE OK", kategorije);
                        objMsgSend.setObject(odgovor);
                        break;
                    case Main.SVI_ARTIKLI_KORISNIK:
                        
                        break;
                    case Main.KORISNIK_KORPA:
                        
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
        while(true){}
    }
    
}
