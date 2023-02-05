package consumertopic_nd_s;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import javax.jms.Topic;

public class Main {

    @Resource(lookup="jms/__defaultConnectionFactory")
    private static ConnectionFactory connectionFactory;
    
    @Resource(lookup="myTopic")
    private static Topic topic;
    
    public static void main(String[] args) {
        JMSContext context = connectionFactory.createContext();
        JMSConsumer consumer = context.createSharedConsumer(topic, "sub2");
        
        while(true)
        {
            Message msg = consumer.receive();
            if(msg instanceof TextMessage)
            {
                try {
                    TextMessage txtMsg = (TextMessage)msg;
                    System.out.println(txtMsg.getText());
                } catch (JMSException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
