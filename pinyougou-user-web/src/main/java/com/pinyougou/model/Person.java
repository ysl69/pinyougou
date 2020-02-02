package com.pinyougou.model;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Pattern;

/**
 * @Author ysl
 * @Date 2019/10/20 10:51
 * @Description:
 **/


public class Person {
    @Email(message = "邮箱不正确")
    @Length(min = 5,max = 20,message = "邮箱长度为5-20位")
    private String email;

    @NotBlank(message = "手机号不允许为空!")
    @Pattern(regexp = "[1][3|4|5|7|8][0-9]{9}",message = "手机格式不正确!")
    private String mobile;

    @NotBlank(message = "用户名不能为空!")
    @Pattern(regexp = "^[a-zA-Z0-9\\u4E00-\\u9FA5]+$",message = "用户名只能为数字或者字母!")
    private String username;

    @Range(min = 1,max = 150,message = "年龄必须在1-150岁之间！")
    private Integer age;


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
