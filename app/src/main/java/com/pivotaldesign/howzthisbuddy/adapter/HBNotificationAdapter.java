package com.pivotaldesign.howzthisbuddy.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.pivotaldesign.howzthisbuddy.R;
import com.pivotaldesign.howzthisbuddy.application.HBApplication;
import com.pivotaldesign.howzthisbuddy.fragments.HBNotificationFragment;

import java.util.ArrayList;

/**
 * Created by VenuNalla on 10/11/15.
 */
public class HBNotificationAdapter extends BaseAdapter implements CompoundButton.OnCheckedChangeListener {

    private ArrayList<HBNotificationFragment.NotificationModel> notificationModelArrayList;
    private Context mContext;
    private Typeface _typefaceRegular = null;


    public HBNotificationAdapter(Context context, ArrayList<HBNotificationFragment.NotificationModel> notificationModels) {
        this.notificationModelArrayList = notificationModels;
        this.mContext = context;
        _typefaceRegular = HBApplication.getInstance().getRegularFont();

    }


    @Override
    public int getCount() {
        return notificationModelArrayList.size();
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
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        Log.d("Status is Checked ", "" + b);
    }

    private static class ViewHolder {
        TextView notifciationItem, notificationSubItem;
        CheckBox notificationConversationBox;
    }

    ViewHolder holder = null;

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = mInflater.inflate(R.layout.layout_notification_listview_item, null);
        }
        holder = new ViewHolder();
        holder.notifciationItem = (TextView) view.findViewById(R.id.textView_notification_item);
        holder.notificationSubItem = (TextView) view.findViewById(R.id.textView_notification_subitem);
        holder.notifciationItem.setTypeface(HBApplication.getInstance().getRegularFont());
        holder.notificationSubItem.setTypeface(HBApplication.getInstance().getRegularFont());
        view.setTag(holder);

        HBNotificationFragment.NotificationModel notificationModel = notificationModelArrayList.get(position);

        holder.notifciationItem.setText(notificationModel.getNotificationItemLabel());
        holder.notificationSubItem.setText(notificationModel.getNotificationSubItemLabel());

        holder.notificationConversationBox = (CheckBox) view.findViewById(R.id.checkbox_conversation);
        holder.notificationConversationBox.setTag(position);
        holder.notificationConversationBox.setOnCheckedChangeListener(this);
        if (position == 0) {
            holder.notificationConversationBox.setVisibility(View.VISIBLE);
        } else {
            holder.notificationConversationBox.setVisibility(View.GONE);
        }
        return view;
    }
}
