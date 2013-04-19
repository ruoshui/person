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
		// KeyguardManager keyguardManager =
		// (KeyguardManager)getSystemService(KEYGUARD_SERVICE);
		// KeyguardLock lock =
		// keyguardManager.newKeyguardLock(KEYGUARD_SERVICE);
		Intent inten = new Intent(context, PersonService.class);
		inten.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startService(inten);

	}

}
