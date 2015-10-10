/**
 * 
 */
package com.pivotaldesign.howzthisbuddy.fragments;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.pivotaldesign.howzthisbuddy.HBOpinionReceivedDetailActivity;
import com.pivotaldesign.howzthisbuddy.R;
import com.pivotaldesign.howzthisbuddy.application.HBApplication;
import com.pivotaldesign.howzthisbuddy.bean.ItemBO;
import com.pivotaldesign.howzthisbuddy.bean.ItemSelfieDetailsBO;
import com.pivotaldesign.howzthisbuddy.bean.OpinionBO;
import com.pivotaldesign.howzthisbuddy.bean.UserItemOpinionRequestSummary;
import com.pivotaldesign.howzthisbuddy.model.HBConstants;
import com.pivotaldesign.howzthisbuddy.util.AppUtilities;
import com.pivotaldesign.howzthisbuddy.util.CheckInternet;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author Satish Kolawale
 *
 */
public class HBReceivedFragment extends Fragment{
	
	
	private CheckInternet ci;
    private AppUtilities au;
	private Gson hbreceivedfrag_gson_req;
	private HashMap<String, String> hbreceivedfragment_hm_getallitemopinionreq;
	private String hbr_str_getallitemopinion_resp;
    private String hbr_str_getselecteditemopinion_resp1;
    private String hbr_str_captureopinion_resp;
	private HashMap<String, String> hbreceivedfragment_hm_getselecteditemopinionreq1;
    private Gson hbreceivedfrag_gson_req1;
	private SharedPreferences spfcreds;
	public static ArrayList<UserItemOpinionRequestSummary> al_completeresp;
	public static ArrayList<OpinionBO> al_opinionrespondedlist;
	public static ArrayList<OpinionBO> al_opinionrespondedpendinglist;
	public static ArrayList<ItemBO> al_itembo;
	public static ArrayList<ItemSelfieDetailsBO> al_itemselfiedetailsbo;
	public static ArrayList<UserItemOpinionRequestSummary> al_completeresp1;
	public static ArrayList<OpinionBO> al_opinionrespondedlist1;
	public static ArrayList<OpinionBO> al_opinionrespondedpendinglist1;
	public static ArrayList<ItemBO> al_itembo1;
	public static ArrayList<ItemSelfieDetailsBO> al_itemselfiedetailsbo1;
	private static final int REQUEST_CODE_CLICK_IMAGE = 2;
	int pos;
	private ListView listBuddy;
	private Long hbrf_lng_selected_itemid;
	private Long hbrf_lng_selected_itemselfieid;
	private Long hbrf_lng_phonenumber;
	private String uriSting;
	private String encImage;

	@SuppressLint("NewApi")
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_received, container, false);
        spfcreds=getActivity().getSharedPreferences("loginprefs", 0);
        hbreceivedfragment_hm_getallitemopinionreq=new HashMap<String, String>();
        al_completeresp=new ArrayList<UserItemOpinionRequestSummary>();
    	al_opinionrespondedlist=new ArrayList<OpinionBO>();
    	al_opinionrespondedpendinglist=new ArrayList<OpinionBO>();
    	al_itembo=new ArrayList<ItemBO>();
    	al_itemselfiedetailsbo=new ArrayList<ItemSelfieDetailsBO>();
    	hbreceivedfragment_hm_getselecteditemopinionreq1=new HashMap<String, String>();
        al_completeresp1=new ArrayList<UserItemOpinionRequestSummary>();
    	al_opinionrespondedlist1=new ArrayList<OpinionBO>();
    	al_opinionrespondedpendinglist1=new ArrayList<OpinionBO>();
    	al_itembo1=new ArrayList<ItemBO>();
    	al_itemselfiedetailsbo1=new ArrayList<ItemSelfieDetailsBO>();
    	TextView hbr_txt_user_profile_name=(TextView)rootView.findViewById(R.id.txt_user_profile_name);
    	ImageView hbr_iv_user_pic=(ImageView)rootView.findViewById(R.id.img_user_profile_photo);
    	hbr_txt_user_profile_name.setTypeface(HBApplication.getInstance().getRegularFont());
    	String name=spfcreds.getString("username", "");
    	String pic=spfcreds.getString("profilepic", "");
    	ci=new CheckInternet(getActivity());
        au=new AppUtilities(getActivity());
    	if(pic.length()!=0){
    		Bitmap bm=au.StringToBitMap(pic);
        	hbr_iv_user_pic.setImageBitmap(bm);
    	}
    	
    	hbr_txt_user_profile_name.setText(name);
        
        ((TextView) rootView.findViewById(R.id.txt_opinion_received_subtitle)).setTypeface(HBApplication.getInstance().getBoldFont());
        
        listBuddy = (ListView) rootView.findViewById(R.id.list_opinion_received);

        if(ci.isConnectingToInternet()){
        	new HBRgetallitemopinionrequests().execute("");
        	
        }else{
	    	Toast.makeText(getActivity(), "Please connect to Network", Toast.LENGTH_SHORT).show();	

        }
        listBuddy.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				hbrf_lng_selected_itemid=al_completeresp.get(position).getItemBO().getItemId();
				hbrf_lng_phonenumber=al_completeresp.get(position).getPhoneNumber();
				pos=position;
				if(ci.isConnectingToInternet()){
				 new HBRgetselecteditemopinionrequests().execute("");
				}else{
			    	Toast.makeText(getActivity(), "Please connect to Network", Toast.LENGTH_SHORT).show();	

		        }
			}
		});
        
        return rootView;
    }
	
	
	private void showOpinionReceivedDetailActivity() {
		Intent i=new Intent(getActivity(),HBOpinionReceivedDetailActivity.class);
		i.putExtra("ItemId", hbrf_lng_selected_itemid);
		i.putExtra("MobNum", hbrf_lng_phonenumber);
		i.putExtra("position",pos);
		startActivity(i);
	}
	
	
	
	
	
	class HBRgetallitemopinionrequests extends AsyncTask<String, String, String> {
		ProgressDialog progress;
		JSONArray hbrf_jsonArr_allitem_resp=null;
		JSONObject  hbrf_json0bj_allitem_resp=null;
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
			hbreceivedfrag_gson_req = new Gson();
			String number=spfcreds.getString("mobilenum", "");
			hbreceivedfragment_hm_getallitemopinionreq.put("phoneNumber", number);
		    String reg_params = hbreceivedfrag_gson_req.toJson(hbreceivedfragment_hm_getallitemopinionreq);
		    hbr_str_getallitemopinion_resp=au.makePostRequest(HBConstants.getOpinionsRecievedList, reg_params);
				return hbr_str_getallitemopinion_resp;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			progress.dismiss();
			al_completeresp.clear();
			al_itembo.clear();
			al_itemselfiedetailsbo.clear();
			if(hbr_str_getallitemopinion_resp!=null){
				try {
					hbrf_jsonArr_allitem_resp=new JSONArray (hbr_str_getallitemopinion_resp.toString());
					UserItemOpinionRequestSummary u_item_op_req_sum;
					ItemBO item_bo;
					ItemSelfieDetailsBO item_sdbo;
					for(int i=0;i<hbrf_jsonArr_allitem_resp.length();i++){
						u_item_op_req_sum=new UserItemOpinionRequestSummary();
						item_bo=new ItemBO();
						item_sdbo=new ItemSelfieDetailsBO();
						JSONObject c = hbrf_jsonArr_allitem_resp.getJSONObject(i);
						Long phonenumber = c.getLong("phoneNumber");
						JSONObject itemBO = c.getJSONObject("itemBO");
						Long itemID=itemBO.getLong("itemId");
						String itemdesc=itemBO.getString("itemDesc");
						String itemTitle=itemBO.getString("itemTitle");
						Long vendorId=itemBO.getLong("vendorId");
						String vendorProductId=itemBO.getString("vendorProductId");
						String productUrl=itemBO.getString("productUrl");
						String price=itemBO.getString("price");
						JSONObject itemSelfieDetailsBO = c.getJSONObject("itemSelfieDetailsBO");
						Long itemselfiedetailId=itemSelfieDetailsBO.getLong("itemSelfieDetailsId");
						Boolean itemselfidailogflag=itemSelfieDetailsBO.getBoolean("itemSelfieDetailsFlag");
						String selfiePic=itemSelfieDetailsBO.getString("selfiePic");
						Long likeCount=c.getLong("likeCount");
						Long disLikeCount=c.getLong("disLikeCount");
						Long mayBeCount=c.getLong("mayBeCount");
						Long opinionRequestCount=c.getLong("opinionRequestCount");
						Long opinionResponseCount=c.getLong("opinionResponseCount");
						String opinionRequestDate=c.getString("opinionRequestDate");
						JSONArray jarropinionrespondedlist;
						OpinionBO ob,ob1;
						if(!c.get("opinionRespondedList").toString().equalsIgnoreCase("null")){
							try {
								jarropinionrespondedlist = c.getJSONArray("opinionRespondedList");
							} catch (Exception e) {
								// TODO Auto-generated catch block
								jarropinionrespondedlist=null;
								e.printStackTrace();
							}
							//OpinionBO ob;
							if(jarropinionrespondedlist!=null){
							for(int j=0;j<jarropinionrespondedlist.length();j++){
								
								
									ob=new OpinionBO();
									Long opinionid=jarropinionrespondedlist.getJSONObject(j).getLong("opinionId");
									String opinionrequestdate=jarropinionrespondedlist.getJSONObject(j).getString("opinionRequestDate");
									Long opiniontypecode=jarropinionrespondedlist.getJSONObject(j).getLong("opinionTypeCode");
									String opinionresponsedate=jarropinionrespondedlist.getJSONObject(j).getString("opinionResponseDate");
									String comments=jarropinionrespondedlist.getJSONObject(j).getString("comments");
									Long responsephonenumber=jarropinionrespondedlist.getJSONObject(j).getLong("responsePhoneNumber");
									ob.setOpinionId(opinionid);
									ob.setOpinionRequestDate(opinionrequestdate);
									ob.setOpinionTypeCode(opiniontypecode);
									ob.setOpinionResponseDate(opinionresponsedate);
									ob.setComments(comments);
									ob.setResponsePhoneNumber(responsephonenumber);
									al_opinionrespondedlist.add(ob);
								}
							}
						}else{
							al_opinionrespondedlist=null;
						}
						
						JSONArray jarropinionrespondedpendinglist = null;
						if(!c.get("opinionRespondPendingList").toString().equalsIgnoreCase("null")){
							try {
								jarropinionrespondedpendinglist = c.getJSONArray("opinionRespondPendingList");
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								jarropinionrespondedpendinglist=null;
							}
							if(jarropinionrespondedpendinglist!=null){
							for(int k=0;k<jarropinionrespondedpendinglist.length();k++){
		                    	
		                    		ob1=new OpinionBO();
		                    		Long opinionid=jarropinionrespondedpendinglist.getJSONObject(k).getLong("opinionId");
									String opinionrequestdate=jarropinionrespondedpendinglist.getJSONObject(k).getString("opinionRequestDate");
									Long opiniontypecode=jarropinionrespondedpendinglist.getJSONObject(k).getLong("opinionTypeCode");
									String opinionresponsedate=jarropinionrespondedpendinglist.getJSONObject(k).getString("opinionResponseDate");
									String comments=jarropinionrespondedpendinglist.getJSONObject(k).getString("comments");
									Long responsephonenumber=jarropinionrespondedpendinglist.getJSONObject(k).getLong("responsePhoneNumber");
									ob1.setOpinionId(opinionid);
									ob1.setOpinionRequestDate(opinionrequestdate);
									ob1.setOpinionTypeCode(opiniontypecode);
									ob1.setOpinionResponseDate(opinionresponsedate);
									ob1.setComments(comments);
									ob1.setResponsePhoneNumber(responsephonenumber);
									al_opinionrespondedpendinglist.add(ob1);
		                    	}
		                    }
						}else{
							al_opinionrespondedpendinglist=null;
						}
						

						u_item_op_req_sum.setPhoneNumber(phonenumber);
						item_bo.setItemId(itemID);
						item_bo.setItemDesc(itemdesc);
						item_bo.setItemTitle(itemTitle);
						item_bo.setVendorId(vendorId);
						item_bo.setVendorProductId(vendorProductId);
						item_bo.setProductUrl(productUrl);
						item_bo.setPrice(price);
						u_item_op_req_sum.setItemBO(item_bo);
						item_sdbo.setItemSelfieDetailsId(itemselfiedetailId);
						item_sdbo.setItemSelfieDetailsFlag(itemselfidailogflag);
						item_sdbo.setSelfiePic(""+selfiePic);
						u_item_op_req_sum.setItemSelfieDetailsBO(item_sdbo);
						u_item_op_req_sum.setLikeCount(likeCount);
						u_item_op_req_sum.setDisLikeCount(disLikeCount);
						u_item_op_req_sum.setMayBeCount(mayBeCount);
						u_item_op_req_sum.setOpinionRequestCount(opinionRequestCount);
						u_item_op_req_sum.setOpinionResponseCount(opinionResponseCount);
						u_item_op_req_sum.setOpinionRequestDate(opinionRequestDate);
						u_item_op_req_sum.setOpinionRespondedList(al_opinionrespondedlist);
						u_item_op_req_sum.setOpinionRespondPendingList(al_opinionrespondedpendinglist);
						al_completeresp.add(u_item_op_req_sum);
						
					}
					listBuddy.setAdapter(new UORS_Adapter());
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else{
				
			}
			
		}
	}

	
	class HBRgetselecteditemopinionrequests extends AsyncTask<String, String, String> {
		ProgressDialog progress;
		JSONArray hbrf_jsonArr_selecteditem_resp=null;
		JSONObject  hbrf_json0bj_selecteditem_resp=null;
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
			hbreceivedfrag_gson_req1 = new Gson();
			hbreceivedfragment_hm_getselecteditemopinionreq1.put("phoneNumber", ""+hbrf_lng_phonenumber);
			hbreceivedfragment_hm_getselecteditemopinionreq1.put("itemId",""+hbrf_lng_selected_itemid);
		    String reg_params = hbreceivedfrag_gson_req1.toJson(hbreceivedfragment_hm_getselecteditemopinionreq1);
		    hbr_str_getselecteditemopinion_resp1=au.makePostRequest(HBConstants.getOpinionsRecievedDetail, reg_params);
			  
				return hbr_str_getselecteditemopinion_resp1;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			progress.dismiss();
			//Displaying Complete data
			//Toast.makeText(getActivity(), ""+hbr_str_getselecteditemopinion_resp.toString(), Toast.LENGTH_SHORT).show();
			al_completeresp1.clear();
	    	al_opinionrespondedlist1.clear();
	    	al_opinionrespondedpendinglist1.clear();
	    	al_itembo1.clear();
	    	al_itemselfiedetailsbo1.clear();
			
			
			
			
			if(hbr_str_getselecteditemopinion_resp1!=null){
				try {
					UserItemOpinionRequestSummary u_item_op_req_sum;
					ItemBO item_bo;
					ItemSelfieDetailsBO item_sdbo;
					//for(int i=0;i<hbrf_jsonArr_selecteditem_resp.length();i++){
						u_item_op_req_sum=new UserItemOpinionRequestSummary();
						item_bo=new ItemBO();
						item_sdbo=new ItemSelfieDetailsBO();
						JSONObject c=new JSONObject(hbr_str_getselecteditemopinion_resp1);
						Long phonenumber = c.getLong("phoneNumber");
						JSONObject itemBO = c.getJSONObject("itemBO");
						Long itemID=itemBO.getLong("itemId");
						String itemdesc=itemBO.getString("itemDesc");
						String itemTitle=itemBO.getString("itemTitle");
						Long vendorId=itemBO.getLong("vendorId");
						String vendorProductId=itemBO.getString("vendorProductId");
						String productUrl=itemBO.getString("productUrl");
						String price=itemBO.getString("price");
						JSONObject itemSelfieDetailsBO = c.getJSONObject("itemSelfieDetailsBO");
						Long itemselfiedetailId=itemSelfieDetailsBO.getLong("itemSelfieDetailsId");
						Boolean itemselfidailogflag=itemSelfieDetailsBO.getBoolean("itemSelfieDetailsFlag");
						String selfiePic=itemSelfieDetailsBO.getString("selfiePic");
						Long likeCount=c.getLong("likeCount");
						Long disLikeCount=c.getLong("disLikeCount");
						Long mayBeCount=c.getLong("mayBeCount");
						Long opinionRequestCount=c.getLong("opinionRequestCount");
						Long opinionResponseCount=c.getLong("opinionResponseCount");
						String opinionRequestDate=c.getString("opinionRequestDate");
						JSONArray jarropinionrespondedlist;
						OpinionBO ob,ob1;
						if(!c.get("opinionRespondedList").toString().equalsIgnoreCase("null")){
							try {
								jarropinionrespondedlist = c.getJSONArray("opinionRespondedList");
							} catch (Exception e) {
								// TODO Auto-generated catch block
								jarropinionrespondedlist=null;
								e.printStackTrace();
							}
							//OpinionBO ob;
							if(jarropinionrespondedlist!=null){
							for(int j=0;j<jarropinionrespondedlist.length();j++){
								
								
									ob=new OpinionBO();
									Long opinionid=jarropinionrespondedlist.getJSONObject(j).getLong("opinionId");
									String opinionrequestdate=jarropinionrespondedlist.getJSONObject(j).getString("opinionRequestDate");
									Long opiniontypecode=jarropinionrespondedlist.getJSONObject(j).getLong("opinionTypeCode");
									String opinionresponsedate=jarropinionrespondedlist.getJSONObject(j).getString("opinionResponseDate");
									String comments=jarropinionrespondedlist.getJSONObject(j).getString("comments");
									Long responsephonenumber=jarropinionrespondedlist.getJSONObject(j).getLong("responsePhoneNumber");
									ob.setOpinionId(opinionid);
									ob.setOpinionRequestDate(opinionrequestdate);
									ob.setOpinionTypeCode(opiniontypecode);
									ob.setOpinionResponseDate(opinionresponsedate);
									ob.setComments(comments);
									ob.setResponsePhoneNumber(responsephonenumber);
									al_opinionrespondedlist1.add(ob);
								}
							}
						}else{
							al_opinionrespondedlist1=null;
						}
						
						JSONArray jarropinionrespondedpendinglist = null;
						if(!c.get("opinionRespondPendingList").toString().equalsIgnoreCase("null")){
							try {
								jarropinionrespondedpendinglist = c.getJSONArray("opinionRespondPendingList");
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								jarropinionrespondedpendinglist=null;
							}
							if(jarropinionrespondedpendinglist!=null){
							for(int k=0;k<jarropinionrespondedpendinglist.length();k++){
		                    	
		                    		ob1=new OpinionBO();
		                    		Long opinionid=jarropinionrespondedpendinglist.getJSONObject(k).getLong("opinionId");
									String opinionrequestdate=jarropinionrespondedpendinglist.getJSONObject(k).getString("opinionRequestDate");
									Long opiniontypecode=jarropinionrespondedpendinglist.getJSONObject(k).getLong("opinionTypeCode");
									String opinionresponsedate=jarropinionrespondedpendinglist.getJSONObject(k).getString("opinionResponseDate");
									String comments=jarropinionrespondedpendinglist.getJSONObject(k).getString("comments");
									Long responsephonenumber=jarropinionrespondedpendinglist.getJSONObject(k).getLong("responsePhoneNumber");
									ob1.setOpinionId(opinionid);
									ob1.setOpinionRequestDate(opinionrequestdate);
									ob1.setOpinionTypeCode(opiniontypecode);
									ob1.setOpinionResponseDate(opinionresponsedate);
									ob1.setComments(comments);
									ob1.setResponsePhoneNumber(responsephonenumber);
									al_opinionrespondedpendinglist1.add(ob1);
		                    	}
		                    }
						}else{
							al_opinionrespondedpendinglist1=null;
						}
						

						u_item_op_req_sum.setPhoneNumber(phonenumber);
						item_bo.setItemId(itemID);
						item_bo.setItemDesc(itemdesc);
						item_bo.setItemTitle(itemTitle);
						item_bo.setVendorId(vendorId);
						item_bo.setVendorProductId(vendorProductId);
						item_bo.setProductUrl(productUrl);
						item_bo.setPrice(price);
						u_item_op_req_sum.setItemBO(item_bo);
						item_sdbo.setItemSelfieDetailsId(itemselfiedetailId);
						item_sdbo.setItemSelfieDetailsFlag(itemselfidailogflag);
						item_sdbo.setSelfiePic(""+selfiePic);
						u_item_op_req_sum.setItemSelfieDetailsBO(item_sdbo);
						u_item_op_req_sum.setLikeCount(likeCount);
						u_item_op_req_sum.setDisLikeCount(disLikeCount);
						u_item_op_req_sum.setMayBeCount(mayBeCount);
						u_item_op_req_sum.setOpinionRequestCount(opinionRequestCount);
						u_item_op_req_sum.setOpinionResponseCount(opinionResponseCount);
						u_item_op_req_sum.setOpinionRequestDate(opinionRequestDate);
						u_item_op_req_sum.setOpinionRespondedList(al_opinionrespondedlist1);
						u_item_op_req_sum.setOpinionRespondPendingList(al_opinionrespondedpendinglist1);
						al_completeresp1.add(u_item_op_req_sum);
						
					//}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				showOpinionReceivedDetailActivity();
			}else{
				
			}
		}
	}


	public class UORS_Adapter extends BaseAdapter
	{

		@Override
		public int getCount() 
		{			
			return al_completeresp.size();
		}

		@Override
		public Object getItem(int position) 
		{			
			return al_completeresp.get(position);
		}

		@Override
		public long getItemId(int position) 
		{
			return 0;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent)
		{			
			/*try {
				ItemMyDiary =(Test_List) getItem(position);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			View MyView = convertView;
			LayoutInflater li = getActivity().getLayoutInflater();
			MyView = li.inflate(R.layout.layout_opinion_received_list_item,null);
			
			TextView hbrf_tv_txt_opinion_received_list_item_name=(TextView)MyView.findViewById(R.id.txt_opinion_received_list_item_name);
			TextView hbrf_tv_txt_opinion_received_list_item_description=(TextView)MyView.findViewById(R.id.txt_opinion_received_list_item_description);
			TextView hbrf_tv_txt_opinion_received_list_item_selfie=(TextView)MyView.findViewById(R.id.txt_opinion_received_list_item_selfie);
			TextView hbrf_tv_txt_opinion_received_list_item_price=(TextView)MyView.findViewById(R.id.txt_opinion_received_list_item_price);
			TextView hbrf_tv_txt_opinion_received_list_item_like=(TextView)MyView.findViewById(R.id.txt_opinion_received_list_item_like);
			TextView hbrf_tv_txt_opinion_received_list_item_not_sure=(TextView)MyView.findViewById(R.id.txt_opinion_received_list_item_not_sure);
			TextView hbrf_tv_txt_opinion_received_list_item_dislike=(TextView)MyView.findViewById(R.id.txt_opinion_received_list_item_dislike);
			

			hbrf_tv_txt_opinion_received_list_item_name.setText(al_completeresp.get(position).getItemBO().getItemTitle());
			hbrf_tv_txt_opinion_received_list_item_description.setText(al_completeresp.get(position).getItemBO().getItemDesc());
			if(!al_completeresp.get(position).getItemSelfieDetailsBO().getSelfiePic().toString().equalsIgnoreCase("null")){
				hbrf_tv_txt_opinion_received_list_item_selfie.setText("View Selfie");
			}else{
				hbrf_tv_txt_opinion_received_list_item_selfie.setText("View Selfie");
			}
			hbrf_tv_txt_opinion_received_list_item_selfie.setTag(position);
			/*hbrf_tv_txt_opinion_received_list_item_selfie.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					int p = ((Integer) v.getTag()) + 1;
					Toast.makeText(getActivity(), ""+al_completeresp.get(position).getItemSelfieDetailsBO().getItemSelfieDetailsId(), Toast.LENGTH_SHORT).show();
					Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					 startActivityForResult(intent, REQUEST_CODE_CLICK_IMAGE);	
					hbrf_lng_selected_itemselfieid=al_completeresp.get(position).getItemSelfieDetailsBO().getItemSelfieDetailsId();

				}
			});*/
			hbrf_tv_txt_opinion_received_list_item_price.setText("Price:$"+al_completeresp.get(position).getItemBO().getPrice());
			hbrf_tv_txt_opinion_received_list_item_like.setText(""+al_completeresp.get(position).getLikeCount());
			hbrf_tv_txt_opinion_received_list_item_not_sure.setText(""+al_completeresp.get(position).getMayBeCount());
			hbrf_tv_txt_opinion_received_list_item_dislike.setText(""+al_completeresp.get(position).getDisLikeCount());

			return MyView;

		}	

		
	}


/*	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		try {
			 if(requestCode==REQUEST_CODE_CLICK_IMAGE && resultCode==Activity.RESULT_OK && null!=data){ 
				 Bitmap photo = (Bitmap) data.getExtras().get("data"); 
			  Uri tempUri = au.getImageUri(getActivity(), photo);
			  String path=au.getRealPath(getActivity(),tempUri);
			  ImageCompressionAsyncTask img=new ImageCompressionAsyncTask(getActivity(),true);
			  uriSting=img.execute(path).get();
				new HBRsendcaptureselfie().execute("");
			 }else {
					Toast.makeText(getActivity(), "Please take a pic",Toast.LENGTH_LONG).show();
				}
			} catch (Exception e) {
				Toast.makeText(getActivity(), "Something wrong", Toast.LENGTH_LONG)
						.show();
			}

	}

*/

	class HBRsendcaptureselfie extends AsyncTask<String, String, String> {
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
				
				Toast.makeText(getActivity(), "Item selfie updated", Toast.LENGTH_SHORT).show();

				}else{
					Toast.makeText(getActivity(), "Item Selfie not updated", Toast.LENGTH_SHORT).show();
				}
			
		}
	}















}