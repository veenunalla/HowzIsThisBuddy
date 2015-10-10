/**
 * 
 */
package com.pivotaldesign.howzthisbuddy.adapter;

import java.text.SimpleDateFormat;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pivotaldesign.howzthisbuddy.R;
import com.pivotaldesign.howzthisbuddy.application.HBApplication;
import com.pivotaldesign.howzthisbuddy.model.HBGivenResponse;
import com.pivotaldesign.howzthisbuddy.model.HBOpinionReceived;
import com.pivotaldesign.howzthisbuddy.model.HBOpinionReceivedDetail;

/**
 * @author Satish Kolawale
 *
 */
public class HBOpinionReceivedRespondedAdapter extends ArrayAdapter<HBOpinionReceivedDetail> {

	private Context _context = null;
	private Typeface _typefaceRegular = null;
	private SimpleDateFormat _dateFormat = null;
	
	public HBOpinionReceivedRespondedAdapter(Context context, HBOpinionReceivedDetail[] given) {
		super(context, R.layout.layout_opinion_received_detail_responded_item, given);
		this._context = context;
		_typefaceRegular = HBApplication.getInstance().getRegularFont();
		_dateFormat = new SimpleDateFormat("MM-dd-yyyy");
	}
	
	
	private static class ViewHolder
	{
		TextView txtRespondedName, txtDate, txtComment;
		ImageView imgStatus;
	}
	
	ViewHolder holder = null;
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		HBOpinionReceivedDetail responded = getItem(position);
		if (convertView == null) {
			LayoutInflater vi = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = vi.inflate(R.layout.layout_opinion_received_detail_responded_item, null);
			
			holder = new  ViewHolder();
			holder.txtRespondedName = (TextView) convertView.findViewById(R.id.txt_opinion_received_responded_contact_name);
			holder.txtDate = (TextView) convertView.findViewById(R.id.txt_opinion_received_responded_date);
			holder.txtComment = (TextView) convertView.findViewById(R.id.txt_opinion_received_responded_comment);
			
			holder.imgStatus = (ImageView) convertView.findViewById(R.id.img_opinion_received_responded_status);
				
			holder.txtRespondedName.setTypeface(_typefaceRegular);
			holder.txtDate.setTypeface(_typefaceRegular);
			holder.txtComment.setTypeface(_typefaceRegular);
			
			convertView.setTag(holder);
		}
		else
			holder = (ViewHolder) convertView.getTag();
		
		if(position % 2 == 1)
			convertView.setBackgroundColor(_context.getResources().getColor(R.color.color_given_response_bg));
		else
			convertView.setBackgroundColor(_context.getResources().getColor(android.R.color.white));
		
			
		if (holder.txtRespondedName != null)
			holder.txtRespondedName.setText(responded.getRespondedBuddyName());
			 
		if(holder.txtDate != null) {
			holder.txtDate.setText("" + _dateFormat.format(responded.getDate()));
		}
		if(responded.getRespondedStatus()==1){
			holder.imgStatus.setImageResource(R.drawable.small_like);
		}else if(responded.getRespondedStatus()==2){
			holder.imgStatus.setImageResource(R.drawable.small_not_sure);
		}else if(responded.getRespondedStatus()==3){
			holder.imgStatus.setImageResource(R.drawable.small_do_not_like);
		}
		
		return convertView;
	}
}
