package com.ltr.location.service;

import javax.jws.WebMethod;
import javax.jws.WebService;

import com.ltr.location.config.NamespaceConfig;

@WebService(
		targetNamespace = NamespaceConfig.webServiceNamespace,
		name = NamespaceConfig.webServiceName
)
public interface PlacesService {
	@WebMethod
	String findByProvince(String provinceName);
	@WebMethod
	String findByProvinceAndCity(String provinceName, String cityName);
	@WebMethod
	String findByLngAndLat(Double longitude, Double latitude);
	@WebMethod
	String hello();
}
