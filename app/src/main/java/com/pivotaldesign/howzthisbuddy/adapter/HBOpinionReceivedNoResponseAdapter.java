/**
 * 
 */
package com.pivotaldesign.howzthisbuddy.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.pivotaldesign.howzthisbuddy.R;
import com.pivotaldesign.howzthisbuddy.application.HBApplication;

/**
 * @author Satish Kolawale
 *
 */
public class HBOpinionReceivedNoResponseAdapter extends ArrayAdapter<String> {

	private Context _context = null;
	private Typeface _typefaceRegular = null;
	
	public HBOpinionReceivedNoResponseAdapter(Context context, String[] given) {
		super(context, R.layout.layout_opinion_received_detail_no_response_item, given);
		this._context = context;
		_typefaceRegular = HBApplication.getInstance().getRegularFont();
	}
	
	
	private static class ViewHolder
	{
		TextView txtContactName;
	}
	
	ViewHolder holder = null;
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater vi = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = vi.inflate(R.layout.layout_opinion_received_detail_no_response_item, null);
			
			holder = new  ViewHolder();
			holder.txtContactName = (TextView) convertView.findViewById(R.id.txt_opinion_received_no_response_contact_name);
		
			holder.txtContactName.setTypeface(_typefaceRegular);
			((TextView) convertView.findViewById(R.id.txt_opinion_received_no_response_invite_again)).setTypeface(_typefaceRegular);
			
			convertView.setTag(holder);
		}
		else
			holder = (ViewHolder) convertView.getTag();
		
		if(position % 2 == 1)
			convertView.setBackgroundColor(_context.getResources().getColor(R.color.color_given_response_bg));
		else
			convertView.setBackgroundColor(_context.getResources().getColor(android.R.color.white));
		
			
		if (holder.txtContactName != null)
			holder.txtContactName.setText(getItem(position));
		
		return convertView;
	}
}
