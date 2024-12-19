package com.mxschardt.outh.auth.config;

import com.mxschardt.outh.auth.redis.repository.OAuth2RegisteredClientRepository;
import com.mxschardt.outh.auth.redis.repository.OAuth2UserDetailsRepository;
import com.mxschardt.outh.auth.redis.repository.RedisRegisteredClientRepository;
import com.mxschardt.outh.auth.redis.service.RedisUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /*
     *
     * Используем стандартную конфигурацию сервера аутентификации
     * и изменяем только то, что нам нужно.
     *
     * */

    // Создаем тестовых пользователей. Загружаем их в базу.
    @Bean
    public UserDetailsService userDetailsService(OAuth2UserDetailsRepository userDetailsRepository) {

        RedisUserDetailsService redisUserDetailsManager =
                new RedisUserDetailsService(userDetailsRepository);

        // Немного нарушаем изоляцию, чтобы не пользоваться RedisUserDetailsService.loadByUserName()
        // и не обрабатывать исключение.
        if (userDetailsRepository.findByUsername("user") == null) {
            UserDetails userDetails = User.withUsername("user")
                    .password("password")
                    .roles("USER")
                    .build();
            redisUserDetailsManager.createUser(userDetails);
        }

        if (userDetailsRepository.findByUsername("admin") == null) {
            UserDetails adminDetails = User.withUsername("admin")
                    .password("password")
                    .roles("ADMIN")
                    .build();

            redisUserDetailsManager.createUser(adminDetails);
        }

        return redisUserDetailsManager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    // Настраиваем основного клиентов.
    @Bean
    public RegisteredClientRepository registeredClientRepository(OAuth2RegisteredClientRepository registeredClientRepository) {
        RedisRegisteredClientRepository redisRegisteredClientRepository =
                new RedisRegisteredClientRepository(registeredClientRepository);

        // Не знаю куда можно перенести инициализацию БД, поэтому будет тут

        // wewe-client.
        String clientId = "e3e34100-c0fd-4a5d-a1be-1483ed971ebf";
        if (registeredClientRepository.findByClientId(clientId) == null) {
            RegisteredClient weweClient = RegisteredClient.withId(clientId)
                    .clientId("wewe-client")
                    .clientSecret("{noop}secret")
                    .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                    .authorizationGrantTypes(auth -> {
                        auth.add(AuthorizationGrantType.AUTHORIZATION_CODE);
                        auth.add(AuthorizationGrantType.REFRESH_TOKEN);
                        auth.add(AuthorizationGrantType.CLIENT_CREDENTIALS);
                    })
                    .redirectUri("http://wewe-client:8080/login/oauth2/code/wewe-client")
                    .scope(OidcScopes.OPENID)
                    .scope(OidcScopes.PROFILE)
                    .scope("read")
                    .clientSettings(
                            ClientSettings.builder()
                                    .requireAuthorizationConsent(true)
                                    .requireProofKey(true)
                                    .build())
                    .build();

            redisRegisteredClientRepository.save(weweClient);
        }

        // Не существующий клиент для проверки БД.
        String testClientId = "e3e34100-c0fd-5a5d-a1be-1483ed971ebf";
        if (registeredClientRepository.findByClientId(testClientId) == null) {
            RegisteredClient testClient = RegisteredClient.withId(testClientId)
                    .clientId("test-client")
                    .clientSecret("{noop}secret")
                    .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                    .authorizationGrantTypes(auth -> {
                        auth.add(AuthorizationGrantType.AUTHORIZATION_CODE);
                        auth.add(AuthorizationGrantType.REFRESH_TOKEN);
                        auth.add(AuthorizationGrantType.CLIENT_CREDENTIALS);
                    })
                    .redirectUri("http://localhost:8080/login/oauth2/code/wewe-client")
                    .scope(OidcScopes.OPENID)
                    .scope(OidcScopes.PROFILE)
                    .scope("read")
                    .clientSettings(
                            ClientSettings.builder()
                                    .requireAuthorizationConsent(true)
                                    .requireProofKey(true)
                                    .build())
                    .build();

            redisRegisteredClientRepository.save(testClient);
        }

        return redisRegisteredClientRepository;
    }

    /*
    * Можно было бы загружать JWK из базы, а не генерировать при каждом запуске,
    * но все работает и без этого.
    */

//    @Bean
//    public JWKSource<SecurityContext> jwkSource() {
//    }
//
//    public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
//        return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
//    }

}