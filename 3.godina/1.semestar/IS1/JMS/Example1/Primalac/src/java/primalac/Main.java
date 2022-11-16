/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package primalac;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.ConnectionFactory;
import javax.annotation.Resource;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.TextMessage;

public class Main {
     @Resource(lookup="myConnFactory")
    private static ConnectionFactory connectionFactory; //resource injection, za klase sa main metodom
    
    @Resource(lookup="myQueue")
    private static Queue myQueue;//bitno da je jms.Queue
    
    public static void main(String[] args) {
       
        JMSContext context = connectionFactory.createContext();
        JMSConsumer consumer = context.createConsumer(myQueue);
        
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
