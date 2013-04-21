package cn.wang.yin.ui;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import cn.wang.yin.personal.R;
import cn.wang.yin.personal.service.HandlerService;
import cn.wang.yin.utils.CollectGpsUtil;
import cn.wang.yin.utils.PersonConstant;
import cn.wang.yin.utils.PersonDbUtils;
import cn.wang.yin.utils.PersonStringUtils;

import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;

@SuppressLint("NewApi")
public class LocationMainActivity extends Activity {
	public static String mtag = "MainActivity";
	Button button1;
	public static TextView textView1;
	int i = 0;
	public static final int FAIL = 0;
	public static final int SUCCESS = 1;
	public static final int SAVE = 3;
	final Timer timer = new Timer();
	TimerTask task;
	Timer uploadTimer = new Timer();
	TimerTask uploadTask;
	SeekBar seekBar1;
	SeekBar seekBar2;
	TextView seekBar2_textView;
	TextView seekBar1_textView;
	public static LocationClient mLocationClient = null;
	TelephonyManager telephonyManager;
	public static long locationTime = PersonConstant.WAIT_TIMS;
	public static long uploadTime = PersonConstant.UPLOAD_TIMS;
	private static final String TAG = "PushDemoActivity";
	public static final String RESPONSE_METHOD = "method";
	public static final String RESPONSE_CONTENT = "content";
	public static final String RESPONSE_ERRCODE = "errcode";
	public static final String EXTRA_MESSAGE = "message";
	public static final String ACTION_MESSAGE = "com.baiud.pushdemo.action.MESSAGE";
	public static final String ACTION_RESPONSE = "bccsclient.action.RESPONSE";
	public static final String ACTION_SHOW_MESSAGE = "bccsclient.action.SHOW_MESSAGE";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		PersonDbUtils.init(getApplicationContext());
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		textView1 = (TextView) findViewById(R.id.textView1);
		seekBar1_textView = (TextView) findViewById(R.id.seekBar1_textView);
		seekBar2_textView = (TextView) findViewById(R.id.seekBar2_textView);
		// push("开始启动服务");
		startService(new Intent(getApplicationContext(), HandlerService.class));
		seekBar1 = (SeekBar) findViewById(R.id.seekBar1);
		seekBar2 = (SeekBar) findViewById(R.id.seekBar2);
		seekBar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {

			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				progress = progress > 0 ? progress : 1;
				if (fromUser) {
					locationTime = 1000 * progress;
				}
//				Intent intent = new Intent(
//						"cn.wang.yin.personal.service.PushMessageReceiver");
//				intent.putExtra("tab", 1);
//				sendBroadcast(intent);
				seekBar1_textView.setText("定位间隔为：" + locationTime / 1000 + "秒");
			}
		});
		seekBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {

			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				progress = progress > 0 ? progress : 1;
				if (fromUser) {
					uploadTime = 1000 * progress;
				}
				seekBar2_textView.setText("上传间隔为：" + uploadTime / 1000 + "秒");

			}
		});
//		PushManager.startWork(getApplicationContext(),
//				PushConstants.LOGIN_TYPE_API_KEY, PersonConstant.API_KEY);
//		PushConstants.restartPushService(this);
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
		handleIntent(intent);
	}

	/**
	 * 处理Intent
	 * 
	 * @param intent
	 *            intent
	 */
	private void handleIntent(Intent intent) {
		String action = intent.getAction();
		Log.d(TAG, "Handle intent: \r\n" + intent);

		if (ACTION_RESPONSE.equals(action)) {

			String method = intent.getStringExtra(RESPONSE_METHOD);

			if (PushConstants.METHOD_BIND.equals(method)) {
				Log.d(TAG, "Handle bind response");
				String toastStr = "";
				int errorCode = intent.getIntExtra(RESPONSE_ERRCODE, 0);
				if (errorCode == 0) {
					String content = intent.getStringExtra(RESPONSE_CONTENT);
					String appid = "";
					String channelid = "";
					String userid = "";

					try {
						JSONObject jsonContent = new JSONObject(content);
						JSONObject params = jsonContent
								.getJSONObject("response_params");
						appid = params.getString("appid");
						channelid = params.getString("channel_id");
						userid = params.getString("user_id");
					} catch (JSONException e) {
						Log.e(TAG, "Parse bind json infos error: " + e);
					}

					SharedPreferences sp = PreferenceManager
							.getDefaultSharedPreferences(this);
					Editor editor = sp.edit();
					editor.putString("appid", appid);
					editor.putString("channel_id", channelid);
					editor.putString("user_id", userid);
					editor.commit();

					// showChannelIds();

					toastStr = "Bind Success";
				} else {
					toastStr = "Bind Fail, Error Code: " + errorCode;
					if (errorCode == 30607) {
						Log.d("Bind Fail", "update channel token-----!");
					}
				}

				Toast.makeText(this, toastStr, Toast.LENGTH_LONG).show();
			}
		} else if (ACTION_MESSAGE.equals(action)) {
			String message = intent.getStringExtra(EXTRA_MESSAGE);
			String summary = "Receive message from server:\n\t";
			JSONObject contentJson = null;
			String contentStr = message;
			try {
				contentJson = new JSONObject(message);
				contentStr = contentJson.toString(4);
			} catch (JSONException e) {
				Log.d(TAG, "Parse message json exception.");
			}
			summary += contentStr;
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage(summary);
			builder.setCancelable(true);
			Dialog dialog = builder.create();
			dialog.setCanceledOnTouchOutside(true);
			dialog.show();
		} else {
			Log.i(TAG, "Activity normally start!");
		}
	}

	public static Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {

			// /定时执行任务
			// ////////////////////////////////////////////////////////////////
			switch (msg.what) {
			case 1:

				break;
			case 2:

				break;
			case 3:

				break;
			case 4: {

			}
				break;
			case 5: {
				if (msg.obj != null) {
					push(msg.obj.toString());
				}
				// Log.i(mtag, textView1.getText().toString());
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

	static int k = 0;

	public static void push(String str) {
		k++;
		if (k > 60) {
			textView1.setText("");
			k = 0;
		}
		textView1
				.setText(PersonStringUtils.pareDateToString(new Date())
						+ "\n"
						+ str
						+ "\n"
						+ "------------------------------------------------------------\n"
						+ textView1.getText() + "\n");
	}

	@Override
	protected void onResume() {

		super.onResume();
	}

	@Override
	protected void onStart() {
		// mLocationClient.start();
		super.onStart();
//		Log.d(TAG, ">=====onStart=====<");
//		Intent recIntent = this.getIntent();
//		String openType = ""
//				+ recIntent.getIntExtra(PushConstants.EXTRA_OPENTYPE, 0);
//		String msgId = recIntent.getStringExtra(PushConstants.EXTRA_MSGID);
//
//		Log.d(TAG,
//				"Collect Activity start feedback info , package:"
//						+ this.getPackageName() + " openType: " + openType
//						+ " msgid: " + msgId);
//
//		PushManager.activityStarted(this);
	}

	@Override
	protected void onStop() {
		super.onStop();
	//	PushManager.activityStoped(this);
	}

}
