package cn.wang.yin.personal.service;

import java.util.Timer;
import java.util.TimerTask;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import cn.wang.yin.personal.R;
import cn.wang.yin.ui.LocationMainActivity;
import cn.wang.yin.utils.CollectGpsUtil;
import cn.wang.yin.utils.PersonConstant;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

public class HandlerService extends IntentService {
	public static LocationClient mLocationClient = null;
	public BDLocationListener myListener = new MyLocationListener();
	public static Timer timer = new Timer();
	public static TimerTask task;
	public static Timer uploadTimer = new Timer();
	public static TimerTask uploadTask;
	public static NotificationManager m_NotificationManager;
	Intent m_Intent;
	PendingIntent m_PendingIntent;
	Notification m_Notification;

	public HandlerService() {

		super("HandlerService");
	}

	public HandlerService(String name) {
		super("HandlerService");
	}

	@Override
	public void onCreate() {
		addNotificaction();
		mLocationClient = new LocationClient(getApplicationContext()); // ����LocationClient��
		mLocationClient.registerLocationListener(myListener); // ע���������
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);
		option.setAddrType("all");// ���صĶ�λ���������ַ��Ϣ
		option.setCoorType("bd09ll");// ���صĶ�λ����ǰٶȾ�γ��,Ĭ��ֵgcj02
		option.setScanSpan((int) PersonConstant.WAIT_TIMS);// ���÷���λ����ļ��ʱ��Ϊ5000ms
		option.disableCache(true);// ��ֹ���û��涨λ
		mLocationClient.setLocOption(option);
		mLocationClient.start();
		super.onCreate();
	}

	/**
	 * ���һ��notification
	 */
	private void addNotificaction() {

		m_NotificationManager = (NotificationManager) this
				.getSystemService(Context.NOTIFICATION_SERVICE);
		// ����һ��Notification
		Notification m_Notification = new Notification();
		// ������ʾ���ֻ����ϱߵ�״̬����ͼ��
		m_Notification.icon = R.drawable.ic_launcher;
		// ����ǰ��notification���ŵ�״̬���ϵ�ʱ����ʾ����
		m_Notification.tickerText = PersonConstant.MSEESGE_REMIND_TICKER;
		m_Notification.flags |= Notification.FLAG_ONGOING_EVENT;
		m_Notification.flags |= Notification.FLAG_AUTO_CANCEL;
		m_Notification.flags |= Notification.FLAG_NO_CLEAR;
		// ���������ʾ
		m_Notification.defaults = Notification.DEFAULT_SOUND;
		m_Notification.audioStreamType = android.media.AudioManager.ADJUST_LOWER;
		Intent intent = new Intent(this, LocationMainActivity.class);
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
				intent, PendingIntent.FLAG_ONE_SHOT);
		m_Notification.setLatestEventInfo(this, "����",
				PersonConstant.MSEESGE_REMIND_CONTENT, pendingIntent);
//		m_NotificationManager.notify(PersonConstant.COMMON_NOTIFICATION,
//				m_Notification);
	}

	@Override
	public void onStart(Intent intent, int startId) {
		uploadTask = new TimerTask() {
			@Override
			public void run() {
				CollectGpsUtil.uploadGps();
				addNotificaction();
			}
		};
		uploadTimer.schedule(uploadTask, PersonConstant.UPLOAD_TIMS,
				PersonConstant.UPLOAD_TIMS);
		super.onStart(intent, startId);
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		
		return super.onBind(intent);
	}

	public static Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			Message message = new Message();
			message.what = 5;
			// /��ʱִ������
			// ////////////////////////////////////////////////////////////////
			switch (msg.what) {
			case 1:
				// if (mLocationClient != null && mLocationClient.isStarted()) {
				// mLocationClient.requestLocation();
				//
				// message.obj = "��λ";
				// } else {
				// message.obj = "���쳣\t" + mLocationClient;
				// }
				break;
			case 2:
				if (msg.obj != null) {
					message.obj = msg.obj.toString();
				}

				break;
			case 3:
				CollectGpsUtil.location = (BDLocation) msg.obj;
				handler.post(CollectGpsUtil.saveRunnnable);
				break;
			case 4:
				if (msg.obj != null) {
					message.obj = msg.obj.toString();
				}
				break;
			}
			//LocationMainActivity.handler.sendMessage(message);
		}
	};
	Runnable locationRunnnable = new Runnable() {
		@Override
		public void run() {

		}
	};

	Runnable saveRunnnable = new Runnable() {
		@Override
		public void run() {

		}
	};

	@Override
	protected void onHandleIntent(Intent intent) {
		// TODO Auto-generated method stub

	}

	public class MyLocationListener implements BDLocationListener {
		@Override
		public void onReceiveLocation(BDLocation location) {

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
