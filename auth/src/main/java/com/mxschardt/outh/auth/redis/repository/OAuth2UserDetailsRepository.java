package com.mxschardt.outh.auth.redis.repository;

import com.mxschardt.outh.auth.redis.entity.OAuth2User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OAuth2UserDetailsRepository extends CrudRepository<OAuth2User, String> {

    // магия...
    OAuth2User findByUsername(String username);

}
