package com.ltr.location.service.impl;

import javax.mail.MessagingException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ltr.location.service.MailService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class MailSendServiceImplTest {

	@Autowired
	MailService mailService;
	
	@Test
	public void testSendMail() throws MessagingException {
		//fail("Not yet implemented");
		String content = "123456";
		mailService.sendMail("914569351@qq.com", "today 's work", content);
	}

}
