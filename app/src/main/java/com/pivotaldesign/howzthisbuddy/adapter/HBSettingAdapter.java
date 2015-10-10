package com.pivotaldesign.howzthisbuddy.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pivotaldesign.howzthisbuddy.R;
import com.pivotaldesign.howzthisbuddy.fragments.HBSettingsFragment;
import com.pivotaldesign.howzthisbuddy.model.HBDrawerItem;

import java.util.ArrayList;

/**
 * Created by VenuNalla on 10/10/15.
 */
public class HBSettingAdapter extends BaseAdapter {

    private Context _context = null;
    private ArrayList<HBSettingsFragment.SettingsModel> _settingsItems = null;

    public HBSettingAdapter(Context context, ArrayList<HBSettingsFragment.SettingsModel> settingsItems) {
        this._context = context;
        this._settingsItems = settingsItems;
    }

    @Override
    public int getCount() {
        return _settingsItems.size();
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
            LayoutInflater mInflater = (LayoutInflater) _context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = mInflater.inflate(R.layout.layout_settings_list_item, null);
        }
        // model class to get name and thumb
        HBSettingsFragment.SettingsModel settings_model_item = new HBSettingsFragment.SettingsModel();
        settings_model_item = _settingsItems.get(position);

        // get image from drawable folder
        int image_drawable_id = _context.getResources().getIdentifier(settings_model_item.getSettingItemThumbImage(), "drawable", _context.getPackageName());

        ImageView settings_item_image = (ImageView) view.findViewById(R.id.settings_item_imageview);
        settings_item_image.setImageResource(image_drawable_id);
        TextView setting_item_textview = (TextView) view.findViewById(R.id.settings_item_textview);
        setting_item_textview.setText(settings_model_item.getSettingItemName());


        return view;
    }
}
