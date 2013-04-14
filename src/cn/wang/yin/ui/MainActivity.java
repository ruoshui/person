package cn.wang.yin.ui;

import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import cn.wang.yin.personal.service.PersonService;
import cn.wang.yin.ui.Location.MyLocationListener;
import cn.wang.yin.utils.CollectGpsUtil;
import cn.wang.yin.utils.PersonConstant;
import cn.wang.yin.utils.PersonDbUtils;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.wang.yin.personal.R;

@SuppressLint("NewApi")
public class MainActivity extends Activity {
	public String mtag = "MainActivity";
	Button button1;
	public static TextView textView1;
	int i = 0;
	public static final int FAIL = 0;
	public static final int SUCCESS = 1;
	final Timer timer = new Timer();
	TimerTask task;
	public LocationClient mLocationClient = null;
	public BDLocationListener myListener = new MyLocationListener();

	// private static List<String> listTmp = new ArrayList();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		PersonDbUtils.init(getApplicationContext());
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		button1 = (Button) findViewById(R.id.button1);
		textView1 = (TextView) findViewById(R.id.textView1);
		// SIMCardInfo.init(getApplicationContext());
		// handler.post(runnnable);
		button1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(),
						PersonService.class);
				stopService(intent);
				textView1.setText(textView1.getText() + "\n" + "停止服务");
			}
		});
		// float fv =
		// Float.valueOf(android.os.Build.VERSION.RELEASE.substring(0,
		// 3).trim());
		// if (fv > 2.3) {
		// StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
		// .detectDiskReads().detectDiskWrites().detectNetwork() //
		// 这里可以替换为detectAll()
		// // 就包括了磁盘读写和网络I/O
		// .penaltyLog() // 打印logcat，当然也可以定位到dropbox，通过文件保存相应的log
		// .build());
		// StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
		// .detectLeakedSqlLiteObjects() // 探测SQLite数据库操作
		// .penaltyLog() // 打印logcat
		// .penaltyDeath().build());
		// }
		mLocationClient = new LocationClient(getApplicationContext()); // 声明LocationClient类
		mLocationClient.registerLocationListener(myListener); // 注册监听函数
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);
		option.setAddrType("all");// 返回的定位结果包含地址信息
		option.setCoorType("bd09ll");// 返回的定位结果是百度经纬度,默认值gcj02
		// option.setScanSpan(5000);// 设置发起定位请求的间隔时间为5000ms
		option.disableCache(true);// 禁止启用缓存定位
		option.setPoiNumber(5); // 最多返回POI个数
		option.setPoiDistance(1000); // poi查询距离
		option.setPoiExtraInfo(true); // 是否需要POI的电话和地址等详细信息
		mLocationClient.setLocOption(option);
		System.out.println(android.os.Build.VERSION.RELEASE);

	}

	Runnable runnnable = new Runnable() {
		@Override
		public void run() {
			// Intent intent = new Intent(getApplicationContext(),
			// PersonService.class);
			// startService(intent);
			handler.sendEmptyMessage(0);

		}
	};

	final Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// /定时执行任务
			// ////////////////////////////////////////////////////////////////
			// NetworkInfo netWork = EtongStringUtils
			// .getActiveNetwork(getApplicationContext());
			switch (msg.what) {
			case 1:
				if (mLocationClient != null && mLocationClient.isStarted())
					mLocationClient.requestLocation();
				textView1.setText(textView1.getText() + "\n" + "定位" + "\n"
						+ "------------------------------");
				Log.i(mtag, "定位");
				CollectGpsUtil.uploadGps();
				Log.i(mtag, "上传" + "\t");
				textView1.setText(textView1.getText() + "\n" + "上传" + "\n"
						+ "------------------------------");
				// Thread t = new Thread(new Runnable() {
				// @Override
				// public void run() {
				// String upload = CollectGpsUtil.uploadGps();
				// Log.i(mtag, "上传" + "\t" + upload);
				// textView1.setText("\n" + upload + "\n"
				// + "------------------------------");
				// }
				//
				// });
				// t.start();
				break;
			case 2:
				String str = (String) textView1.getText();
				if (str.split("\n").length > 30) {
					textView1.setText("");
				}
				textView1.setText(textView1.getText() + "\n"
						+ msg.obj.toString() + "\n"
						+ "------------------------------");
				Log.i(mtag, textView1.getText().toString());
				break;
			case 3:
				BDLocation location = (BDLocation) msg.obj;
				CollectGpsUtil.saveGps(location);
				textView1.setText(textView1.getText() + "\n" + "存储" + "\n"
						+ "------------------------------");
				Log.i(mtag, textView1.getText().toString());
				break;
			case 4:
				break;
			}

		}
	};

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onRestart() {
		super.onRestart();
	}

	@Override
	protected void onResume() {
		task = new TimerTask() {
			@Override
			public void run() {
				Message message = new Message();
				message.what = 1;
				handler.sendMessage(message);
			}
		};
		timer.schedule(task, PersonConstant.WAIT_TIMS, PersonConstant.WAIT_TIMS);
		super.onResume();
	}

	@Override
	protected void onStart() {
		mLocationClient.start();
		super.onStart();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	public class MyLocationListener implements BDLocationListener {
		@Override
		public void onReceiveLocation(BDLocation location) {
			if (location == null)
				return;
			StringBuffer sb = new StringBuffer(256);
			sb.append("time : ");
			sb.append(location.getTime());
			sb.append("\nerror code : ");
			sb.append(location.getLocType());
			sb.append("\nlatitude : ");
			sb.append(location.getLatitude());
			sb.append("\nlontitude : ");
			sb.append(location.getLongitude());
			sb.append("\nradius : ");
			sb.append(location.getRadius());
			if (location.getLocType() == BDLocation.TypeGpsLocation) {
				sb.append("\nspeed : ");
				sb.append(location.getSpeed());
				sb.append("\nsatellite : ");
				sb.append(location.getSatelliteNumber());
			} else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
				sb.append("\naddr : ");
				sb.append(location.getAddrStr());
			}
			Message message = new Message();
			message.what = 2;
			message.obj = sb;
			handler.sendMessage(message);
			Message msg = new Message();
			msg.what = 3;
			msg.obj = location;
			handler.sendMessage(msg);

		}

		public void onReceivePoi(BDLocation poiLocation) {
			if (poiLocation == null) {
				return;
			}

		}
	}
}
