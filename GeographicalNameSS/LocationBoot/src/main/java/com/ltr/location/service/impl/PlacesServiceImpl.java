package com.ltr.location.service.impl;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.ltr.location.config.NamespaceConfig;
import com.ltr.location.mapper.PlacesRepository;
import com.ltr.location.service.PlacesService;

/**
 * 
 * 接口增加Redis缓存
 * 
 */
@WebService(serviceName = "locationServices", // 服务名称
		targetNamespace = NamespaceConfig.webServiceNamespace, // wsdl命名空间
		name = NamespaceConfig.webServiceName, // 客户端生成代码时的接口名称
		portName = "locationPort", endpointInterface = "com.ltr.location.service.PlacesService" // 指定发布webservcie的接口类
)
@Service
@com.alibaba.dubbo.config.annotation.Service
public class PlacesServiceImpl implements PlacesService {
	@Autowired
	private PlacesRepository placesRepository;
	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	private static final int Redis_Cache_Time_Out_Hours = 12; // Redis缓存超时时间，12小时
	private static final int Redis_Cache_Min_For_NULL = 5;    // 缓存空数据的时限为5分钟

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
		redisSet(provinceName, ret);
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
		redisSet(key, ret);
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
		redisSet(key, ret);
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
	 * 序列化对象为json字符串
	 * 
	 * @param <T>
	 * @param object
	 * @return
	 */
	private <T> String getJson(List<T> object) {
		PropertyFilter profilter = new PropertyFilter() {

			// 排除Json字符串中多余字段
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
		if (!obj.equals("[]")) {
			// 防止缓存雪崩，在原基础上为每个缓存数据添加随机的缓存时间
			int Random_Cache_Hours = (int) (Math.random() * 10);
			stringRedisTemplate.opsForValue().set(key, obj, Redis_Cache_Time_Out_Hours + Random_Cache_Hours,
					TimeUnit.HOURS);
		} 
		// 防止缓存穿透攻击，对无法找到的数据仍然缓存五分钟
		else if (obj.equals("[]")) {
			stringRedisTemplate.opsForValue().set(key, obj, Redis_Cache_Min_For_NULL, TimeUnit.MINUTES);
		}

	}

	private String redisGet(String key) {
		return stringRedisTemplate.opsForValue().get(key);
	}

}
