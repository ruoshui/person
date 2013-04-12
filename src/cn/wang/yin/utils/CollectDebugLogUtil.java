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

import cn.wang.yin.bean.DebuggingInformation;
import cn.wang.yin.hessian.api.Remot;


public class CollectDebugLogUtil implements Serializable {

	private static List<DebuggingInformation> degList = new ArrayList();

	public static List<DebuggingInformation> getDegList() {
		return degList;
	}

	public static void setDegList(List<DebuggingInformation> degList) {
		CollectDebugLogUtil.degList = degList;
	}

	/**
	 * 收集调试信息
	 */
	public static void uploadCollectDebug() {
		NetworkInfo netWork = PersonStringUtils.getActiveNetwork(null);
		if (netWork.getType() == ConnectivityManager.TYPE_WIFI) {
			System.out.println("开始上传调试信息");
			getAll();
			try {
				for (int i = 0; i < degList.size(); i++) {
					if (degList.get(i).getMessage() == null) {
						degList.get(i).setMessage("");
					}
					if (degList.get(i).getExceptiontype() == null) {
						degList.get(i).setExceptiontype("");
					}
					if (degList.get(i).getExlocation() == null) {
						degList.get(i).setExlocation("");
					}
					if (degList.get(i).getPhonenum() == null) {
						degList.get(i).setPhonenum("");
					}
					if (degList.get(i).getPruducttime() == null) {
						degList.get(i).setPruducttime("");
					}

					try {
						Remot report = RemoteFactoryUtils.getFactory().create(
								Remot.class, PersonConstant.REMOTE_URL);
						boolean ret = false;
						// ret = report.CelectionDebug(
						// degList.get(i).getMessage(), degList.get(i)
						// .getExceptiontype(), degList.get(i)
						// .getExlocation(), degList.get(i)
						// .getPhonenum(), degList.get(i)
						// .getPruducttime(), PersonStringUtils
						// .pareDateToString(new Date()), 0);
						if (ret) {
							delete(degList.get(i).getId());
						}

					} catch (Exception e) {
						CollectDebugLogUtil.saveDebug(e.getMessage(), e
								.getClass().toString(), "\t"
								+ "CollectDebugLogUtil");
						e.printStackTrace();
					}
				}

			} catch (Exception e) {
				CollectDebugLogUtil.saveDebug(e.getMessage(), e.getClass()
						.toString(), "\t" + "CollectDebugLogUtil");
				e.printStackTrace();
			}

		}
	}

	/**
	 * 收集调试信息
	 */
	public static void saveDebug(String message, String exceptiontype,
			String exlocation) {
		SQLiteDatabase db = PersonDbUtils.getInstance().getWritableDatabase();
		// EtongUser user = InstensUserData.getUserData().getEtongUser();
		db.execSQL(PersonConstant.SQL_PERSON_COLLECT);
		ContentValues initialValues = new ContentValues();
		initialValues.put("message", message);
		initialValues.put("exceptiontype", exceptiontype);
		initialValues.put("exlocation", exlocation);
		initialValues.put("phonenum", "13168096632");
		initialValues.put("pruducttime",
				PersonStringUtils.pareDateToString(new Date()));
		db.insert("person_collect", null, initialValues);
		db.close();
	}

	/****
	 * 查询所有调试信息
	 */
	public static void getAll() {
		try {
			SQLiteDatabase db = PersonDbUtils.getInstance()
					.getWritableDatabase();
			Cursor cur = db.query("etong_collect", new String[] { "id",
					"message", "exceptiontype", "exlocation", "phonenum",
					"pruducttime" }, null, null, null, null, null);
			for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
				DebuggingInformation debug = new DebuggingInformation();
				debug.setId(cur.getInt(0));
				debug.setMessage(cur.getString(1));
				debug.setExceptiontype(cur.getString(2));
				debug.setExlocation(cur.getString(3));
				debug.setPhonenum(cur.getString(4));
				debug.setPruducttime(cur.getString(5));
				degList.add(debug);
			}
			db.close();
		} catch (Exception e) {
			CollectDebugLogUtil.saveDebug(e.getMessage(), e.getClass()
					.toString(), "\t" + "CollectDebugLogUtil");
			e.printStackTrace();
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
			delete = db.delete("etong_collect", "id" + "=" + id, null) > 0;
			db.close();
		} catch (Exception e) {
			CollectDebugLogUtil.saveDebug(e.getMessage(), e.getClass()
					.toString(), "\t" + "CollectDebugLogUtil");
			e.printStackTrace();
		}
		return delete;
	}
}
