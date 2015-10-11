package com.pivotaldesign.howzthisbuddy.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.pivotaldesign.howzthisbuddy.R;

import java.util.ArrayList;

/**
 * Created by VenuNalla on 10/11/15.
 */
public class HBNotificationRingtoneAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<String> notificationRingtoneList;

    public HBNotificationRingtoneAdapter(Context context, ArrayList<String> ringtonesList) {
        this.mContext = context;
        this.notificationRingtoneList = ringtonesList;
    }

    @Override
    public int getCount() {
        return notificationRingtoneList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = mInflater.inflate(R.layout.layout_notification_ringtone_item, null);
        }
        TextView notificationRingtoneText = (TextView) view.findViewById(R.id.notification_ringtone_item);
        notificationRingtoneText.setText(notificationRingtoneList.get(position));
        return view;
    }
}
