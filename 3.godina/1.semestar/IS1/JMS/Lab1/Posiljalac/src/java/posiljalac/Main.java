/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package posiljalac;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
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
    
    public static void main(String[] args) {
        JMSContext context = connFactory.createContext();
        JMSProducer producer = context.createProducer();
        
        int ind = 0;
        while(true)
        {
            try {
                ind++;
                TextMessage txtMsg = context.createTextMessage("tekst " + ind);
                producer.send(topic, txtMsg);
                System.out.println("Poslao " + txtMsg.getText());
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            } catch (JMSException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
