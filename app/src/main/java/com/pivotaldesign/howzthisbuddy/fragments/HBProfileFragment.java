/**
 * 
 */
package com.pivotaldesign.howzthisbuddy.fragments;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.pivotaldesign.howzthisbuddy.HBHomeActivity;
import com.pivotaldesign.howzthisbuddy.HBRegistrationActivity;
import com.pivotaldesign.howzthisbuddy.R;
import com.pivotaldesign.howzthisbuddy.application.HBApplication;
import com.pivotaldesign.howzthisbuddy.model.HBConstants;
import com.pivotaldesign.howzthisbuddy.util.AppUtilities;
import com.pivotaldesign.howzthisbuddy.util.CheckInternet;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author Satish Kolawale
 *
 */

/**
 * @author Rajesh 
 *
 */
public class HBProfileFragment extends Fragment implements OnClickListener{
	
	private TextView tv_txt_profile_profile_title;
	private TextView tv_txt_profile_pic_cam;
	private TextView tv_txt_profile_done;
	private TextView tv_txt_profile_name;
	private TextView tv_txt_profile_email;
	private TextView tv_txt_profile_mobile_number;
	private ImageView iv_profile_pic;
	private static final int RESULT_LOAD = 1;
	private static final int REQUEST_CODE_CLICK_IMAGE = 2;
	public static String encode_STRING;
	String img_Decodable_Str;
	private CheckInternet ci;
	private String uriSting="";
	private String hbpf_str_main_req_params;
	private String hbpf_str_edit_useer_profile_resp;
	private EditText edt_prfl_name;
	private EditText edt_prfl_email;
	private EditText  edt_prfl_number;
	private Gson gson_child_prfl;
	private Gson gson_main_prfl_details;
	private SharedPreferences hbh_spf_login_details;
	private HBRegistrationActivity hbr=new HBRegistrationActivity();
	private String encImage;
    private AppUtilities au;
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        ci=new CheckInternet(getActivity());
        hbh_spf_login_details=getActivity().getSharedPreferences("loginprefs",0);
        tv_txt_profile_profile_title=(TextView) rootView.findViewById(R.id.txt_profile_profile_title);
        tv_txt_profile_profile_title.setTypeface(HBApplication.getInstance().getRegularFont());
        tv_txt_profile_pic_cam=(TextView)rootView.findViewById(R.id.txt_profile_pic_cam);
        tv_txt_profile_pic_cam.setTypeface(HBApplication.getInstance().getRegularFont());
        tv_txt_profile_pic_cam.setOnClickListener(this);
        tv_txt_profile_done=(TextView) rootView.findViewById(R.id.txt_profile_done);
        tv_txt_profile_done.setTypeface(HBApplication.getInstance().getRegularFont());
        tv_txt_profile_name=(TextView) rootView.findViewById(R.id.txt_profile_name);
        tv_txt_profile_name.setTypeface(HBApplication.getInstance().getRegularFont());
        tv_txt_profile_email=(TextView) rootView.findViewById(R.id.txt_profile_email);
        tv_txt_profile_email.setTypeface(HBApplication.getInstance().getRegularFont());
        tv_txt_profile_mobile_number=(TextView) rootView.findViewById(R.id.txt_profile_mobile_number);
        tv_txt_profile_mobile_number.setTypeface(HBApplication.getInstance().getRegularFont());
        iv_profile_pic=(ImageView)rootView.findViewById(R.id.iv_profile_pic);
        iv_profile_pic.setOnClickListener(this);
        tv_txt_profile_done.setOnClickListener(this);
        edt_prfl_name=(EditText)rootView.findViewById(R.id.edt_profile_name);
        edt_prfl_email=(EditText)rootView.findViewById(R.id.edt_profile_email);
        edt_prfl_number=(EditText)rootView.findViewById(R.id.edt_profile_mobile_number);
        String number=hbh_spf_login_details.getString("mobilenum", "");
    	String pic=hbh_spf_login_details.getString("profilepic", "");
    	String name=hbh_spf_login_details.getString("username", "");
        au=new AppUtilities(getActivity());
    	if(pic.length()!=0){
    		Bitmap bm=au.StringToBitMap(pic);
    		iv_profile_pic.setImageBitmap(bm);
    	}
    	if(name.length()!=0){
    		edt_prfl_name.setText(name);
    	}
        edt_prfl_number.setText(number);
        return rootView;
    }
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.iv_profile_pic:
			
			Intent galleryIntent = new Intent(Intent.ACTION_PICK,
					android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
			startActivityForResult(galleryIntent, RESULT_LOAD);
			
			break;
		case R.id.txt_profile_pic_cam:
			
			 Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			 startActivityForResult(intent, REQUEST_CODE_CLICK_IMAGE);			
			break;
		case R.id.txt_profile_done:

			String name=edt_prfl_name.getText().toString();
			String email=edt_prfl_email.getText().toString();
			String phone=edt_prfl_number.getText().toString();
			if(email.length()==0){
				email="test@t.com";
			}
			
			if(name.length()!=0){
				
				encImage=au.encryptedImage(uriSting);
				
				if(!name.equalsIgnoreCase(null)){
					if(ci.isConnectingToInternet()){
					
					JSONObject hbpf_jobj_parent = new JSONObject();
		            JSONObject hbpf_jobj_child = new JSONObject();
					try {
						hbpf_jobj_child.put("phoneNumber", phone);
						hbpf_jobj_child.put("profilePic", encImage);
						
						hbpf_jobj_parent.put("phoneNumber", phone);
						hbpf_jobj_parent.put("name", name);
						hbpf_jobj_parent.put("emailId", email);
						hbpf_jobj_parent.put("userProfilePictureBO", hbpf_jobj_child);
						hbpf_str_main_req_params=hbpf_jobj_parent.toString();
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			
							new HBprofileupdate().execute("");
						
					}else{
						Toast.makeText(getActivity(), "Please connect to network", Toast.LENGTH_SHORT).show();
					}
				}
			}else{
				Toast.makeText(getActivity(), "Please enter Name", Toast.LENGTH_SHORT).show();
			}
			
			break;
		}
		
		
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		try {
			if (requestCode == RESULT_LOAD && resultCode == Activity.RESULT_OK
					&& null != data) {
				ImageCompressionAsyncTask img=new ImageCompressionAsyncTask(getActivity(),true);
					uriSting=img.execute(data.getDataString()).get();
					File imgFile = new  File(uriSting);
					iv_profile_pic.setImageBitmap(BitmapFactory
							.decodeFile(imgFile.getAbsolutePath()));
			} else if(requestCode==REQUEST_CODE_CLICK_IMAGE && resultCode==Activity.RESULT_OK && null!=data){ Bitmap photo = (Bitmap) data.getExtras().get("data"); 
			  Uri tempUri = au.getImageUri(getActivity(), photo);
			  String path=au.getRealPath(getActivity(),tempUri);
			  ImageCompressionAsyncTask img=new ImageCompressionAsyncTask(getActivity(),true);
			  uriSting=img.execute(path).get();
				File imgFile = new  File(uriSting);
				iv_profile_pic.setImageBitmap(BitmapFactory
						.decodeFile(imgFile.getAbsolutePath()));
			}else {
				Toast.makeText(getActivity(), "Please take a pic",Toast.LENGTH_LONG).show();
			}
		} catch (Exception e) {
			Toast.makeText(getActivity(), "Something wrong", Toast.LENGTH_LONG)
					.show();
		}

	}
	
	
	
	
	class HBprofileupdate extends AsyncTask<String, String, String> {
		ProgressDialog progress;
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progress = new ProgressDialog(getActivity());
			progress.setMessage("please wait.....");
			progress.setCancelable(false);
			progress.show();
		}

		@Override
		protected String doInBackground(String... params) {
			hbpf_str_edit_useer_profile_resp=au.makeRequeststatusline(HBConstants.edituserprofile, hbpf_str_main_req_params);
				return hbpf_str_edit_useer_profile_resp;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			progress.dismiss();
			if(hbpf_str_edit_useer_profile_resp.equalsIgnoreCase("HTTP/1.1 200 OK")){
				Toast.makeText(getActivity(), "Profile updated", Toast.LENGTH_SHORT).show();
				Editor edt=hbh_spf_login_details.edit();
				edt.putString("username", edt_prfl_name.getText().toString());
				if(encImage.length()!=0){
					edt.putString("profilepic", encImage);
				}
				edt.commit();
				Intent i=new Intent(getActivity(),HBHomeActivity.class);
				startActivity(i);
			}else{
				Toast.makeText(getActivity(), "Profile not updated,Please update", Toast.LENGTH_SHORT).show();
			}
			
		}
	}

}
