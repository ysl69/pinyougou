package com.pinyougou.shop.security;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.TbSeller;
import com.pinyougou.sellergoods.service.SellerService;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @Author ysl
 * @Date 2019/7/7 20:51
 * @Description:
 **/


public class UserDetailsServiceImpl implements UserDetailsService {

    @Reference
    private SellerService sellerService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //return new User(username,"{noop}123456", AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_SELLER"));

        TbSeller tbSeller = sellerService.findOne(username);

        if (tbSeller == null){
            return null;
        }

        String status = tbSeller.getStatus();

        if (!"1".equals(status)){
            // 未审核 就是 账号不可用
            return null;
        }


        //return new User(username,"{noop}"+tbSeller.getPassword(),AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_SELLER"));
        return new User(username,tbSeller.getPassword(),AuthorityUtils.commaSeparatedStringToAuthorityList(
                "ROLE_SELLER"));
    }
}
