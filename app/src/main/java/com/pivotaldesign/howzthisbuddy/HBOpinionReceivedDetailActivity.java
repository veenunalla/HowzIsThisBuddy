/**
 * 
 */
package com.pivotaldesign.howzthisbuddy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.pivotaldesign.howzthisbuddy.application.HBApplication;
import com.pivotaldesign.howzthisbuddy.fragments.HBReceivedFragment;
import com.pivotaldesign.howzthisbuddy.fragments.HBReceivedNoResponseFragment;
import com.pivotaldesign.howzthisbuddy.fragments.HBReceivedRespondedFragment;
import com.pivotaldesign.howzthisbuddy.model.HBConstants;
import com.pivotaldesign.howzthisbuddy.util.AppUtilities;
import com.pivotaldesign.howzthisbuddy.util.CheckInternet;
import com.pivotaldesign.howzthisbuddy.util.HBCustomShapeDrawable;
import com.pivotaldesign.howzthisbuddy.fragments.*;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author Satish Kolawale
 *
 */
public class HBOpinionReceivedDetailActivity extends FragmentActivity {
	
	private HBReceivedFragment hbrf;
    private AppUtilities au;
    private SharedPreferences spfcreds;
    private static final int REQUEST_CODE_CLICK_IMAGE = 2;
	
	 private int respondedcount;
	 private int pendingcount;
	 private int reqcount;
	 private String uriSting;
	 private String encImage;
	 private String hbr_str_captureopinion_resp;
	 private Long hbrf_lng_selected_itemselfieid;
	 private int pos=0;
	 private Thread thread;
		private Handler handler = new Handler();
		ProgressDialog progress = null;
		private CheckInternet ci;
     //private Long itemid,mobnum;

	@SuppressWarnings("deprecation")
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		hbrf=new HBReceivedFragment();
        au=new AppUtilities(getApplicationContext());
        ci=new CheckInternet(getApplicationContext());
       // Bundle b=getIntent().getExtras();
      //  itemid=b.getLong("ItemId");
       // mobnum=b.getLong("MobNum");
       // int pos=b.getInt("position");
        spfcreds=getSharedPreferences("loginprefs", 0);
		setContentView(R.layout.activity_opinion_received_detail);
		TextView hbr_txt_user_profile_name=(TextView)findViewById(R.id.txt_user_profile_name);
    	ImageView hbr_iv_user_pic=(ImageView)findViewById(R.id.img_user_profile_photo);
    	hbr_txt_user_profile_name.setTypeface(HBApplication.getInstance().getRegularFont());
    	String name=spfcreds.getString("username", "");
    	String pic=spfcreds.getString("profilepic", "");
    	if(pic.length()!=0){
    		Bitmap bm=au.StringToBitMap(pic);
        	hbr_iv_user_pic.setImageBitmap(bm);
    	}
    	
    	hbr_txt_user_profile_name.setText(name);
		
		ActionBar actionBar = getActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        actionBar.setIcon(new ColorDrawable(getResources().getColor(android.R.color.transparent))); 
        actionBar.setCustomView(R.layout.layout_actionbar);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        //actionBar.setHomeAsUpIndicator(R.drawable.back_arrow);
        
        
		
        TextView tv_txt_user_profile_name=(TextView) findViewById(R.id.txt_user_profile_name);
        TextView tv_txt_product_detail_name=(TextView) findViewById(R.id.txt_product_detail_name);
        TextView tv_txt_product_detail_product_id=(TextView) findViewById(R.id.txt_product_detail_product_id);
        TextView tv_txt_product_detail_product_description=(TextView) findViewById(R.id.txt_product_detail_product_description);
        TextView tv_txt_product_detail_size=(TextView) findViewById(R.id.txt_product_detail_size);
        TextView tv_txt_product_detail_color=(TextView) findViewById(R.id.txt_product_detail_color);
        TextView tv_txt_product_detail_price=(TextView) findViewById(R.id.txt_product_detail_price);
        TextView tv_txt_opinion_received_detail_invite_more_buddy=(TextView) findViewById(R.id.txt_opinion_received_detail_invite_more_buddy);
        tv_txt_user_profile_name.setTypeface(HBApplication.getInstance().getRegularFont());
        tv_txt_product_detail_name.setTypeface(HBApplication.getInstance().getRegularFont());
        tv_txt_product_detail_product_id.setTypeface(HBApplication.getInstance().getRegularFont());
        tv_txt_product_detail_product_description.setTypeface(HBApplication.getInstance().getNormalFont());
        tv_txt_product_detail_size.setTypeface(HBApplication.getInstance().getNormalFont());
        tv_txt_product_detail_color.setTypeface(HBApplication.getInstance().getNormalFont());
        tv_txt_product_detail_price.setTypeface(HBApplication.getInstance().getBoldFont());
        tv_txt_opinion_received_detail_invite_more_buddy.setTypeface(HBApplication.getInstance().getRegularFont());
        final TextView txtViewSelfie = (TextView) findViewById(R.id.txt_product_detail_selfie);
       
        txtViewSelfie.setTypeface(HBApplication.getInstance().getRegularFont());
        TextView txtOldPrice = (TextView) findViewById(R.id.txt_product_detail_product_origin_price);
        txtOldPrice.setPaintFlags(txtOldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        txtOldPrice.setTypeface(HBApplication.getInstance().getRegularFont());
        
        TextView txtFirstColorCode = (TextView) findViewById(R.id.txt_product_detail_first_color_code);
        TextView txtSecondColorCode = (TextView) findViewById(R.id.txt_product_detail_second_color_code);
        TextView txtThirdColorCode = (TextView) findViewById(R.id.txt_product_detail_thord_color_code);
        
        
        reqcount=Integer.parseInt(""+hbrf.al_completeresp1.get(pos).getOpinionRequestCount());
        respondedcount=Integer.parseInt(""+hbrf.al_completeresp1.get(pos).getOpinionResponseCount());
        pendingcount=reqcount-respondedcount;
        tv_txt_product_detail_name.setText(hbrf.al_completeresp1.get(pos).getItemBO().getItemTitle());
       // tv_txt_product_detail_product_id.setText(""+hbrf.al_itembo.get(pos).getItemId());
        tv_txt_product_detail_product_description.setText(hbrf.al_completeresp1.get(pos).getItemBO().getItemDesc());
        tv_txt_product_detail_price.setText("Now:$"+hbrf.al_completeresp1.get(pos).getItemBO().getPrice());
        if(!hbrf.al_completeresp1.get(pos).getItemSelfieDetailsBO().isItemSelfieDetailsFlag()){
        	 txtViewSelfie.setText("Capture Selfie");
        }else{
        	 txtViewSelfie.setText("View Selfie");
        }
        txtViewSelfie.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(txtViewSelfie.getText().toString().equalsIgnoreCase("Capture Selfie")){
					Toast.makeText(getApplicationContext(), "Capture", Toast.LENGTH_SHORT).show();
					Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					 startActivityForResult(intent, REQUEST_CODE_CLICK_IMAGE);	
				        hbrf_lng_selected_itemselfieid=hbrf.al_completeresp1.get(pos).getItemSelfieDetailsBO().getItemSelfieDetailsId();

				}else{
					Toast.makeText(getApplicationContext(), "View", Toast.LENGTH_SHORT).show();
				}
				
			}
		});
        /*for (int a =0; a<hbrf.completeresp.size();a++)
        {
            HashMap<String, String> tmpData = (HashMap<String, String>) hbrf.completeresp.get(a);
            Set<String> key = tmpData.keySet();
            Iterator it = key.iterator();
            while (it.hasNext()) {
                String hmKey = (String)it.next();
                String hmData = (String) tmpData.get(hmKey);
                System.out.println("Key: "+hmKey +" & Data: "+hmData);

                if(hmKey.equalsIgnoreCase("itemID")&&hmData.equalsIgnoreCase(itemid)){
                }
                it.remove(); // avoids a ConcurrentModificationException
            }

        }  */  
        
        RoundRectShape rs = new RoundRectShape(null/*new float[] { 10, 10, 10, 10, 10, 10, 10, 10 }*/, null, null);
        ShapeDrawable sd = new HBCustomShapeDrawable(rs, Color.RED, Color.WHITE, 0);
        txtFirstColorCode.setBackgroundDrawable(sd);
        
        //RoundRectShape rs = new RoundRectShape(null, null, null);
        ShapeDrawable secondDrawable = new HBCustomShapeDrawable(rs, Color.GRAY, Color.WHITE, 0);
        txtSecondColorCode.setBackgroundDrawable(secondDrawable);
        
        
        ShapeDrawable thirdDrawable = new HBCustomShapeDrawable(rs, Color.YELLOW, Color.WHITE, 0);
        txtThirdColorCode.setBackgroundDrawable(thirdDrawable);
         
        ViewPager pager = (ViewPager) findViewById(R.id.viewPager_opinion_received_detail);
		pager.setAdapter(new SectionsPagerAdapter(getSupportFragmentManager()));
		
		PagerTabStrip pagerTabStrip = (PagerTabStrip) findViewById(R.id.pager_opinion_received_title_strip);
		//pagerTabStrip.setDrawFullUnderline(true);
		pagerTabStrip.setTabIndicatorColor(Color.BLUE);
        
	}
	
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		try {
			 if(requestCode==REQUEST_CODE_CLICK_IMAGE && resultCode==Activity.RESULT_OK && null!=data){ 
				 Bitmap photo = (Bitmap) data.getExtras().get("data"); 
			  Uri tempUri = au.getImageUri(getApplicationContext(), photo);
			  String path=au.getRealPath(getApplicationContext(),tempUri);
			  ImageCompressionAsyncTask img=new ImageCompressionAsyncTask(HBOpinionReceivedDetailActivity.this,true);
			  uriSting=img.execute(path).get();
				//new HBRsendcaptureselfie().execute("");
			  pic_upload();
			}else {
				Toast.makeText(getApplicationContext(), "Hey pick your image first",Toast.LENGTH_LONG).show();
			}
		} catch (Exception e) {
			Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG)
					.show();
		}

	}

	
	@Override
	public boolean onOptionsItemSelected(MenuItem menuItem) {
	    switch (menuItem.getItemId()) {
	        case android.R.id.home:
	            finish();
	    }
	    return (super.onOptionsItemSelected(menuItem));
	}
	
	 
	class SectionsPagerAdapter extends FragmentStatePagerAdapter {
		String[] _sections = new String[]{ "Responded("+respondedcount+")", "No Response("+pendingcount+")" };
		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			Fragment fragment = null;
			switch (position) {
				case 0:
					fragment = new HBReceivedRespondedFragment();
					break;
				case 1:
					fragment = new HBReceivedNoResponseFragment();
	
				default:
					break;
			}
			Bundle args = new Bundle();
			fragment.setArguments(args);			
			return fragment;
		}

		@Override
		public int getCount() {
			// Show total pages.
			return _sections.length;
		}
		
		@Override
		public CharSequence getPageTitle(int position) {
			//Locale locale = Locale.getDefault();
			return _sections[position];
		}
	}

	
	class HBRsendcaptureselfie extends AsyncTask<String, String, String> {
		ProgressDialog progress;
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progress = new ProgressDialog(getApplicationContext());
			progress.setMessage("please wait.....");
			progress.setCancelable(false);
			progress.show();
		}

		@Override
		protected String doInBackground(String... params) {
			 encImage=au.encryptedImage(uriSting);
				JSONObject hbr_jobj = new JSONObject();
				try {
					hbr_jobj.put("itemSelfieDetailsId", ""+hbrf_lng_selected_itemselfieid);
					hbr_jobj.put("selfiePic", encImage);
					

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			String reg_params = hbr_jobj.toString();
			
		    hbr_str_captureopinion_resp=au.makeRequeststatusline(HBConstants.updateOpinionItemSelfieDetails, reg_params);
			return hbr_str_captureopinion_resp;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			progress.dismiss();
			if(hbr_str_captureopinion_resp.contains("HTTP/1.1 200 OK")){
				
				Toast.makeText(getApplicationContext(), "Item selfie updated", Toast.LENGTH_SHORT).show();

				}else{
					Toast.makeText(getApplicationContext(), "Item Selfie not updated", Toast.LENGTH_SHORT).show();
				}
			
		}
	}


	
	
	public void pic_upload(){
		progress = new ProgressDialog(HBOpinionReceivedDetailActivity.this);
		progress.setCancelable(false);
		String plz_craetedb = "Please Wait...";
		progress.setMessage(plz_craetedb);
		progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progress.setProgress(0);
		progress.setMax(100);
		progress.show();
		thread = new Thread(){
			public void run(){
				try{

					 encImage=au.encryptedImage(uriSting);
						JSONObject hbr_jobj = new JSONObject();
						try {
							hbr_jobj.put("itemSelfieDetailsId", ""+hbrf_lng_selected_itemselfieid);
							hbr_jobj.put("selfiePic", encImage);
							

						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					String reg_params = hbr_jobj.toString();
				    hbr_str_captureopinion_resp=au.makeRequeststatusline(HBConstants.updateOpinionItemSelfieDetails, reg_params);
				
				}
				catch(Exception e){
					e.printStackTrace();
				}
				handler.post(createUI);
			}
		};
		thread.start();
	}
	
	final Runnable createUI = new Runnable() {
		public void run(){
			progress.dismiss();
			if(hbr_str_captureopinion_resp.contains("HTTP/1.1 200 OK")){
				
				Toast.makeText(getApplicationContext(), "Item selfie updated", Toast.LENGTH_SHORT).show();

				}else{
					Toast.makeText(getApplicationContext(), "Item Selfie not updated", Toast.LENGTH_SHORT).show();
				}
			
		}
	};


	
}