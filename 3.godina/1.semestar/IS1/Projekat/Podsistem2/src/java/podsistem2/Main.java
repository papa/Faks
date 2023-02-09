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
import utility.Zahtev;

public class Main extends Thread {

    private static final int KREIRAJ_KATEGORIJU = 5;
    private static final int KREIRAJ_ARTIKL = 6;
    private static final int MENJAJ_CENU = 7;
    private static final int POSTAVI_POPUST = 8;
    private static final int DODAJ_ARTIKL_KORPA = 9;
    private static final int BRISI_ARTIKL_KORPA = 10;
    private static final int SVE_KATEGORIJE = 14;
    private static final int SVI_ARTIKLI_KORISNIK = 15;
    private static final int KORISNIK_KORPA = 16;

    @Resource(lookup = "projConnFactory")
    private static ConnectionFactory connectionFactory;

    @Resource(lookup = "topicServer")
    private static Topic myTopic;

    EntityManagerFactory emf = Persistence.createEntityManagerFactory("Podsistem2PU");
    EntityManager em = emf.createEntityManager();

    private void persistObject(Object o)
    {
        em.joinTransaction();
        em.persist(o);
        em.flush();
    }
    
    private void removeObject(Object o)
    {
        em.joinTransaction();
        em.remove(o);
        em.flush();
    }
    
    //zahtev 14
    private List<Kategorija> getSveKategorije() {
        List<Kategorija> kategorije = em.createNamedQuery("Kategorija.findAll").getResultList();
        for (Kategorija k : kategorije) {
            k.setArtiklList(null);
            k.setKategorijaList(null);
        }
        return kategorije;
    }

    //TODO da li je ok da postoji kategorija istog naziva sa istom nadkategorijom
    //zahtev 5
    private Odgovor kreirajKategoriju(String naziv, String nazivNad) {
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
    private Odgovor kreirajArtikl(int idKor, String nazivArt, String opis, double cena, double popust, String nazivKategorije) {
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
    private Odgovor menjajCenu(int idKor, String nazivArt, double cena) {
        List<Artikl> artikli = em.createNamedQuery("Artikl.findByIDKorNaziv").setParameter("iDKor", idKor).setParameter("naziv", nazivArt).getResultList();
        if (artikli.isEmpty()) {
            return new Odgovor(-1, "KORISNIK NEMA ARTIKL DATOG NAZIVA");
        }

        Artikl a = artikli.get(0);
        a.setCena(cena);
        persistObject(a);
        return new Odgovor(0, "USPESNO POSTAVLJENA CENA");
    }

    //zahtev 8
    private Odgovor postaviPopust(int idKor, String nazivArt, double popust) {
        if (popust < 0 || popust > 100) 
            return new Odgovor(-1, "POPUST MORA BITI U OPSEGU 0-100");
        List<Artikl> artikli = em.createNamedQuery("Artikl.findByIDKorNaziv").setParameter("iDKor", idKor).setParameter("naziv", nazivArt).getResultList();
        if (artikli.isEmpty()) 
            return new Odgovor(-1, "KORISNIK NEMA ARTIKL DATOG NAZIVA");
        
        Artikl a = artikli.get(0);
        a.setPopust(popust);
        persistObject(a);
        return new Odgovor(0, "USPESNO POSTAVLJEN POPUST");
    }

    //zahtev 9
    private Odgovor dodajArtikle(int idKor, int idArt, int koliko) {
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
            k.setSadrziList(null); // todo da li treba 
            persistObject(k);
        } else {
            k = korpe.get(0);
        }

        List<Sadrzi> sadrziList = em.createNamedQuery("Sadrzi.findByIDArtIDKorpa").setParameter("iDArt", idArt).setParameter("iDKorpa", k.getIDKorpa()).getResultList();
        Sadrzi s = null;
        if (sadrziList.isEmpty()) {
            s = new Sadrzi(idArt, idKor);
            s.setKolicina(koliko); s.setCena(a.getCena() - a.getCena() * a.getPopust() / 100); s.setArtikl(a); s.setKorpa(k);
            persistObject(s);

            k.setUkupnaCena(k.getUkupnaCena() + koliko * s.getCena());
            persistObject(k);
        } else {
            s = sadrziList.get(0);
            s.setKolicina(s.getKolicina() + koliko);
            persistObject(s);

            k.setUkupnaCena(k.getUkupnaCena() + koliko * s.getCena());
            persistObject(k);
        }

        return new Odgovor(0, "SVE OK");
    }

    //zahtev
    private Odgovor izbaciArtikle(int idKor, int idArt, int koliko) {
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
            s.setKolicina(s.getKolicina() - koliko);
            persistObject(s);

            k.setUkupnaCena(k.getUkupnaCena() - koliko * s.getCena());
            persistObject(k);

            if (s.getKolicina() == 0) {
                removeObject(s);
            }
        }

        return new Odgovor(0, "SVE OK");
    }
    
     //zahtev 15
    private Odgovor getArtikliKorisnik(int idKor) {
        List<Artikl> artikli = em.createNamedQuery("Artikl.findByIDKor").setParameter("iDKor", idKor).getResultList();
        for (Artikl a : artikli) {
            a.setRecenzijaList(null);
            a.setSadrziList(null);
            a.getIDKat().setArtiklList(null);
            a.getIDKat().setKategorijaList(null);
        }
        return new Odgovor(0, "SVE OK", artikli);
    }

    //zahtev 16
    private Odgovor getKorpa(int idKor) {
        List<Korpa> korpe = em.createNamedQuery("Korpa.findByIDKorpa").setParameter("iDKorpa", idKor).getResultList();
        if (korpe.isEmpty()) {
            Korpa k = new Korpa();
            k.setIDKorpa(idKor);
            k.setUkupnaCena(0);
            k.setSadrziList(null); // todo da li treba 
            persistObject(k);
            return new Odgovor(0, "SVE OK", k);
        } else {
            List<Sadrzi> sadrziList = em.createNamedQuery("Sadrzi.findByIDKorpa").setParameter("iDKorpa", korpe.get(0).getIDKorpa()).getResultList();
            for (Sadrzi s : sadrziList) 
            {
                Artikl a = s.getArtikl();
                a.setRecenzijaList(null);
                a.setSadrziList(null);
                a.getIDKat().setArtiklList(null);
                a.getIDKat().setKategorijaList(null);
            }
            return new Odgovor(0, "SVE OK", sadrziList);
        }
    }

    @Override
    public void run() {

        System.out.println("Started podsistem2...");
        JMSContext context = connectionFactory.createContext();
        JMSConsumer consumer = context.createConsumer(myTopic, "id=2");
        JMSProducer producer = context.createProducer();
        ObjectMessage objMsgSend = context.createObjectMessage();
        Odgovor odgovor = null;
        ArrayList<Object> params = null;
        String nazivKategorije = null;
        int idKor = 0;
        String nazivArt = null;
        String opis = null;
        double cena = 0;
        double popust = 0;
        int idArt = 0;
        int koliko = 0;
        while (true) {
            System.out.println("Cekam na zahtev od servera...");
            try {

                ObjectMessage objMsg = (ObjectMessage) consumer.receive();
                Zahtev zahtev = (Zahtev) objMsg.getObject();
                System.out.println("Primio zahtev od servera");
                switch (zahtev.getBrZahteva()) {
                    case Main.KREIRAJ_KATEGORIJU:
                        params = zahtev.getParametri();
                        nazivKategorije = (String) params.get(0);
                        String nazivNadKat = (String) params.get(1);
                        odgovor = kreirajKategoriju(nazivKategorije, nazivNadKat);
                        objMsgSend.setObject(odgovor);
                        break;
                    case Main.KREIRAJ_ARTIKL:
                        params = zahtev.getParametri();
                        idKor = (int) params.get(0);
                        nazivArt = (String) params.get(1);
                        opis = (String) params.get(2);
                        cena = (double) params.get(3);
                        popust = (double) params.get(4);
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
                        idArt = (int) params.get(1);
                        koliko = (int) params.get(2);
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
        new Komunikacija23(connectionFactory, myTopic).start();
        while (true) {
        }
    }

}
