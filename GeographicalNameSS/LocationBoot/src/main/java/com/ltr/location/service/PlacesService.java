package com.ltr.location.service;


public interface PlacesService {
	String findByProvince(String provinceName);

	String findByProvinceAndCity(String provinceName, String cityName);

	String findByLngAndLat(Double longitude, Double latitude);
	
	String hello();
}
