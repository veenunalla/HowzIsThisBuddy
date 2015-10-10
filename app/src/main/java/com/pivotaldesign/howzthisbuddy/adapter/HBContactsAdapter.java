/**
 * 
 */
package com.pivotaldesign.howzthisbuddy.adapter;

import java.util.ArrayList;

import com.pivotaldesign.howzthisbuddy.R;
import com.pivotaldesign.howzthisbuddy.application.HBApplication;
import com.pivotaldesign.howzthisbuddy.bean.Localcontactbo;

import android.content.Context;
import android.graphics.Typeface;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author Satish Kolawale
 *
 */
public class HBContactsAdapter extends ArrayAdapter<Localcontactbo> implements CompoundButton.OnCheckedChangeListener {

	public SparseBooleanArray mCheckStates;
	private Context _context = null;
	private Typeface _typefaceRegular = null;
	boolean[] checkBoxState;
	
	public HBContactsAdapter(Context context, int resource,  Localcontactbo[] objects) {
		super(context, resource, objects);
		this._context = context;
		 mCheckStates = new SparseBooleanArray(objects.length);
		_typefaceRegular = HBApplication.getInstance().getRegularFont();
	}

	private static class ViewHolder
	{
		TextView txtBuddyName, txtBuddyPhoneNumber;
		ImageView imgBuddyPhoto;
		CheckBox checkBuddySelected;
	}
	
	ViewHolder holder = null;
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Localcontactbo buddyName = getItem(position);
		if (convertView == null) {
			LayoutInflater vi = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = vi.inflate(R.layout.layout_received_buddy_contact_item, null);
			
			holder = new  ViewHolder();
			holder.txtBuddyName = (TextView) convertView.findViewById(R.id.txt_received_buddy_contact_name);
			holder.txtBuddyPhoneNumber = (TextView) convertView.findViewById(R.id.txt_received_buddy_contact_phone);
			holder.checkBuddySelected = (CheckBox) convertView.findViewById(R.id.check_received_buddy_contact);
			holder.imgBuddyPhoto = (ImageView) convertView.findViewById(R.id.img_received_buddy_contact_photo);
				
			holder.txtBuddyName.setTypeface(_typefaceRegular);
			holder.txtBuddyPhoneNumber.setTypeface(_typefaceRegular);
			convertView.setTag(holder);
			holder.checkBuddySelected.setTag(position);
			holder.checkBuddySelected.setOnCheckedChangeListener(this);
			holder.checkBuddySelected.setChecked(mCheckStates.get(position, false));
			/*holder.checkBuddySelected.setChecked(list.get(position).isChecked());
			holder.checkBuddySelected.setOnCheckedChangeListener(new OnCheckedChangeListener()
			{
			    @Override
			    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
			    {
			        if (isChecked)
			        {
			            list.get(position).setChecked(true);
			        }
			        else
			        {
			            list.get(position).setChecked(false);
			        }
			    }
			});*/

		}
		else{
			holder = (ViewHolder) convertView.getTag();
		}
		/*holder.checkBuddySelected.setChecked(checkBoxState[position]);
		holder.checkBuddySelected.setOnClickListener(new View.OnClickListener() {

	           public void onClick(View v) {
	               if(((CheckBox)v).isChecked()){
	                   checkBoxState[position]=true;
	                   data[position].setCheck(true);
	               }else{
	                   checkBoxState[position]=false;
	                   data[position].setCheck(false);
	               }
	            }
	        });*/
		 
		//if(buddyName.getContactnumber().length()>=10){
			
			if (holder.txtBuddyName != null){
				holder.txtBuddyName.setText(buddyName.getContactname());
			}
			if(holder.txtBuddyPhoneNumber != null){
				//holder.txtBuddyPhoneNumber.setText("123-456-7890");
				holder.txtBuddyPhoneNumber.setText(buddyName.getContactnumber());
			}

		//}
		 
			
		
			return convertView;
	}
	 public boolean isChecked(int position) {
         return mCheckStates.get(position, false);
     }

     public void setChecked(int position, boolean isChecked) {
         mCheckStates.put(position, isChecked);
         System.out.println("hello...........");
        notifyDataSetChanged();
     }

     public void toggle(int position) {
         setChecked(position, !isChecked(position));
     }
	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// TODO Auto-generated method stub
        mCheckStates.put((Integer) buttonView.getTag(), isChecked);         
	}
}
