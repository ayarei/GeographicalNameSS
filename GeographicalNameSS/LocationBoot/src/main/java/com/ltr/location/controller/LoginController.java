package com.ltr.location.controller;

import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import com.ltr.location.service.MailService;

// TODO 添加拦截器
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
	public String login(
			@RequestParam("logincode") String code, 
			@RequestParam("mailaddress") String mailAddress,
			Model model, 
			HttpSession session) {

		String saveMailAddress = (String) session.getAttribute("mailAddress");
		String saveCode = (String) session.getAttribute("code");

		// 当验证码或者邮箱错误时，返回重新登录
		if (!mailAddress.equalsIgnoreCase(saveMailAddress) || !code.equalsIgnoreCase(saveCode)) {
			model.addAttribute("msg", "验证码或邮箱错误");
			return "redirect:templates/login.html";
		}
		// 用session保存登录状态
		session.setAttribute("isLogin", mailAddress + "-" + code);
		return "redirect:/";
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
	public String reg(@RequestParam("mailaddress") String mailAddress, Model model, HttpSession session) {
		// 验证码邮件主题
		String subject = "地名信息服务验证码";
		// 生成六位验证码
		int num = (int) ((Math.random() * 9 + 1) * 100000);
		String code = String.valueOf(num);
		
		// 将生成的验证码与用户邮箱在Session中绑定
		session.setAttribute("mailAddress", mailAddress);
		session.setAttribute("code", code);
		// 将验证码通过邮箱发送
		try {
			mailService.sendMail(mailAddress, subject, code);
			model.addAttribute("msg", "邮件已发送到邮箱");
			return "success";
		} catch (MessagingException e) {
			model.addAttribute("msg", "邮件发送错误");
			return "fail";
		}

	}
}
