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

@Path("podsistem3")
public class podsistem3Endpoints {
    
    @Resource(lookup="projConnFactory")
    private ConnectionFactory connectionFactory;
    
    @Resource(lookup="topicServer")
    private Topic myTopic;
    
    private static final int PLACANJE = 11;
    private static final int KORISNIK_NARUDZBINE = 17;
    private static final int SVE_NARUDZBINE = 18;
    private static final int SVE_TRANSKACIJE = 19;
    private static final int PODSISTEM_ID = 3;
    
    private Response posaljiZahtev(Zahtev zahtev)
    {
        try {
            JMSContext context = connectionFactory.createContext();
            JMSConsumer consumer=context.createConsumer(myTopic, "id=0");
            JMSProducer producer = context.createProducer();
            
            System.out.println("Server zahtev " + Integer.toString(zahtev.getBrZahteva()) + " pokrenut");
            
            ObjectMessage objMsg = context.createObjectMessage(zahtev);
            objMsg.setIntProperty("id", PODSISTEM_ID);
            
            producer.send(myTopic, objMsg);
            
            System.out.println("Poslat zahtev " + Integer.toString(zahtev.getBrZahteva()) + " podsistemu3");
            
            ObjectMessage objMsgRec = (ObjectMessage)consumer.receive();
            Odgovor odgovor = (Odgovor)objMsgRec.getObject();
            System.out.println("Primio odgovor od podsistema 3");    
            
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
    @Path("/zahtev11")
    public Response placanje(@QueryParam("idKor") int idKor){
        Zahtev zahtev = new Zahtev();
        zahtev.postaviBrZahteva(PLACANJE);
        zahtev.dodajParam(idKor);
        return posaljiZahtev(zahtev);
    }
    
    //tesiraj bez ove anotacije
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @GET
    @Path("/zahtev17")
    public Response getNarudzbineKorisnik(@QueryParam("idKor") int idKor)
    {
        Zahtev zahtev = new Zahtev();
        zahtev.postaviBrZahteva(KORISNIK_NARUDZBINE);
        zahtev.dodajParam(idKor);
        return posaljiZahtev(zahtev);
    }
    
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @GET
    @Path("/zahtev18")
    public Response getSveNarudzbine()
    {
        Zahtev zahtev = new Zahtev();
        zahtev.postaviBrZahteva(SVE_NARUDZBINE);
        return posaljiZahtev(zahtev);
    }
     
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    @GET
    @Path("/zahtev19")
    public Response getSveTranskacije()
    {
        Zahtev zahtev = new Zahtev();
        zahtev.postaviBrZahteva(SVE_TRANSKACIJE);
        return posaljiZahtev(zahtev);
    }
}
