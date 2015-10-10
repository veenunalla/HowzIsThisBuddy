/**
 * 
 */
package com.pivotaldesign.howzthisbuddy.fragments;

import com.pivotaldesign.howzthisbuddy.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author Satish Kolawale
 *
 */
public class HBOpinionGivenFragment extends Fragment {

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
         
        return rootView;
    }
}
