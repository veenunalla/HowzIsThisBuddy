package com.pivotaldesign.howzthisbuddy;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.pivotaldesign.howzthisbuddy.bean.ResponseContactInfBO;
import com.pivotaldesign.howzthisbuddy.model.HBConstants;
import com.pivotaldesign.howzthisbuddy.util.AppUtilities;
import com.pivotaldesign.howzthisbuddy.util.CheckInternet;
import com.pivotaldesign.howzthisbuddy.util.SystemUiHider;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */



public class HBSplashActivity extends Activity {
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
	private SharedPreferences hbs_sp_loginprefs;
	private SharedPreferences hbs_sp_callingapp_details;
    private static final boolean AUTO_HIDE = true;
    private HBHomeActivity hbha;
    private CheckInternet ci;
    
    private Thread thread;
	public Handler handler = new Handler();
	private String hbha_str_getcontacts_resp;
	private ArrayList<String> list_numbers;
	private AppUtilities au;
	public static ArrayList<String> list_response_numbers;
	public static ArrayList<String> list_response_name;
	private JSONArray hbh_jarr_contact_resp=null;
	private SharedPreferences hbh_spf_contact_resp;
	private ResponseContactInfBO rcib;
	public static ArrayList<ResponseContactInfBO> al_rcib=new ArrayList<ResponseContactInfBO>();
	





    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * If set, will toggle the system UI visibility upon interaction. Otherwise,
     * will show the system UI visibility upon interaction.
     */
    private static final boolean TOGGLE_ON_CLICK = true;

    /**
     * The flags to pass to {@link SystemUiHider#getInstance}.
     */
    private static final int HIDER_FLAGS = SystemUiHider.FLAG_HIDE_NAVIGATION;

    /**
     * The instance of the {@link SystemUiHider} for this activity.
     */
    private SystemUiHider mSystemUiHider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
		getActionBar().hide();
        setContentView(R.layout.activity_splash);
        ci=new CheckInternet(getApplicationContext());
        hbha=new HBHomeActivity();
        final View controlsView = findViewById(R.id.fullscreen_content_controls);
        final View contentView = findViewById(R.id.layout_splash);

        list_numbers=new ArrayList<String>();
        au=new AppUtilities(getApplicationContext());
		hbh_spf_contact_resp=getSharedPreferences("resp_contacts_info",0);

        hbs_sp_loginprefs=getSharedPreferences("loginprefs", 0);
        hbs_sp_callingapp_details=getSharedPreferences("callingappcredentials", 0);
        // Set up an instance of SystemUiHider to control the system UI for
        // this activity.
        fetchContacts();
        Intent intent = getIntent();
        if(intent.getExtras()!=null){
        	
        	 String action = intent.getAction();
             Log.v("Action", action);
             String type = intent.getType();
            // Log.v("Type", type);
             if (Intent.ACTION_SEND.equals(action) && type != null) {
                 if ("text/plain".equals(type)) {
                    // handleSendText(intent); // Handle text being sent
                 	String str_org_id=intent.getExtras().getString("ORG_ID");
                 	String str_org_name=intent.getExtras().getString("ORG_NAME");
                 	String str_item_id=intent.getExtras().getString("ITEM_ID");
                 	String str_item_name=intent.getExtras().getString("ITEM_NAME");
                 	String str_item_desc=intent.getExtras().getString("ITEM_DESC");
                 	String str_item_price=intent.getExtras().getString("ITEM_PRICE");
                 	String str_item_url=intent.getExtras().getString("ITEM_URL");

                 	Editor et=hbs_sp_callingapp_details.edit();
                 	et.putString("ORG_ID", str_org_id);
                 	et.putString("ORG_NAME", str_org_name);
                 	et.putString("ITEM_ID", str_item_id);
                 	et.putString("ITEM_NAME", str_item_name);
                 	et.putString("ITEM_DESC", str_item_desc);
                 	et.putString("ITEM_PRICE", str_item_price);
                 	et.putString("ITEM_URL", str_item_url);
                 	et.putString("status", "true");
                 	et.commit();

                 //	Toast.makeText(getApplicationContext(), str_item_url, Toast.LENGTH_SHORT).show();
                 }
             }
             
        }
       
        mSystemUiHider = SystemUiHider.getInstance(this, contentView, HIDER_FLAGS);
        mSystemUiHider.setup();
        mSystemUiHider.setOnVisibilityChangeListener(new SystemUiHider.OnVisibilityChangeListener() {
                    // Cached values.
                    int mControlsHeight;
                    int mShortAnimTime;

                    @Override
                    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
                    public void onVisibilityChange(boolean visible) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
                            // If the ViewPropertyAnimator API is available
                            // (Honeycomb MR2 and later), use it to animate the
                            // in-layout UI controls at the bottom of the
                            // screen.
                            if (mControlsHeight == 0) {
                                mControlsHeight = controlsView.getHeight();
                            }
                            if (mShortAnimTime == 0) {
                                mShortAnimTime = getResources().getInteger(
                                        android.R.integer.config_shortAnimTime);
                            }
                            controlsView.animate()
                                    .translationY(visible ? 0 : mControlsHeight)
                                    .setDuration(mShortAnimTime);
                        } else {
                            // If the ViewPropertyAnimator APIs aren't
                            // available, simply show or hide the in-layout UI
                            // controls.
                            controlsView.setVisibility(visible ? View.VISIBLE : View.GONE);
                        }

                        if (visible && AUTO_HIDE) {
                            // Schedule a hide().
                            delayedHide(AUTO_HIDE_DELAY_MILLIS);
                        }
                    }
                });

        // Set up the user interaction to manually show or hide the system UI.
        contentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TOGGLE_ON_CLICK) {
                    mSystemUiHider.toggle();
                } else {
                    mSystemUiHider.show();
                }
            }
        });

        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
        //findViewById(R.id.dummy_button).setOnTouchListener(mDelayHideTouchListener);
        
        new Handler().postDelayed(_runnable, 3000);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(10);
    }


    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY_MILLIS);
            }
            return false;
        }
    };

    Handler mHideHandler = new Handler();
    Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            mSystemUiHider.hide();
        }
    };

    /**
     * Schedules a call to hide() in [delay] milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }
    
    
    private Runnable _runnable = new  Runnable() {
		
		@Override
		public void run() {
			showHomeActivity();
		}
	};
	
	
	private void showHomeActivity() {
		finish();
		String mobilenumber=hbs_sp_loginprefs.getString("mobilenum", null);
		if(mobilenumber!=null){
			startActivity(new Intent(HBSplashActivity.this, HBHomeActivity.class));

		}else{
			startActivity(new Intent(HBSplashActivity.this, HBRegistrationActivity.class));

		}
		
	}




public void fetchContacts() {
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
						String downloadpic=c.getString("profilePicS3Url");
						int picversion=c.getInt("pictureVersion");
						editor.putString(phonenumber, profilepic);
						String name=au.getContactName(getApplicationContext(), phonenumber);
						list_response_numbers.add(phonenumber);
						list_response_name.add(name);
						rcib=new ResponseContactInfBO();
						rcib.setName(name);
						rcib.setPhonenum(phonenumber);
						rcib.setProfilePicS3Url(downloadpic);
						rcib.setDownloadIndicator(picversion);
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