package com.ltr.location.mapper;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ltr.location.queryresult.CityResult;
import com.ltr.location.queryresult.CountyResult;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class PlacesRepositoryTest {
	
	@Autowired
	PlacesRepository placesRepository;

	@Test
	public void testFindByProvince() {
		//fail("Not yet implemented");
		String provinceName = "河北";
		List<CityResult> list = placesRepository.findByProvinceLike(provinceName);
		for(CityResult p : list) {
			System.out.print("City:" + p.getCity() + "；");
			System.out.println();
		}
	}

	@Test
	public void testFindByProvinceAndCity() {
		//fail("Not yet implemented");
		String city = "邯郸市";
		String province = "河北省";
		List<CountyResult> list = placesRepository.findByProvinceAndCity(province, city);
		for(CountyResult p : list) {
			System.out.print("County:" + p.getCounty() + "；");
			System.out.println();
		}
	}

	@Test
	public void testfinByLngAndLat() {
		//fail("Not yet implemented");
		Double lng = 117.15;
		Double laat = 39.2;
		List<CountyResult> list = placesRepository.findByLngAndLat(lng, laat);
		for(CountyResult p : list) {
			System.out.print("County:" + p.getCounty() + "；");
			System.out.print("Lnt:" + p.getLongitude() + "；");
			System.out.print("Lat:" + p.getLatitude() + "；");
			System.out.println();
		}
	}

}
