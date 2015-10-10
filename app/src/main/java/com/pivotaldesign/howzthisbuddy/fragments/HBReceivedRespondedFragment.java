/**
 * 
 */
package com.pivotaldesign.howzthisbuddy.fragments;

import java.util.Date;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.pivotaldesign.howzthisbuddy.R;
import com.pivotaldesign.howzthisbuddy.adapter.HBOpinionReceivedRespondedAdapter;
import com.pivotaldesign.howzthisbuddy.application.HBApplication;
import com.pivotaldesign.howzthisbuddy.model.HBOpinionReceivedDetail;
import com.pivotaldesign.howzthisbuddy.util.AppUtilities;

/**
 * @author Satish Kolawale
 *
 */
public class HBReceivedRespondedFragment extends Fragment {

	private ListView _listResponded = null;	 
	private Dialog _commentDialog = null;
	private AppUtilities au=new AppUtilities(getActivity());
	int pos;
	HBReceivedFragment hbrf=new HBReceivedFragment();
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_received_responded, container, false);
        _listResponded = (ListView) rootView.findViewById(R.id.list_opinion_received_detail_responded);
		
		HBOpinionReceivedDetail respondeds[] = new HBOpinionReceivedDetail[hbrf.al_opinionrespondedlist1.size()];
		for (int index = 0; index < respondeds.length; index++) {
			HBOpinionReceivedDetail responded = new HBOpinionReceivedDetail();
			String number=Long.toString(hbrf.al_completeresp1.get(0).getOpinionRespondedList().get(index).getResponsePhoneNumber());
			String name=au.getContactName(getActivity(), number);
			responded.setRespondedBuddyName(name);
			responded.setDate(new Date());
			responded.setRespondedStatus(hbrf.al_completeresp1.get(0).getOpinionRespondedList().get(index).getOpinionTypeCode());
			respondeds[index] = responded;
		}
		
		HBOpinionReceivedRespondedAdapter adapter = new HBOpinionReceivedRespondedAdapter(getActivity(), respondeds);
		_listResponded.setAdapter(adapter);
		_listResponded.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				pos=position;
				showCommentDialog();				
			}
		});
        
        return rootView;
    }
	
	
	/**
	 * Show alert dialog for sending password to your email account 
	 */
	private void showCommentDialog() {
		// custom dialog
		_commentDialog = new Dialog(getActivity());
		_commentDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		_commentDialog.setContentView(R.layout.layout_opinion_received_detail_comments_popup);
		
		TextView txtComments = (TextView) _commentDialog.findViewById(R.id.txt_opinion_received_detail_comments);
		txtComments.setText(hbrf.al_opinionrespondedlist1.get(pos).getComments());
		//txtComments.setText("Android apps are built as a combination of distinct components that can be invoked individually. For instance, an individual activity provides a single screen for a user interface, and a service independently performs work in the background.");
		txtComments.setTypeface(HBApplication.getInstance().getRegularFont());
		 
		ImageView imgClose = (ImageView) _commentDialog.findViewById(R.id.img_opinion_received_detail_close);
		imgClose.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				_commentDialog.dismiss();
			}
		});
		_commentDialog.show();
	}
}