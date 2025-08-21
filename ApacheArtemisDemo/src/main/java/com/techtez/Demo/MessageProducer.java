package com.techtez.Demo;

import jakarta.jms.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageProducer {

    @Autowired
    private ConnectionFactory connectionFactory;

    // Sends a message with additional parameters
    public void sendMessage(String message, String filterType, int priority, long expiration, boolean durable) {
        System.out.println("MessageProducer: Preparing to send message with filter: " + filterType);

        Connection connection = null;
        Session session = null;
        jakarta.jms.MessageProducer producer = null;

        try {
            // Create connection and session
            connection = connectionFactory.createConnection();
            connection.start();
            session = connection.createSession(true, Session.SESSION_TRANSACTED);

            // Create destination queue
            Queue destinationQueue = session.createQueue("myQueue");
            producer = session.createProducer(destinationQueue);

            // Create text message
            TextMessage textMessage = session.createTextMessage(message);
            textMessage.setStringProperty("filterType", filterType); // Assign filter

            // ✅ Set correct JMS properties
            textMessage.setJMSPriority(priority);
            producer.setPriority(priority);
            producer.setTimeToLive(expiration);
            producer.setDeliveryMode(durable ? DeliveryMode.PERSISTENT : DeliveryMode.NON_PERSISTENT);

            // Send message
            producer.send(textMessage);
            session.commit();

            System.out.println("Message sent with Filter: " + filterType + ", Priority: " + priority +
                    ", Expiration: " + expiration + ", Durable: " + durable);

        } catch (JMSException e) {
            System.err.println("Error while sending message. Rolling back transaction.");
            try {
                if (session != null) {
                    session.rollback();
                }
            } catch (JMSException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            // Cleanup resources
            try {
                if (producer != null) producer.close();
                if (session != null) session.close();
                if (connection != null) connection.close();
            } catch (JMSException closeEx) {
                closeEx.printStackTrace();
            }
        }
    }
}









/*implicit closing files.
 * 
 * package com.techtez.Demo;

import jakarta.jms.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageProducer {

    @Autowired
    private ConnectionFactory connectionFactory;

    // Sends a message with additional parameters
    public void sendMessage(String message, String filterType, int priority, long expiration, boolean durable) {
        System.out.println("MessageProducer: Preparing to send message with filter: " + filterType);

        try (Connection connection = connectionFactory.createConnection();
             Session session = connection.createSession(true, Session.SESSION_TRANSACTED)) {

            Queue destinationQueue = session.createQueue("myQueue");
            jakarta.jms.MessageProducer producer = session.createProducer(destinationQueue);

            TextMessage textMessage = session.createTextMessage(message);
            textMessage.setStringProperty("filterType", filterType); // Assign filter

            // ✅ Set message-specific properties
//            textMessage.setJMSPriority(priority);
//            textMessage.setJMSExpiration(System.currentTimeMillis() + expiration);
            
         // ✅ Set message-specific properties
            textMessage.setJMSPriority(priority);

            // ✅ Properly set expiration (Time-to-Live)
            textMessage.setTimeToLive(expiration); // Set message lifetime

            producer.send(textMessage, durable ? DeliveryMode.PERSISTENT : DeliveryMode.NON_PERSISTENT, priority, expiration);
            session.commit();

            System.out.println("Message sent with Filter: " + filterType + ", Priority: " + priority +
                    ", Expiration: " + expiration + ", Durable: " + durable);

        } catch (JMSException e) {
            System.err.println("Error while sending message. Rolling back transaction.");
            e.printStackTrace();
        }
    }

}
*/


/*
@RestController
@RequestMapping("/api")
public class MessageProducer {

    @Autowired
    private JmsTemplate jmsTemplate;

    @PostMapping("/send")  // Send message to the queue
    public String sendMessage(@RequestBody String message) {
        jmsTemplate.convertAndSend("myQueue", message);  // This send will be part of a transaction
        System.out.println(message);
        return "Message sent successfully!";
    }

    
}
*/








/*
 * Step 3: jmsTemplate Sends Message to Artemis Queue
📌 What Happens Internally?
1️⃣ The JmsTemplate.convertAndSend("myQueue", message); is executed.
2️⃣ Spring Boot automatically detects Artemis as the JMS provider.
3️⃣ JmsTemplate establishes a JMS connection to the embedded Artemis broker.
4️⃣ A JMS session is created.
5️⃣ The message is wrapped into a JMS TextMessage.
6️⃣ The message is placed in the "myQueue" queue inside Artemis.
 */

/* internally this happens : JmsTemplate
Connection connection = connectionFactory.createConnection();
Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
Queue queue = session.createQueue("myQueue");
MessageProducer producer = session.createProducer(queue);
TextMessage jmsMessage = session.createTextMessage(message);
producer.send(jmsMessage);
*/
//--------------------------------------------------------------------------------------

/*
 * internal structure of @JmsListener
 * 1. Spring Boot Starts and Registers @JmsListener
📌 Execution Order During Application Startup
1️⃣ Spring Boot scans components (@Component, @JmsListener).
2️⃣ It finds @JmsListener(destination = "myQueue") in MessageConsumer.java.
3️⃣ Spring Boot automatically registers a JMS listener for "myQueue".
4️⃣ A JMS Connection is established with the Artemis broker.
5️⃣ A JMS Session is created to listen to "myQueue".
6️⃣ A MessageConsumer object is created internally.

🔹 Internally, this happens in the background:

java
Copy
Edit
Connection connection = connectionFactory.createConnection();
Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
Queue queue = session.createQueue("myQueue");
MessageConsumer consumer = session.createConsumer(queue);
consumer.setMessageListener(new MessageListener() {
    public void onMessage(Message message) {
        // Calls your @JmsListener method
    }
});
connection.start();
✅ At This Point:

The listener is waiting for messages.
 */
//-------------------------------------------------------------------

/*
 * Final Summary: The Complete Flow
1️⃣ You send a POST request in Postman (http://localhost:8080/send).
2️⃣ Spring Boot processes the request and calls MessageProducer.sendMessage().
3️⃣ jmsTemplate sends the message to the Artemis queue (myQueue).
4️⃣ Artemis stores the message and waits for a consumer.
5️⃣ MessageConsumer picks up the message and processes it (@JmsListener).
6️⃣ Message is printed in the console (System.out.println).
7️⃣ Artemis marks the message as processed and removes it from the queue.
 */
