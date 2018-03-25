package io.fun.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClientController {

//    @Autowired
//    OAuth2ClientContext clientContext;

    @Autowired
    OAuth2RestTemplate oAuth2RestTemplate;

    @GetMapping("/awesome")
    public ResponseEntity<String> doAwesome(OAuth2Authentication auth) {

//        BaseOAuth2ProtectedResourceDetails resourceDetails = new BaseOAuth2ProtectedResourceDetails();
//        resourceDetails.setId("fun-resource");

//        OAuth2RestTemplate oAuth2RestTemplate = new OAuth2RestTemplate(resourceDetails, clientContext);

        return oAuth2RestTemplate.getForEntity("http://localhost:9090/fun", String.class);
    }
}
