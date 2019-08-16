package com.ltr.location.config;

import javax.xml.ws.Endpoint;

import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ltr.location.service.PlacesService;
import com.ltr.location.service.impl.PlacesServiceImpl;

@Configuration
public class LocationWebServiceConfig {
	@Autowired
	private Bus bus;
	
	/**
	 * 
	 * 默认WebService访问地址为/services
	 * 
	 */

	@Bean
	public Endpoint endpoint() {
		EndpointImpl endpoint = new EndpointImpl(bus, new PlacesServiceImpl());
		endpoint.publish("/location"); // 服务发布地址
		return endpoint;
	}
}
