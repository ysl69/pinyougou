package com.pinyougou.user.service.impl;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.pinyougou.user.service.UserService;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo; 									  
import org.apache.commons.lang3.StringUtils;
import com.pinyougou.core.service.CoreServiceImpl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.DigestUtils;
import tk.mybatis.mapper.entity.Example;

import com.pinyougou.mapper.TbUserMapper;
import com.pinyougou.pojo.TbUser;  




/**
 * 服务实现层
 * @author Administrator
 *
 */
@Service
public class UserServiceImpl extends CoreServiceImpl<TbUser>  implements UserService {

	
	private TbUserMapper userMapper;

	@Autowired
	public UserServiceImpl(TbUserMapper userMapper) {
		super(userMapper, TbUser.class);
		this.userMapper=userMapper;
	}

	
	

	
	@Override
    public PageInfo<TbUser> findPage(Integer pageNo, Integer pageSize) {
        PageHelper.startPage(pageNo,pageSize);
        List<TbUser> all = userMapper.selectAll();
        PageInfo<TbUser> info = new PageInfo<TbUser>(all);

        //序列化再反序列化
        String s = JSON.toJSONString(info);
        PageInfo<TbUser> pageInfo = JSON.parseObject(s, PageInfo.class);
        return pageInfo;
    }

	
	

	 @Override
    public PageInfo<TbUser> findPage(Integer pageNo, Integer pageSize, TbUser user) {
        PageHelper.startPage(pageNo,pageSize);

        Example example = new Example(TbUser.class);
        Example.Criteria criteria = example.createCriteria();

        if(user!=null){			
						if(StringUtils.isNotBlank(user.getUsername())){
				criteria.andLike("username","%"+user.getUsername()+"%");
				//criteria.andUsernameLike("%"+user.getUsername()+"%");
			}
			if(StringUtils.isNotBlank(user.getPassword())){
				criteria.andLike("password","%"+user.getPassword()+"%");
				//criteria.andPasswordLike("%"+user.getPassword()+"%");
			}
			if(StringUtils.isNotBlank(user.getPhone())){
				criteria.andLike("phone","%"+user.getPhone()+"%");
				//criteria.andPhoneLike("%"+user.getPhone()+"%");
			}
			if(StringUtils.isNotBlank(user.getEmail())){
				criteria.andLike("email","%"+user.getEmail()+"%");
				//criteria.andEmailLike("%"+user.getEmail()+"%");
			}
			if(StringUtils.isNotBlank(user.getSourceType())){
				criteria.andLike("sourceType","%"+user.getSourceType()+"%");
				//criteria.andSourceTypeLike("%"+user.getSourceType()+"%");
			}
			if(StringUtils.isNotBlank(user.getNickName())){
				criteria.andLike("nickName","%"+user.getNickName()+"%");
				//criteria.andNickNameLike("%"+user.getNickName()+"%");
			}
			if(StringUtils.isNotBlank(user.getName())){
				criteria.andLike("name","%"+user.getName()+"%");
				//criteria.andNameLike("%"+user.getName()+"%");
			}
			if(StringUtils.isNotBlank(user.getStatus())){
				criteria.andLike("status","%"+user.getStatus()+"%");
				//criteria.andStatusLike("%"+user.getStatus()+"%");
			}
			if(StringUtils.isNotBlank(user.getHeadPic())){
				criteria.andLike("headPic","%"+user.getHeadPic()+"%");
				//criteria.andHeadPicLike("%"+user.getHeadPic()+"%");
			}
			if(StringUtils.isNotBlank(user.getQq())){
				criteria.andLike("qq","%"+user.getQq()+"%");
				//criteria.andQqLike("%"+user.getQq()+"%");
			}
			if(StringUtils.isNotBlank(user.getIsMobileCheck())){
				criteria.andLike("isMobileCheck","%"+user.getIsMobileCheck()+"%");
				//criteria.andIsMobileCheckLike("%"+user.getIsMobileCheck()+"%");
			}
			if(StringUtils.isNotBlank(user.getIsEmailCheck())){
				criteria.andLike("isEmailCheck","%"+user.getIsEmailCheck()+"%");
				//criteria.andIsEmailCheckLike("%"+user.getIsEmailCheck()+"%");
			}
			if(StringUtils.isNotBlank(user.getSex())){
				criteria.andLike("sex","%"+user.getSex()+"%");
				//criteria.andSexLike("%"+user.getSex()+"%");
			}
	
		}
        List<TbUser> all = userMapper.selectByExample(example);
        PageInfo<TbUser> info = new PageInfo<TbUser>(all);
        //序列化再反序列化
        String s = JSON.toJSONString(info);
        PageInfo<TbUser> pageInfo = JSON.parseObject(s, PageInfo.class);

        return pageInfo;
    }


    @Autowired
	private RedisTemplate redisTemplate;

	@Autowired
	private DefaultMQProducer producer;

	@Value("${template_code}")
	private String templateCode;

	@Value("${sign_name}")
	private String signName;

	/**
	 * 生成短信验证码
	 * @param phone
	 */
	@Override
	public void createSmsCode(String phone) {
		try {
			//1.生成6位数字
			//那么math.random()*9+1一定是小于10的，(Math.random()*9+1)*100000一定是<10*100000=1000000的一个数
			String code =  (long) ((Math.random() * 9 + 1) * 100000)+"";

			//2.存储到redis中  集成redis  : 1.依赖  2.配置文件 3.注入resdisTemplate
			//redisTemplate.boundHashOps("SmsCode").put(phone,code);
			redisTemplate.boundValueOps("ZHUCE_"+phone).set(code); //key:手机号  value:验证码的值
			redisTemplate.boundValueOps("ZHUCE_"+phone).expire(24L, TimeUnit.HOURS);

			//3.组装消息对象,  手机号  签名 模板  验证码
			Map<String, String> map = new HashMap<>();
			map.put("mobile",phone);
			map.put("sign_name",signName);
			map.put("template_code",templateCode);
			map.put("param","{\"code\":\""+code+"\"}");

			//4 .发送消息  1.依赖  2. 配置文件 3. 注入producer 4.发送消息
			Message message = new Message("SMS_TOPIC","SEND_MESSAGE_TAG","createSmsCode",
					JSON.toJSONString(map).getBytes());

			producer.send(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	/**
	 * 判断短信验证码是否存在
	 * @param phone
	 * @param code
	 * @return
	 */
	@Override
	public boolean checkSmsCode(String phone, String code) {
		// 得到缓存中存储的验证码
		String sysCode = (String) redisTemplate.boundValueOps("ZHUCE_" + phone).get();

		if(StringUtils.isEmpty(phone) || StringUtils.isEmpty(code)){
			return false;
		}
		if (!sysCode.equals(code)){
			return false;
		}

		return true;
	}


	@Override
	public void add(TbUser record) {
		//1.md5 加密存储密码
		String password = record.getPassword();//明文
		String passwordencode = DigestUtils.md5DigestAsHex(password.getBytes());//密码文
		record.setPassword(passwordencode);
		//2.补充必要字段属性值
		record.setCreated(new Date());//创建日期
		record.setUpdated(record.getCreated());//修改日期

		//3.添加数据到数据库中
		userMapper.insert(record);
	}
}
