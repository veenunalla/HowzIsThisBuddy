package com.pivotaldesign.howzthisbuddy.util;

import android.content.Context;
import android.preference.PreferenceManager;

/**
 * Created by VenuNalla on 10/12/15.
 */
public class SharedPreferenceHelper {

    public static String NOTIFICATION_CONVERSATION = "notification_conversation";


    public static String NOTIFICATION_RINGTONE = "notification_ringtone";
    public static String NOTIFICATION_RINGTONE_POSITION = "notification_ringtone_position";
    public static String NOTIFICATION_RINGTONE_URI = "notification_ringtone_uri";

    public static String NOTIFICATION_SOUND_MODE = "notification_vibrate";
    public static String NOTIFICATION_SOUND_MODE_POSITION = "notification_vibrate_position";
    public static String NOTIFICATION_POPUP_MODE = "notification_popup";
    public static String NOTIFICATION_POPUP_MODE_POSITION = "notification_popup_position";



    public SharedPreferenceHelper() {

    }

    public void storeNotificationRingtoneURI(Context context, String uri) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString(NOTIFICATION_RINGTONE_URI, uri).commit();

    }

    public String getStoreNotificationRingtoneURI(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(NOTIFICATION_RINGTONE_URI, "Default ringtone");
    }

    public void storeConversationTones(Context context,boolean isChecked){
        PreferenceManager.getDefaultSharedPreferences(context).edit().putBoolean(NOTIFICATION_CONVERSATION, isChecked).commit();
    }

    public boolean getStoredConversationTones(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(NOTIFICATION_CONVERSATION, false);
    }

    public void storeNotificationRingToneposition(Context context, int position) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putInt(NOTIFICATION_RINGTONE_POSITION, position).commit();
    }

    public void storeNotificationSoundposition(Context context, int position) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putInt(NOTIFICATION_SOUND_MODE_POSITION, position).commit();

    }

    public void storeNotificationPopUpposition(Context context, int position) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putInt(NOTIFICATION_POPUP_MODE_POSITION, position).commit();
    }


    public void storeNotificationRingTone(Context context, String name) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString(NOTIFICATION_RINGTONE, name).commit();

    }

    public void storeNotificationSound(Context context, String name) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString(NOTIFICATION_SOUND_MODE, name).commit();

    }

    public void storeNotificationPopUp(Context context, String name) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString(NOTIFICATION_POPUP_MODE, name).commit();
    }

    public int getStoreNotificationRingToneposition(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getInt(NOTIFICATION_RINGTONE_POSITION, 0);

    }

    public int getStoreNotificationSoundposition(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getInt(NOTIFICATION_SOUND_MODE_POSITION, 1);

    }

    public int getStoreNotificationPopUpposition(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getInt(NOTIFICATION_POPUP_MODE_POSITION, 3);
    }

    public String getStoreNotificationRingTone(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(NOTIFICATION_RINGTONE, "Default ringtone");

    }

    public String getStoreNotificationSound(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(NOTIFICATION_SOUND_MODE, "Default");

    }

    public String getStoreNotificationPopUp(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(NOTIFICATION_POPUP_MODE, "Always show popup");
    }
}
