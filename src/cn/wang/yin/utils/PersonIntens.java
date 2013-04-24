package cn.wang.yin.utils;

import com.baidu.platform.comapi.basestruct.GeoPoint;

import android.app.Application;

public class PersonIntens extends Application {
	private static GeoPoint point;
	private static String addr;

	public PersonIntens() {
		if (point == null) {
			point = new GeoPoint((int) (22.594101 * 1e6),
					(int) (113.971166 * 1e6));
		}
	}

	public static void setLon(double lon) {
		point.setLongitudeE6((int) (lon * 1e6));
	}

	public static void setLat(double lat) {
		point.setLatitudeE6((int) (lat * 1e6));
	}

	public static GeoPoint getPoint() {
		return point;
	}

	public static String getAddr() {
		return addr;
	}

	public static void setAddr(String addr) {
		PersonIntens.addr = addr;
	}

}