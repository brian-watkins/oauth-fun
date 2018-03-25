package io.fun.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;

@Configuration
public class ResourceConfig {

    @Bean
    public OAuth2RestTemplate oAuth2RestTemplate() {
        ClientCredentialsResourceDetails resourceDetails = new ClientCredentialsResourceDetails();
        resourceDetails.setId("fun-resource");
        resourceDetails.setClientId("funClient");
        resourceDetails.setClientSecret("funSecret");
        resourceDetails.setAccessTokenUri("http://localhost:8080/uaa/oauth/token");

        return new OAuth2RestTemplate(resourceDetails);
    }
}
