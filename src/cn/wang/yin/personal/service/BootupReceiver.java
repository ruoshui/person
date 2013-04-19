package cn.wang.yin.personal.service;

//--------------------------------- IMPORTS ------------------------------------
import android.app.KeyguardManager;
import android.app.KeyguardManager.KeyguardLock;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootupReceiver extends BroadcastReceiver {

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.content.BroadcastReceiver
	 * #onReceive(android.content.Context, android.content.Intent)
	 */
	@Override
	public void onReceive(Context context, Intent intent) {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Intent inten = new Intent(context, HandlerService.class);
		inten.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startService(inten);

	}

}
