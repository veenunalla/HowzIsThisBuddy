/**
 * 
 */
package com.pivotaldesign.howzthisbuddy.adapter;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pivotaldesign.howzthisbuddy.R;
import com.pivotaldesign.howzthisbuddy.application.HBApplication;

/**
 * @author Satish Kolawale
 *
 */
public class HBOpinionReceivedAdapter extends ArrayAdapter<String> {

	private Context _context = null;
	private Typeface _typefaceRegular = null;
	private Typeface _typefaceBold = null;
	
	public HBOpinionReceivedAdapter(Context context, int resource,  String[] objects) {
		super(context, resource, objects);
		this._context = context;
		_typefaceRegular = HBApplication.getInstance().getRegularFont();
		_typefaceBold = HBApplication.getInstance().getBoldFont();
	}

	private static class ViewHolder
	{
		TextView txtProductName, txtProductDescription, txtViewSelfie, txtOriginPrice, txtCurrentPrice, txtOpinionReceivedDate;
		TextView txtLikeCount, txtNotSureCount, txtDisLikeCount;
		ImageView imgProductPhoto;
	}
	
	ViewHolder holder = null;
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater vi = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = vi.inflate(R.layout.layout_opinion_received_list_item, null);
			
			holder = new  ViewHolder();
			holder.txtProductName = (TextView) convertView.findViewById(R.id.txt_opinion_received_list_item_name);
			holder.txtProductDescription = (TextView) convertView.findViewById(R.id.txt_opinion_received_list_item_description);
			holder.txtViewSelfie = (TextView) convertView.findViewById(R.id.txt_opinion_received_list_item_selfie);
			holder.txtOriginPrice = (TextView) convertView.findViewById(R.id.txt_opinion_received_list_item_origin_price);
			holder.txtCurrentPrice = (TextView) convertView.findViewById(R.id.txt_opinion_received_list_item_price);
			holder.txtOpinionReceivedDate = (TextView) convertView.findViewById(R.id.txt_opinion_received_list_item_date);
			holder.txtLikeCount = (TextView) convertView.findViewById(R.id.txt_opinion_received_list_item_like);
			holder.txtNotSureCount = (TextView) convertView.findViewById(R.id.txt_opinion_received_list_item_not_sure);
			holder.txtDisLikeCount = (TextView) convertView.findViewById(R.id.txt_opinion_received_list_item_dislike);
			
			holder.imgProductPhoto = (ImageView) convertView.findViewById(R.id.img_opinion_received_list_item_picture);
				
			holder.txtProductName.setTypeface(_typefaceRegular);
			holder.txtProductDescription.setTypeface(_typefaceRegular);
			holder.txtViewSelfie.setTypeface(_typefaceBold);
			holder.txtOriginPrice.setTypeface(_typefaceBold);
			holder.txtCurrentPrice.setTypeface(_typefaceBold);
			holder.txtProductDescription.setTypeface(_typefaceRegular);
			holder.txtLikeCount.setTypeface(_typefaceBold);
			holder.txtNotSureCount.setTypeface(_typefaceBold);
			holder.txtDisLikeCount.setTypeface(_typefaceBold);
			holder.txtOpinionReceivedDate.setTypeface(_typefaceRegular);
			
			holder.txtViewSelfie.setText(" View Selfie!");
			
			
			holder.txtOriginPrice.setPaintFlags(holder.txtOriginPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
			
			convertView.setTag(holder);
		}
		else
			holder = (ViewHolder) convertView.getTag();
		
		 SimpleDateFormat format = new SimpleDateFormat("MM-dd-yy");
		 holder.txtOpinionReceivedDate.setText("" + format.format(new Date()));
		return convertView;
	}
}
