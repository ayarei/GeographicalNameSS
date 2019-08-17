package com.ltr.location.controller;

import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSendException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ltr.location.globalconst.GlobalConst;
import com.ltr.location.service.MailService;


/**
 * 
 * @author LTR
 *
 */
@Controller
public class LoginController {

	@Autowired
	private MailService mailService;

	/**
	 * 验证登录信息
	 * 
	 * @param code
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public String login(
			@RequestParam("logincode") String code, 
			Model model, 
			HttpSession session) {

		String saveMailAddress = (String) session.getAttribute(GlobalConst.USER_SESSION_LOGIN_MAILADDRESS_KEY);
		String saveCode = (String) session.getAttribute(GlobalConst.USER_SESSION_LOGIN_CODE_KEY);

		// 当验证码错误或者session中无绑定邮箱时，返回重新登录
		if (saveMailAddress == null || !code.equalsIgnoreCase(saveCode)) {
			return "loginFail";
		}
		// 用session保存登录状态
		session.setAttribute(GlobalConst.USER_SESSION_LOGIN_KEY, code);
		return "loginSuccess";
	}

	/**
	 * 通过邮箱获取验证码
	 * 
	 * @param mailAddress
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	@ResponseBody
	public String register(@RequestParam("mailaddress") String mailAddress, Model model, HttpSession session) {
		// 验证码邮件主题
		String subject = "地名信息服务验证码";
		// 生成六位验证码
		int num = (int) ((Math.random() * 9 + 1) * 100000);
		String code = String.valueOf(num);
		
		// 将生成的验证码与用户邮箱在Session中绑定
		session.setAttribute(GlobalConst.USER_SESSION_LOGIN_MAILADDRESS_KEY, mailAddress);
		session.setAttribute(GlobalConst.USER_SESSION_LOGIN_CODE_KEY, code);
		// 将验证码通过邮箱发送
		try {
			mailService.sendMail(mailAddress, subject, code);
			model.addAttribute("msg", "邮件已发送到邮箱");
			return "success";
		} catch (MessagingException e) {
			model.addAttribute("msg", "邮件发送错误");
			return "fail";
		} catch(MailSendException e){
			model.addAttribute("msg", "邮件发送错误");
			return "fail";
		}
	}
	
	@RequestMapping(value = "/register",method = RequestMethod.GET)
	public String registerPage() {
		return "mail/register";
	}
	
	@RequestMapping(value = "/login",method = RequestMethod.GET)
	public String loginPage() {
		return "mail/login";
	}
	
}