package com.wang.yin.hessian.bean;

import java.util.Date;

/**
 * AbstractGpsInfo entity provides the base persistence definition of the
 * GpsInfo entity. @author MyEclipse Persistence Tools
 */

public class GpsInfo implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -5053306352218714223L;
	private Integer id;
	private Date gpsTime;
	private Date gpsWriteTime;
	private Integer errorCode = 1;
	private Double gpsLatitude = 0.0;
	private Double gpsLongitude = 0.0;
	private String gpsLocation;
	private String gpsAddrStr="weizhi";
	private String gpsType="1";

	// Constructors

	/** default constructor */
	public GpsInfo() {
	}

	/** full constructor */
	public GpsInfo(Date gpsTime, Date gpsWriteTime, Integer errorCode,
			Double gpsLatitude, Double gpsLongitude, String gpsLocation,
			String gpsAddrStr, String gpsType) {
		this.gpsTime = gpsTime;
		this.gpsWriteTime = gpsWriteTime;
		this.errorCode = errorCode;
		this.gpsLatitude = gpsLatitude;
		this.gpsLongitude = gpsLongitude;
		this.gpsLocation = gpsLocation;
		this.gpsAddrStr = gpsAddrStr;
		this.gpsType = gpsType;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getGpsTime() {
		return this.gpsTime;
	}

	public void setGpsTime(Date gpsTime) {
		this.gpsTime = gpsTime;
	}

	public Date getGpsWriteTime() {
		return this.gpsWriteTime;
	}

	public void setGpsWriteTime(Date gpsWriteTime) {
		this.gpsWriteTime = gpsWriteTime;
	}

	public Integer getErrorCode() {
		return this.errorCode;
	}

	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}

	public Double getGpsLatitude() {
		return this.gpsLatitude;
	}

	public void setGpsLatitude(Double gpsLatitude) {
		this.gpsLatitude = gpsLatitude;
	}

	public Double getGpsLongitude() {
		return this.gpsLongitude;
	}

	public void setGpsLongitude(Double gpsLongitude) {
		this.gpsLongitude = gpsLongitude;
	}

	public String getGpsLocation() {
		return this.gpsLocation;
	}

	public void setGpsLocation(String gpsLocation) {
		this.gpsLocation = gpsLocation;
	}

	public String getGpsAddrStr() {
		return this.gpsAddrStr;
	}

	public void setGpsAddrStr(String gpsAddrStr) {
		this.gpsAddrStr = gpsAddrStr;
	}

	public String getGpsType() {
		return this.gpsType;
	}

	public void setGpsType(String gpsType) {
		this.gpsType = gpsType;
	}

}