/**
 * 
 */
package com.pivotaldesign.howzthisbuddy.fragments;

import java.io.File;

import org.json.JSONException;
import org.json.JSONObject;

import com.pivotaldesign.howzthisbuddy.R;
import com.pivotaldesign.howzthisbuddy.application.HBApplication;
import com.pivotaldesign.howzthisbuddy.model.HBConstants;
import com.pivotaldesign.howzthisbuddy.model.HBNotifier;
import com.pivotaldesign.howzthisbuddy.util.AppUtilities;
import com.pivotaldesign.howzthisbuddy.util.CheckInternet;
import com.pivotaldesign.howzthisbuddy.util.HBCustomShapeDrawable;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author Satish Kolawale
 *
 */
public class HBResponseFragment extends Fragment implements OnClickListener{
	
	private ImageButton _imgLike = null;
	private ImageButton _imgNotSure = null;
	private ImageButton _imgDoNotLike = null;
	private SharedPreferences hbh_spf_login_details;
	private HBNotifier _notifier = null;
    private AppUtilities au;
    private CheckInternet ci;

    private HBPendingDetailFragment hbpdf=new HBPendingDetailFragment(_notifier);
    private HBGivenFragment hbgf=new HBGivenFragment(_notifier);
    private int pos;
    private int useropinintype=0;
    private String number;
    private EditText edt_opinion_response_describe;
    private TextView txtSelfie;
    private String respondopinion;
    private String hbrf_str_resp_respopinion;
    private static final int REQUEST_CODE_CLICK_IMAGE = 2;
    private String uriSting="";
    private String encImage="";
    public HBResponseFragment(HBNotifier notifier) {
		this._notifier = notifier;
	}
	@SuppressLint("NewApi")
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_response, container, false);
         pos=hbpdf.hbpfposition.get("position");
        /*Bundle bundle = this.getArguments();
        String pos=bundle.getString("Position");*/
        Toast.makeText(getActivity(), ""+pos, Toast.LENGTH_SHORT).show();
        hbh_spf_login_details=getActivity().getSharedPreferences("loginprefs",0);
        TextView hbr_txt_user_profile_name=(TextView)rootView.findViewById(R.id.txt_user_profile_name);
    	ImageView hbr_iv_user_pic=(ImageView)rootView.findViewById(R.id.img_user_profile_photo);
    	hbr_txt_user_profile_name.setTypeface(HBApplication.getInstance().getRegularFont());
        String pic=hbh_spf_login_details.getString("profilepic", "");
    	String name=hbh_spf_login_details.getString("username", "");
    	number=hbh_spf_login_details.getString("mobilenum", "");
        au=new AppUtilities(getActivity());
        ci=new CheckInternet(getActivity());
    	if(pic.length()!=0){
    		Bitmap bm=au.StringToBitMap(pic);
    		hbr_iv_user_pic.setImageBitmap(bm);
    	}
    	hbr_txt_user_profile_name.setText(name);
        
        TextView tv_txt_product_detail_name=(TextView) rootView.findViewById(R.id.txt_product_detail_name);
        tv_txt_product_detail_name.setTypeface(HBApplication.getInstance().getRegularFont());
        if(!hbgf.opi_giv_det_bo.getOpinionsPending().get(pos).getItemBO().getItemTitle().toString().equalsIgnoreCase("null")){
        tv_txt_product_detail_name.setText(hbgf.opi_giv_det_bo.getOpinionsPending().get(pos).getItemBO().getItemTitle());
        }else{
            tv_txt_product_detail_name.setText("");

        }
        
        
        TextView tv_txt_product_detail_product_id=(TextView) rootView.findViewById(R.id.txt_product_detail_product_id);
        tv_txt_product_detail_product_id.setTypeface(HBApplication.getInstance().getRegularFont());
        
        TextView tv_txt_product_detail_product_description=(TextView) rootView.findViewById(R.id.txt_product_detail_product_description);
        tv_txt_product_detail_product_description.setTypeface(HBApplication.getInstance().getNormalFont());
        tv_txt_product_detail_product_description.setText(hbgf.opi_giv_det_bo.getOpinionsPending().get(pos).getItemBO().getItemDesc());
        
        TextView tv_txt_product_detail_size=(TextView) rootView.findViewById(R.id.txt_product_detail_size);
        tv_txt_product_detail_size.setTypeface(HBApplication.getInstance().getNormalFont());
       
        TextView tv_txt_product_detail_color=(TextView) rootView.findViewById(R.id.txt_product_detail_color);
        tv_txt_product_detail_color.setTypeface(HBApplication.getInstance().getNormalFont());
        
        TextView tv_txt_product_detail_price=(TextView) rootView.findViewById(R.id.txt_product_detail_price);
        tv_txt_product_detail_price.setTypeface(HBApplication.getInstance().getBoldFont());
        tv_txt_product_detail_price.setText("Now:$"+hbgf.opi_giv_det_bo.getOpinionsPending().get(pos).getItemBO().getPrice());
        
        txtSelfie = (TextView) rootView.findViewById(R.id.txt_product_detail_selfie);
        txtSelfie.setTypeface(HBApplication.getInstance().getRegularFont());
        txtSelfie.setOnClickListener(this);
        String selfiedetails=hbgf.opi_giv_det_bo.getOpinionsPending().get(pos).getItemSelfieDetailsBO().getSelfiePic().toString();
        if(selfiedetails.equalsIgnoreCase("null")){
        	txtSelfie.setText("Capture Selfie!");
        }else{
        	txtSelfie.setText("View Selfie!");
        }
        
        TextView tv_txt_opinion_response_send_your_opinions=(TextView) rootView.findViewById(R.id.txt_opinion_response_send_your_opinions);
        tv_txt_opinion_response_send_your_opinions.setTypeface(HBApplication.getInstance().getRegularFont());
        tv_txt_opinion_response_send_your_opinions.setOnClickListener(this);
        
        TextView txtOldPrice = (TextView) rootView.findViewById(R.id.txt_product_detail_product_origin_price);
        txtOldPrice.setPaintFlags(txtOldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        txtOldPrice.setTypeface(HBApplication.getInstance().getRegularFont());
        
        edt_opinion_response_describe=(EditText) rootView.findViewById(R.id.edt_opinion_response_describe);
        edt_opinion_response_describe.setTypeface(HBApplication.getInstance().getRegularFont());
        
        
        TextView txtFirstColorCode = (TextView) rootView.findViewById(R.id.txt_product_detail_first_color_code);
        TextView txtSecondColorCode = (TextView) rootView.findViewById(R.id.txt_product_detail_second_color_code);
        TextView txtThirdColorCode = (TextView) rootView.findViewById(R.id.txt_product_detail_thord_color_code);
        
        RoundRectShape rs = new RoundRectShape(null/*new float[] { 10, 10, 10, 10, 10, 10, 10, 10 }*/, null, null);
        ShapeDrawable sd = new HBCustomShapeDrawable(rs, Color.RED, Color.WHITE, 0);
        txtFirstColorCode.setBackgroundDrawable(sd);
        
        //RoundRectShape rs = new RoundRectShape(null, null, null);
        ShapeDrawable secondDrawable = new HBCustomShapeDrawable(rs, Color.GRAY, Color.WHITE, 0);
        txtSecondColorCode.setBackgroundDrawable(secondDrawable);
        
        
        ShapeDrawable thirdDrawable = new HBCustomShapeDrawable(rs, Color.YELLOW, Color.WHITE, 0);
        txtThirdColorCode.setBackgroundDrawable(thirdDrawable);
         
        _imgLike = (ImageButton) rootView.findViewById(R.id.img_opinion_response_like);
        _imgNotSure = (ImageButton) rootView.findViewById(R.id.img_opinion_response_not_sure);
        _imgDoNotLike = (ImageButton) rootView.findViewById(R.id.img_opinion_response_do_not_like);
        
        _imgLike.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				v.setSelected(true);
				_imgNotSure.setSelected(false);
				_imgDoNotLike.setSelected(false);
				useropinintype=1;
			}
		});
        
        _imgNotSure.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				v.setSelected(true);
				_imgLike.setSelected(false);
				_imgDoNotLike.setSelected(false);
				useropinintype=2;
			}
		});
 
        _imgDoNotLike.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			v.setSelected(true);
			_imgNotSure.setSelected(false);
			_imgLike.setSelected(false);
			useropinintype=3;
		}
	});
        return rootView;
    }
	
	
	private Drawable createRectanagleShape() {
		ShapeDrawable shape = new ShapeDrawable();
		shape.getPaint().setStyle(Style.FILL);
		shape.getPaint().setColor(Color.RED/*getActivity().getResources().getColor(R.color.color_transparent)*/);
		
		shape.getPaint().setStyle(Style.STROKE);
		shape.getPaint().setStrokeWidth(4);
		shape.getPaint().setColor(Color.GREEN/*getActivity().getResources().getColor(R.color.category_green_stroke)*/);

		ShapeDrawable shapeD = new ShapeDrawable();
		shapeD.getPaint().setStyle(Style.FILL);
		shapeD.getPaint().setColor(Color.GREEN);
		ClipDrawable clipDrawable = new ClipDrawable(shapeD, Gravity.CENTER, ClipDrawable.HORIZONTAL);

		LayerDrawable layerDrawable = new LayerDrawable(new Drawable[] { clipDrawable, shape });
		return layerDrawable;
	}
	
	
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.txt_opinion_response_send_your_opinions:
			long opinionid=hbgf.opi_giv_det_bo.getOpinionsPending().get(pos).getOpinionBO().getOpinionId();
			String opinionreqdate="1223556433";
			int opiniontype=useropinintype;
			String comments=edt_opinion_response_describe.getText().toString();
			if(opiniontype!=0){
				JSONObject jobj=new JSONObject();
				try {
					jobj.put("opinionId", opinionid);
					jobj.put("opinionRequestDate", opinionreqdate);
					jobj.put("opinionTypeCode", opiniontype);
					jobj.put("comments", comments);
					respondopinion=jobj.toString();
					if(ci.isConnectingToInternet()){
					new HBrespondopinion().execute("");
					}else{
						Toast.makeText(getActivity(), "Please connect to network", Toast.LENGTH_SHORT).show();
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}else{
				Toast.makeText(getActivity(), "Please select your opinion", Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.txt_product_detail_selfie:
			if(txtSelfie.getText().toString().equalsIgnoreCase("Capture Selfie!")){
				Toast.makeText(getActivity(), "Capture", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				 startActivityForResult(intent, REQUEST_CODE_CLICK_IMAGE);		
			}else{
				Toast.makeText(getActivity(), "View", Toast.LENGTH_SHORT).show();

			}
			break;
		}
		
		
	}
	
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		try {
			if(requestCode==REQUEST_CODE_CLICK_IMAGE && resultCode==Activity.RESULT_OK && null!=data){ Bitmap photo = (Bitmap) data.getExtras().get("data"); 
			  Uri tempUri = au.getImageUri(getActivity(), photo);
			  String path=au.getRealPath(getActivity(),tempUri);
			  ImageCompressionAsyncTask img=new ImageCompressionAsyncTask(getActivity(),true);
			  uriSting=img.execute(path).get();
			  encImage=au.encryptedImage(uriSting);
				
			}else {
				Toast.makeText(getActivity(), "Please take a pic",Toast.LENGTH_LONG).show();
			}
		} catch (Exception e) {
			Toast.makeText(getActivity(), "Something wrong", Toast.LENGTH_LONG)
					.show();
		}

	}



	class HBrespondopinion extends AsyncTask<String, String, String> {
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
			
		    hbrf_str_resp_respopinion=au.makeRequeststatusline(HBConstants.respondOpinion, respondopinion);
				return hbrf_str_resp_respopinion;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			progress.dismiss();
			if(hbrf_str_resp_respopinion.equalsIgnoreCase("HTTP/1.1 200 OK")){
				Toast.makeText(getActivity(),"Opinion updated", Toast.LENGTH_SHORT).show();
				}else{
					Toast.makeText(getActivity(),"Opinion not updated", Toast.LENGTH_SHORT).show();
				}
			FragmentManager fm = getFragmentManager();

			FragmentTransaction ft = fm.beginTransaction();

			HBGivenFragment llf = new HBGivenFragment(_notifier);

			ft.replace(R.id.frame_container, llf);

			ft.commit();
			
		}
	}
























}
