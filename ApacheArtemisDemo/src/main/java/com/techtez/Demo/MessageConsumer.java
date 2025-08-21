package com.techtez.Demo;

import jakarta.jms.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class MessageConsumer {

    @Autowired
    private ConnectionFactory factory;

    private final ExecutorService executor = Executors.newFixedThreadPool(3);
    private final List<String> receivedMessages = new CopyOnWriteArrayList<>();

    public void startConsumers() {
        executor.submit(new ConsumerThread("ABCD"));
        executor.submit(new ConsumerThread("XYZ"));
        executor.submit(new ConsumerThread("PQR"));
    }

    public List<String> getReceivedMessagesList() {
        return receivedMessages;
    }

    private class ConsumerThread implements Runnable {
        private String filter;

        public ConsumerThread(String filter) {
            this.filter = filter;
        }

        @Override
        public void run() {
            System.out.println("Starting consumer thread for filter: " + filter);
            try (Connection conn = factory.createConnection();
                 Session sess = conn.createSession(true, Session.CLIENT_ACKNOWLEDGE)) {

                Queue targetQueue = sess.createQueue("myQueue");
                String selector = "filterType = '" + filter + "'";
                jakarta.jms.MessageConsumer consumer = sess.createConsumer(targetQueue, selector);

                conn.start();

                while (true) {
                    Message incomingMsg = consumer.receive();
                    if (incomingMsg instanceof TextMessage) {
                        TextMessage textMessage = (TextMessage) incomingMsg;
                        String messageData = textMessage.getText();
                        int priority = textMessage.getJMSPriority();
                        long expiration = textMessage.getJMSExpiration();  // Corrected property
                        boolean durable = textMessage.getJMSDeliveryMode() == DeliveryMode.PERSISTENT;

                        // 🛑 Strict expiration check using actual expiration time
                        long currentTime = System.currentTimeMillis();
                        if (expiration > 0 && currentTime > expiration) {
                            System.out.println("Skipping expired message: " + messageData);
                            continue; // Skip processing
                        }

                        // Convert expiration timestamp to human-readable format
                        String formattedExpiration = (expiration > 0)
                                ? new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(expiration))
                                : "No Expiry";

                        // Print in a readable format
                        String output = "Received: " + messageData +
                                        " | Priority: " + priority +
                                        " | Expiration: " + formattedExpiration +
                                        " | Durable: " + (durable ? "YES" : "NO");
                        receivedMessages.add(output); // Store message
                        System.out.println(output);

                        textMessage.acknowledge(); // Manually acknowledge only valid messages
                    }
                }
            } catch (JMSException ex) {
                ex.printStackTrace();
            }
        }
    }
   
}


















/*package com.techtez.Demo;

import jakarta.jms.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class MessageConsumer {

    @Autowired
    private ConnectionFactory factory;

    
    // Executors: Efficient, reuses threads    
//  private final ExecutorService executor = Executors.newCachedThreadPool(); // Can scale threads as needed

    private final ExecutorService executor = Executors.newFixedThreadPool(3); // Thread pool with 3 threads
    private final List<String> receivedMessages = new CopyOnWriteArrayList<>(); // Store received messages

    public void startConsumers() {
        executor.submit(new ConsumerThread("ABCD")); // Thread1
        executor.submit(new ConsumerThread("XYZ"));
        executor.submit(new ConsumerThread("PQR"));
    }

    public String getReceivedMessages() {
        return String.join("\n", receivedMessages);
    }

    // ConsumerThread implements Runnable
    private class ConsumerThread implements Runnable {
        private String filter;

        public ConsumerThread(String filter) {
            this.filter = filter;
        }

        @Override
        public void run() {
            System.out.println("Starting consumer thread for filter: " + filter);
            try (Connection conn = factory.createConnection();
                 Session sess = conn.createSession(true, Session.SESSION_TRANSACTED)) { // SESSION_TRANSACTED

                Queue targetQueue = sess.createQueue("myQueue");
                String selector = "filterType = '" + filter + "'"; // Filter condition
                jakarta.jms.MessageConsumer consumer = sess.createConsumer(targetQueue, selector);

                conn.start();

                while (true) {
                    Message incomingMsg = consumer.receive();
                    if (incomingMsg instanceof TextMessage) {
                        String messageData = ((TextMessage) incomingMsg).getText();
                        receivedMessages.add("Thread [" + filter + "] received: " + messageData); // Store message
                        System.out.println("Thread [" + filter + "] received: " + messageData);
                        sess.commit(); // Consuming & Acknowledge the message
                    }
                }

            } catch (JMSException ex) {
                ex.printStackTrace();
            }
        }
    }
}
*/