package io.fun.resource;

import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
public class ResourceController {

    @GetMapping("/fun")
    public Map<String, Object> getFun(OAuth2Authentication auth, Principal principal) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("token", auth.getDetails());
        map.put("principal", principal.toString());

        return map;
    }

}
