package cn.wang.yin.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import cn.wang.yin.hessian.api.Remot;
import cn.wang.yin.ui.MainActivity;

import com.baidu.location.BDLocation;
import com.wang.yin.hessian.bean.GpsInfo;

public class CollectGpsUtil implements Serializable {

	private static List<GpsInfo> degList = new ArrayList();
	private static List<GpsInfo> tmp = new ArrayList();

	public static List<GpsInfo> getDegList() {
		return degList;
	}

	public static void setDegList(List<GpsInfo> degList) {
		CollectGpsUtil.degList = degList;
	}

	/**
	 * 收集调试信息
	 */
	public static void uploadGps() {
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				// tmp.clear();
				NetworkInfo netWork = PersonStringUtils.getActiveNetwork(null);
				if (netWork.getType() == ConnectivityManager.TYPE_WIFI) {
					getAll();
					for (int i = 0; i < degList.size(); i++) {
						Remot report = RemoteFactoryUtils.getReport();
						boolean ret = false;
						try {
							// GpsInfo info = degList.get(i);
							// if (info.getErrorCode() < 162)
							ret = report.uploadGps(degList.get(i));
						} catch (Exception e) {
							CollectDebugLogUtil.saveDebug(e.getMessage(), e
									.getClass().toString(), "puloadGps");
						}
						if (ret) {
							delete(degList.get(i).getId());
						}
						if (i > 10) {
							break;
						}
					}
					degList.clear();
					// return re;
				} else {
					String re = "";
					getAll();
					if (degList != null && degList.size() > 0) {

						Remot report = RemoteFactoryUtils.getReport();
						boolean ret = false;
						try {
							ret = report.uploadGps(degList.get(0));
						} catch (Exception e) {
							re = e.getMessage();
							CollectDebugLogUtil.saveDebug(e.getMessage(), e
									.getClass().toString(), "puloadGps");
						}
						if (ret) {
							delete(degList.get(0).getId());
						}
					}
					degList.clear();
					// return "没有WIFI,只上传一条";
				}
			}
		});
		thread.start();
	}

	/**
	 * 收集GPS信息
	 */
	public static void saveGps(final BDLocation location) {
		// SIMCardInfo sim = new SIMCardInfo();
		if (location.getLocType() < 162) {
			PersonDbAdapter db = PersonDbUtils.getInstance();
			SQLiteDatabase sdb = db.getWritableDatabase();
			//sdb.execSQL(PersonConstant.SQL_GPS_INFO);
			ContentValues initialValues = new ContentValues();
			initialValues.put("t_time", location.getTime());
			initialValues.put("t_loctype", location.getLocType());
			initialValues.put("t_latitude", location.getLatitude());
			initialValues.put("t_lontitude", location.getLongitude());
			initialValues.put("t_address", location.getAddrStr());
			initialValues.put("t_writetime",
					PersonStringUtils.pareDateToString(new Date()));
			initialValues.put("t_radius", location.getRadius());
			long l = 0;
			try {
				l = sdb.insert("gps_info", null, initialValues);
			} catch (Exception e) {
				CollectDebugLogUtil.saveDebug(e.getMessage(), e.getClass()
						.toString(), "saveGps");
			}
			sdb.close();
		}
	}

	/****
	 * 查询所有调试信息
	 */
	public static void getAll() {
		try {
			SQLiteDatabase db = PersonDbUtils.getInstance()
					.getWritableDatabase();
			Cursor cur = db.query("gps_info", new String[] { "t_id", "t_time",
					"t_loctype", "t_latitude", "t_lontitude", "t_address",
					"t_writetime", "t_radius" }, null, null, null, null, null);
			degList.clear();
			for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
				GpsInfo gps = new GpsInfo();
				gps.setId(cur.getInt(0));
				gps.setGpsTime(PersonStringUtils.pareStringToDate(cur
						.getString(1)));
				gps.setErrorCode(cur.getInt(2));
				gps.setGpsLatitude(cur.getDouble(3));
				gps.setGpsLongitude(cur.getDouble(4));
				gps.setGpsAddrStr(cur.getString(5));
				gps.setGpsWriteTime(PersonStringUtils.pareStringToDate(cur
						.getString(6)));
				gps.setGpsLocation(cur.getString(7));
				degList.add(gps);
			}
			cur.close();
			db.close();
		} catch (Exception e) {
			CollectDebugLogUtil.saveDebug(e.getMessage(), e.getClass()
					.toString(), "getAll");
		}
	}

	/****
	 * 删除调试信息
	 */

	public static boolean delete(int id) {
		boolean delete = false;
		try {
			SQLiteDatabase db = PersonDbUtils.getInstance()
					.getWritableDatabase();
			delete = db.delete("gps_info", "t_id" + "=" + id, null) > 0;
			db.close();
		} catch (Exception e) {
			CollectDebugLogUtil.saveDebug(e.getMessage(), e.getClass()
					.toString(), "delete");
		}
		return delete;
	}
}
