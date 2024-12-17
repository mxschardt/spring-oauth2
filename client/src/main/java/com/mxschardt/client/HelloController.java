package com.mxschardt.client;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// Базовый контроллер для проверки работоспособности сервера.
@RestController
public class HelloController {

    @GetMapping("/")
    public String getHello() {
        return "hello";
    }

    @GetMapping("/secured")
    public String getSecured() {
        return "Secured.";
    }

}
