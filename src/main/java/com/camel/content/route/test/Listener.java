package com.camel.content.route.test;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

public class Listener implements MessageListener {

    private String queue;

    public Listener(String queue) {
        this.queue = queue;
    }

    public void onMessage(Message message) {
        try {
            System.out.println(queue + " id:"
                    + ((ObjectMessage) message).getObject());
            
            System.out.println("Secret Message: "
                    + ((ObjectMessage) message).getStringProperty("Queue"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
