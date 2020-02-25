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
 * @Date 2019/6/27 11:21
 * @Description:
 **/


public class UserDetailsServiceImpl implements UserDetailsService {

    @Reference
    private SellerService sellerService;



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //return new User(username,"{noop}123456", AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_SELLER"));


        //System.out.println("UserDetailsServiceImpl======经过了这个方法======");


        //1.从数据库中获取用户的信息(用户的密码)
        //1.1 先根据页面传递过来的用户名（seller_id） 查询用户对象
        TbSeller tbSeller = sellerService.findOne(username);

        //System.out.println(tbSeller.getStatus());


        //1.2 判断如果用户不存在  直接返回null
        if (tbSeller==null){
            return null;
        }

        //1.3  判断 用户 是否已经被审核了 如果没有审核 return
        if(!"1".equals(tbSeller.getStatus())){
        //if (!tbSeller.getStatus().equals("1")){
            //未审核 就是 账号不可以用
            return null;
        }
        //1.4 如果 用户 存在  需要匹配密码（自动完成） 就获取用户的密码
        String password = tbSeller.getPassword();


        //3.给用户设置权限
        /*List<GrantedAuthority> list = new ArrayList<>();
        list.add(new SimpleGrantedAuthority("ROLE_USER"));//授权角色
        list.add(new SimpleGrantedAuthority("ROLE_ADMIN"));//授权角色*/

        //2.交给springsecurity框架 自动的匹配
        //return new User(username,"{noop}"+password,AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER"));

        // 加密
        return new User(username,password,AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER"));
    }
}
