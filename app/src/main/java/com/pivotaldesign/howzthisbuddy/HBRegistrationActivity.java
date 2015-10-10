/**
 * 
 */
package com.pivotaldesign.howzthisbuddy;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.pivotaldesign.howzthisbuddy.application.HBApplication;
import com.pivotaldesign.howzthisbuddy.model.HBConstants;
import com.pivotaldesign.howzthisbuddy.util.AppUtilities;
import com.pivotaldesign.howzthisbuddy.util.CheckInternet;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gcm.GCMRegistrar;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.gson.Gson;
import com.pivotaldesign.howzthisbuddy.application.HBApplication;
import com.pivotaldesign.howzthisbuddy.model.HBConstants;
import com.pivotaldesign.howzthisbuddy.util.AppUtilities;
import com.pivotaldesign.howzthisbuddy.util.CheckInternet;

/**
 * @author Satish Kolawale
 *
 */
@SuppressWarnings({ "deprecation", "unused" })
public class HBRegistrationActivity extends Activity {
	
	private Dialog _confirmationDialog = null;
	

	private HashMap<String,String> hbr_hm_reg_req_params;
	private HashMap<String,String>hbr_hm_otp_req_params;

	private String hbr_str_mobile_number;
	private String hbr_str_reg_resp_otprequest;
	private String hbr_str_reg_resp_otp;
	
	private String hbr_str_pop_otp;
	
	public static String response;
	private AppUtilities au;
	
	private JSONObject hbr_jo_reg_resp;

	//AppConstants appconstants;
	
	private Gson gson_reg_req;
	private Gson gson_otp_req;
	
	private TextView hbr_tv_disp_phone_number;
	private  EditText hbr_et_verification_code;
	private EditText hbr_et_country_code;
	private Spinner hbr_spnr_country;
	private CheckInternet checkInternet;
	
	private SharedPreferences hbr_sp_loginprefs;
	private SharedPreferences.Editor hbr_spe_loginprefs_editor;
	
	// GCM Implementation
	GoogleCloudMessaging gcm;
	Context context;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_registration);
		GCMRegistrar.checkDevice(this);
		GCMRegistrar.checkManifest(this);
		gcm = GoogleCloudMessaging.getInstance(this);
		
		
		getActionBar().hide();
		checkInternet=new CheckInternet(getApplicationContext());
		au=new AppUtilities(getApplicationContext());
		hbr_hm_reg_req_params = new HashMap<String, String>();
		hbr_hm_otp_req_params = new HashMap<String, String>();
		hbr_sp_loginprefs=getSharedPreferences("loginprefs",Context.MODE_PRIVATE);
		hbr_spe_loginprefs_editor=hbr_sp_loginprefs.edit();
		hbr_et_country_code=(EditText)findViewById(R.id.edt_registration_country_code);
		hbr_spnr_country=(Spinner)findViewById(R.id.spn_registration_country);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, HBConstants.country);
		hbr_spnr_country.setAdapter(adapter);
		
		hbr_spnr_country.setOnItemSelectedListener(new OnItemSelectedListener()
        {

             @Override
             public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
              // TODO Auto-generated method stub
          String country=hbr_spnr_country.getSelectedItem().toString();
          if(country.equalsIgnoreCase("USA")){
        	  hbr_et_country_code.setText("+1");
          }else if(country.equalsIgnoreCase("India")){
        	  hbr_et_country_code.setText("+91");
          }else if(country.equalsIgnoreCase("-Select-")){
        	  hbr_et_country_code.setText("");
          }

          }

          @Override
      public void onNothingSelected(AdapterView<?> arg0) {
           // TODO Auto-generated method stub

            }

        });
		
		
		((TextView) findViewById(R.id.txt_registration_title)).setTypeface(HBApplication.getInstance().getBoldFont());
		((TextView) findViewById(R.id.txt_registration_content)).setTypeface(HBApplication.getInstance().getRegularFont());
		((TextView) findViewById(R.id.txt_registration_phone_hint)).setTypeface(HBApplication.getInstance().getNormalFont());
		((TextView) findViewById(R.id.txt_registration_sms)).setTypeface(HBApplication.getInstance().getRegularFont());
		((EditText) findViewById(R.id.edt_registration_phone_number)).setTypeface(HBApplication.getInstance().getRegularFont());
		Button btnSubmit = (Button) findViewById(R.id.btn_registration_submit);
		btnSubmit.setTypeface(HBApplication.getInstance().getRegularFont());
		btnSubmit.setOnClickListener(new OnClickListener() {
				
			@Override
			public void onClick(View v) {
				hbr_str_mobile_number=((EditText) findViewById(R.id.edt_registration_phone_number)).getText().toString();
				
				if(!hbr_spnr_country.getSelectedItem().toString().equalsIgnoreCase("-Select-")){
					
					if((hbr_str_mobile_number.length())==10){
						
						hbr_hm_reg_req_params.put("phoneNumber", hbr_et_country_code.getText().toString().replace("+", "")+hbr_str_mobile_number);
						hbr_hm_reg_req_params.put("name", "");
						hbr_hm_reg_req_params.put("emailId", "");
						
					    if(checkInternet.isConnectingToInternet()){
								try {
									new HBRRegistraionreq().execute("");
								} catch (Exception e) {
									e.printStackTrace();
								}
					    }else{
					    	Toast.makeText(getApplicationContext(), "Please connect to Network", Toast.LENGTH_SHORT).show();
					    }
					}else{
						Toast.makeText(getApplicationContext(), "Please enter a valid phone number", Toast.LENGTH_SHORT).show();
					}
					
				}else{
			    	Toast.makeText(getApplicationContext(), "Please select country name", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
	/**
	 * Show alert dialog for sending password to your email account 
	 */
	private void showCommentDialog() {
		// custom dialog
		_confirmationDialog = new Dialog(this);
		_confirmationDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		_confirmationDialog.setContentView(R.layout.layout_registration_confirmation_popup);
		 hbr_tv_disp_phone_number=(TextView) _confirmationDialog.findViewById(R.id.txt_registration_popup_phone_number);
		 hbr_et_verification_code=(EditText) _confirmationDialog.findViewById(R.id.edt_registration_popup_verification_code);
		((TextView) _confirmationDialog.findViewById(R.id.txt_registration_popup_title)).setTypeface(HBApplication.getInstance().getBoldFont());
		((TextView) _confirmationDialog.findViewById(R.id.txt_registration_popup_verification)).setTypeface(HBApplication.getInstance().getBoldFont());
		((TextView) _confirmationDialog.findViewById(R.id.txt_registration_popup_ask_contact)).setTypeface(HBApplication.getInstance().getRegularFont());
		//((TextView) _confirmationDialog.findViewById(R.id.txt_registration_popup_phone_number)).setTypeface(HBApplication.getInstance().getBoldFont());
		hbr_tv_disp_phone_number.setTypeface(HBApplication.getInstance().getBoldFont());
		hbr_tv_disp_phone_number.setText(hbr_et_country_code.getText().toString()+hbr_str_mobile_number);
		hbr_et_verification_code.setTypeface(HBApplication.getInstance().getBoldFont());
		//hbr_et_verification_code.setText(hbr_str_reg_resp_otp);
		((TextView) _confirmationDialog.findViewById(R.id.txt_registration_popup_verification_code)).setTypeface(HBApplication.getInstance().getRegularFont());
		((TextView) _confirmationDialog.findViewById(R.id.txt_registration_popup_press_again)).setTypeface(HBApplication.getInstance().getRegularFont());
		((EditText) _confirmationDialog.findViewById(R.id.edt_registration_popup_verification_code)).setTypeface(HBApplication.getInstance().getRegularFont());
		Button btnSubmit = (Button) _confirmationDialog.findViewById(R.id.btn_registration_popup_submit);
		btnSubmit.setTypeface(HBApplication.getInstance().getRegularFont());
		btnSubmit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(checkInternet.isConnectingToInternet()){
					if(hbr_et_verification_code.length()==6){
					new HBROTPreq().execute("");
					
					}else{
						Toast.makeText(getApplicationContext(), "Please enter valid OTP", Toast.LENGTH_SHORT).show();
					//showCommentDialog();
					}
				}else{
			    	Toast.makeText(getApplicationContext(), "Please connect to Network", Toast.LENGTH_SHORT).show();	
				}
				_confirmationDialog.dismiss();
			}
		});
		
		ImageView imgClose = (ImageView) _confirmationDialog.findViewById(R.id.img_registration_popup_close);
		imgClose.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				_confirmationDialog.dismiss();
			}
		});
		_confirmationDialog.show();
	}
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
	
	private void showHomeActivity() {
		startActivity(new Intent(HBRegistrationActivity.this, HBHomeActivity.class));
	}
	
	class HBRRegistraionreq extends AsyncTask<String, String, String> {
		ProgressDialog progress;
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progress = new ProgressDialog(HBRegistrationActivity.this);
			progress.setMessage("please wait.....");
			progress.setCancelable(false);
			progress.show();
		}

		@Override
		protected String doInBackground(String... params) {
			gson_reg_req = new Gson();
		    String reg_params = gson_reg_req.toJson(hbr_hm_reg_req_params);
		    hbr_str_reg_resp_otprequest=au.makeRequeststatusline(HBConstants.reg_req, reg_params);
				return hbr_str_reg_resp_otprequest;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			progress.dismiss();
			if(hbr_str_reg_resp_otprequest.equalsIgnoreCase("HTTP/1.1 200 OK")){
			showCommentDialog();
			}else{
				Toast.makeText(getApplicationContext(), "Invalid Mobile Number", Toast.LENGTH_SHORT).show();
			}
		}
	}

	
	class HBROTPreq extends AsyncTask<String, String, String> {
		ProgressDialog progress;
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progress = new ProgressDialog(HBRegistrationActivity.this);
			progress.setMessage("please wait.....");
			progress.setCancelable(false);
			progress.show();
		}

		@Override
		protected String doInBackground(String... params) {
			String registrationId = null;
			String msg;
			if (gcm == null) {
				gcm = GoogleCloudMessaging.getInstance(getApplicationContext());
			}
			try {
				registrationId = gcm.register(AppUtilities.SENDER_ID);
				Log.d("RegisterActivity", "registerInBackground - regId: "
						+ registrationId);
				
			} catch (IOException ex) {
				 msg = "Error :" + ex.getMessage();
				Log.d("RegisterActivity", "Error: " + msg);
				ex.printStackTrace();
			}
			
			hbr_hm_otp_req_params.put("phoneNumber", hbr_tv_disp_phone_number.getText().toString().replace("+", ""));
			hbr_hm_otp_req_params.put("otpCode", hbr_et_verification_code.getText().toString());
			hbr_hm_otp_req_params.put("gcmRegistrationID", registrationId);
			
			
			/*
			hbr_hm_otp_req_params.put("gcmRegistrationID", "123456789");
			*/
			gson_otp_req=new Gson();
			String otp_params=gson_otp_req.toJson(hbr_hm_otp_req_params);
			
			hbr_str_pop_otp=au.makeRequeststatusline(HBConstants.otp_req, otp_params);
			
			Log.d("OTP resp",hbr_str_pop_otp);
			
				return hbr_str_pop_otp;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			
			if(hbr_str_pop_otp.contains("HTTP/1.1 200 OK")){
			hbr_spe_loginprefs_editor.putString("mobilenum", hbr_et_country_code.getText().toString().replace("+", "")+hbr_str_mobile_number);
			hbr_spe_loginprefs_editor.commit();
			progress.dismiss();
			showHomeActivity();
			}else{
				Toast.makeText(getApplicationContext(), "One time password is incorrect,Please try again", Toast.LENGTH_SHORT).show();
				showCommentDialog();
			}
		}
		
	}
}
