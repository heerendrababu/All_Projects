package com.techtez.config;


import java.time.Duration;
 
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.security.web.SecurityFilterChain;
 
@Configuration
public class OAuth2Config {
	
	@Bean
    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http) throws Exception {
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
        return http.build();
    }
 
	@Bean
	public RegisteredClient registeredClient() {
	    return RegisteredClient.withId("client-id") // Ensure this matches
	            .clientId("client-id")             // Ensure this matches
	            .clientSecret("{noop}client-secret") // Use {noop} to indicate plain text secret
	            .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
	            .authorizationGrantType(org.springframework.security.oauth2.core.AuthorizationGrantType.CLIENT_CREDENTIALS)
	            .scope("default")
	            .tokenSettings(tokenSettings())
	            .clientSettings(ClientSettings.builder().build())
	            .build();
	}

 
    @Bean
    public TokenSettings tokenSettings() {
        return TokenSettings.builder()
                .accessTokenTimeToLive(Duration.ofHours(1)) // Token expiry time set to 1 hour
                .build();
    }
    
    @Bean
    public RegisteredClientRepository registeredClientRepository() {
        // Return an InMemoryRegisteredClientRepository to store registered clients
        return new InMemoryRegisteredClientRepository(registeredClient());
    }
 
 
}
 
 




















/*import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.security.web.SecurityFilterChain;
import java.time.Duration;

@Configuration
public class OAuth2Config {

    private static final Logger logger = LoggerFactory.getLogger(OAuth2Config.class);

    @Bean
    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http) throws Exception {
        logger.info("Configuring Authorization Server Security Filter Chain"); // Log configuration
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
        return http.build();
    }

    

    

        @Bean
        public RegisteredClient registeredClient() {
            logger.info("Creating RegisteredClient bean");
            return RegisteredClient.withId("client-id")
                .clientId("client-id")
                .clientSecret("{noop}client-secret")
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .scope("default")
                .tokenSettings(tokenSettings())
                .clientSettings(ClientSettings.builder().build())
                .build();
        }

    

    @Bean
    public TokenSettings tokenSettings() {
        logger.info("Configuring Token Settings with 1-hour expiry"); // Log token settings
        return TokenSettings.builder()
                .accessTokenTimeToLive(Duration.ofHours(1)) // Token expiry time set to 1 hour
                .build();
    }

    @Bean
    public RegisteredClientRepository registeredClientRepository() {
        logger.info("Using InMemoryRegisteredClientRepository to store clients"); // Log repository usage
        return new InMemoryRegisteredClientRepository(registeredClient());
    }
} */
