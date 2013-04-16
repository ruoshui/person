package cn.wang.yin.personal.service;

import android.app.IntentService;
import android.content.Intent;
import cn.wang.yin.utils.CollectGpsUtil;
import cn.wang.yin.utils.PersonConstant;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

public class HandlerService extends IntentService {
	public HandlerService(String name) {
		super(name);
	}

	Runnable runnnable = new Runnable() {
		@Override
		public void run() {

			LocationClient mLocationClient = null;
			mLocationClient = new LocationClient(getApplicationContext()); // 声明LocationClient类
			mLocationClient.registerLocationListener(new BDLocationListener() {
				@Override
				public void onReceivePoi(BDLocation location) {
				}

				@Override
				public void onReceiveLocation(BDLocation location) {
					// TODO Auto-generated method stub
					if (location == null)
						return;
					CollectGpsUtil.saveGps(location);
				}
			}); // 注册监听函数
			LocationClientOption option = new LocationClientOption();
			option.setOpenGps(true);
			option.setAddrType("all");// 返回的定位结果包含地址信息
			option.setCoorType("bd09ll");// 返回的定位结果是百度经纬度,默认值gcj02
			option.setScanSpan((int) PersonConstant.WAIT_TIMS);// 设置发起定位请求的间隔时间为5000ms
			option.disableCache(true);// 禁止启用缓存定位
			// option.setPoiNumber(5); // 最多返回POI个数
			// option.setPoiDistance(1000); // poi查询距离
			// option.setPoiExtraInfo(true); // 是否需要POI的电话和地址等详细信息
			mLocationClient.setLocOption(option);
			mLocationClient.start();
		}
	};

	@Override
	protected void onHandleIntent(Intent intent) {
		// TODO Auto-generated method stub

	}

}
