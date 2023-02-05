/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package menadzer;

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

    @Resource(lookup="labConnFactory")
    static ConnectionFactory connFactory;
    
    @Resource(lookup="labTopic")
    static Topic myTopic;
    
    public static void main(String[] args) throws InterruptedException {
        JMSContext context = connFactory.createContext();
        JMSProducer producer = context.createProducer();
        
        while(true)
        {
            try {
                TextMessage txtMsg = context.createTextMessage();
                int x = (int)(Math.random()*5);
                txtMsg.setText("Ratluk " + x);
                txtMsg.setIntProperty("Tip", 0);
                producer.send(myTopic, txtMsg);
                System.out.println("Poslao " + txtMsg.getText());
                
                txtMsg = context.createTextMessage();
                x = (int)(Math.random()*5);
                txtMsg.setText("Cokoladica " + x);
                txtMsg.setIntProperty("Tip", 1);
                producer.send(myTopic, txtMsg);
                System.out.println("Poslao " + txtMsg.getText());
                
                txtMsg = context.createTextMessage();
                x = (int)(Math.random()*5);
                txtMsg.setText("Bombona " + x);
                txtMsg.setIntProperty("Tip", 2);
                producer.send(myTopic, txtMsg);
                System.out.println("Poslao " + txtMsg.getText());
                
                Thread.sleep(20000);
            } catch (JMSException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
}
