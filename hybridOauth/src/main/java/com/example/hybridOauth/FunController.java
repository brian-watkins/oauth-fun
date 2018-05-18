package com.example.hybridOauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FunController {

    @Autowired
    OAuth2ClientContext clientContext;

    @GetMapping("/fun")
    public String getFun() {
        return "Bowling: " + clientContext.getAccessToken();
    }
}
