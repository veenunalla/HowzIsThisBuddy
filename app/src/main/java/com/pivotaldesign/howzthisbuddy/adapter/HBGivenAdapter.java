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
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.pivotaldesign.howzthisbuddy.R;
import com.pivotaldesign.howzthisbuddy.application.HBApplication;
import com.pivotaldesign.howzthisbuddy.model.HBGiven;
import com.pivotaldesign.howzthisbuddy.util.AppUtilities;

/**
 * @author Satish Kolawale
 *
 */
public class HBGivenAdapter extends ArrayAdapter<HBGiven> {

	private Context _context = null;
	private Typeface _typefaceRegular = null;
	private AppUtilities au=new AppUtilities(_context);
	
	
	public HBGivenAdapter(Context context, HBGiven[] given) {
		super(context, R.layout.layout_given_list_item, given);
		this._context = context;
		_typefaceRegular = HBApplication.getInstance().getRegularFont();
	}
	
	
	private static class ViewHolder
	{
		TextView txtGivenName, txtGivenCount, txtPendingCount;
		ImageView iv_img_opinion_given_profile;
	}
	
	ViewHolder holder = null;
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		HBGiven given = getItem(position);
		if (convertView == null) {
			LayoutInflater vi = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = vi.inflate(R.layout.layout_given_list_item, null);
			
			
			holder = new  ViewHolder();
			holder.txtGivenName = (TextView) convertView.findViewById(R.id.txt_opinion_given_name);
			holder.txtGivenCount = (TextView) convertView.findViewById(R.id.txt_opinion_given_given_count);
			holder.txtPendingCount = (TextView) convertView.findViewById(R.id.txt_opinion_given_pending_count);
			holder.iv_img_opinion_given_profile=(ImageView)convertView.findViewById(R.id.img_opinion_given_profile);
				
			holder.txtGivenName.setTypeface(_typefaceRegular);
			holder.txtGivenCount.setTypeface(_typefaceRegular);
			holder.txtPendingCount.setTypeface(_typefaceRegular);
			
			convertView.setTag(holder);
		}
		else
			holder = (ViewHolder) convertView.getTag();
		
		 
		/*if(position % 2 == 0)
			convertView.setBackgroundColor(_context.getResources().getColor(R.color.color_given_response_bg));
		else
			convertView.setBackgroundColor(_context.getResources().getColor(android.R.color.white));*/
		String  name;
		if (holder.txtGivenName != null){
			//holder.txtGivenName.setText(given.getGivenName());
		name=au.getContactName(getContext(), given.getMobilenumber());
		holder.txtGivenName.setText(""+given.getGivenName());
		}
		if(holder.txtGivenCount != null){
			holder.txtGivenCount.setText("Given(" + given.getGivenCount() + ")");
		}
		if(holder.txtPendingCount != null){
			holder.txtPendingCount.setText("Pending(" + given.getPendingCount() + ")");
		}
		return convertView;
	}
}
