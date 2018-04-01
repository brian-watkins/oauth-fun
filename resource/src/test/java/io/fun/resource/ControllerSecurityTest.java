package io.fun.resource;

import com.github.tomakehurst.wiremock.common.ConsoleNotifier;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(properties = {
        "security.oauth2.resource.tokenInfoUri=http://localhost:9999/check_token",
        "security.oauth2.resource.jwt.keyUri="
})
public class ControllerSecurityTest {

    @ClassRule
    public static WireMockRule wireMockRule = new WireMockRule(options()
            .port(9999));

    @Autowired
    WebApplicationContext webApplicationContext;

    MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();
    }

    @Test
    public void itAllowsTokenWithFunResourceReadScope() throws Exception {
        stubFor(post(urlEqualTo("/check_token"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"active\":true,\"user_name\":\"bwatkins\",\"user_email\":\"bwatkins@fun.com\",\"aud\":[\"fun-resource\",\"super-resource\"],\"scope\":[ \"super-resource.write\", \"fun-resource.read\" ]}")));

        mockMvc.perform(get("/fun")
                .header("Authorization", "Bearer good-token"))
                .andExpect(status().isOk());
    }

    @Test
    public void itDeniesActiveTokenWithoutReadScore() throws Exception {
        stubFor(post(urlEqualTo("/check_token"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"active\":true,\"user_name\":\"bwatkins\",\"user_email\":\"bwatkins@fun.com\",\"aud\":[\"fun-resource\",\"super-resource\"],\"scope\":[ \"super-resource.write\", \"fun-resource.write\" ]}")));

        mockMvc.perform(get("/fun")
                .header("Authorization", "Bearer bad-token"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void itDeniesActiveTokenWithoutProperAudience() throws Exception {
        stubFor(post(urlEqualTo("/check_token"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"active\":true,\"user_name\":\"bwatkins\",\"user_email\":\"bwatkins@fun.com\",\"aud\":[\"super-resource\"],\"scope\":[ \"super-resource.write\" ]}")));

        mockMvc.perform(get("/fun")
                .header("Authorization", "Bearer bad-token"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void itAllowsAnyoneToAccessTheOpenController() throws Exception {
        mockMvc.perform(get("/open"))
                .andExpect(status().isOk());
    }

}