package com.springboot.demo_ybx07;

import com.springboot.demo_ybx07.entity.UserEntity;
import com.springboot.demo_ybx07.service.MailService;
import com.springboot.demo_ybx07.util.PasswordHelper;

import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.TemplateEngine;

import java.util.HashMap;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class DemoYbx07ApplicationTests {
	@Autowired
	private MailService mailService;

	@Test
	public void contextLoads() {
		log.info("开始发送邮件！");
		String to = "1520688026@qq.com";
		String subject = "Springboot 发送 模版邮件";
		Map<String, Object> paramMap = new HashMap();
		paramMap.put("username", "Darcy");
		mailService.sendTemplateMail(to, subject, paramMap, "RegisterSuccess");
	}

	private RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();
	public static final String ALGORITHM_NAME = "md5"; // 基础散列算法
	public static final int HASH_ITERATIONS = 2; // 自定义散列次数

	public void encryptPassword(UserEntity user) {
		// 随机字符串作为salt因子，实际参与运算的salt我们还引入其它干扰因子
		user.setSalt(randomNumberGenerator.nextBytes().toHex());
		String newPassword = new SimpleHash(ALGORITHM_NAME, user.getPassWord(),
											ByteSource.Util.bytes(user.getCredentialsSalt()), HASH_ITERATIONS).toHex();
		user.setPassWord(newPassword);
	}
	@Test
	public void test (){
		UserEntity user = new UserEntity();
		user.setUserName("1");
		user.setPassWord("1");
		encryptPassword(user);
		System.out.println("密码为"+user.getPassWord());
	}

}
