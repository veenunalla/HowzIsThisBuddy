/**
 * 
 */
package com.pivotaldesign.howzthisbuddy.adapter;

import java.util.ArrayList;

import com.pivotaldesign.howzthisbuddy.R;
import com.pivotaldesign.howzthisbuddy.model.HBDrawerItem;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author Satish Kolawale
 *
 */
public class HBDrawerListAdapter extends BaseAdapter {

	private Context _context = null;
	private ArrayList<HBDrawerItem> _navDrawerItems = null;
	
	public HBDrawerListAdapter(Context context, ArrayList<HBDrawerItem> navDrawerItems) {
		this._context = context;
		this._navDrawerItems = navDrawerItems;
	}
 
	@Override
	public int getCount() {
		return _navDrawerItems.size();
	}

	 
	@Override
	public Object getItem(int position) {
		return _navDrawerItems.get(position);
	}

	 
	@Override
	public long getItemId(int position) {
		return position;
	}

	 
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) _context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.layout_drawer_list_item, null);
        }
         
        ImageView imgIcon = (ImageView) convertView.findViewById(R.id.icon);
        TextView txtTitle = (TextView) convertView.findViewById(R.id.title);
        //TextView txtCount = (TextView) convertView.findViewById(R.id.counter);
         
        imgIcon.setImageResource(_navDrawerItems.get(position).getIcon());        
        txtTitle.setText(_navDrawerItems.get(position).getTitle());
        
        // displaying count
        // check whether it set visible or not
        /*if(_navDrawerItems.get(position).getCounterVisibility()){
        	txtCount.setText(_navDrawerItems.get(position).getCount());
        }else{
        	// hide the counter view
        	txtCount.setVisibility(View.GONE);
        }*/
        
        return convertView;
	}

}
