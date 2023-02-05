package producertopic;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.TextMessage;
import javax.jms.Topic;


public class Main {
    
    @Resource(lookup="jms/__defaultConnectionFactory")
    private static ConnectionFactory connectionFactory;
    
    @Resource(lookup="myTopic")
    private static Topic topic;
    
    public static void main(String[] args) {
        JMSContext context = connectionFactory.createContext();
        
        JMSProducer producer = context.createProducer();
        
        for(int i = 0;i < 1000;i++)
        {
            try {
                TextMessage txtMsg = context.createTextMessage("Poruka " + i);
                producer.send(topic, txtMsg);
                System.out.println("Poslata poruka " + i);
                Thread.sleep(10000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
