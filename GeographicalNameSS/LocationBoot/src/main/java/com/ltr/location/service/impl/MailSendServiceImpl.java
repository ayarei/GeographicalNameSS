package com.ltr.location.service.impl;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.ltr.location.globalconst.GlobalConst;
import com.ltr.location.service.MailService;

@Service
public class MailSendServiceImpl implements MailService {

	@Autowired
	private JavaMailSender mailSender;
	@Autowired
	TemplateEngine templateEngine;

	@Value("${mail.fromMail.addr}")
	private String sendFrom;

	/**
	 * 通过模板发送验证码
	 * @throws MessagingException 
	 */
	@Override
	public void sendMail(String sendTo, String subject, String code) throws MessagingException,MailSendException {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		Context context = new Context();
		context.setVariable(GlobalConst.USER_SESSION_LOGIN_CODE_KEY, code);
		String emailContent = templateEngine.process("mailcode", context);
		helper.setFrom(sendFrom);
		helper.setTo(sendTo);
		helper.setSubject(subject);
		helper.setText(emailContent, true);
		mailSender.send(message);

	}

}
