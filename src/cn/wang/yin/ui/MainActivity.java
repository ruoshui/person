package cn.wang.yin.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import cn.wang.yin.personal.service.PersonService;
import cn.wang.yin.utils.PersonDbUtils;
import cn.wang.yin.utils.SIMCardInfo;

import com.wang.yin.personal.R;

@SuppressLint("NewApi")
public class MainActivity extends Activity {
	Button button1;
	public TextView textView1;
	int i = 0;
	public static final int FAIL = 0;
	public static final int SUCCESS = 1;

	// private static List<String> listTmp = new ArrayList();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		PersonDbUtils.init(getApplicationContext());
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		button1 = (Button) findViewById(R.id.button1);
		textView1 = (TextView) findViewById(R.id.textView1);
		//SIMCardInfo.init(getApplicationContext());
		handler.post(runnnable);
		button1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(),
						PersonService.class);
				stopService(intent);
				textView1.setText(textView1.getText() + "\n" + "停止服务");
			}
		});
		float fv = Float.valueOf(android.os.Build.VERSION.RELEASE.substring(0,
				3).trim());
		if (fv > 2.3) {
			StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
					.detectDiskReads().detectDiskWrites().detectNetwork() // 这里可以替换为detectAll()
																			// 就包括了磁盘读写和网络I/O
					.penaltyLog() // 打印logcat，当然也可以定位到dropbox，通过文件保存相应的log
					.build());
			StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
					.detectLeakedSqlLiteObjects() // 探测SQLite数据库操作
					.penaltyLog() // 打印logcat
					.penaltyDeath().build());
		}
		
		System.out.println(android.os.Build.VERSION.RELEASE);

	}

	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {

			case SUCCESS:

				break;
			case FAIL:
				break;
			}
		}

	};
	Runnable runnnable = new Runnable() {
		@Override
		public void run() {
			Intent intent = new Intent(getApplicationContext(),
					PersonService.class);
			startService(intent);
			textView1.setText(textView1.getText() + "\n" + "启动服务");
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
		super.onResume();
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

}
