package com.pivotaldesign.howzthisbuddy;

import java.util.Timer;
import java.util.TimerTask;

import android.app.KeyguardManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.hardware.display.DisplayManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.PowerManager;
import android.util.Log;
import android.view.Display;
import android.widget.Toast;

import com.google.android.gcm.GCMBaseIntentService;
import com.pivotaldesign.howzthisbuddy.Controllers.PopupViewActivity;
import com.pivotaldesign.howzthisbuddy.R;
import com.pivotaldesign.howzthisbuddy.util.SharedPreferenceHelper;

public class GCMIntentService extends GCMBaseIntentService {

	private static final String TAG = "GCM Tutorial::Service";

	private  final int VIBRATE_OFF = 0;
	private  final int VIBRATE_DEFAULT = 1;
	private  final int VIBRATE_SHORT = 2;
//	private  final int VIBRATE_LONG = 3;
	private  final int POPUP_OFF = 0;
	private  final int POPUP_ON_WHEN_SCREEN_ON = 1;
	private  final int POPUP_ON_WHEN_SCREEN_OFF = 2;
//	private  final int POPUP_ALWAYS_SHOW = 3;


	// Use your PROJECT ID from Google API into SENDER_ID
	public static final String SENDER_ID = "24397121204";

	public GCMIntentService() {
		super(SENDER_ID);
	}

	private boolean isConversationTonesEnabled;
	private String getNotificationRingtoneUri;
	private int vibrateType;
	private int popUpType;
	private String message;
	private Uri notificationSoundURI;
	private boolean noVibrate;
	private long[] vibrateMode;
	private long[] vibrateLong = new long[] { 1000, 1000, 1000, 1000, 1000,1000,1000 };
	private long[] vibrateShort = new long[] { 1000, 1000};
	private long[] vibrateDefault = new long[] { 1000, 1000, 1000, 1000 };


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


		getDefaultValuesFromSharedPreference(context);

		boolean screenLocked = getDisplayStatus(context);


		if (getNotificationRingtoneUri.equals("Default ringtone"))
		{
			// define sound URI, the sound to be played when there's a notification
			notificationSoundURI = RingtoneManager
					.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
		}else {
			notificationSoundURI = Uri.parse(getNotificationRingtoneUri);
		}
		noVibrate = false;
		vibrateMode = new long[0];
		if (vibrateType == VIBRATE_OFF){
			noVibrate = true;
		}
		else if (vibrateType == VIBRATE_DEFAULT){
			vibrateMode = vibrateDefault;
			// Create the notification with a notification builder

		}else if (vibrateType == VIBRATE_SHORT){
			vibrateMode = vibrateShort;
			// Create the notification with a notification builder

		}else {
			vibrateMode = vibrateLong;
			// Create the notification with a notification builder
		}
		if (popUpType == POPUP_OFF){


		}else if (popUpType == POPUP_ON_WHEN_SCREEN_ON){
			if (!screenLocked){
				Intent intent = new Intent(context, PopupViewActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				this.getApplicationContext().startActivity(intent);
//				showNotification(data, context);
			}
		}else if (popUpType == POPUP_ON_WHEN_SCREEN_OFF){
			if (screenLocked){
				showNotification(data, context);
			}
		}else {
			Intent intent = new Intent(context, PopupViewActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			this.getApplicationContext().startActivity(intent);
			showNotification(data,context);
		}
	}

	private void showNotification(Intent data, Context context){

		Notification notification;

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
		if (noVibrate){
			notification = new Notification.Builder(this)
					.setSmallIcon(R.drawable.launcher_icon)
					.setContentTitle("HowzthisBuddy")
					.setContentIntent(pIntent)
					.setSound(notificationSoundURI)
					.setContentText(message).setContentIntent(pIntent)
					.setPriority(Notification.PRIORITY_HIGH).build();
		}else {
			notification = new Notification.Builder(this)
					.setSmallIcon(R.drawable.launcher_icon)
					.setContentTitle("HowzthisBuddy")
					.setContentIntent(pIntent)
					.setSound(notificationSoundURI)
					.setVibrate(vibrateMode)
					.setContentText(message).setContentIntent(pIntent)
					.setPriority(Notification.PRIORITY_HIGH).build();
		}
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

	private boolean getDisplayStatus(Context context) {
		boolean isLocked = false;
		KeyguardManager myKM = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
		if( myKM.inKeyguardRestrictedInputMode()) {
			isLocked = true;
		} else {
			isLocked = false;
		}
		return isLocked;
	}

	private void getDefaultValuesFromSharedPreference(Context context) {
		SharedPreferenceHelper sharedPreferenceHelper = new SharedPreferenceHelper();
		isConversationTonesEnabled = sharedPreferenceHelper.getStoredConversationTones(context);
		getNotificationRingtoneUri = sharedPreferenceHelper.getStoreNotificationRingtoneURI(context);
		vibrateType = sharedPreferenceHelper.getStoreNotificationSoundposition(context);
		popUpType = sharedPreferenceHelper.getStoreNotificationPopUpposition(context);
		Log.d("Nme", "Na");
	}

	@Override
	protected void onError(Context arg0, String errorId) {

		Log.e(TAG, "onError: errorId=" + errorId);
	}

}