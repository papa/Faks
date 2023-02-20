package podsistem2;

import entiteti.Artikl;
import entiteti.Kategorija;
import entiteti.Korpa;
import entiteti.Sadrzi;
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

public class Main {
    private static final int KREIRAJ_KATEGORIJU = 5;
    private static final int KREIRAJ_ARTIKL = 6;
    private static final int MENJAJ_CENU = 7;
    private static final int POSTAVI_POPUST = 8;
    private static final int DODAJ_ARTIKL_KORPA = 9;
    private static final int BRISI_ARTIKL_KORPA = 10;
    private static final int SVE_KATEGORIJE = 14;
    private static final int SVI_ARTIKLI_KORISNIK = 15;
    private static final int KORISNIK_KORPA = 16;

    private static final int CISTI_KORPA_GET_ARTIKLI = 102;
    private static final int ISPRAZNI_KORPU = 122;
    
    private static int ID_SEND = 0;
    
    @Resource(lookup = "projConnFactory")
    private static ConnectionFactory connectionFactory;

    @Resource(lookup = "topicServer")
    private static Topic myTopic;

    private static JMSConsumer consumer = null;
    private static JMSProducer producer = null;
    private static JMSContext context = null;
    
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("Podsistem2PU");
    private static EntityManager em = emf.createEntityManager();

    private static void persistObject(Object o)
    {
        em.getTransaction().begin();
        em.persist(o);
        em.flush();
        em.getTransaction().commit();
    }
    
    private static void removeObject(Object o)
    {
        em.getTransaction().begin();
        em.remove(o);
        em.flush();
        em.getTransaction().commit();
    }
    
    private static Zahtev isprazniKorpu(int idKor)
    {
        List<Korpa> korpe = em.createNamedQuery("Korpa.findByIDKorpa").setParameter("iDKorpa", idKor).getResultList();
        Korpa k = korpe.get(0);
        Zahtev z = new Zahtev();
        z.postaviBrZahteva(0);
        List<Sadrzi> sadrziList = em.createNamedQuery("Sadrzi.findByIDKorpa").setParameter("iDKorpa", k.getIDKorpa()).getResultList();
        for(Sadrzi s : sadrziList)
        {
            removeObject(s);
        }
        
        em.getTransaction().begin();
        k.setUkupnaCena(0);
        em.flush();
        em.getTransaction().commit();
        
        return z;
    }
    
    private static Zahtev getArtikliKorpa(int idKor)
    {
        List<Sadrzi> sadrziList = em.createNamedQuery("Sadrzi.findByIDKorpa").setParameter("iDKorpa", idKor).getResultList();
        if(sadrziList.isEmpty())
        {
            Zahtev z = new Zahtev();
            z.postaviBrZahteva(-1);
            return z;
        }
        Zahtev z = new Zahtev();
        z.postaviBrZahteva(0);
        for(Sadrzi s : sadrziList)
        {
            PaketArtikl pArt = new PaketArtikl(s.getArtikl().getIDArt(), s.getKolicina(), s.getCena()*s.getKolicina(), s.getArtikl().getIDKor());
            z.dodajParam(pArt);
        }
        return z;
    }
    
    //zahtev 14
    private static List<Kategorija> getSveKategorije() {
        List<Kategorija> kategorije = em.createNamedQuery("Kategorija.findAll").getResultList();
        for (Kategorija k : kategorije) {
            k.setArtiklList(null);
            k.setKategorijaList(null);
            Kategorija k2 = k.getNadKat();
            while(k2 != null)
            {
                k2.setArtiklList(null);
                k2.setKategorijaList(null);
                k2 = k2.getNadKat();
            }
        }
        return kategorije;
    }

    //TODO da li je ok da postoji kategorija istog naziva sa istom nadkategorijom
    //zahtev 5
    private static Odgovor kreirajKategoriju(String naziv, String nazivNad) {
        List<Kategorija> kategorije = em.createNamedQuery("Kategorija.findByNaziv").setParameter("naziv", nazivNad).getResultList();
        if (kategorije.isEmpty() && nazivNad != null) 
            return new Odgovor(-1, "NE POSTOJI KATEGORIJA SA DATIM NAZIVOM NADKATEGORIJE");
        
        Kategorija nad = null;
        if (nazivNad != null) 
            nad = kategorije.get(0);
        kategorije = em.createNamedQuery("Kategorija.findByNaziv").setParameter("naziv", naziv).getResultList();
        if (!kategorije.isEmpty() && ((nad == null && kategorije.get(0).getNadKat() == null) || (nad != null && kategorije.get(0).getNadKat().equals(nad)))) 
            return new Odgovor(-1, "VEC POSTOJI KATEGORIJA SA NAZIVOM I NADKATEGORIJOM");

        Kategorija k = new Kategorija();
        k.setNaziv(naziv); k.setNadKat(nad); k.setIDKat(0);
        persistObject(k);
        return new Odgovor(0, "KATEGORIJA USPESNO KREIRANA");
    }

    //zahtev 6
    private static Odgovor kreirajArtikl(int idKor, String nazivArt, String opis, double cena, double popust, String nazivKategorije) {
        if (popust < 0 || popust > 100)
            return new Odgovor(-1, "POPUST MORA BITI U OPSEGU 0-100");
        List<Kategorija> kategorije = em.createNamedQuery("Kategorija.findByNaziv").setParameter("naziv", nazivKategorije).getResultList();
        if (kategorije.isEmpty()) 
            return new Odgovor(-1, "NE POSTOJI KATEGORIJA SA DATIM NAZIVOM");

        Kategorija k = kategorije.get(0);

        List<Artikl> artikli = em.createNamedQuery("Artikl.findByIDKorNaziv").setParameter("iDKor", idKor).setParameter("naziv", nazivArt).getResultList();
        if (!artikli.isEmpty()) 
            return new Odgovor(-1, "KORISNIK VEC IMA ARTIKL DATOG NAZIVA");
        
        Artikl a = new Artikl(0,nazivArt,cena, popust,idKor);
        a.setIDKat(k); a.setOpis(opis);
        persistObject(a);
        return new Odgovor(0, "USPESNO KREIRAN ARTIKL");
    }

    //zahtev 7
    private static Odgovor menjajCenu(int idKor, String nazivArt, double cena) {
        List<Artikl> artikli = em.createNamedQuery("Artikl.findByIDKorNaziv").setParameter("iDKor", idKor).setParameter("naziv", nazivArt).getResultList();
        if (artikli.isEmpty()) {
            return new Odgovor(-1, "KORISNIK NEMA ARTIKL DATOG NAZIVA");
        }

        Artikl a = artikli.get(0);
        em.getTransaction().begin();
        a.setCena(cena);
        em.flush();
        em.getTransaction().commit();
        return new Odgovor(0, "USPESNO POSTAVLJENA CENA");
    }

    //zahtev 8
    private static Odgovor postaviPopust(int idKor, String nazivArt, double popust) {
        if (popust < 0 || popust > 100) 
            return new Odgovor(-1, "POPUST MORA BITI U OPSEGU 0-100");
        List<Artikl> artikli = em.createNamedQuery("Artikl.findByIDKorNaziv").setParameter("iDKor", idKor).setParameter("naziv", nazivArt).getResultList();
        if (artikli.isEmpty()) 
            return new Odgovor(-1, "KORISNIK NEMA ARTIKL DATOG NAZIVA");
        
        Artikl a = artikli.get(0);
        em.getTransaction().begin();
        a.setPopust(popust);
        em.flush();
        em.getTransaction().commit();
        return new Odgovor(0, "USPESNO POSTAVLJEN POPUST");
    }

    //zahtev 9
    private static Odgovor dodajArtikle(int idKor, int idArt, int koliko) {
        List<Artikl> artikli = em.createNamedQuery("Artikl.findByIDArt").setParameter("iDArt", idArt).getResultList();
        if (artikli.isEmpty()) 
            return new Odgovor(-1, "NE POSTOJI ARTIKL SA DATIM ID");
        Artikl a = artikli.get(0);
        if (a.getIDKor() == idKor) 
            return new Odgovor(-1, "NE MOZETE KUPITI SVOJ ARTIKL");
        
        List<Korpa> korpe = em.createNamedQuery("Korpa.findByIDKorpa").setParameter("iDKorpa", idKor).getResultList();
        Korpa k = null;
        if (korpe.isEmpty()) {
            k = new Korpa(idKor, 0);
        } else {
            k = korpe.get(0);
        }

        List<Sadrzi> sadrziList = em.createNamedQuery("Sadrzi.findByIDArtIDKorpa").setParameter("iDArt", idArt).setParameter("iDKorpa", k.getIDKorpa()).getResultList();
        Sadrzi s = null;
        if (sadrziList.isEmpty()) {
            double cena = a.getCena() - a.getCena() * a.getPopust() / 100;
            em.getTransaction().begin();
            k.setUkupnaCena(k.getUkupnaCena() + koliko * cena);
            if(korpe.isEmpty())
            {
                System.out.println("Persist za korpu");
                em.persist(k);
            }
            em.flush();
            em.getTransaction().commit();
            
            s = new Sadrzi(idArt, idKor);
            s.setKolicina(koliko); s.setCena(cena); s.setArtikl(a); s.setKorpa(k);
            persistObject(s);
        } 
        else 
        {
            s = sadrziList.get(0);
            em.getTransaction().begin();
            s.setKolicina(s.getKolicina() + koliko);
            em.flush();
            em.getTransaction().commit();
            
            em.getTransaction().begin();
            k.setUkupnaCena(k.getUkupnaCena() + koliko * s.getCena());
            em.flush();
            em.getTransaction().commit();
        }

        return new Odgovor(0, "ARTIKLI USPESNO UBACENI U KORPU");
    }

    //zahtev
    private static Odgovor izbaciArtikle(int idKor, int idArt, int koliko) {
        List<Artikl> artikli = em.createNamedQuery("Artikl.findByIDArt").setParameter("iDArt", idArt).getResultList();
        if (artikli.isEmpty()) 
            return new Odgovor(-1, "NE POSTOJI ARTIKL SA DATIM ID");
        Artikl a = artikli.get(0);
        if (a.getIDKor() == idKor) 
            return new Odgovor(-1, "NE MOZETE IZBACITI SVOJ ARTIKL");
        
        List<Korpa> korpe = em.createNamedQuery("Korpa.findByIDKorpa").setParameter("iDKorpa", idKor).getResultList();
        Korpa k = null;
        if (korpe.isEmpty()) {
            k = new Korpa();
            k.setIDKorpa(idKor);
            k.setUkupnaCena(0);
            k.setSadrziList(null); // todo da li treba 
            persistObject(k);
        } else {
            k = korpe.get(0);
        }

        List<Sadrzi> sadrziList = em.createNamedQuery("Sadrzi.findByIDArtIDKorpa").setParameter("iDArt", idArt).setParameter("iDKorpa", k.getIDKorpa()).getResultList();
        Sadrzi s = null;
        if (sadrziList.isEmpty()) {
            return new Odgovor(-1, "NISTA NI UBACILI OVAJ PROIZVOD U KORPU");
        } else {
            s = sadrziList.get(0);
            if (s.getKolicina() < koliko) {
                return new Odgovor(-1, "IMATE MANJE ARTIKALA OD ONOGA STO ZELITE DA IZBACITE");
            }
            em.getTransaction().begin();
            s.setKolicina(s.getKolicina() - koliko);
            em.flush();
            em.getTransaction().commit();

            em.getTransaction().begin();
            k.setUkupnaCena(k.getUkupnaCena() - koliko * s.getCena());
            em.flush();
            em.getTransaction().commit();

            if (s.getKolicina() == 0) {
                removeObject(s);
            }
        }

        return new Odgovor(0, "ARTIKLI USPESNO IZBACENI IZ KORPE");
    }
    
     //zahtev 15
    private static Odgovor getArtikliKorisnik(int idKor) {
        List<Artikl> artikli = em.createNamedQuery("Artikl.findByIDKor").setParameter("iDKor", idKor).getResultList();
        for (Artikl a : artikli) {
            a.setRecenzijaList(null);
            a.setSadrziList(null);
            Kategorija k2 = a.getIDKat();
            while(k2 != null)
            {
                k2.setArtiklList(null);
                k2.setKategorijaList(null);
                k2 = k2.getNadKat();
            }
        }
        return new Odgovor(0, "SVE OK", artikli);
    }

    //zahtev 16
    private static Odgovor getKorpa(int idKor) {
        List<Korpa> korpe = em.createNamedQuery("Korpa.findByIDKorpa").setParameter("iDKorpa", idKor).getResultList();
        Korpa k = null;
        if (korpe.isEmpty()) {
            k = new Korpa();
            k.setIDKorpa(idKor);
            k.setUkupnaCena(0);
            persistObject(k);
        } else {
            k = korpe.get(0);
        }
        List<Sadrzi> sadrziList = em.createNamedQuery("Sadrzi.findByIDKorpa").setParameter("iDKorpa", k.getIDKorpa()).getResultList();
        k.setSadrziList(null);
        for (Sadrzi s : sadrziList) 
        {
            Artikl a = s.getArtikl();
            a.setRecenzijaList(null);
            a.setSadrziList(null);
            Kategorija k2 = a.getIDKat();
            while(k2 != null)
            {
                k2.setArtiklList(null);
                k2.setKategorijaList(null);
                k2 = k2.getNadKat();
            }
        }
        return new Odgovor(0, "SVE OK", sadrziList);
    }

    
    public static void run() 
    {
        System.out.println("Started podsistem2...");
        if(context == null)
        {
            context = connectionFactory.createContext();
            consumer = context.createConsumer(myTopic, "id=2");
            producer = context.createProducer();
        }
        ObjectMessage objMsgSend = context.createObjectMessage();
       
        while (true) {
            ID_SEND = 0;
            System.out.println("Cekam na zahtev ...");
            try {

                ObjectMessage objMsg = (ObjectMessage) consumer.receive();
                Zahtev zahtev = (Zahtev) objMsg.getObject();
                System.out.println("Primio zahtev...");
                switch (zahtev.getBrZahteva()) {
                    case Main.KREIRAJ_KATEGORIJU:
                        ArrayList<Object> params = zahtev.getParametri();
                        String nazivKategorije = (String) params.get(0);
                        String nazivNadKat = (String) params.get(1);
                        Odgovor odgovor = kreirajKategoriju(nazivKategorije, nazivNadKat);
                        objMsgSend.setObject(odgovor);
                        break;
                    case Main.KREIRAJ_ARTIKL:
                        params = zahtev.getParametri();
                        int idKor = (int) params.get(0);
                        String nazivArt = (String) params.get(1);
                        String opis = (String) params.get(2);
                        double cena = (double) params.get(3);
                        double popust = (double) params.get(4);
                        nazivKategorije = (String) params.get(5);
                        odgovor = kreirajArtikl(idKor, nazivArt, opis, cena, popust, nazivKategorije);
                        objMsgSend.setObject(odgovor);
                        break;
                    case Main.MENJAJ_CENU:
                        params = zahtev.getParametri();
                        idKor = (int) params.get(0);
                        nazivArt = (String) params.get(1);
                        cena = (double) params.get(2);
                        odgovor = menjajCenu(idKor, nazivArt, cena);
                        objMsgSend.setObject(odgovor);
                        break;
                    case Main.POSTAVI_POPUST:
                        params = zahtev.getParametri();
                        idKor = (int) params.get(0);
                        nazivArt = (String) params.get(1);
                        popust = (double) params.get(2);
                        odgovor = postaviPopust(idKor, nazivArt, popust);
                        objMsgSend.setObject(odgovor);
                        break;
                    case Main.DODAJ_ARTIKL_KORPA:
                        params = zahtev.getParametri();
                        idKor = (int) params.get(0);
                        int idArt = (int) params.get(1);
                        int koliko = (int) params.get(2);
                        odgovor = dodajArtikle(idKor, idArt, koliko);
                        objMsgSend.setObject(odgovor);
                        break;
                    case Main.BRISI_ARTIKL_KORPA:
                        params = zahtev.getParametri();
                        idKor = (int) params.get(0);
                        idArt = (int) params.get(1);
                        koliko = (int) params.get(2);
                        odgovor = izbaciArtikle(idKor, idArt, koliko);
                        objMsgSend.setObject(odgovor);
                        break;
                    case Main.SVE_KATEGORIJE:
                        List<Kategorija> kategorije = getSveKategorije();
                        odgovor = new Odgovor(0, "SVE OK", kategorije);
                        objMsgSend.setObject(odgovor);
                        break;
                    case Main.SVI_ARTIKLI_KORISNIK:
                        params = zahtev.getParametri();
                        idKor = (int) params.get(0);
                        odgovor = getArtikliKorisnik(idKor);
                        objMsgSend.setObject(odgovor);
                        break;
                    case Main.KORISNIK_KORPA:
                        params = zahtev.getParametri();
                        idKor = (int) params.get(0);
                        odgovor = getKorpa(idKor);
                        objMsgSend.setObject(odgovor);
                        break;
                    case CISTI_KORPA_GET_ARTIKLI:
                        idKor = (int)zahtev.getParametri().get(0);
                        Zahtev zahtevOdg = getArtikliKorpa(idKor);
                        objMsgSend.setObject(zahtevOdg);
                        ID_SEND = 3;
                        break;
                    case ISPRAZNI_KORPU:
                        idKor = (int)zahtev.getParametri().get(0);
                        zahtevOdg = isprazniKorpu(idKor);
                        objMsgSend.setObject(zahtevOdg);
                        ID_SEND = 3;
                        break;
                }

                objMsgSend.setIntProperty("id", ID_SEND);
                producer.send(myTopic, objMsgSend);
                System.out.println("Poslao...");
            } catch (JMSException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void main(String[] args) {
        run();
    }

}
