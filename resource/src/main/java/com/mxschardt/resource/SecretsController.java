package com.mxschardt.resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// Базовый rest контроллер для выдачи информации, скрытой за аутентификацией.
@RestController
@RequestMapping(value = "/secrets")
public class SecretsController {

    @GetMapping()
    public String getSecret() {
        return "this is a well-kept secret";
    }

}