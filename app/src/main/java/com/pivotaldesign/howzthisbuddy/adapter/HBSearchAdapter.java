package com.pivotaldesign.howzthisbuddy.adapter;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpStatus;

import com.pivotaldesign.howzthisbuddy.R;
import com.pivotaldesign.howzthisbuddy.application.HBApplication;
import com.pivotaldesign.howzthisbuddy.bean.ResponseContactInfBO;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;

public class HBSearchAdapter extends BaseAdapter implements OnCheckedChangeListener{

	
	// Declare Variables
	 public SparseBooleanArray mCheckStates;
		Context mContext;
		LayoutInflater inflater;
		private List<ResponseContactInfBO> responcecontactlist = null;
		private ArrayList<ResponseContactInfBO> resplist;

		public HBSearchAdapter(Context context,
				List<ResponseContactInfBO> responcecontactlist) {
			mContext = context;
            mCheckStates = new SparseBooleanArray(responcecontactlist.size());
			this.responcecontactlist = responcecontactlist;
			inflater = LayoutInflater.from(mContext);
			this.resplist = new ArrayList<ResponseContactInfBO>();
			this.resplist.addAll(responcecontactlist);
		}

		public class ViewHolder {
			TextView rank;
			TextView country;
			TextView population;
			ImageView flag;
		}

		@Override
		public int getCount() {
			return responcecontactlist.size();
		}

		@Override
		public ResponseContactInfBO getItem(int position) {
			return responcecontactlist.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		public View getView(final int position, View view, ViewGroup parent) {
			/*final ViewHolder holder;
			if (view == null) {
				holder = new ViewHolder();
				view = inflater.inflate(R.layout.listview_item, null);
				// Locate the TextViews in listview_item.xml
				holder.rank = (TextView) view.findViewById(R.id.rank);
				holder.country = (TextView) view.findViewById(R.id.country);
				holder.population = (TextView) view.findViewById(R.id.population);
				// Locate the ImageView in listview_item.xml
				holder.flag = (ImageView) view.findViewById(R.id.flag);
				view.setTag(holder);
			} else {
				holder = (ViewHolder) view.getTag();
			}
			// Set the results into TextViews
			holder.rank.setText(worldpopulationlist.get(position).getRank());
			holder.country.setText(worldpopulationlist.get(position).getCountry());
			holder.population.setText(worldpopulationlist.get(position)
					.getPopulation());
			// Set the results into ImageView
			holder.flag.setImageResource(worldpopulationlist.get(position)
					.getFlag());
			// Listen for ListView Item Click

			return view;*/
	        View vi=view;
            if(view==null)
             vi = inflater.inflate(R.layout.layout_received_buddy_contact_item, null); 
           TextView txtBuddyName = (TextView) vi.findViewById(R.id.txt_received_buddy_contact_name);
           TextView txtBuddyPhoneNumber = (TextView) vi.findViewById(R.id.txt_received_buddy_contact_phone);
           CheckBox checkBuddySelected = (CheckBox) vi.findViewById(R.id.check_received_buddy_contact);
			ImageView imgBuddyPhoto = (ImageView) vi.findViewById(R.id.img_received_buddy_contact_photo);
				
			txtBuddyName.setTypeface(HBApplication.getInstance().getRegularFont());
			txtBuddyPhoneNumber.setTypeface(HBApplication.getInstance().getRegularFont());
			checkBuddySelected.setTag(position);
			checkBuddySelected.setOnCheckedChangeListener(this);
			checkBuddySelected.setChecked(mCheckStates.get(position, false));
            
			/*txtBuddyName.setText(cn.get(position).getContactname());
			txtBuddyPhoneNumber.setText(cn.get(position).getContactnumber());*/
			
			txtBuddyName.setText(responcecontactlist.get(position).getName().toString());
			txtBuddyPhoneNumber.setText(responcecontactlist.get(position).getPhonenum().toString());
			if(!responcecontactlist.get(position).getProfilePicS3Url().toString().equalsIgnoreCase("null")){
				new ImageDownloaderTask(imgBuddyPhoto).execute(responcecontactlist.get(position).getProfilePicS3Url().toString());
			}
            
            /*
            
            
             TextView tv= (TextView) vi.findViewById(R.id.contact_name);
             tv1= (TextView) vi.findViewById(R.id.phone_number);
             cb = (CheckBox) vi.findViewById(R.id.checkBox_id);
             tv.setText(name1.get(position));
             tv1.setText(phno1.get(position));
             cb.setTag(position);
             cb.setChecked(mCheckStates.get(position, false));
             cb.setOnCheckedChangeListener(this);
*/
            return vi;
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
      
      public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
          // TODO Auto-generated method stub
           mCheckStates.put((Integer) buttonView.getTag(), isChecked);         
      }


		
		// Filter Class
		public void filter(String charText) {
			charText = charText.toLowerCase(Locale.getDefault());
			responcecontactlist.clear();
			if (charText.length() == 0) {
				responcecontactlist.addAll(resplist);
			} else {
				for (ResponseContactInfBO wp : resplist) {
					if (wp.getName().toLowerCase(Locale.getDefault())
							.contains(charText)) {
						responcecontactlist.add(wp);
					}
				}
			}
			notifyDataSetChanged();
		}

	
		class ImageDownloaderTask extends AsyncTask<String, Void, Bitmap> {
		    private final WeakReference<ImageView> imageViewReference;

		    public ImageDownloaderTask(ImageView imageView) {
		        imageViewReference = new WeakReference<ImageView>(imageView);
		    }

		    @Override
		    protected Bitmap doInBackground(String... params) {
		        return downloadBitmap(params[0]);
		    }

		    @Override
		    protected void onPostExecute(Bitmap bitmap) {
		        if (isCancelled()) {
		            bitmap = null;
		        }

		        if (imageViewReference != null) {
		            ImageView imageView = imageViewReference.get();
		            if (imageView != null) {
		                if (bitmap != null) {
		                    imageView.setImageBitmap(bitmap);
		                } else {
		                    Drawable placeholder = imageView.getContext().getResources().getDrawable(R.drawable.person_icon);
		                    imageView.setImageDrawable(placeholder);
		                }
		            }
		        }
		    }
		}
	
		private Bitmap downloadBitmap(String url) {
		    HttpURLConnection urlConnection = null;
		    try {
		        URL uri = new URL(url);
		        urlConnection = (HttpURLConnection) uri.openConnection();
		        int statusCode = urlConnection.getResponseCode();
		        if (statusCode != HttpStatus.SC_OK) {
		            return null;
		        }

		        InputStream inputStream = urlConnection.getInputStream();
		        if (inputStream != null) {
		            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
		            return bitmap;
		        }
		    } catch (Exception e) {
		        urlConnection.disconnect();
		        Log.w("ImageDownloader", "Error downloading image from " + url);
		    } finally {
		        if (urlConnection != null) {
		            urlConnection.disconnect();
		        }
		    }
		    return null;
		}
	
}
