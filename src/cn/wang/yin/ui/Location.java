package cn.wang.yin.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import cn.wang.yin.personal.R;
import cn.wang.yin.personal.service.HandlerService;
import cn.wang.yin.utils.PersonConstant;
import cn.wang.yin.utils.PersonDbUtils;
import cn.wang.yin.utils.PersonIntence;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.map.GraphicsOverlay;
import com.baidu.mapapi.map.ItemizedOverlay;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.OverlayItem;
import com.baidu.mapapi.map.PopupClickListener;
import com.baidu.mapapi.map.PopupOverlay;
import com.baidu.platform.comapi.basestruct.GeoPoint;

public class Location extends Activity {
	View mPopView = null;
	TextView pop_text;
	BMapManager mBMapMan = null;
	public static MapView mMapView = null;
	GraphicsOverlay graphicsOverlay = null;
	// List<LocationInfo> listLocation = new ArrayList();
	MapController mMapController;
	Timer timer = new Timer();
	TimerTask task;
	protected static TabChangeReceiver receiver;
	PopupOverlay pop = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		PersonDbUtils.init(
				getApplicationContext(),
				getSharedPreferences(PersonConstant.USER_AGENT_INFO,
						Context.MODE_PRIVATE));
		super.onCreate(savedInstanceState);
		mBMapMan = new BMapManager(getApplication());
		mBMapMan.init(PersonConstant.BAIDU_MAP_KEY, null);
		setContentView(R.layout.location);
		System.out.println("map");
		mMapView = (MapView) findViewById(R.id.sinagle_taavel_map_view);
		mMapView.setBuiltInZoomControls(false);
		// �����������õ����ſؼ�
		mMapController = mMapView.getController();
		mMapController.setCenter(PersonIntence.getPoint());// ���õ�ͼ���ĵ�
		mMapController.setZoom(19);// ���õ�ͼzoom����
		graphicsOverlay = new GraphicsOverlay(mMapView);
		mMapView.getOverlays().add(graphicsOverlay);
		mMapView.refresh();// ˢ�µ�ͼ
		// push("��ʼ��������");
		startService(new Intent(getApplicationContext(), HandlerService.class));
		mPopView = super.getLayoutInflater().inflate(R.layout.popview, null);
		pop_text = (TextView) mPopView.findViewById(R.id.pop_text);
		pop = new PopupOverlay(mMapView, new PopupClickListener() {
			@Override
			public void onClickedPopup(int index) {
				// �ڴ˴���pop����¼���indexΪ�����������,�����������������

			}
		});
		mPopView.setVisibility(View.GONE);
		mMapView.addView(mPopView);

	}

	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				break;
			}

		}
	};

	public class TabChangeReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			Log.i("onReceive", "���յ�");
			int intExtra = intent.getIntExtra(
					PersonConstant.LOCATION_CHANGE_TAG, 0);
			if (PersonConstant.LOCATION_CHANGE == intExtra) {
				mMapView.getOverlays().clear();
				// mMapView.removeAllViews();
				Log.i("onReceive", "ˢ�µ�ͼ");
				OverItemS ov = new OverItemS(getResources().getDrawable(
						R.drawable.gplaces), mMapView);
				mMapView.getOverlays().add(ov);

				Drawable endmarker = getResources().getDrawable(
						R.drawable.gplaces);
				OverlayItem enditem = new OverlayItem(PersonIntence.getPoint(),
						"item3", PersonIntence.getAddr());
				enditem.setMarker(endmarker);

				ov.addItem(enditem);

				mMapController.setCenter(PersonIntence.getPoint());//
				mMapController.setZoom(19);// ���õ�ͼzoom����
				mMapView.refresh();// ˢ�µ�ͼ
			}
		}
	}

	// ItemizedOverlay
	public class OverItemS extends ItemizedOverlay<OverlayItem> {

		private List<OverlayItem> GeoList = new ArrayList<OverlayItem>();

		public OverItemS(Drawable drawable, MapView mapView) {
			super(drawable, mapView);
			// TODO Auto-generated constructor stub
		}

		@Override
		// ��������¼�
		protected boolean onTap(int i) {
			OverlayItem overItem = getItem(i);
			GeoPoint pt = overItem.getPoint();
			mPopView.setVisibility(View.VISIBLE);
			// pop.showPopup(null, pt, BIND_ABOVE_CLIENT);
			// mPopView.setX(pt.getLatitudeE6());
			// mPopView.setY(pt.getLongitudeE6());
			pop_text.setText(overItem.getSnippet());
			return true;
		}

		@Override
		public boolean onTap(GeoPoint arg0, MapView arg1) {
			// ��ȥ����������
			mPopView.setVisibility(View.GONE);
			return super.onTap(arg0, arg1);
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		unregisterReceiver(receiver);
		super.onPause();
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
	}

	@Override
	protected void onResume() {
		receiver = new TabChangeReceiver();
		registerReceiver(receiver, new IntentFilter("cn.wang.yin.ui.Location"),
				null, handler);
		super.onResume();

	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

}
