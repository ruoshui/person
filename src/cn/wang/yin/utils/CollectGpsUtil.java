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
import android.os.Message;
import cn.wang.yin.hessian.api.Remot;
import cn.wang.yin.ui.LocationMainActivity;

import com.baidu.location.BDLocation;
import com.wang.yin.hessian.bean.GpsInfo;

public class CollectGpsUtil implements Serializable {

	private static List<GpsInfo> degList = new ArrayList();
	private static List<GpsInfo> tmp = new ArrayList();
	public static BDLocation location;
	private static double lon = 0.0;
	private static double lat = 0.0;

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
				Message msg = new Message();
				msg.what = 4;
				String message = "";
				if (netWork != null
						&& netWork.getType() == ConnectivityManager.TYPE_WIFI) {
					message = "有WIFI\n";
					try {
						getAll();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						message += e1.getMessage();
					}
					// for (int i = 0; i < degList.size(); i++) {
					// Remot report = RemoteFactoryUtils.getReport();
					// boolean ret = false;
					// try {
					// // GpsInfo info = degList.get(i);
					// // if (info.getErrorCode() < 162)
					// ret = report.uploadGps(degList.get(i));
					// } catch (Exception e) {
					// CollectDebugLogUtil.saveDebug(e.getMessage(), e
					// .getClass().toString(), "puloadGps");
					// }
					// if (ret) {
					// if (degList != null && degList.size() > 0
					// && degList.get(i) != null)
					// delete(degList.get(i).getId());
					// }
					// }
					Remot report = null;
					int ret = 0;
					try {
						report = RemoteFactoryUtils.getReport();
						ret = report.uploadGps(degList);
						message += "上传了" + ret + "条数据\n";
						for (int i = 0; i < degList.size(); i++) {
							delete(degList.get(i).getId());
						}
						degList.clear();
					} catch (Exception e) {
						message += "上传异常  \n" + e.getMessage();
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					// return re;
				} else if (netWork != null) {
					message = "没有WIFI\n";
					try {
						getAll();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						message += e1.getMessage();
					}
					if (degList != null && degList.size() > 0) {

						Remot report = RemoteFactoryUtils.getReport();
						boolean ret = false;
						try {
							ret = report.uploadGps(degList.get(0));
						} catch (Exception e) {
							message += e.getMessage();
							// re = e.getMessage();
							CollectDebugLogUtil.saveDebug(e.getMessage(), e
									.getClass().toString(), "puloadGps");
						}
						if (ret && degList != null && degList.size() > 0) {
							delete(degList.get(0).getId());
						}
					}
					message += "上传了1条数据，目前还有" + (degList.size() - 1) + "条数据\n";
					degList.clear();
					// return "没有WIFI,只上传一条";
				} else if (netWork == null) {
					message = "没有任何网络\n";
				}
				msg.obj = message;
				LocationMainActivity.handler.sendMessage(msg);
			}
		});
		thread.start();
	}

	/**
	 * 收集GPS信息
	 */
	public static Runnable saveRunnnable = new Runnable() {
		@Override
		public void run() {
			if (PersonDbUtils.isLocked()) {
				try {
					Thread.sleep(PersonConstant.SLEEP_TIMS);
					LocationMainActivity.handler.post(saveRunnnable);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return;
				}
				return;
			} else {
				PersonDbUtils.lock();
			}
			if (getLat() == location.getLatitude()
					&& getLon() == location.getLongitude()) {
				Message msg = new Message();
				msg.what = 4;
				msg.obj = "与上一个点重复，不必存储";
				LocationMainActivity.handler.sendMessage(msg);
				PersonDbUtils.unLock();
			} else {
				setLat(location.getLatitude());
				setLon(location.getLongitude());
			}

			Message msg = new Message();
			msg.what = 4;
			String message = "";
			if (location != null && location.getLocType() < 162) {
				PersonDbAdapter db = PersonDbUtils.getInstance();
				SQLiteDatabase sdb = db.getWritableDatabase();
				// sdb.execSQL(PersonConstant.SQL_GPS_INFO);
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
					message = "存储进去了 \t当前数据库索引id为：" + l + "\n";
				} catch (Exception e) {
					PersonDbUtils.unLock();
					message += "存储异常  \n" + e.getMessage();
					CollectDebugLogUtil.saveDebug(e.getMessage(), e.getClass()
							.toString(), "saveGps");

				}
				sdb.close();
				PersonDbUtils.unLock();
			}
			msg.obj = message;
			LocationMainActivity.handler.sendMessage(msg);
		}
	};

	/**
	 * 收集GPS信息
	 */
	public static void saveGps(final BDLocation location) {
		if (PersonDbUtils.isLocked()) {
			try {
				Thread.sleep(PersonConstant.SLEEP_TIMS);
				saveGps(location);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return;
			}
			return;
		} else {
			PersonDbUtils.lock();
		}

		// SIMCardInfo sim = new SIMCardInfo();
		if (location != null && location.getLocType() < 162) {
			PersonDbAdapter db = PersonDbUtils.getInstance();
			SQLiteDatabase sdb = db.getWritableDatabase();
			// sdb.execSQL(PersonConstant.SQL_GPS_INFO);
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
				PersonDbUtils.unLock();
				CollectDebugLogUtil.saveDebug(e.getMessage(), e.getClass()
						.toString(), "saveGps");
			}
			sdb.close();
			PersonDbUtils.unLock();
		}
	}

	/****
	 * 查询所有调试信息
	 */
	public static void getAll() {
		if (PersonDbUtils.isLocked()) {
			try {
				Thread.sleep(PersonConstant.SLEEP_TIMS);
				getAll();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return;
			}
			return;
		} else {
			PersonDbUtils.lock();
		}
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
			PersonDbUtils.unLock();
		} catch (Exception e) {
			PersonDbUtils.unLock();
			CollectDebugLogUtil.saveDebug(e.getMessage(), e.getClass()
					.toString(), "getAll");
		}
	}

	/****
	 * 删除调试信息
	 */

	public static boolean delete(int id) {
		if (PersonDbUtils.isLocked()) {
			try {
				Thread.sleep(PersonConstant.SLEEP_TIMS);
				return delete(id);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return delete(id);
			}

		} else {
			PersonDbUtils.lock();
		}

		boolean delete = false;
		try {
			SQLiteDatabase db = PersonDbUtils.getInstance()
					.getWritableDatabase();
			delete = db.delete("gps_info", "t_id" + "=" + id, null) > 0;
			db.close();
			PersonDbUtils.unLock();
		} catch (Exception e) {
			PersonDbUtils.unLock();
			CollectDebugLogUtil.saveDebug(e.getMessage(), e.getClass()
					.toString(), "delete");
		}
		return delete;
	}

	public static double getLon() {
		return lon;
	}

	public static void setLon(double lon) {
		CollectGpsUtil.lon = lon;
	}

	public static double getLat() {
		return lat;
	}

	public static void setLat(double lat) {
		CollectGpsUtil.lat = lat;
	}

}
