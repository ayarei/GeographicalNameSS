package com.ltr.location.mapper;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ltr.location.pojo.Places;
import com.ltr.location.queryresult.CityResult;
import com.ltr.location.queryresult.CountyResult;

/**
 * 基于JPA生成基本的CRUD
 * 
 * @author 91456
 *
 */
public interface PlacesRepository extends JpaRepository<Places, Integer>,Serializable {

	/**
	 * 根据省名查找地市名
	 * 
	 * @param provinceName
	 * @return
	 */
	@Query(value = "select city from Places where Province Like %?1% group by city", nativeQuery = true)
	List<CityResult> findByProvinceLike(String provinceName);

	/**
	 * 根据省名和城市名查找区县名
	 * 
	 * @param provinceName
	 * @param cityName
	 * @return
	 */
	List<CountyResult> findByProvinceAndCity(String provinceName, String cityName);

	/**
	 * 根据经纬度查找最近的区县 查找范围自定义
	 * 
	 * @param longitude   经度
	 * @param latitude    纬度
	 * @return
	 */
	@Query(value = "select county,longitude,latitude from Places where longitude < ?1 + 1 and longitude > ?1 - 1 and latitude < ?2 + 1 and latitude > ?2 - 1 "
			+ "order by sqrt((((?1-longitude)*PI()*12656*cos(((?2+latitude)/2)*PI()/180)/180)"
			+ " * ((?1-longitude)*PI()*12656*cos (((?2+latitude)/2)*PI()/180)/180) ) "
			+ "+ ( ((?2-latitude)*PI()*12656/180) * ((?2-latitude)*PI()*12656/180))) asc limit 1", nativeQuery = true)
	List<CountyResult> findByLngAndLat(Double longitude, Double latitude);
}
