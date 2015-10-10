/**
 * 
 */
package com.pivotaldesign.howzthisbuddy;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.pivotaldesign.howzthisbuddy.adapter.HBDrawerListAdapter;
import com.pivotaldesign.howzthisbuddy.bean.ResponseContactInfBO;
import com.pivotaldesign.howzthisbuddy.fragments.HBAboutFragment;
import com.pivotaldesign.howzthisbuddy.fragments.HBGivenFragment;
import com.pivotaldesign.howzthisbuddy.fragments.HBOfferFragment;
import com.pivotaldesign.howzthisbuddy.fragments.HBProfileFragment;
import com.pivotaldesign.howzthisbuddy.fragments.HBReceivedFragment;
import com.pivotaldesign.howzthisbuddy.fragments.HBRequestLaunchFragment;
import com.pivotaldesign.howzthisbuddy.fragments.HBSettingsFragment;
import com.pivotaldesign.howzthisbuddy.fragments.HBShareFragment;
import com.pivotaldesign.howzthisbuddy.model.HBConstants;
import com.pivotaldesign.howzthisbuddy.model.HBDrawerItem;
import com.pivotaldesign.howzthisbuddy.model.HBNotifier;
import com.pivotaldesign.howzthisbuddy.util.AppUtilities;
import com.pivotaldesign.howzthisbuddy.util.CheckInternet;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

/**
 * @author Satish Kolawale
 *
 */
public class HBHomeActivity extends Activity implements HBNotifier{

	
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	private int _currentPageIndex = 0;


	
	// slide menu items
	private String[] navMenuTitles;

	private ArrayList<HBDrawerItem> navDrawerItems;
	private HBDrawerListAdapter adapter;
	
	private AppUtilities au;
	private ArrayList<String> list_numbers;
	public static ArrayList<String> list_response_numbers;
	public static ArrayList<String> list_response_name;
	private Thread thread;
	private ProgressDialog progress;
	public Handler handler = new Handler();

	private SharedPreferences hbh_spf_login_details;
	private String hbha_str_getcontacts_resp;
	private JSONArray hbh_jarr_contact_resp=null;
	private SharedPreferences hbh_spf_contact_resp;
	private SharedPreferences hbh_spf_callingapp_details;
	private ResponseContactInfBO rcib;
	private CheckInternet ci;
	public static ArrayList<ResponseContactInfBO> al_rcib=new ArrayList<ResponseContactInfBO>();
	
	
	
    protected void onCreate(android.os.Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_home);
		ci=new CheckInternet(getApplicationContext());
		hbh_spf_contact_resp=getSharedPreferences("resp_contacts_info",0);
        hbh_spf_login_details=getSharedPreferences("loginprefs",0);
        hbh_spf_callingapp_details=getSharedPreferences("callingappcredentials", 0);

		au=new AppUtilities(getApplicationContext());
		// load slide menu items
		navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

		// nav drawer icons from resources
		/*navMenuIcons = getResources().obtainTypedArray(R.array.nav_drawer_icons);*/

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.list_slidermenu);
		
		
		navDrawerItems = new ArrayList<HBDrawerItem>();

		
		// adding nav drawer items to array
		//navDrawerItems.add(new HBDrawerItem(navMenuTitles[0], R.drawable.opinions_received));
		navDrawerItems.add(new HBDrawerItem(navMenuTitles[0], R.drawable.opinions_received));
		navDrawerItems.add(new HBDrawerItem(navMenuTitles[1], R.drawable.opinions_given));
		//navDrawerItems.add(new HBDrawerItem(navMenuTitles[3], R.drawable.opinions_given));
		navDrawerItems.add(new HBDrawerItem(navMenuTitles[2], R.drawable.profile));
		navDrawerItems.add(new HBDrawerItem(navMenuTitles[3], R.drawable.settings));
		navDrawerItems.add(new HBDrawerItem(navMenuTitles[4], R.drawable.share_with_friends));
		navDrawerItems.add(new HBDrawerItem(navMenuTitles[5], R.drawable.hows));
		navDrawerItems.add(new HBDrawerItem(navMenuTitles[6], R.drawable.offers));
		
		mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

		// setting the nav drawer list adapter
		adapter = new HBDrawerListAdapter(getApplicationContext(), navDrawerItems);
		mDrawerList.setAdapter(adapter);
		
		ActionBar actionBar = getActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        actionBar.setIcon(new ColorDrawable(getResources().getColor(android.R.color.transparent))); 
        actionBar.setCustomView(R.layout.layout_actionbar);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
		
		
	 
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.menu_icon, //nav menu toggle icon
				R.string.app_name, // nav drawer open - description for accessibility
				R.string.app_name // nav drawer close - description for accessibility
		) {
			public void onDrawerClosed(View view) {
				//getActionBar().setTitle(mTitle);
				// calling onPrepareOptionsMenu() to show action bar icons
				 
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				//getActionBar().setTitle(mDrawerTitle);
				// calling onPrepareOptionsMenu() to hide action bar icons
				 
				invalidateOptionsMenu();
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) {
			// on first time display view for first nav item
			String name=hbh_spf_login_details.getString("username", "");
			String status=hbh_spf_callingapp_details.getString("status", "");
			if(status.length()==0){
				if(name.length()==0){
					displayView(2);
					//displayView(0);
				}else{
					displayView(0);
				}
			}else{
				Editor et=hbh_spf_callingapp_details.edit();
				et.putString("status", "");
				et.commit();
				/*Intent in=new Intent();
				in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				in.setClass(HBHomeActivity.this, HBRequestLaunchFragment.class);
				getApplicationContext().startActivity(in);*/
				
				FragmentManager fm = getFragmentManager();

				FragmentTransaction ft = fm.beginTransaction();

				HBRequestLaunchFragment llf = new HBRequestLaunchFragment();

				ft.replace(R.id.frame_container, llf);

				ft.commit();

			}
		}
		
		///enable to update contacts
		
		//fetchContacts();
	}
	
	
	/**
	 * Slide menu item click listener
	 * */
	private class SlideMenuClickListener implements ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			// display view for selected nav drawer item
			displayView(position);
		}
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		//getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	

	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// toggle nav drawer on selecting action bar app icon/title
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}		
		return false;
	}
	
	/**
	 * Diplaying fragment view for selected nav drawer list item
	 * */
	private void displayView(int position) {
		// update the main content by replacing fragments
		_currentPageIndex = position;
		Fragment fragment = null;
		switch (position) {
		
			/*case 0:
				fragment = new HBRequestLaunchFragment();
				break;*/
			case 0:
				fragment = new HBReceivedFragment();
				break;
			case 1:
				fragment = new HBGivenFragment(this);
				break;
			/*case 3:
				fragment = new HBResponseFragment(this);
				break;*/
			case 2:
				fragment = new HBProfileFragment();
				break;
			case 3:
				fragment = new HBSettingsFragment();
				break;
			case 4:
				fragment = new HBShareFragment();
				break;				
			case 5:
				fragment = new HBAboutFragment();
				break;
			case 6:
				fragment = new HBOfferFragment();
			default:
				break;
		}

		if (fragment != null) {
			FragmentManager fragmentManager = getFragmentManager();
			fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();

			// update selected item and title, then close the drawer
			mDrawerList.setItemChecked(position, true);
			mDrawerList.setSelection(position);
			setTitle(navMenuTitles[position]);
			mDrawerLayout.closeDrawer(mDrawerList);
		} else {
			// error in creating fragment
			Log.e("MainActivity", "Error in creating fragment");
			 
		}
	}
	
	@Override
	public void setTitle(CharSequence title) {
		//getActionBar().setTitle(mTitle);
		getActionBar().setTitle("");
	}

	
	/**
	 * When using the ActionBarDrawerToggle, you must call it during
	 * onPostCreate() and onConfigurationChanged()...
	 */
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}



	@Override
	public void notifier(int notifierId) {
		if(notifierId == 3)
			displayView(3);
	}
	
	/*@Override
	public void onBackPressed() {
		if(_currentPageIndex != 0)
			displayView(0);
		else
			super.onBackPressed();
	}*/
	
	
	@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
       
        	   Intent homeIntent = new Intent(Intent.ACTION_MAIN);
               homeIntent.addCategory( Intent.CATEGORY_HOME );
               homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
               startActivity(homeIntent);
               
               
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
	
	
public void fetchContacts(Context c) {
	if(ci.isConnectingToInternet()){
		
		/*progress = new ProgressDialog(HBHomeActivity.this);
		progress.setCancelable(false);
		progress.setMessage("Please wait.....");
		progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progress.setProgress(0);
		progress.setMax(100);
		progress.show();*/
		thread = new Thread() {
			public void run() {
		list_numbers=new ArrayList<String>();

		list_numbers=au.fetchContacts(getApplicationContext());
		
		HashSet hs = new HashSet();
		hs.addAll(list_numbers);
		list_numbers.clear();
		list_numbers.addAll(hs);
		
		String al_nums=list_numbers.toString().replaceAll("\\s", "");
		
		
		hbha_str_getcontacts_resp=au.makePostRequest(HBConstants.getcontatcsinfo, al_nums);

	
			handler.post(createUI);
		
		}
	};
	thread.start();
	}
	else{
		Toast.makeText(getApplicationContext(), "Please connect to network", Toast.LENGTH_SHORT).show();
	}
}
	
	final Runnable createUI = new Runnable() {
		public void run() {
			//progress.dismiss();
			if(hbha_str_getcontacts_resp!=null){
				try {
					list_response_numbers=new ArrayList<String>();
					list_response_name=new ArrayList<String>();
					 hbh_jarr_contact_resp=new JSONArray(hbha_str_getcontacts_resp.toString());
					 Editor editor = hbh_spf_contact_resp.edit();
					for(int i=0;i<hbh_jarr_contact_resp.length();i++){
						JSONObject c = hbh_jarr_contact_resp.getJSONObject(i);

						String phonenumber=c.getString("phoneNumber");
						String profilepic=c.getString("profilePic");
						editor.putString(phonenumber, profilepic);
						String name=au.getContactName(getApplicationContext(), phonenumber);
						list_response_numbers.add(phonenumber);
						list_response_name.add(name);
						rcib=new ResponseContactInfBO();
						rcib.setName(name);
						rcib.setPhonenum(phonenumber);
						al_rcib.add(rcib);
					}
					 editor.commit();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			Map<String, ?> allEntries = hbh_spf_contact_resp.getAll();
			for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
			    Log.d("map values", entry.getKey() + ": " + entry.getValue().toString());
			} 

			
			Toast.makeText(getApplicationContext(), "Contacts updated", Toast.LENGTH_SHORT).show();

			//tv.setText(sb.toString());

		}
	};

	
	
	
	
	
}