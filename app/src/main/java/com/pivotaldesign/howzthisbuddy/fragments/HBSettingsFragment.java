/**
 * 
 */
package com.pivotaldesign.howzthisbuddy.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.pivotaldesign.howzthisbuddy.R;
import com.pivotaldesign.howzthisbuddy.adapter.HBSettingAdapter;
import com.pivotaldesign.howzthisbuddy.application.HBApplication;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * @author Satish Kolawale
 *
 */
public class HBSettingsFragment extends Fragment{

    private ListView settingsListview;
    private ArrayList<SettingsModel> settingsItems;
    private HBSettingAdapter settingAdapter;

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);

        initUI(rootView);
        addSettingsItemsToListView();
        settingAdapter = new HBSettingAdapter(getActivity().getApplicationContext(), settingsItems);
        settingsListview.setAdapter(settingAdapter);
        settingsListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if (position == 0) {
                    FragmentManager fragmentManager2 = getFragmentManager();
                    FragmentTransaction fragmentTransaction2 = fragmentManager2.beginTransaction();
                    HBNotificationFragment fragment2 = new HBNotificationFragment();
                    fragmentTransaction2.replace(R.id.frame_container, fragment2);
                    fragmentTransaction2.addToBackStack(null);
                    fragmentTransaction2.commit();
                }
            }
        });

//        ((TextView) rootView.findViewById(R.id.txt_settings_settings_title)).setTypeface(HBApplication.getInstance().getRegularFont());
//        ((TextView) rootView.findViewById(R.id.txt_settings_done)).setTypeface(HBApplication.getInstance().getRegularFont());
//
//        ((TextView) rootView.findViewById(R.id.txt_settings_notification_title)).setTypeface(HBApplication.getInstance().getRegularFont());
//        ((TextView) rootView.findViewById(R.id.txt_settings_location_title)).setTypeface(HBApplication.getInstance().getRegularFont());
        
        return rootView;
    }

    private void initUI(View rootView) {
        TextView settingsTitle = (TextView) rootView.findViewById(R.id.settings_title_label);
        settingsTitle.setTypeface(HBApplication.getInstance().getRegularFont());
        settingsListview = (ListView) rootView.findViewById(R.id.settings_listview);
    }

    private void addSettingsItemsToListView() {
        settingsItems = new ArrayList<SettingsModel>();
        SettingsModel settingsModel = new SettingsModel();
        settingsModel.setSettingItemName("Profile");
        settingsModel.setSettingItemThumbImage("settings_profile");
        settingsItems.add(settingsModel);
        settingsModel = new SettingsModel();
        settingsModel.setSettingItemName("Notifications");
        settingsModel.setSettingItemThumbImage("settings_notification");
        settingsItems.add(settingsModel);
        settingsModel = new SettingsModel();
        settingsModel.setSettingItemName("My Location");
        settingsModel.setSettingItemThumbImage("settings_location");
        settingsItems.add(settingsModel);
    }


    public static class SettingsModel {
        private String settingItemName;
        private String settingItemThumbImage;

        public String getSettingItemName() {
            return settingItemName;
        }

        public void setSettingItemName(String settingItemName) {
            this.settingItemName = settingItemName;
        }

        public String getSettingItemThumbImage() {
            return settingItemThumbImage;
        }

        public void setSettingItemThumbImage(String settingItemThumbImage) {
            this.settingItemThumbImage = settingItemThumbImage;
        }


    }
}
