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

@Path("podsistem1")
public class podsistem1Endpoints {
    
    @Resource(lookup="projConnFactory")
    private ConnectionFactory connectionFactory;
    
    @Resource(lookup="topicServer")
    private Topic myTopic;
    
    JMSContext context = null;
    JMSConsumer consumer=null;
    JMSProducer producer = null;
    
    private static final int KREIRAJ_GRAD = 1;
    private static final int KREIRAJ_KORISNIKA = 2;
    private static final int DODAJ_NOVAC = 3;
    private static final int PROMENA_ADRESA_GRAD = 4;
    private static final int SVI_GRADOVI = 12;
    private static final int SVI_KORISNICI = 13;
    private static final int LOGIN = 50;
    private static final int PODSISTEM_ID = 1;
    
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
            
            System.out.println("Poslat zahtev " + Integer.toString(zahtev.getBrZahteva()) + " podsistemu1");
            
            ObjectMessage objMsgRec = (ObjectMessage)consumer.receive();
            Odgovor odgovor = (Odgovor)objMsgRec.getObject();
            System.out.println("Primio odgovor od podsistema 1");    
            
            if(odgovor.getObjekat() == null)
                return Response.status(Response.Status.OK).entity(odgovor.getPoruka()).build();
            else 
                return Response.status(Response.Status.OK).entity(odgovor.getObjekat()).build();
        } catch (JMSException ex) {
            Logger.getLogger(podsistem1Endpoints.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("EXCEPTION").build();
    }
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @POST
    @Path("/zahtev1")
    public Response kreirajGrad(@QueryParam("nazivGrada") String nazivGrada)
    {
        Zahtev zahtev = new Zahtev();
        zahtev.postaviBrZahteva(KREIRAJ_GRAD);
        zahtev.dodajParam(nazivGrada);
        return posaljiZahtev(zahtev);
    }
     
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @POST
    @Path("/zahtev2")
    public Response kreirajKorisnika(@QueryParam("username") String username, @QueryParam("ime") String ime,
            @QueryParam("prezime") String prezime, @QueryParam("sifra") String sifra, @QueryParam("adresa") String adresa,
            @QueryParam("novac") double novac, @QueryParam("nazivGrada") String nazivGrada)
    {
        Zahtev zahtev = new Zahtev();
        zahtev.postaviBrZahteva(KREIRAJ_KORISNIKA);
        zahtev.dodajParam(username);
        zahtev.dodajParam(ime);
        zahtev.dodajParam(prezime);
        zahtev.dodajParam(sifra);
        zahtev.dodajParam(adresa);
        zahtev.dodajParam(novac);
        zahtev.dodajParam(nazivGrada);
        return posaljiZahtev(zahtev);
    }
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @POST
    @Path("/zahtev3")
    public Response dodajNovacKorisniku(@QueryParam("username") String username, @QueryParam("novac") double novac)
    {
        Zahtev zahtev = new Zahtev();
        zahtev.postaviBrZahteva(DODAJ_NOVAC);
        zahtev.dodajParam(username);
        zahtev.dodajParam(novac);
        return posaljiZahtev(zahtev);
    }
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @POST
    @Path("/zahtev4")
    public Response promeniAdresuGradKorisniku(@QueryParam("username") String username, @QueryParam("adresa")String adresa,
            @QueryParam("nazivGrada") String nazivGrada)
    {
        Zahtev zahtev = new Zahtev();
        zahtev.postaviBrZahteva(PROMENA_ADRESA_GRAD);
        zahtev.dodajParam(username);
        zahtev.dodajParam(adresa);
        zahtev.dodajParam(nazivGrada);
        return posaljiZahtev(zahtev);
    }
    
    //tesiraj bez ove anotacije
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @GET
    @Path("/zahtev12")
    public Response dohvatiSveGradove(){
        Zahtev zahtev = new Zahtev();
        zahtev.postaviBrZahteva(SVI_GRADOVI);
        return posaljiZahtev(zahtev);
    }
    
    //tesiraj bez ove anotacije
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @GET
    @Path("/zahtev13")
    public Response getSviKorisnici()
    {
        Zahtev zahtev = new Zahtev();
        zahtev.postaviBrZahteva(SVI_KORISNICI);
        return posaljiZahtev(zahtev);
    }
    
    //tesiraj bez ove anotacije
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @GET
    @Path("/zahtevLogin")
    public Response login(@QueryParam("username")String username, @QueryParam("sifra") String sifra)
    {
        Zahtev zahtev = new Zahtev();
        zahtev.postaviBrZahteva(LOGIN);
        zahtev.dodajParam(username);
        zahtev.dodajParam(sifra);
        return posaljiZahtev(zahtev);
    }
}
