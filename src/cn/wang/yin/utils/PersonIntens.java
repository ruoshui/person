package cn.wang.yin.utils;

import org.apache.commons.lang.StringUtils;

import com.baidu.platform.comapi.basestruct.GeoPoint;

import android.app.Application;

public class PersonIntens extends Application {
	private static GeoPoint point;
	private static String addr;

	public PersonIntens() {

	}

	public static void setLon(double lon) {
		if (point == null) {
			point = new GeoPoint((int) (22.594101 * 1E6),
					(int) (113.971166 * 1e6));
		}
		point.setLongitudeE6((int) (lon * 1e6));
	}

	public static void setLat(double lat) {
		if (point == null) {
			point = new GeoPoint((int) (22.594101 * 1E6),
					(int) (113.971166 * 1e6));
		}
		point.setLatitudeE6((int) (lat * 1e6));
	}

	public static GeoPoint getPoint() {
		if (point == null) {
			point = new GeoPoint((int) (22.594101 * 1E6),
					(int) (113.971166 * 1e6));
		}
		return point;
	}

	public static String getAddr() {
		addr = StringUtils.isNotBlank(addr) ? addr : "Î»ÖÃÎ´Öª";
		return addr;
	}

	public static void setAddr(String addr) {
		PersonIntens.addr = addr;
	}

}
