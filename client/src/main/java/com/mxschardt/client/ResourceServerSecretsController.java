package com.mxschardt.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import static org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction.clientRegistrationId;

// Настройка клиентского сервера для работы с сервером ресурсов.
@RestController
public class ResourceServerSecretsController {

    private final WebClient webClient;
    private final String resourceServerBaseUri;

    public ResourceServerSecretsController(WebClient webClient,
                                           @Value("${resourceServer.baseUri}") String messagesBaseUri) {
        this.webClient = webClient;
        this.resourceServerBaseUri = messagesBaseUri;
    }

    @GetMapping("/")
    public String getHello() {
        return "hello";
    }

    @GetMapping("/secrets")
    public String getSecretsWithAuthorizationCode() {
        return webClient
                .get()
                .uri(this.resourceServerBaseUri)
                .attributes(clientRegistrationId("wewe-client-authorization-code"))
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    @GetMapping(value = "/secrets", params = "grant_type=client_credentials")
    public String getSecretsWithClientCredentials() {
        return this.webClient
                .get()
                .uri(this.resourceServerBaseUri)
                .attributes(clientRegistrationId("wewe-client-client-credentials"))
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

}
