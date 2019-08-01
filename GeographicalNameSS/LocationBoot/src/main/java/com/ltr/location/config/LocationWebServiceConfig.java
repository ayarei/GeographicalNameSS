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

	@Bean
	public Endpoint endpoint() {
		EndpointImpl endpoint = new EndpointImpl(bus, new PlacesServiceImpl());
		endpoint.publish("/location");
		return endpoint;
	}
}