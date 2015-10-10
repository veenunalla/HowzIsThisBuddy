/**
 * 
 */
package com.pivotaldesign.howzthisbuddy.fragments;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.pivotaldesign.howzthisbuddy.HBOpinionGivenDetailActivity;
import com.pivotaldesign.howzthisbuddy.R;
import com.pivotaldesign.howzthisbuddy.adapter.HBGivenAdapter;
import com.pivotaldesign.howzthisbuddy.application.HBApplication;
import com.pivotaldesign.howzthisbuddy.bean.ItemBO;
import com.pivotaldesign.howzthisbuddy.bean.ItemSelfieDetailsBO;
import com.pivotaldesign.howzthisbuddy.bean.OpinionBO;
import com.pivotaldesign.howzthisbuddy.bean.OpinionsGivenDetailsBO;
import com.pivotaldesign.howzthisbuddy.bean.UserItemOpinionBO;
import com.pivotaldesign.howzthisbuddy.model.HBConstants;
import com.pivotaldesign.howzthisbuddy.model.HBGiven;
import com.pivotaldesign.howzthisbuddy.model.HBNotifier;
import com.pivotaldesign.howzthisbuddy.util.AppUtilities;
import com.pivotaldesign.howzthisbuddy.util.CheckInternet;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Path.Op;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author Satish Kolawale
 *
 */
public class HBGivenFragment extends Fragment {

	private SharedPreferences hbh_spf_login_details;
	private HBNotifier _notifier = null;
    private AppUtilities au;
    private CheckInternet ci;
    private Gson hbgf_gson_req;
    private HashMap<String,String> hbgf_hm_givenfragment_req_params;
    private String number;
    private String hbgf_str_resp_givenfragment;
    
    private ListView listGivenBuddy;
    private JSONArray hbgf_ja_resp=null;
    private String listdetail;
    public static ArrayList<UserItemOpinionBO> al_uiob_gvn;
    public static ArrayList<UserItemOpinionBO> al_uiob_pending;
    public static ArrayList<OpinionsGivenDetailsBO> al_uiob_detail;
	public static OpinionsGivenDetailsBO opi_giv_det_bo=new OpinionsGivenDetailsBO();

	public HBGivenFragment(HBNotifier notifier) {
		this._notifier = notifier;
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_given, container, false);
        hbgf_hm_givenfragment_req_params=new HashMap<String, String>();
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
        
        TextView txtScreenTitle = (TextView) rootView.findViewById(R.id.txt_screen_title);
        txtScreenTitle.setText(getResources().getString(R.string.str_opinion_given_title));
        txtScreenTitle.setTypeface(HBApplication.getInstance().getBoldFont());
        
        TextView txtBuddyName = (TextView) rootView.findViewById(R.id.txt_user_profile_name);
        txtBuddyName.setTypeface(HBApplication.getInstance().getBoldFont());
         
        listGivenBuddy = (ListView) rootView.findViewById(R.id.list_given_buddy);
        listGivenBuddy.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				JSONObject jobj=new JSONObject();
				try {
					jobj.put("requestPhoneNumber", hbgf_ja_resp.getJSONObject(position).getString("requestPhoneNumber"));
					jobj.put("responsePhoneNumber", number);
					listdetail=jobj.toString();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(ci.isConnectingToInternet()){
				new HBgetOpinionsToBeGivenListDetail().execute("");
				}
				else{
					Toast.makeText(getActivity(), "Please connect to network", Toast.LENGTH_SHORT).show();
				}
			}
		});
        if(ci.isConnectingToInternet()){
        new HBgetOpinionsToBeGivenList().execute("");
        }
		else{
			Toast.makeText(getActivity(), "Please connect to network", Toast.LENGTH_SHORT).show();
		}
        
        return rootView;
    }
	
	
	private void showGivenDetailActivity() {
		startActivityForResult(new Intent(getActivity(), HBOpinionGivenDetailActivity.class), 101);
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == 101) {
			//_notifier.notifier(1);
			FragmentManager fm = getFragmentManager();

			FragmentTransaction ft = fm.beginTransaction();

			HBResponseFragment llf = new HBResponseFragment(_notifier);

			ft.replace(R.id.frame_container, llf);

			ft.commit();
		}
	}

	///919441907588

	class HBgetOpinionsToBeGivenList extends AsyncTask<String, String, String> {
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
			hbgf_gson_req = new Gson();
			hbgf_hm_givenfragment_req_params.put("phoneNumber",number);

		    String reg_params = hbgf_gson_req.toJson(hbgf_hm_givenfragment_req_params);
		    hbgf_str_resp_givenfragment=au.makePostRequest(HBConstants.getOpinionsToBeGivenList, reg_params);
				return hbgf_str_resp_givenfragment;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			progress.dismiss();
			try {
				hbgf_ja_resp=new JSONArray(hbgf_str_resp_givenfragment.toString());
				HBGiven givens[] = new HBGiven[hbgf_ja_resp.length()];
		        for (int i = 0; i < givens.length; i++) {
					HBGiven given = new HBGiven();
					given.setId(i);
					given.setMobilenumber(hbgf_ja_resp.getJSONObject(i).getString("requestPhoneNumber"));
					String number=hbgf_ja_resp.getJSONObject(i).getString("requestPhoneNumber");
					String name=au.getContactName(getActivity(), number);
					given.setGivenName(name);
					given.setGivenCount(hbgf_ja_resp.getJSONObject(i).getInt("opinionGivenCount"));
					given.setPendingCount(hbgf_ja_resp.getJSONObject(i).getInt("opinionPendingCount"));
					givens[i] = given;
				}
		        
		        HBGivenAdapter givenAdapter = new HBGivenAdapter(getActivity(), givens);
		        listGivenBuddy.setAdapter(givenAdapter);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}


	class HBgetOpinionsToBeGivenListDetail extends AsyncTask<String, String, String> {
		ProgressDialog progress;
		JSONArray hbgf_ja_resp=null;
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
			
		    hbgf_str_resp_givenfragment=au.makePostRequest(HBConstants.getOpinionsToBeGivenDetail, listdetail);
				return hbgf_str_resp_givenfragment;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			progress.dismiss();
			JSONObject hbgf_ga_resp;
			UserItemOpinionBO usr_itm_opi_bo_gvn;
			UserItemOpinionBO usr_itm_opi_bo_pndng;
			ItemBO item_bo;
			ItemBO item_bo1;
			OpinionBO opinion_bo;
			OpinionBO opinion_bo1;
			ItemSelfieDetailsBO item_selfie_gvn_bo;
			ItemSelfieDetailsBO item_selfie_pend_bo;
			al_uiob_gvn=new ArrayList<UserItemOpinionBO>();
			al_uiob_pending=new ArrayList<UserItemOpinionBO>();
			al_uiob_detail=new ArrayList<OpinionsGivenDetailsBO>();
			JSONArray hbgf_ja_opinionsgiven;
			JSONArray hbgf_ja_opinionspending;
			try {
				hbgf_ga_resp=new JSONObject(hbgf_str_resp_givenfragment.toString());
				
				opi_giv_det_bo.setRequestPhoneNumber(hbgf_ga_resp.getLong("requestPhoneNumber"));
				opi_giv_det_bo.setResponsePhoneNumber(hbgf_ga_resp.getLong("responsePhoneNumber"));
				if(hbgf_ga_resp.getJSONArray("opinionsGiven").length()!=0){
					try {
						hbgf_ja_opinionsgiven = hbgf_ga_resp.getJSONArray("opinionsGiven");
						for(int i=0;i<hbgf_ja_opinionsgiven.length();i++){
							JSONObject jobj_gvn;
							usr_itm_opi_bo_gvn=new UserItemOpinionBO();
							item_bo=new ItemBO();
							opinion_bo=new OpinionBO();
							item_selfie_gvn_bo=new ItemSelfieDetailsBO();
							//jobj_gvn=hbgf_ja_opinionsgiven.getJSONObject(index);
							item_bo.setItemId(hbgf_ja_opinionsgiven.getJSONObject(i).getJSONObject("itemBO").getLong("itemId"));
							item_bo.setItemDesc(hbgf_ja_opinionsgiven.getJSONObject(i).getJSONObject("itemBO").getString("itemDesc"));
							item_bo.setItemTitle(hbgf_ja_opinionsgiven.getJSONObject(i).getJSONObject("itemBO").getString("itemTitle"));
							item_bo.setVendorId(hbgf_ja_opinionsgiven.getJSONObject(i).getJSONObject("itemBO").getLong("vendorId"));
							item_bo.setVendorProductId(hbgf_ja_opinionsgiven.getJSONObject(i).getJSONObject("itemBO").getString("vendorProductId"));
							item_bo.setProductUrl(hbgf_ja_opinionsgiven.getJSONObject(i).getJSONObject("itemBO").getString("productUrl"));
							item_bo.setPrice(hbgf_ja_opinionsgiven.getJSONObject(i).getJSONObject("itemBO").getString("price"));
						    opinion_bo.setOpinionId(hbgf_ja_opinionsgiven.getJSONObject(i).getJSONObject("opinionBO").getLong("opinionId"));
						    opinion_bo.setOpinionRequestDate(hbgf_ja_opinionsgiven.getJSONObject(i).getJSONObject("opinionBO").getString("opinionRequestDate"));;
						    opinion_bo.setOpinionTypeCode(hbgf_ja_opinionsgiven.getJSONObject(i).getJSONObject("opinionBO").getLong("opinionTypeCode"));
						    opinion_bo.setOpinionResponseDate(hbgf_ja_opinionsgiven.getJSONObject(i).getJSONObject("opinionBO").getString("opinionResponseDate"));
						    opinion_bo.setComments(hbgf_ja_opinionsgiven.getJSONObject(i).getJSONObject("opinionBO").getString("comments"));
						    opinion_bo.setResponsePhoneNumber(hbgf_ja_opinionsgiven.getJSONObject(i).getJSONObject("opinionBO").getLong("responsePhoneNumber"));
						    item_selfie_gvn_bo.setItemSelfieDetailsId(hbgf_ja_opinionsgiven.getJSONObject(i).getJSONObject("itemSelfieDetailsBO").getLong("itemSelfieDetailsId"));
						    item_selfie_gvn_bo.setItemSelfieDetailsFlag(hbgf_ja_opinionsgiven.getJSONObject(i).getJSONObject("itemSelfieDetailsBO").getBoolean("itemSelfieDetailsFlag"));
						    item_selfie_gvn_bo.setSelfiePic(hbgf_ja_opinionsgiven.getJSONObject(i).getJSONObject("itemSelfieDetailsBO").getString("selfiePic"));
						    usr_itm_opi_bo_gvn.setItemBO(item_bo);
						    usr_itm_opi_bo_gvn.setOpinionBO(opinion_bo);
						    usr_itm_opi_bo_gvn.setItemSelfieDetailsBO(item_selfie_gvn_bo);
						    usr_itm_opi_bo_gvn.setRequestPhoneNumber(hbgf_ja_opinionsgiven.getJSONObject(i).getLong("requestPhoneNumber"));
						    usr_itm_opi_bo_gvn.setResponsePhoneNumber(hbgf_ja_opinionsgiven.getJSONObject(i).getString("responsePhoneNumber"));
						    al_uiob_gvn.add(usr_itm_opi_bo_gvn);
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						hbgf_ja_opinionsgiven=null;
						e.printStackTrace();
					}
				}
				else{
					//al_uiob_gvn.add(null);
				}
				if(hbgf_ga_resp.getJSONArray("opinionsPending").length()!=0){
					try{
						hbgf_ja_opinionspending=hbgf_ga_resp.getJSONArray("opinionsPending");
						for(int j=0;j<hbgf_ja_opinionspending.length();j++){
							usr_itm_opi_bo_pndng=new UserItemOpinionBO();
							item_bo1=new ItemBO();
							opinion_bo1=new OpinionBO();
							item_selfie_pend_bo=new ItemSelfieDetailsBO();
							item_bo1.setItemId(hbgf_ja_opinionspending.getJSONObject(j).getJSONObject("itemBO").getLong("itemId"));
							item_bo1.setItemDesc(hbgf_ja_opinionspending.getJSONObject(j).getJSONObject("itemBO").getString("itemDesc"));
							item_bo1.setItemTitle(hbgf_ja_opinionspending.getJSONObject(j).getJSONObject("itemBO").getString("itemTitle"));
							item_bo1.setVendorId(hbgf_ja_opinionspending.getJSONObject(j).getJSONObject("itemBO").getLong("vendorId"));
							item_bo1.setVendorProductId(hbgf_ja_opinionspending.getJSONObject(j).getJSONObject("itemBO").getString("vendorProductId"));
							item_bo1.setProductUrl(hbgf_ja_opinionspending.getJSONObject(j).getJSONObject("itemBO").getString("productUrl"));
							item_bo1.setPrice(hbgf_ja_opinionspending.getJSONObject(j).getJSONObject("itemBO").getString("price"));
						    opinion_bo1.setOpinionId(hbgf_ja_opinionspending.getJSONObject(j).getJSONObject("opinionBO").getLong("opinionId"));
						    opinion_bo1.setOpinionRequestDate(hbgf_ja_opinionspending.getJSONObject(j).getJSONObject("opinionBO").getString("opinionRequestDate"));;
						    opinion_bo1.setOpinionTypeCode(hbgf_ja_opinionspending.getJSONObject(j).getJSONObject("opinionBO").getLong("opinionTypeCode"));
						    opinion_bo1.setOpinionResponseDate(hbgf_ja_opinionspending.getJSONObject(j).getJSONObject("opinionBO").getString("opinionResponseDate"));
						    opinion_bo1.setComments(hbgf_ja_opinionspending.getJSONObject(j).getJSONObject("opinionBO").getString("comments"));
						    opinion_bo1.setResponsePhoneNumber(hbgf_ja_opinionspending.getJSONObject(j).getJSONObject("opinionBO").getLong("responsePhoneNumber"));
						    item_selfie_pend_bo.setItemSelfieDetailsId(hbgf_ja_opinionspending.getJSONObject(j).getJSONObject("itemSelfieDetailsBO").getLong("itemSelfieDetailsId"));
						    item_selfie_pend_bo.setItemSelfieDetailsFlag(hbgf_ja_opinionspending.getJSONObject(j).getJSONObject("itemSelfieDetailsBO").getBoolean("itemSelfieDetailsFlag"));
						    item_selfie_pend_bo.setSelfiePic(hbgf_ja_opinionspending.getJSONObject(j).getJSONObject("itemSelfieDetailsBO").getString("selfiePic"));
						    usr_itm_opi_bo_pndng.setItemBO(item_bo1);
						    usr_itm_opi_bo_pndng.setOpinionBO(opinion_bo1);
						    usr_itm_opi_bo_pndng.setItemSelfieDetailsBO(item_selfie_pend_bo);
						    usr_itm_opi_bo_pndng.setRequestPhoneNumber(hbgf_ja_opinionspending.getJSONObject(j).getLong("requestPhoneNumber"));
						    usr_itm_opi_bo_pndng.setResponsePhoneNumber(hbgf_ja_opinionspending.getJSONObject(j).getString("responsePhoneNumber"));
						    al_uiob_pending.add(usr_itm_opi_bo_pndng);
						}
						
						
					}catch(Exception e){
						e.printStackTrace();
					}
				}else{
					//al_uiob_pending.add(null);
				}
				opi_giv_det_bo.setOpinionsGiven(al_uiob_gvn);
				opi_giv_det_bo.setOpinionsPending(al_uiob_pending);
				/*HBGiven givens[] = new HBGiven[hbgf_ja_resp.length()];
		        for (int i = 0; i < givens.length; i++) {
					HBGiven given = new HBGiven();
					given.setId(i);
					given.setMobilenumber(hbgf_ja_resp.getJSONObject(i).getString("requestPhoneNumber"));
					given.setGivenName("Walter White " + i);
					given.setGivenCount(hbgf_ja_resp.getJSONObject(i).getInt("opinionGivenCount"));
					given.setPendingCount(hbgf_ja_resp.getJSONObject(i).getInt("opinionPendingCount"));
					givens[i] = given;
				}*/
		        
		        /*HBGivenAdapter givenAdapter = new HBGivenAdapter(getActivity(), givens);
		        listGivenBuddy.setAdapter(givenAdapter);*/
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			al_uiob_detail.add(opi_giv_det_bo);
			showGivenDetailActivity();
		}
	}






}
