/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package radnik;

import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.Queue;
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
    
    @Resource(lookup="lab2Queue")
    static Queue myQueue;
    
    public static void main(String[] args) {
       
        
        
    }
    
}
