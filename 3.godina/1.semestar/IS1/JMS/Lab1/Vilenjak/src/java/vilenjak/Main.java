/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vilenjak;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.TextMessage;
import slatkis.Slatkis;
/**
 *
 * @author Jelena
 */
public class Main {

    @Resource(lookup="labConnFactory")
    static ConnectionFactory connFactory;
    
    @Resource(lookup="lab2Queue")
    static Queue myQueue;
    public static void main(String[] args) {
       JMSContext context = connFactory.createContext();
       JMSConsumer consumer = context.createConsumer(myQueue);    
       while(true)
       {
           try {
               ObjectMessage objMsg = (ObjectMessage)consumer.receiveNoWait();
               if(objMsg == null)
               { 
                    Thread.sleep(10000);
                    continue;
               }
               Slatkis slatkis = (Slatkis)objMsg.getObject();
               System.out.println("Primio " + slatkis.get());
           } catch (JMSException ex) {
               Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
           }
           catch (InterruptedException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
           }
       } 
    }
    
}
