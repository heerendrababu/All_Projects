package com.techtez.config;



import org.apache.activemq.artemis.core.server.embedded.EmbeddedActiveMQ;
import org.apache.activemq.artemis.core.server.embedded.EmbeddedActiveMQFactory;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class EmbeddedBrokerConfig {

    private EmbeddedActiveMQ embeddedBroker;

    @PostConstruct
    public void startBroker() throws Exception {
        embeddedBroker = EmbeddedActiveMQFactory.createEmbeddedActiveMQ();
        embeddedBroker.getConfiguration().setPersistenceEnabled(false);  // In-memory broker (no persistence)
        embeddedBroker.start();
        System.out.println("Embedded Artemis Broker started successfully");
    }
}
