package io.fun.resource;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@Configuration
@EnableResourceServer
public class OauthConfig extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/open")
                    .permitAll()
                .antMatchers("/fun")
//                    .authenticated()
                    .access("#oauth2.hasScope('fun-resource.read')");
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer config) {
//        config.resourceId(null);
        config.resourceId("fun-resource");
    }

}
