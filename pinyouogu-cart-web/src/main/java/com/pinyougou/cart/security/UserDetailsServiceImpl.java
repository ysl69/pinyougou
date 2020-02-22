package com.pinyougou.cart.security;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @Author ysl
 * @Date 2020/2/22 22:11
 * @Description: 认证类
 **/


public class UserDetailsServiceImpl implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new User(username,"", AuthorityUtils.commaSeparatedStringToAuthorityList("RILE_USER"));
    }
}
