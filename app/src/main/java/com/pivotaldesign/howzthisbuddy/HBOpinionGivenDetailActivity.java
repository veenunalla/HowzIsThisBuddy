/**
 * 
 */
package com.pivotaldesign.howzthisbuddy;

import android.app.ActionBar;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.pivotaldesign.howzthisbuddy.application.HBApplication;
import com.pivotaldesign.howzthisbuddy.fragments.HBGivenDetailFragment;
import com.pivotaldesign.howzthisbuddy.fragments.HBGivenFragment;
import com.pivotaldesign.howzthisbuddy.fragments.HBPendingDetailFragment;
import com.pivotaldesign.howzthisbuddy.model.HBNotifier;
import com.pivotaldesign.howzthisbuddy.util.AppUtilities;
import com.pivotaldesign.howzthisbuddy.util.CheckInternet;

/**
 * @author Satish Kolawale
 *
 */
public class HBOpinionGivenDetailActivity extends FragmentActivity  implements HBNotifier{

	private SharedPreferences hbh_spf_login_details;
	private HBNotifier _notifier = null;
    private AppUtilities au;
    private CheckInternet ci;
    private String number;
    private HBGivenFragment hbgf=new HBGivenFragment(_notifier);
    String pendingcount;
    String givencount;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_opinion_given_details);
		hbh_spf_login_details=getApplicationContext().getSharedPreferences("loginprefs",0);
	    pendingcount=""+(hbgf.al_uiob_detail.get(0).getOpinionsPending().size());
		givencount=""+(hbgf.al_uiob_detail.get(0).getOpinionsGiven().size());
		ActionBar actionBar = getActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
        actionBar.setIcon(new ColorDrawable(getResources().getColor(android.R.color.transparent))); 
        actionBar.setCustomView(R.layout.layout_actionbar);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        TextView hbr_txt_user_profile_name=(TextView)findViewById(R.id.txt_user_profile_name);
    	ImageView hbr_iv_user_pic=(ImageView)findViewById(R.id.img_user_profile_photo);
    	hbr_txt_user_profile_name.setTypeface(HBApplication.getInstance().getRegularFont());
        String pic=hbh_spf_login_details.getString("profilepic", "");
    	String name=hbh_spf_login_details.getString("username", "");
    	number=hbh_spf_login_details.getString("mobilenum", "");
        au=new AppUtilities(getApplicationContext());
        ci=new CheckInternet(getApplicationContext());
    	if(pic.length()!=0){
    		Bitmap bm=au.StringToBitMap(pic);
    		hbr_iv_user_pic.setImageBitmap(bm);
    	}
    	hbr_txt_user_profile_name.setText(name);
        
        TextView txtScreenTitle = (TextView) findViewById(R.id.txt_screen_title);
        txtScreenTitle.setText(getResources().getString(R.string.str_opinion_given_title));
        txtScreenTitle.setTypeface(HBApplication.getInstance().getBoldFont());
        
        TextView txtBuddyName = (TextView) findViewById(R.id.txt_user_profile_name);
        txtBuddyName.setTypeface(HBApplication.getInstance().getBoldFont());
        
        TextView txtInviteByName = (TextView) findViewById(R.id.txt_opinion_given_details_invite_by_name);
        TextView txtGivenCount = (TextView) findViewById(R.id.txt_opinion_given_details_buddy_count);
        
        txtInviteByName.setTypeface(HBApplication.getInstance().getRegularFont());
        txtGivenCount.setTypeface(HBApplication.getInstance().getRegularFont());
        
        txtInviteByName.setText("Invites By "+name);
        txtGivenCount.setText("Total:"+(Integer.parseInt(givencount)+Integer.parseInt(pendingcount)));
        
		ViewPager pager = (ViewPager) findViewById(R.id.viewPager_given_detail);
		pager.setAdapter(new SectionsPagerAdapter(getSupportFragmentManager()));
		
		PagerTabStrip pagerTabStrip = (PagerTabStrip) findViewById(R.id.pager_title_strip);
		//pagerTabStrip.setDrawFullUnderline(true);
		pagerTabStrip.setTabIndicatorColor(Color.BLUE);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem menuItem) {
	    switch (menuItem.getItemId()) {
	        case android.R.id.home:
	            finish();
	    }
	    return (super.onOptionsItemSelected(menuItem));
	}
	
	private void showResponseActivity() {
		setResult(101);
		finish();
	}
	
	
	class SectionsPagerAdapter extends FragmentStatePagerAdapter {
		
		
		String[] _sections = new String[]{ "Given("+givencount+")", "Pending("+pendingcount+")" };
		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			Fragment fragment = null;
			switch (position) {
			case 0:
				fragment = new HBGivenDetailFragment();
				break;
			case 1:
				fragment = new HBPendingDetailFragment(HBOpinionGivenDetailActivity.this);

			default:
				break;
			}
			Bundle args = new Bundle();
			fragment.setArguments(args);			
			return fragment;
		}

		@Override
		public int getCount() {
			// Show total pages.
			return _sections.length;
		}
		
		@Override
		public CharSequence getPageTitle(int position) {
			//Locale locale = Locale.getDefault();
			return _sections[position];
		}
	}


	@Override
	public void notifier(int notifierId) {
		setResult(101);
		finish();		
	}
}