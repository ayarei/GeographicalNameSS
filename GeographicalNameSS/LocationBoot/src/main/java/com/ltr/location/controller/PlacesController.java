package com.ltr.location.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ltr.location.service.PlacesService;

@RestController
public class PlacesController {

	@Autowired
	PlacesService placesService;

	/**
	 * 返回省份下所有城市
	 * 
	 * 模糊查询：四川->四川省
	 * 
	 * @param provinceName
	 * @return
	 */
	@RequestMapping(value = "/province/{name}", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
	public String findCityByProvince(@PathVariable("name") String provinceName) {
		return placesService.findByProvince(provinceName);
	}

	/**
	 * 返回城市下的所有区县 
	 * 
	 * 不可模糊查询
	 * 
	 * @param provinceName
	 * @param cityName
	 * @return
	 */
	@RequestMapping(value = "/province/{provinceName}/{cityName}", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
	public String findCountyByProAndCity(@PathVariable("provinceName") String provinceName,
			@PathVariable("cityName") String cityName) {
		return placesService.findByProvinceAndCity(provinceName, cityName);
	}

	/**
	 * 根据经纬度查询最近的区县
	 * 
	 * @param longitude 目标经度
	 * @param latitude  目标纬度
	 * @return
	 */
	@RequestMapping(value = "/nearby", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
	public String findNearByCounty(@RequestParam(value = "lng", required = true) Double longitude,
			@RequestParam(value = "lat", required = true) Double latitude) {
		return placesService.findByLngAndLat(longitude, latitude);
	}

	@RequestMapping(value = "/hello", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
	public String hello() {
		return placesService.hello();
	}
}
