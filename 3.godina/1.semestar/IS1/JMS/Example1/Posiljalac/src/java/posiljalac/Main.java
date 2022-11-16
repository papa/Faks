package posiljalac;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.ConnectionFactory;
import javax.annotation.Resource;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.Queue;  
import javax.jms.TextMessage;
/*
-kad se brise sa glassfish servera, radi se lazy brisanje, 
pa je potrebno ocistiti red
-moze i java maven, on includuje sve sam
*/
public class Main {
    @Resource(lookup="myConnFactory")
    private static ConnectionFactory connectionFactory; //resource injection, za klase sa main metodom
    
    @Resource(lookup="myQueue")
    private static Queue myQueue;//bitno da je jms.Queue
    
    public static void main(String[] args) {
        try {
            JMSContext context = connectionFactory.createContext();
            JMSProducer producer = context.createProducer();
            
            TextMessage textMsg = context.createTextMessage("text Poruke 2");
            textMsg.setText("text Poruke 2"); //moze i preko konstruktora kao gore
            producer.send(myQueue, textMsg);
        } catch (JMSException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }  
}