package com.ltr.location.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.ltr.location.globalconst.GlobalConst;

@Component
public class LoginInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
		String loginInfo = (String) request.getSession().getAttribute(GlobalConst.USER_SESSION_LOGIN_KEY);
		if (null == loginInfo || loginInfo.equals("")) {
			response.sendRedirect("/");
			return false;
		} 
		else {
			return true;
		}

	}

}
