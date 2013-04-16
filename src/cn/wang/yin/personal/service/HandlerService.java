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
			mLocationClient = new LocationClient(getApplicationContext()); // ����LocationClient��
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
			}); // ע���������
			LocationClientOption option = new LocationClientOption();
			option.setOpenGps(true);
			option.setAddrType("all");// ���صĶ�λ���������ַ��Ϣ
			option.setCoorType("bd09ll");// ���صĶ�λ����ǰٶȾ�γ��,Ĭ��ֵgcj02
			option.setScanSpan((int) PersonConstant.WAIT_TIMS);// ���÷���λ����ļ��ʱ��Ϊ5000ms
			option.disableCache(true);// ��ֹ���û��涨λ
			// option.setPoiNumber(5); // ��෵��POI����
			// option.setPoiDistance(1000); // poi��ѯ����
			// option.setPoiExtraInfo(true); // �Ƿ���ҪPOI�ĵ绰�͵�ַ����ϸ��Ϣ
			mLocationClient.setLocOption(option);
			mLocationClient.start();
		}
	};

	@Override
	protected void onHandleIntent(Intent intent) {
		// TODO Auto-generated method stub

	}

}
