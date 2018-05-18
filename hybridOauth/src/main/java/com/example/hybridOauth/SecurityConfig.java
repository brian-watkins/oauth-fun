package com.example.hybridOauth;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;
import org.springframework.security.web.util.matcher.RequestHeaderRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

@Configuration
public class SecurityConfig {

    @Configuration
    @Order(1000)
    @EnableOAuth2Sso
    public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

        @Override
        public void configure(final HttpSecurity http) throws Exception {
            RequestMatcher nonAuthorization = new NegatedRequestMatcher(authorizationHeaderMatcher());
            http.requestMatcher(nonAuthorization).authorizeRequests()
                    .anyRequest().authenticated();
        }

    }


    @Configuration
    @EnableResourceServer
    public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

        @Override
        public void configure(final HttpSecurity http) throws Exception {

            http.requestMatcher(authorizationHeaderMatcher())
                    .authorizeRequests()
                    .anyRequest().authenticated();
        }

        @Override
        public void configure(ResourceServerSecurityConfigurer config) {
            config.resourceId(null);
        }

    }

    private RequestHeaderRequestMatcher authorizationHeaderMatcher() {
        return new RequestHeaderRequestMatcher("Authorization");
    }
}
