/**
 * 
 */
package com.pivotaldesign.howzthisbuddy.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pivotaldesign.howzthisbuddy.R;
import com.pivotaldesign.howzthisbuddy.application.HBApplication;

/**
 * @author Satish Kolawale
 *
 */
public class HBSettingsFragment extends Fragment{

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);
        
        ((TextView) rootView.findViewById(R.id.txt_settings_settings_title)).setTypeface(HBApplication.getInstance().getRegularFont());
        ((TextView) rootView.findViewById(R.id.txt_settings_done)).setTypeface(HBApplication.getInstance().getRegularFont());
        
        ((TextView) rootView.findViewById(R.id.txt_settings_notification_title)).setTypeface(HBApplication.getInstance().getRegularFont());
        ((TextView) rootView.findViewById(R.id.txt_settings_location_title)).setTypeface(HBApplication.getInstance().getRegularFont());
        
        return rootView;
    }
}
