package com.mxschardt.client.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;

@Configuration
public class OAuth2LoginConfig {

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        return new InMemoryClientRegistrationRepository(
                this.weweClientAuthorizationCodeRegistration(),
                this.weweClientClientCredentialsRegistration());
    }

    private ClientRegistration weweClientAuthorizationCodeRegistration() {
        return ClientRegistration.withRegistrationId("wewe-client-authorization-code")
                .clientId("wewe-client")
                .clientSecret("{noop}secret")
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .redirectUri("{baseUrl}/login/oauth2/code/wewe-client")
                .scope("openid", "profile", "read")
                .issuerUri("http://wewe-auth:9001")
                .authorizationUri("http://wewe-auth:9001/oauth2/authorize")
                .tokenUri("http://wewe-auth:9001/oauth2/token")
                .jwkSetUri("http://wewe-auth:9001/oauth2/jwks")
                .clientName("Wewe Auth")
                .build();
    }

    private ClientRegistration weweClientClientCredentialsRegistration() {
        return ClientRegistration.withRegistrationId("wewe-client-client-credentials")
                .clientId("wewe-client")
                .clientSecret("{noop}secret")
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .redirectUri("{baseUrl}/login/oauth2/code/wewe-client")
                .scope("openid", "profile", "read")
                .issuerUri("http://wewe-auth:9001")
                .authorizationUri("http://wewe-auth:9001/oauth2/authorize")
                .tokenUri("http://wewe-auth:9001/oauth2/token")
                .jwkSetUri("http://wewe-auth:9001/oauth2/jwks")
                .clientName("Wewe Auth Client Credentials")
                .build();
    }

}