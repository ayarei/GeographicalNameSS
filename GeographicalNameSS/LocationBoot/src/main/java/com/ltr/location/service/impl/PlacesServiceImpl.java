package com.ltr.location.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.ltr.location.mapper.PlacesRepository;
import com.ltr.location.service.PlacesService;

/**
 * 接口增加Redis缓存
 * 
 *
 */
@Service
@com.alibaba.dubbo.config.annotation.Service
public class PlacesServiceImpl implements PlacesService {

	@Autowired
	private PlacesRepository placesRepository;
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	/*
	 * redis存储非List对象时需要
	 */
	// @Autowired
	// private RedisTemplate redisTemplate;

	/**
	 * 给省名返回属于本省的所有地市名
	 * 
	 * 模糊查询
	 * 
	 */
	@Override
	public String findByProvince(String provinceName) {
		// 先从缓存取数据
		String ret = redisGet(provinceName);
		if (null != ret) {
			return ret;
		}
		ret = getJson(placesRepository.findByProvinceLike(provinceName));
		if (!ret.equals("[]")) {
			redisSet(provinceName, ret);
		}
		return ret;
	}

	/**
	 * 给省名、地市名返回区县名
	 * 
	 */
	@Override
	public String findByProvinceAndCity(String provinceName, String cityName) {
		String key = provinceName + ";" + cityName;
		// 先从缓存取数据
		String ret = redisGet(key);
		if (null != ret) {
			return ret;
		}
		ret = getJson(placesRepository.findByProvinceAndCity(provinceName, cityName));
		if (!ret.equals("[]")) {
			redisSet(key, ret);
		}
		return ret;
	}

	/**
	 * 给经纬度查询匹配最近区县名
	 * 
	 */
	@Override
	public String findByLngAndLat(Double longitude, Double latitude) {
		String key = String.valueOf(longitude) + String.valueOf(latitude);
		// 先从缓存取数据
		String ret = redisGet(key);
		if (null != ret) {
			return ret;
		}
		ret = getJson(placesRepository.findByLngAndLat(longitude, latitude));
		if (!ret.equals("[]")) {
			redisSet(key, ret);
		}
		return ret;
	}

	/**
	 * Dubbo测试
	 */
	@Override
	public String hello() {
		return "hello Dubbo!";
	}

	/**
	 * 将List转为json字符串以提供RPC接口调用
	 * 
	 * （Dubbo无法序列化List对象）
	 * 
	 * @param <T>
	 * @param object
	 * @return
	 */
	private <T> String getJson(List<T> object) {
		PropertyFilter profilter = new PropertyFilter() {

			@Override
			public boolean apply(Object object, String name, Object value) {
				if (name.equalsIgnoreCase("targetClass") || name.equalsIgnoreCase("target")) {
					// false表示当前字段将被排除在外
					return false;
				}
				return true;
			}

		};
		return JSON.toJSONString(object, profilter);
	}

	/**
	 * redis存取操作
	 * 
	 * @param key
	 * @param obj
	 */
	private void redisSet(String key, String obj) {
		stringRedisTemplate.opsForValue().set(key, obj);
	}

	private String redisGet(String key) {
		return stringRedisTemplate.opsForValue().get(key);
	}

}
