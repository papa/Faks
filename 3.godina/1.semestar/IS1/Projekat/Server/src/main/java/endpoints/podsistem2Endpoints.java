package endpoints;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.ObjectMessage;
import javax.jms.Topic;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import utility.Odgovor;
import utility.Zahtev;

@Path("podsistem2")
public class podsistem2Endpoints {
    
    @Resource(lookup="projConnFactory")
    private ConnectionFactory connectionFactory;
    
    @Resource(lookup="topicServer")
    private Topic myTopic;
    
    JMSContext context = null;
    JMSConsumer consumer=null;
    JMSProducer producer = null;
    
    private static final int KREIRAJ_KATEGORIJU = 5;
    private static final int KREIRAJ_ARTIKL = 6;
    private static final int MENJAJ_CENU = 7;
    private static final int POSTAVI_POPUST = 8;
    private static final int DODAJ_ARTIKL_KORPA = 9;
    private static final int BRISI_ARTIKL_KORPA = 10;
    private static final int SVE_KATEGORIJE = 14;
    private static final int SVI_ARTIKLI_KORISNIK = 15;
    private static final int KORISNIK_KORPA = 16;
    private static final int PODSISTEM_ID = 2;
    
    
    private Response posaljiZahtev(Zahtev zahtev)
    {
        try {
            if(context == null){
                context = connectionFactory.createContext();
                consumer=context.createConsumer(myTopic, "id=0");
                producer = context.createProducer();
            }
            System.out.println("Server zahtev " + Integer.toString(zahtev.getBrZahteva()) + " pokrenut");
            
            ObjectMessage objMsg = context.createObjectMessage(zahtev);
            objMsg.setIntProperty("id", PODSISTEM_ID);
            
            producer.send(myTopic, objMsg);
            
            System.out.println("Poslat zahtev " + Integer.toString(zahtev.getBrZahteva()) + " podsistemu2");
            
            ObjectMessage objMsgRec = (ObjectMessage)consumer.receive();
            Odgovor odgovor = (Odgovor)objMsgRec.getObject();
            System.out.println("Primio odgovor od podsistema 2");    
            
            if(context != null)
            {
                consumer.close();
                context.close();
                context = null;
            }
            
            if(odgovor.getObjekat() == null)
                return Response.status(Response.Status.OK).entity(odgovor.getPoruka()).build();
            else 
                return Response.status(Response.Status.OK).entity(odgovor.getObjekat()).build();
        } catch (JMSException ex) {
            Logger.getLogger(podsistem1Endpoints.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("EXCEPTION").build();
    }
    
    //tesiraj bez ove anotacije
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @POST
    @Path("/zahtev5")
    public Response kreirajKategoriju(@QueryParam("nazivKat") String nazivKat, @QueryParam("nazivNadKat") String nazivNadKat){
        Zahtev zahtev = new Zahtev();
        zahtev.postaviBrZahteva(KREIRAJ_KATEGORIJU);
        zahtev.dodajParam(nazivKat);
        if(nazivNadKat.equals(""))
            zahtev.dodajParam(null);
        else 
            zahtev.dodajParam(nazivNadKat);
        return posaljiZahtev(zahtev);
    }
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @POST
    @Path("/zahtev6")
    public Response kreirajArtikl(@QueryParam("idKor") int idKor, @QueryParam("nazivArt") String nazivArt, 
            @QueryParam("opis") String opis, @QueryParam("cena") double cena, @QueryParam("popust") double popust,
            @QueryParam("nazivKat") String nazivKat){
        Zahtev zahtev = new Zahtev();
        zahtev.postaviBrZahteva(KREIRAJ_ARTIKL);
        zahtev.dodajParam(idKor);
        zahtev.dodajParam(nazivArt);
        zahtev.dodajParam(opis);
        zahtev.dodajParam(cena);
        zahtev.dodajParam(popust);
        zahtev.dodajParam(nazivKat);
        return posaljiZahtev(zahtev);
    }
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @POST
    @Path("/zahtev7")
    public Response promemeniCenu(@QueryParam("idKor") int idKor, @QueryParam("nazivArt") String nazivArt, 
           @QueryParam("novaCena") double novaCena){
        Zahtev zahtev = new Zahtev();
        zahtev.postaviBrZahteva(MENJAJ_CENU);
        zahtev.dodajParam(idKor);
        zahtev.dodajParam(nazivArt);
        zahtev.dodajParam(novaCena);
        return posaljiZahtev(zahtev);
    }
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @POST
    @Path("/zahtev8")
    public Response postaviPopust(@QueryParam("idKor") int idKor, @QueryParam("nazivArt") String nazivArt, 
           @QueryParam("popust") double popust){
        Zahtev zahtev = new Zahtev();
        zahtev.postaviBrZahteva(POSTAVI_POPUST);
        zahtev.dodajParam(idKor);
        zahtev.dodajParam(nazivArt);
        zahtev.dodajParam(popust);
        return posaljiZahtev(zahtev);
    }
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @POST
    @Path("/zahtev9")
    public Response dodajArtikleKorpa(@QueryParam("idKor") int idKor, @QueryParam("idArt") int idArt, 
           @QueryParam("brArt") int brArt){
        Zahtev zahtev = new Zahtev();
        zahtev.postaviBrZahteva(DODAJ_ARTIKL_KORPA);
        zahtev.dodajParam(idKor);
        zahtev.dodajParam(idArt);
        zahtev.dodajParam(brArt);
        return posaljiZahtev(zahtev);
    }
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @POST
    @Path("/zahtev10")
    public Response izbaciArtikleKorpa(@QueryParam("idKor") int idKor, @QueryParam("idArt") int idArt, 
           @QueryParam("brArt") int brArt){
        Zahtev zahtev = new Zahtev();
        zahtev.postaviBrZahteva(BRISI_ARTIKL_KORPA);
        zahtev.dodajParam(idKor);
        zahtev.dodajParam(idArt);
        zahtev.dodajParam(brArt);
        return posaljiZahtev(zahtev);
    }
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @GET
    @Path("/zahtev14")
    public Response getSveKategorije(){
        Zahtev zahtev = new Zahtev();
        zahtev.postaviBrZahteva(SVE_KATEGORIJE);
        return posaljiZahtev(zahtev);
    }
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @GET
    @Path("/zahtev15")
    public Response getSviArtikliKorisnik(@QueryParam("idKor") int idKor){
        Zahtev zahtev = new Zahtev();
        zahtev.postaviBrZahteva(SVI_ARTIKLI_KORISNIK);
        zahtev.dodajParam(idKor);
        return posaljiZahtev(zahtev);
    }
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @GET
    @Path("/zahtev16")
    public Response getKorisnikKorpa(@QueryParam("idKor") int idKor){
        Zahtev zahtev = new Zahtev();
        zahtev.postaviBrZahteva(KORISNIK_KORPA);
        zahtev.dodajParam(idKor);
        return posaljiZahtev(zahtev);
    }
    
}
