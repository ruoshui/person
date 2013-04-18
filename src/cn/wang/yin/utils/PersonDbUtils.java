package cn.wang.yin.utils;

import cn.wang.yin.ui.LocationMainActivity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Message;

public class PersonDbUtils {
	private static PersonDbAdapter personDb;
	private static Context context;
	private static SharedPreferences preference;

	private static boolean lock = false;

	public static void init(Context ctx) {
		if (ctx != null) {
			context = ctx;
		}
		getInstance();
		SQLiteDatabase sdb = personDb.getWritableDatabase();
		// sdb.execSQL(Constant.DROP_SQL_GPS_INFO);
		sdb.execSQL(PersonConstant.SQL_GPS_INFO);
		sdb.execSQL(PersonConstant.SQL_PERSON_COLLECT);
		sdb.close();
	}

	public static void lock() {
		Message message = new Message();
		message.what = 5;
		message.obj = "锁定数据库";
		LocationMainActivity.handler.sendMessage(message);
		setLock(true);
	}

	public static void unLock() {
		Message message = new Message();
		message.what = 5;
		message.obj = "解除锁定数据库";
		LocationMainActivity.handler.sendMessage(message);
		setLock(false);
	}

	public static PersonDbAdapter getInstance() {
		if (personDb == null) {
			personDb = new PersonDbAdapter(context);
		}

		// getPreferenceManager();
		// preference.
		// getPreferences(Activity.MODE_PRIVATE);
		return personDb;
	}

	public static boolean isLocked() {
		return lock;
	}

	private static void setLock(boolean lock) {
		PersonDbUtils.lock = lock;
	}

	public static SharedPreferences getPreference() {
		return preference;
	}

	public static void setPreference(SharedPreferences preference) {
		PersonDbUtils.preference = preference;
	}

	public static boolean putValue(String key, String value,
			SharedPreferences pre) {
		if (preference == null) {
			preference = pre;
		}
		Editor editor = preference.edit();
		editor.putString(key, value);
		return editor.commit();

	}

	public static boolean putValue(String key, boolean value,
			SharedPreferences pre) {
		if (preference == null) {
			preference = pre;
		}
		Editor editor = preference.edit();
		editor.putBoolean(key, value);
		return editor.commit();

	}

	public static boolean putValue(String key, float value,
			SharedPreferences pre) {
		if (preference == null) {
			preference = pre;
		}
		Editor editor = preference.edit();
		editor.putFloat(key, value);
		return editor.commit();

	}

	public static boolean putValue(String key, double value,
			SharedPreferences pre) {
		if (preference == null) {
			preference = pre;
		}
		Editor editor = preference.edit();
		editor.putFloat(key, (float) value);
		return editor.commit();

	}

	public static boolean putValue(String key, int value, SharedPreferences pre) {
		if (preference == null) {
			preference = pre;
		}
		Editor editor = preference.edit();
		editor.putInt(key, value);
		return editor.commit();

	}

	public static boolean putValue(String key, long value, SharedPreferences pre) {
		if (preference == null) {
			preference = pre;
		}
		Editor editor = preference.edit();
		editor.putLong(key, value);
		return editor.commit();
	}

	public static boolean remove(String key) {
		Editor editor = preference.edit();
		editor.remove(key);
		return editor.commit();
		// editor.putLong(key, value);
	}

	public static String getValue(String key, String defValue) {
		return preference.getString(key, defValue);
	}

	public static float getValue(String key, float defValue) {
		return preference.getFloat(key, defValue);
	}

	public static boolean getValue(String key, boolean defValue) {
		return preference.getBoolean(key, defValue);
	}

	public static long getValue(String key, long defValue) {
		return preference.getLong(key, defValue);
	}

	public static int getValue(String key, int defValue) {
		return preference.getInt(key, defValue);
	}

	public static Context getContext() {
		return context;
	}

	public static void setContext(Context context) {
		PersonDbUtils.context = context;
	}

}
