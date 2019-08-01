package com.ltr.location.queryresult;

import java.io.Serializable;

public interface CountyResult extends Serializable{
	String getCounty();
	Double getLongitude();
	Double getLatitude();
}
