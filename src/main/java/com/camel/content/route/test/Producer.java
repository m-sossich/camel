package com.camel.content.route.test;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;

public class Producer {

    private static String brokerURL = "tcp://localhost:61616";
    private static transient ConnectionFactory factory;
    private transient Connection connection;
    private transient Session session;
    private transient MessageProducer producer;

    private static int id = 1000000;

    public Producer() throws JMSException {
        factory = new ActiveMQConnectionFactory(brokerURL);
        connection = factory.createConnection();
        connection.start();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        producer = session.createProducer(null);
    }

    public void close() throws JMSException {
        if (connection != null) {
            connection.close();
        }
    }

    public static void main(String[] args) throws JMSException {
        Producer producer = new Producer();
        producer.sendCamelTest();
    }

     public void sendCamelTest() throws JMSException {
        Destination destination = session.createQueue("Test.camel");
        
        Message message = session.createObjectMessage(id++);
        message.setStringProperty("queueId", "1");
        message.setStringProperty("Queue", "Se supone que era para la cola 1");
     
        System.out.println("Sending: id: "
                + ((ObjectMessage) message).getObject() + " on queue: "
                + destination);
        producer.send(destination, message);
        
        message = session.createObjectMessage(id++);
        message.setStringProperty("queueId", "2");
        message.setStringProperty("Queue", "Se supone que era para la cola 2");
     
        System.out.println("Sending: id: "
                + ((ObjectMessage) message).getObject() + " on queue: "
                + destination);
        producer.send(destination, message);
    }

}
