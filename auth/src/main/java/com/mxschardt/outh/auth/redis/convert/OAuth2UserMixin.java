package com.mxschardt.outh.auth.redis.convert;

import com.fasterxml.jackson.annotation.*;

import java.util.Collection;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
abstract class OAuth2UserMixin {

    @JsonCreator
    OAuth2UserMixin(@JsonProperty("username") String username,
                           @JsonProperty("password") String password,
                           @JsonProperty("enabled") boolean enabled,
                           @JsonProperty("accountNonExpired") boolean accountNonExpired,
                           @JsonProperty("credentialsNonExpired") boolean credentialsNonExpired,
                           @JsonProperty("accountNonLocked") boolean accountNonLocked,
                           @JsonProperty("authorities") Collection<String> authoroties) {
    }

}