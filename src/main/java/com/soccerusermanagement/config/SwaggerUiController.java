package com.soccerusermanagement.config;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class SwaggerUiController {

    @CrossOrigin(origins = "*")
    @GetMapping({ "/v3/api-docs/swagger-config", "/swagger-ui/swagger-config", "/swagger-config" })
    public Map<String, Object> swaggerConfig() {
        Map<String, Object> cfg = new HashMap<>();
        cfg.put("url", "/v3/api-docs");
        cfg.put("validatorUrl", "");
        cfg.put("oauth2RedirectUrl", "/swagger-ui/oauth2-redirect.html");
        return cfg;
    }
}
