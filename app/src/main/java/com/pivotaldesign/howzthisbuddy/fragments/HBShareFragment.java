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
import android.widget.TextView;

/**
 * @author Satish Kolawale
 *
 */
public class HBShareFragment extends Fragment{

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_share, container, false);
        
        ((TextView) rootView.findViewById(R.id.txt_share_with_friend_title)).setTypeface(HBApplication.getInstance().getRegularFont());
        ((TextView) rootView.findViewById(R.id.txt_share_with_friende_share)).setTypeface(HBApplication.getInstance().getRegularFont());
        ((TextView) rootView.findViewById(R.id.txt_share_facebook)).setTypeface(HBApplication.getInstance().getBoldFont());
        ((TextView) rootView.findViewById(R.id.txt_share_twitter)).setTypeface(HBApplication.getInstance().getBoldFont());
        ((TextView) rootView.findViewById(R.id.txt_share_google)).setTypeface(HBApplication.getInstance().getBoldFont());
        ((TextView) rootView.findViewById(R.id.txt_share_email)).setTypeface(HBApplication.getInstance().getBoldFont());
        
        
        return rootView;
    }
}
