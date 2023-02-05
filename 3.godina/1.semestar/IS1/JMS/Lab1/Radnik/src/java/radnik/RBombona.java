/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package radnik;

import slatkis.Slatkis;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;

/**
 *
 * @author Jelena
 */
public class RBombona extends Main{
    
    public static void main(String[] args) {
        JMSContext context = connFactory.createContext();
        context.setClientID("2");
        JMSConsumer consumer = context.createDurableConsumer(myTopic,"sub1","Tip=" + 2, false);
        JMSProducer producer = context.createProducer();
        while(true)
        {
            try {
                Message msg = consumer.receive();
                TextMessage txtMsg = (TextMessage)msg;
                System.out.println("Primio " + txtMsg.getText());
                
                Slatkis slatkis = new Slatkis(txtMsg.getText()); 
                ObjectMessage objMsg = context.createObjectMessage(slatkis);
                producer.send(myQueue, objMsg);
                System.out.println("Poslao na red " + txtMsg.getText());
            } catch (JMSException ex) {
                Logger.getLogger(RBombona.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
