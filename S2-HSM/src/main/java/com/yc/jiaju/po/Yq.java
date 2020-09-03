package com.yc.jiaju.po;

public class Yq {

	private Integer yid;
	
	private String province;
	
	private String city;
	
	private Integer citycode;

	public Integer getYid() {
		return yid;
	}

	public void setYid(Integer yid) {
		this.yid = yid;
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

	public Integer getCitycode() {
		return citycode;
	}

	public void setCitycode(Integer citycode) {
		this.citycode = citycode;
	}

	@Override
	public String toString() {
		return "Yq [yid=" + yid + ", province=" + province + ", city=" + city + ", citycode=" + citycode + "]";
	}
	
	
}
