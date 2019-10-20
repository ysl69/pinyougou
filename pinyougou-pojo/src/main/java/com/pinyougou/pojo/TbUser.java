package com.pinyougou.pojo;

import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.Pattern;

@Table(name = "tb_user")
public class TbUser implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 用户名
     */
    @Column(name = "username")
    @NotBlank(message = "用户名不能为空!")
    @Pattern(regexp = "^[a-zA-Z0-9\\u4E00-\\u9FA5]+$",message = "用户名只能为数字或者字母!")
    private String username;

    /**
     * 密码，加密存储
     */
    @Column(name = "password")
    @NotBlank(message = "密码不能为空!")
    private String password;

    /**
     * 注册手机号
     */
    @Column(name = "phone")
    @NotBlank(message = "手机号不允许为空!")
    @Pattern(regexp = "[1][3|4|5|7|8][0-9]{9}",message = "手机号格式不正确！")
    private String phone;

    /**
     * 注册邮箱
     */
    @Column(name = "email")
    private String email;

    /**
     * 创建时间
     */
    @Column(name = "created")
    private Date created;

    @Column(name = "updated")
    private Date updated;

    /**
     * 会员来源：1:PC，2：H5，3：Android，4：IOS，5：WeChat
     */
    @Column(name = "source_type")
    private String sourceType;

    /**
     * 昵称
     */
    @Column(name = "nick_name")
    private String nickName;

    /**
     * 真实姓名
     */
    @Column(name = "name")
    private String name;

    /**
     * 使用状态（Y正常 N非正常）
     */
    @Column(name = "status")
    private String status;

    /**
     * 头像地址
     */
    @Column(name = "head_pic")
    private String headPic;

    /**
     * QQ号码
     */
    @Column(name = "qq")
    private String qq;

    /**
     * 账户余额
     */
    @Column(name = "account_balance")
    private Long accountBalance;

    /**
     * 手机是否验证 （0否  1是）
     */
    @Column(name = "is_mobile_check")
    private String isMobileCheck;

    /**
     * 邮箱是否检测（0否  1是）
     */
    @Column(name = "is_email_check")
    private String isEmailCheck;

    /**
     * 性别，1男，2女
     */
    @Column(name = "sex")
    private String sex;

    /**
     * 会员等级
     */
    @Column(name = "user_level")
    private Integer userLevel;

    /**
     * 积分
     */
    @Column(name = "points")
    private Integer points;

    /**
     * 经验值
     */
    @Column(name = "experience_value")
    private Integer experienceValue;

    /**
     * 生日
     */
    @Column(name = "birthday")
    private Date birthday;

    /**
     * 最后登录时间
     */
    @Column(name = "last_login_time")
    private Date lastLoginTime;

    private static final long serialVersionUID = 1L;

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取用户名
     *
     * @return username - 用户名
     */
    public String getUsername() {
        return username;
    }

    /**
     * 设置用户名
     *
     * @param username 用户名
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 获取密码，加密存储
     *
     * @return password - 密码，加密存储
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置密码，加密存储
     *
     * @param password 密码，加密存储
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取注册手机号
     *
     * @return phone - 注册手机号
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 设置注册手机号
     *
     * @param phone 注册手机号
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * 获取注册邮箱
     *
     * @return email - 注册邮箱
     */
    public String getEmail() {
        return email;
    }

    /**
     * 设置注册邮箱
     *
     * @param email 注册邮箱
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 获取创建时间
     *
     * @return created - 创建时间
     */
    public Date getCreated() {
        return created;
    }

    /**
     * 设置创建时间
     *
     * @param created 创建时间
     */
    public void setCreated(Date created) {
        this.created = created;
    }

    /**
     * @return updated
     */
    public Date getUpdated() {
        return updated;
    }

    /**
     * @param updated
     */
    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    /**
     * 获取会员来源：1:PC，2：H5，3：Android，4：IOS，5：WeChat
     *
     * @return source_type - 会员来源：1:PC，2：H5，3：Android，4：IOS，5：WeChat
     */
    public String getSourceType() {
        return sourceType;
    }

    /**
     * 设置会员来源：1:PC，2：H5，3：Android，4：IOS，5：WeChat
     *
     * @param sourceType 会员来源：1:PC，2：H5，3：Android，4：IOS，5：WeChat
     */
    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    /**
     * 获取昵称
     *
     * @return nick_name - 昵称
     */
    public String getNickName() {
        return nickName;
    }

    /**
     * 设置昵称
     *
     * @param nickName 昵称
     */
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    /**
     * 获取真实姓名
     *
     * @return name - 真实姓名
     */
    public String getName() {
        return name;
    }

    /**
     * 设置真实姓名
     *
     * @param name 真实姓名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取使用状态（Y正常 N非正常）
     *
     * @return status - 使用状态（Y正常 N非正常）
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置使用状态（Y正常 N非正常）
     *
     * @param status 使用状态（Y正常 N非正常）
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 获取头像地址
     *
     * @return head_pic - 头像地址
     */
    public String getHeadPic() {
        return headPic;
    }

    /**
     * 设置头像地址
     *
     * @param headPic 头像地址
     */
    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }

    /**
     * 获取QQ号码
     *
     * @return qq - QQ号码
     */
    public String getQq() {
        return qq;
    }

    /**
     * 设置QQ号码
     *
     * @param qq QQ号码
     */
    public void setQq(String qq) {
        this.qq = qq;
    }

    /**
     * 获取账户余额
     *
     * @return account_balance - 账户余额
     */
    public Long getAccountBalance() {
        return accountBalance;
    }

    /**
     * 设置账户余额
     *
     * @param accountBalance 账户余额
     */
    public void setAccountBalance(Long accountBalance) {
        this.accountBalance = accountBalance;
    }

    /**
     * 获取手机是否验证 （0否  1是）
     *
     * @return is_mobile_check - 手机是否验证 （0否  1是）
     */
    public String getIsMobileCheck() {
        return isMobileCheck;
    }

    /**
     * 设置手机是否验证 （0否  1是）
     *
     * @param isMobileCheck 手机是否验证 （0否  1是）
     */
    public void setIsMobileCheck(String isMobileCheck) {
        this.isMobileCheck = isMobileCheck;
    }

    /**
     * 获取邮箱是否检测（0否  1是）
     *
     * @return is_email_check - 邮箱是否检测（0否  1是）
     */
    public String getIsEmailCheck() {
        return isEmailCheck;
    }

    /**
     * 设置邮箱是否检测（0否  1是）
     *
     * @param isEmailCheck 邮箱是否检测（0否  1是）
     */
    public void setIsEmailCheck(String isEmailCheck) {
        this.isEmailCheck = isEmailCheck;
    }

    /**
     * 获取性别，1男，2女
     *
     * @return sex - 性别，1男，2女
     */
    public String getSex() {
        return sex;
    }

    /**
     * 设置性别，1男，2女
     *
     * @param sex 性别，1男，2女
     */
    public void setSex(String sex) {
        this.sex = sex;
    }

    /**
     * 获取会员等级
     *
     * @return user_level - 会员等级
     */
    public Integer getUserLevel() {
        return userLevel;
    }

    /**
     * 设置会员等级
     *
     * @param userLevel 会员等级
     */
    public void setUserLevel(Integer userLevel) {
        this.userLevel = userLevel;
    }

    /**
     * 获取积分
     *
     * @return points - 积分
     */
    public Integer getPoints() {
        return points;
    }

    /**
     * 设置积分
     *
     * @param points 积分
     */
    public void setPoints(Integer points) {
        this.points = points;
    }

    /**
     * 获取经验值
     *
     * @return experience_value - 经验值
     */
    public Integer getExperienceValue() {
        return experienceValue;
    }

    /**
     * 设置经验值
     *
     * @param experienceValue 经验值
     */
    public void setExperienceValue(Integer experienceValue) {
        this.experienceValue = experienceValue;
    }

    /**
     * 获取生日
     *
     * @return birthday - 生日
     */
    public Date getBirthday() {
        return birthday;
    }

    /**
     * 设置生日
     *
     * @param birthday 生日
     */
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    /**
     * 获取最后登录时间
     *
     * @return last_login_time - 最后登录时间
     */
    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    /**
     * 设置最后登录时间
     *
     * @param lastLoginTime 最后登录时间
     */
    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }
}