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
import com.pivotaldesign.howzthisbuddy.model.HBOffer;

/**
 * @author Satish Kolawale
 *
 */
public class HBOfferAdapter extends ArrayAdapter<HBOffer> {

	private Context _context = null;
	private Typeface _typefaceRegular = null;
	private Typeface _typefaceBold = null;
	
	public HBOfferAdapter(Context context, HBOffer[] offers) {
		super(context, R.layout.layout_offers_list_item, offers);
		this._context = context;
		_typefaceRegular = HBApplication.getInstance().getRegularFont();
		_typefaceBold = HBApplication.getInstance().getBoldFont();
	}
	
	
	private static class ViewHolder
	{
		TextView txtOfferName, txtOfferDescription;
	}
	
	ViewHolder holder = null;
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		HBOffer offer = getItem(position);
		if (convertView == null) {
			LayoutInflater vi = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = vi.inflate(R.layout.layout_offers_list_item, null);
			
			holder = new  ViewHolder();
			holder.txtOfferName = (TextView) convertView.findViewById(R.id.txt_offers_list_item_offer_id);
			holder.txtOfferDescription = (TextView) convertView.findViewById(R.id.txt_offers_list_item_offer_description);
				
			holder.txtOfferName.setTypeface(_typefaceBold);
			holder.txtOfferDescription.setTypeface(_typefaceRegular);
			
			convertView.setTag(holder);
		}
		else
			holder = (ViewHolder) convertView.getTag();
		
		if(position % 2 != 0)
			convertView.setBackgroundColor(_context.getResources().getColor(R.color.color_offer_cell_odd_bg));
		else
			convertView.setBackgroundColor(_context.getResources().getColor(R.color.color_offer_cell_even_bg));
		
			
		if (holder.txtOfferName != null)
			holder.txtOfferName.setText("Offer " + offer.getOfferId());
			 
		if(holder.txtOfferDescription != null)
			holder.txtOfferDescription.setText("" + offer.getOfferDescription());
		
	 
		return convertView;
	}
}
