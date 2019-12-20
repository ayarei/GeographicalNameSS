package com.ltr.location.config;

import javax.xml.ws.Endpoint;

import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ltr.location.service.impl.PlacesServiceImpl;

@Configuration
public class LocationWebServiceConfig {
	@Autowired
	private Bus bus;
	
//	/**
//	 * 
//	 * 默认WebService访问地址为/services
//	 * 
//	 */
//	@Bean("cxfServletRegistration")
//    public ServletRegistrationBean dispatcherServlet() {
//        //注册servlet 拦截/ws 开头的请求 不设置 默认为：/services/*
//        return new ServletRegistrationBean(new CXFServlet(), "/location");
//    }

	@Bean
	public Endpoint endpoint() {
		EndpointImpl endpoint = new EndpointImpl(bus, new PlacesServiceImpl());
		endpoint.publish("/location"); // 服务发布地址
		return endpoint;
	}
}
