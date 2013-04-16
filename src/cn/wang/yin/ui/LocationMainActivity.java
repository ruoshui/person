package cn.wang.yin.ui;

import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import cn.wang.yin.personal.R;
import cn.wang.yin.personal.service.PersonService;
import cn.wang.yin.ui.MainActivity.MyLocationListener;
import cn.wang.yin.utils.CollectGpsUtil;
import cn.wang.yin.utils.PersonConstant;
import cn.wang.yin.utils.PersonDbUtils;
import cn.wang.yin.utils.SIMCardInfo;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

@SuppressLint("NewApi")
public class LocationMainActivity extends Activity {
	public static String mtag = "MainActivity";
	Button button1;
	public static TextView textView1;
	int i = 0;
	public static final int FAIL = 0;
	public static final int SUCCESS = 1;
	final Timer timer = new Timer();
	TimerTask task;
	Timer uploadTimer = new Timer();
	TimerTask uploadTask;

	public static LocationClient mLocationClient = null;
	public BDLocationListener myListener = new MyLocationListener();
	TelephonyManager telephonyManager;

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

		System.out.println(android.os.Build.VERSION.RELEASE);

		// SIMCardInfo sci = new SIMCardInfo(getApplicationContext());
		// textView1.setText(textView1.getText() + "\n" + "手机号码："
		// + sci.getNativePhoneNumber() + "\n" + sci.getProvidersName());
		// telephonyManager = (TelephonyManager) getApplicationContext()
		// .getSystemService(Context.TELEPHONY_SERVICE);
		// StringBuilder sb = new StringBuilder();
		// sb.append(telephonyManager.getDeviceId() + "\n");
		// sb.append(telephonyManager.getCallState() + "\n");
		// sb.append(telephonyManager.getDeviceSoftwareVersion() + "\n");
		// sb.append(telephonyManager.getLine1Number() + "\n");
		// sb.append(telephonyManager.getPhoneType() + "\n");
		// sb.append(telephonyManager.getSubscriberId() + "\n");
		// textView1.setText(textView1.getText() + "\n" + sb.toString());
		mLocationClient = new LocationClient(getApplicationContext()); // 声明LocationClient类
		mLocationClient.registerLocationListener(myListener); // 注册监听函数
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);
		option.setAddrType("all");// 返回的定位结果包含地址信息
		option.setCoorType("bd09ll");// 返回的定位结果是百度经纬度,默认值gcj02
		// option.setScanSpan(5000);// 设置发起定位请求的间隔时间为5000ms
		option.disableCache(true);// 禁止启用缓存定位
		mLocationClient.setLocOption(option);
		mLocationClient.start();
	}

	Runnable runnnable = new Runnable() {
		@Override
		public void run() {

			mLocationClient.registerLocationListener(new BDLocationListener() {
				@Override
				public void onReceivePoi(BDLocation location) {
				}

				@Override
				public void onReceiveLocation(BDLocation location) {
					// TODO Auto-generated method stub
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
					// BDLocation location = (BDLocation) msg.obj;
					// Message message = new Message();
					message.what = 3;
					handler.sendMessage(message);
					CollectGpsUtil.saveGps(location);

				}
			}); // 注册监听函数

		}
	};

	public static Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			String str = (String) textView1.getText();
			if (str.split("\n").length > 30) {
				textView1.setText("");
			}
			// /定时执行任务
			// ////////////////////////////////////////////////////////////////
			switch (msg.what) {
			case 1:
				if (mLocationClient != null && mLocationClient.isStarted()) {
					mLocationClient.requestLocation();
					textView1.setText(textView1.getText() + "\n" + "定位" + "\n"
							+ "------------------------------");
				} else {
					textView1.setText(textView1.getText() + "\n" + "不能定位"
							+ "\n" + "------------------------------");
				}

				Log.i(mtag, "定位");
				break;
			case 2:
				textView1.setText(textView1.getText() + "\n"
						+ msg.obj.toString() + "\n"
						+ "------------------------------");
				Log.i(mtag, textView1.getText().toString());
				break;
			case 3:

				CollectGpsUtil.location = (BDLocation) msg.obj;
				handler.post(CollectGpsUtil.saveRunnnable);
				textView1.setText(textView1.getText() + "\n" + "开始存储" + "\n"
						+ "------------------------------");
				Log.i(mtag, textView1.getText().toString());
				break;
			case 4: {
				if (msg.obj != null) {
					textView1.setText(textView1.getText() + "\n"
							+ msg.obj.toString() + "\n"
							+ "------------------------------");
				}

				Log.i(mtag, textView1.getText().toString());
			}
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

		uploadTask = new TimerTask() {
			@Override
			public void run() {
				CollectGpsUtil.uploadGps();
			}
		};
		uploadTimer.schedule(uploadTask, PersonConstant.WAIT_TIMS * 3,
				PersonConstant.WAIT_TIMS * 3);

		super.onResume();
	}

	@Override
	protected void onStart() {
		// mLocationClient.start();
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

		@Override
		public void onReceivePoi(BDLocation poiLocation) {
			if (poiLocation == null) {
				return;
			}

		}
	}
}
