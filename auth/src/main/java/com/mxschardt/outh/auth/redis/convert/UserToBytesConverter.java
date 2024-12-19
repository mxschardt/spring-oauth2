package com.mxschardt.outh.auth.redis.convert;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mxschardt.outh.auth.redis.entity.OAuth2User;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.security.jackson2.SecurityJackson2Modules;
import org.springframework.security.oauth2.server.authorization.jackson2.OAuth2AuthorizationServerJackson2Module;

@WritingConverter
public class UserToBytesConverter implements Converter<OAuth2User, byte[]> {

    private final Jackson2JsonRedisSerializer<OAuth2User> serializer;

    public UserToBytesConverter() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModules(
                SecurityJackson2Modules.getModules(UserToBytesConverter.class.getClassLoader()));
        objectMapper.registerModules(new OAuth2AuthorizationServerJackson2Module());
        objectMapper.addMixIn(OAuth2User.class, OAuth2UserMixin.class);
        this.serializer =
                new Jackson2JsonRedisSerializer<>(objectMapper, OAuth2User.class);
    }

    @Override
    public byte[] convert(OAuth2User value) {
        return this.serializer.serialize(value);
    }
}
