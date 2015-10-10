package com.pivotaldesign.howzthisbuddy;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.PowerManager;
import android.util.Log;

import com.google.android.gcm.GCMBaseIntentService;
import com.pivotaldesign.howzthisbuddy.R;

public class GCMIntentService extends GCMBaseIntentService {

	private static final String TAG = "GCM Tutorial::Service";

	// Use your PROJECT ID from Google API into SENDER_ID
	public static final String SENDER_ID = "24397121204";

	public GCMIntentService() {
		super(SENDER_ID);
	}

	@Override
	protected void onRegistered(Context context, String registrationId) {

		Log.i(TAG, "onRegistered: registrationId=" + registrationId);
	}

	@Override
	protected void onUnregistered(Context context, String registrationId) {

		Log.i(TAG, "onUnregistered: registrationId=" + registrationId);
	}

	@Override
	protected void onMessage(Context context, Intent data) {
		String message;

		// define sound URI, the sound to be played when there's a notification
		Uri soundUri = RingtoneManager
				.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

		// Message from PHP server
		message = data.getStringExtra("message");
		// Open a new activity called GCMMessageView
		Intent intent = new Intent(this, GCMMessageView.class);
		// Pass data to the new activity
		intent.putExtra("message", message);
		// Starts the activity on notification click
		/*PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent,
				PendingIntent.FLAG_UPDATE_CURRENT);
		*/
		PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent,
				0);
		
		// Create the notification with a notification builder
		Notification notification = new Notification.Builder(this)
				.setSmallIcon(R.drawable.launcher_icon)
				.setContentTitle("HowzthisBuddy")
				.setContentIntent(pIntent)
				.setSound(soundUri)
				.setContentText(message).setContentIntent(pIntent)
				.setPriority(Notification.PRIORITY_HIGH)
				.addAction(R.drawable.launcher_icon, "View", pIntent).addAction(0, "Remind", pIntent).build();
		
		// Remove the notification on click
		notification.flags |= Notification.FLAG_AUTO_CANCEL;

		NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		//manager.notify(R.string.app_name, notification);
		manager.notify(0, notification);
		{
			// Wake Android Device when notification received
			PowerManager pm = (PowerManager) context
					.getSystemService(Context.POWER_SERVICE);
			final PowerManager.WakeLock mWakelock = pm.newWakeLock(
					PowerManager.FULL_WAKE_LOCK
							| PowerManager.ACQUIRE_CAUSES_WAKEUP, "GCM_PUSH");
			mWakelock.acquire();

			// Timer before putting Android Device to sleep mode.
			Timer timer = new Timer();
			TimerTask task = new TimerTask() {
				public void run() {
					mWakelock.release();
				}
			};
			timer.schedule(task, 5000);
		}

	}

	@Override
	protected void onError(Context arg0, String errorId) {

		Log.e(TAG, "onError: errorId=" + errorId);
	}

}