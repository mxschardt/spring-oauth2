
package com.mxschardt.outh.auth.config;

import com.mxschardt.outh.auth.redis.convert.*;
import com.mxschardt.outh.auth.redis.repository.OAuth2AuthorizationGrantAuthorizationRepository;
import com.mxschardt.outh.auth.redis.repository.OAuth2UserConsentRepository;
import com.mxschardt.outh.auth.redis.service.RedisOAuth2AuthorizationConsentService;
import com.mxschardt.outh.auth.redis.service.RedisOAuth2AuthorizationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.convert.RedisCustomConversions;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;

import java.util.Arrays;

// Конфигурация подключения к Redis.
@EnableRedisRepositories("com.mxschardt.oauth.auth.repository")
@Configuration(proxyBeanMethods = false)
public class RedisConfig {

    @Value("${redis.host}")
    private String redisHost;

    @Value("${redis.port}")
    private int redisPort;

    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration(redisHost, redisPort);

        return new LettuceConnectionFactory(configuration);
    }

    @Bean
    public RedisCustomConversions redisCustomConversions() {
        return new RedisCustomConversions(Arrays.asList(
                new BytesToClaimsHolderConverter(),
                new BytesToOAuth2AuthorizationRequestConverter(),
                new BytesToUserConverter(),
                new BytesToUsernamePasswordAuthenticationTokenConverter(),
                new ClaimsHolderToBytesConverter(),
                new OAuth2AuthorizationRequestToBytesConverter(),
                new UserToBytesConverter(),
                new UsernamePasswordAuthenticationTokenToBytesConverter()
        ));
    }

    @Bean
    public RedisOAuth2AuthorizationService authorizationService(RegisteredClientRepository registeredClientRepository,
                                                                OAuth2AuthorizationGrantAuthorizationRepository authorizationGrantAuthorizationRepository) {
        return new RedisOAuth2AuthorizationService(registeredClientRepository,
                authorizationGrantAuthorizationRepository);
    }

    @Bean
    public RedisOAuth2AuthorizationConsentService authorizationConsentService(
            OAuth2UserConsentRepository userConsentRepository) {
        return new RedisOAuth2AuthorizationConsentService(userConsentRepository);
    }

}