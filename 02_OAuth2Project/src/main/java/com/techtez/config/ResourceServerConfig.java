package com.techtez.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
 
@Configuration
public class ResourceServerConfig 
{
	
	
	@Bean
    protected SecurityFilterChain resourceServerSecurityFilterChain(HttpSecurity http) throws Exception 
	{
        http
            .authorizeHttpRequests(authorize -> authorize
                .anyRequest().authenticated()
            )
            //JWT Generation: AuthorizationServerConfig (TokenSettings).
            //JWT Validation: ResourceServerConfig (oauth2ResourceServer()).
            .oauth2ResourceServer(oauth2 -> oauth2
                .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter()))
            );
        return http.build();
    }
 
    private JwtAuthenticationConverter jwtAuthenticationConverter() {
        return new JwtAuthenticationConverter();
    }
 
}
































/*import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ResourceServerConfig
{

    private static final Logger logger = LoggerFactory.getLogger(ResourceServerConfig.class);

    @Bean
    public SecurityFilterChain resourceServerSecurityFilterChain(HttpSecurity http) throws Exception {
        logger.info("Configuring Resource Server Security Filter Chain"); // Log resource server config
        http
            .authorizeHttpRequests(authorize -> authorize
                .anyRequest().authenticated()
            )
            .oauth2ResourceServer(oauth2 -> oauth2
                .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter()))
            );
        return http.build();
    }

    private JwtAuthenticationConverter jwtAuthenticationConverter() {
        logger.info("Configuring JwtAuthenticationConverter"); // Log JWT converter config
        return new JwtAuthenticationConverter();
    }
} */
