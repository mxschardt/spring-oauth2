package com.mxschardt.outh.auth.redis.service;

import com.mxschardt.outh.auth.redis.entity.OAuth2User;
import com.mxschardt.outh.auth.redis.repository.OAuth2UserDetailsRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.Assert;

public class RedisUserDetailsService implements UserDetailsService {

    private final OAuth2UserDetailsRepository userRepository;

    public RedisUserDetailsService(OAuth2UserDetailsRepository userRepository) {
        Assert.notNull(userRepository, "registeredClientRepository cannot be null");
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        OAuth2User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        } else {
            return ModelMapper.convertUserDetails(user);
        }
    }

    public void createUser(UserDetails user) {
        Assert.notNull(user, "user cannot be null");
        OAuth2User oauth2RegisteredClient = ModelMapper.convertOAuth2UserDetails(user);
        this.userRepository.save(oauth2RegisteredClient);
    }

}
