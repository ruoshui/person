package cn.wang.yin.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.NetworkInfo;
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
import cn.wang.yin.utils.PersonIntens;
import cn.wang.yin.utils.PersonStringUtils;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.map.GraphicsOverlay;
import com.baidu.mapapi.map.ItemizedOverlay;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MapView.LayoutParams;
import com.baidu.mapapi.map.OverlayItem;
import com.baidu.platform.comapi.basestruct.GeoPoint;

public class Location extends Activity {
	public static View mPopView = null;
	public static TextView pop_text;
	BMapManager mBMapMan = null;
	public static MapView mMapView = null;
	GraphicsOverlay graphicsOverlay = null;
	// List<LocationInfo> listLocation = new ArrayList();
	MapController mMapController;
	Timer timer = new Timer();
	TimerTask task;
	protected static TabChangeReceiver receiver;

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
		// 设置启用内置的缩放控件
		mMapController = mMapView.getController();
		mMapController.setCenter(PersonIntens.getPoint());// 设置地图中心点
		mMapController.setZoom(17);// 设置地图zoom级别
		graphicsOverlay = new GraphicsOverlay(mMapView);
		mMapView.getOverlays().add(graphicsOverlay);
		mMapView.refresh();// 刷新地图
		// push("开始启动服务");
		startService(new Intent(getApplicationContext(), HandlerService.class));
		mPopView = super.getLayoutInflater().inflate(R.layout.popview, null);
		pop_text = (TextView) mPopView.findViewById(R.id.pop_text);
	}

	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				Drawable endmarker = getResources().getDrawable(
						R.drawable.gplaces);
				OverlayItem enditem = new OverlayItem(PersonIntens.getPoint(),
						"item3", PersonIntens.getAddr());
				enditem.setMarker(endmarker);
				OverItemS ov = new OverItemS(null, mMapView, Location.this);
				ov.addItem(enditem);
				mMapView.getOverlays().add(ov);
				mMapController.setCenter(PersonIntens.getPoint());//
				mMapController.setZoom(17);// 设置地图zoom级别
				mMapView.refresh();// 刷新地图
				// mMapView.addView(mPopView, new MapView.LayoutParams(
				// LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,
				// null, MapView.LayoutParams.TOP_LEFT));
				// mPopView.setVisibility(View.GONE);
				break;
			}

		}
	};

	public class TabChangeReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			int intExtra = intent.getIntExtra(
					PersonConstant.LOCATION_CHANGE_TAG, 0);

		}
	}

	// ItemizedOverlay
	public class OverItemS extends ItemizedOverlay<OverlayItem> {

		private List<OverlayItem> GeoList = new ArrayList<OverlayItem>();
		private Context mContext;

		public OverItemS(Drawable drawable, MapView mapView, Context context) {
			super(null, mapView);
			this.mContext = context;
			// TODO Auto-generated constructor stub
		}

		@Override
		protected OverlayItem createItem(int i) {
			return GeoList.get(i);
		}

		@Override
		public int size() {
			return GeoList.size();
		}

		@Override
		public void addItem(OverlayItem item) {
			GeoList.add(item);
		}

		@Override
		// 处理当点击事件
		protected boolean onTap(int i) {
			OverlayItem overItem = getItem(i);
			GeoPoint pt = GeoList.get(i).getPoint();
			mMapView.updateViewLayout(mPopView, new MapView.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, pt,
					com.baidu.mapapi.map.MapView.LayoutParams.BOTTOM_CENTER));
			mPopView.setVisibility(View.VISIBLE);
			pop_text.setText(overItem.getSnippet());
			return true;
		}

		@Override
		public boolean onTap(GeoPoint arg0, MapView arg1) {
			// 消去弹出的气泡
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
		super.onResume();

	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		task = new TimerTask() {
			@Override
			public void run() {
				handler.sendEmptyMessage(0);
			}
		};
		timer.schedule(task, PersonConstant.WAIT_TIMS, PersonConstant.WAIT_TIMS);
		super.onStart();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

}
