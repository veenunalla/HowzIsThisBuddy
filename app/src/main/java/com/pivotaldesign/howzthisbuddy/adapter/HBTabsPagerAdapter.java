/**
 * 
 */
package com.pivotaldesign.howzthisbuddy.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.pivotaldesign.howzthisbuddy.fragments.HBOpinionGivenFragment;

/**
 * @author Satish Kolawale
 *
 */
public class HBTabsPagerAdapter extends FragmentPagerAdapter {


	public HBTabsPagerAdapter(FragmentManager fm) {
		super(fm);
	}
	 
	@Override
	public Fragment getItem(int index) {
		Fragment fragment = null;
		switch (index) {
	        case 0:
	            // Top Rated fragment activity
	        	fragment =  new HBOpinionGivenFragment();
	        case 1:
	            // Games fragment activity
	        	fragment =  new HBOpinionGivenFragment();
	        
        }
		return fragment;
	}

	 
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 2;
	}

}
