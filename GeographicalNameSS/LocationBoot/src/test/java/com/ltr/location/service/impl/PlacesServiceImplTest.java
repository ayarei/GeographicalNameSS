package com.ltr.location.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ltr.location.service.PlacesService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class PlacesServiceImplTest {

	@Autowired
	PlacesService placesService;

	@Test
	public void testFindByProvince() throws InterruptedException {
		// fail("Not yet implemented");
		String province = "美国";
		for(int i = 0; i < 8; i++) {
			Thread.sleep(500);
			String ret = placesService.findByProvince(province);
			System.out.println(ret);
		}
		
		
	}

	@Test
	public void testFindByProvinceAndCity() throws InterruptedException {
		// fail("Not yet implemented");
		String province = "四川省";
		String city = "成都市";
		for(int i = 0; i < 8; i++) {
			Thread.sleep(500);
			String ret = placesService.findByProvinceAndCity(province, city);
			System.out.println(ret);
		}
	}

	@Test
	public void testFindByLngAndLat() {
		// fail("Not yet implemented");
	}

}
