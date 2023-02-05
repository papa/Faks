package sahovskisavez;

import entiteti.Korisnik;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class SahovskiSavez {

    
    public static void main(String[] args) {
        EntityManagerFactory entMngFact = Persistence.createEntityManagerFactory("SahovskiSavezPU");
        EntityManager entMng = entMngFact.createEntityManager();
        
        try {
            EntityTransaction transcation = entMng.getTransaction();
            
            Korisnik korisnik = new Korisnik();
            korisnik.setIme("Laza");
            korisnik.setPrezime("Lazic");
            korisnik.setBrLK(123);
            korisnik.setSifK(Integer.SIZE);
            
            transcation.begin();
            entMng.persist(korisnik);
            transcation.commit();
        }   
        finally{
            if(entMng.getTransaction().isActive())
                   entMng.getTransaction().rollback();
            entMng.close();
        }
        
        entMngFact.close();
    }
    
}
