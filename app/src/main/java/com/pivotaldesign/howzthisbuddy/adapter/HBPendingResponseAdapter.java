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
import com.pivotaldesign.howzthisbuddy.model.HBGivenResponse;

/**
 * @author Satish Kolawale
 *
 */
public class HBPendingResponseAdapter extends ArrayAdapter<HBGivenResponse> {

	private Context _context = null;
	private Typeface _typefaceRegular = null;
	
	public HBPendingResponseAdapter(Context context, HBGivenResponse[] given) {
		super(context, R.layout.layout_opinion_response_pending_list_item, given);
		this._context = context;
		_typefaceRegular = HBApplication.getInstance().getRegularFont();
	}
	
	
	private static class ViewHolder
	{
		TextView txtPendingName, txtPrice, txtPendingResponseSelfie;
	}
	
	ViewHolder holder = null;
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		HBGivenResponse given = getItem(position);
		if (convertView == null) {
			LayoutInflater vi = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = vi.inflate(R.layout.layout_opinion_response_pending_list_item, null);
			
			holder = new  ViewHolder();
			holder.txtPendingName = (TextView) convertView.findViewById(R.id.txt_opinion_response_pending_list_item_title);
			holder.txtPrice = (TextView) convertView.findViewById(R.id.txt_opinion_response_pending_list_item_price);
			holder.txtPendingResponseSelfie = (TextView) convertView.findViewById(R.id.txt_opinion_response_pending_list_item_view_selfie);
				
			holder.txtPendingName.setTypeface(_typefaceRegular);
			holder.txtPrice.setTypeface(_typefaceRegular);
			holder.txtPendingResponseSelfie.setTypeface(_typefaceRegular);
			
			convertView.setTag(holder);
		}
		else
			holder = (ViewHolder) convertView.getTag();
			
		if (holder.txtPendingName != null){
			holder.txtPendingName.setText(given.getProductName());
		}
		if(holder.txtPrice != null){
			holder.txtPrice.setText("$" + given.getPrice());
		}
		String pic=given.getSelfieUrl();
		if(holder.txtPendingResponseSelfie != null){
			if(pic.equalsIgnoreCase("null")){
				holder.txtPendingResponseSelfie.setText("View Slefie");
			}else{
				holder.txtPendingResponseSelfie.setText("View Selfie");
			}
			
		}
	 
		return convertView;
	}
}