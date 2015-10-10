/**
 * 
 */
package com.pivotaldesign.howzthisbuddy.fragments;

import com.pivotaldesign.howzthisbuddy.R;
import com.pivotaldesign.howzthisbuddy.application.HBApplication;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * @author Satish Kolawale
 *
 */
public class HBAboutFragment extends Fragment{

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_about, container, false);
        ((TextView) rootView.findViewById(R.id.txt_about_title)).setTypeface(HBApplication.getInstance().getRegularFont());
        ((TextView) rootView.findViewById(R.id.txt_about_version)).setTypeface(HBApplication.getInstance().getRegularFont());
        ((TextView) rootView.findViewById(R.id.txt_about_visit_site)).setTypeface(HBApplication.getInstance().getRegularFont());
        ((TextView) rootView.findViewById(R.id.txt_about_site_address)).setTypeface(HBApplication.getInstance().getRegularFont());
        ((TextView) rootView.findViewById(R.id.txt_about_copyright)).setTypeface(HBApplication.getInstance().getRegularFont());
        ((Button) rootView.findViewById(R.id.btn_about_email)).setTypeface(HBApplication.getInstance().getRegularFont());
        ((Button) rootView.findViewById(R.id.btn_privacy_policy)).setTypeface(HBApplication.getInstance().getRegularFont());
        ((Button) rootView.findViewById(R.id.btn_about_terms)).setTypeface(HBApplication.getInstance().getRegularFont());
         
        return rootView;
    }
}
