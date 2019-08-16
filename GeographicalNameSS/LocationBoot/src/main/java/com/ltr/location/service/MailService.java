package com.ltr.location.service;

import javax.mail.MessagingException;

public interface MailService {
	
	/**
	 * 发送邮件到指定邮箱
	 * @param sendTo   送达邮箱
	 * @param subject  邮件主题
	 * @param content  邮件内容
	 * @throws MessagingException 
	 */
	 void sendMail(String sendTo, String subject, String content) throws MessagingException;
}
