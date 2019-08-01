package com.ltr.location.pojo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "places")
public class Places implements Serializable{

	@Id
	@Column(name = "id")
	private Integer id;
	@Column(name = "province", nullable = true, length = 255)
	private String province;
	@Column(name = "city", nullable = true, length = 255)
	private String city;
	@Column(name = "county", nullable = true, length = 255)
	private String county;
	@Column(name = "longitude", nullable = true)
	private Double longitude;
	@Column(name = "latitude", nullable = true)
	private Double latitude;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

}
