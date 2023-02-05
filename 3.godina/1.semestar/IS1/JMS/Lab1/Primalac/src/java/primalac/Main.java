/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package primalac;

import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.TextMessage;
import javax.jms.Topic;

/**
 *
 * @author Jelena
 */
public class Main {

    @Resource(lookup = "myConnFactory2")
    static ConnectionFactory connFactory;
    
    @Resource(lookup = "vTopic2")
    static Topic topic;
    
    public static void main(String[] args) throws JMSException {
       String id = args[0];
       JMSContext context = connFactory.createContext();
       //context.setClientID(id);
       JMSConsumer consumer = context.createSharedDurableConsumer(topic, "sub2");
       while(true)
       {
           try {
               TextMessage txtMsg = (TextMessage)consumer.receive();
               System.out.println("Primio " + txtMsg.getText());
               Thread.sleep(2000);
           } catch (InterruptedException ex) {
               Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
           } catch (JMSException ex) {
               Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
           }
       }
    }
    
}
