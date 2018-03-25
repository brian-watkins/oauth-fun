package io.fun.resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OpenController {

    @GetMapping("/open")
    public String getOpen() {
        return "Hey!!!";
    }
}
