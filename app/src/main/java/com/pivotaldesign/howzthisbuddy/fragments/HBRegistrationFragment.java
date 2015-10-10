/**
 * 
 */
package com.pivotaldesign.howzthisbuddy.fragments;

import android.app.Dialog;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.pivotaldesign.howzthisbuddy.R;
import com.pivotaldesign.howzthisbuddy.application.HBApplication;

/**
 * @author Satish Kolawale
 *
 */
public class HBRegistrationFragment extends Fragment{

	private Dialog _confirmationDialog = null;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.activity_registration, container, false);
        
        ((TextView) rootView.findViewById(R.id.txt_registration_title)).setTypeface(HBApplication.getInstance().getBoldFont());
        ((TextView) rootView.findViewById(R.id.txt_registration_content)).setTypeface(HBApplication.getInstance().getRegularFont());
        ((TextView) rootView.findViewById(R.id.txt_registration_phone_hint)).setTypeface(HBApplication.getInstance().getNormalFont());
        ((TextView) rootView.findViewById(R.id.txt_registration_sms)).setTypeface(HBApplication.getInstance().getRegularFont());
        ((EditText) rootView.findViewById(R.id.edt_registration_phone_number)).setTypeface(HBApplication.getInstance().getRegularFont());
        Button btnSubmit = (Button) rootView.findViewById(R.id.btn_registration_submit);
        btnSubmit.setTypeface(HBApplication.getInstance().getRegularFont());
        btnSubmit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showCommentDialog();
			}
		});
         
        return rootView;
    }
	
	
	/**
	 * Show alert dialog for sending password to your email account 
	 */
	private void showCommentDialog() {
		// custom dialog
		_confirmationDialog = new Dialog(getActivity());
		_confirmationDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		_confirmationDialog.setContentView(R.layout.layout_registration_confirmation_popup);
		
		((TextView) _confirmationDialog.findViewById(R.id.txt_registration_popup_title)).setTypeface(HBApplication.getInstance().getBoldFont());
		((TextView) _confirmationDialog.findViewById(R.id.txt_registration_popup_verification)).setTypeface(HBApplication.getInstance().getBoldFont());
		((TextView) _confirmationDialog.findViewById(R.id.txt_registration_popup_ask_contact)).setTypeface(HBApplication.getInstance().getRegularFont());
		((TextView) _confirmationDialog.findViewById(R.id.txt_registration_popup_phone_number)).setTypeface(HBApplication.getInstance().getBoldFont());
		((TextView) _confirmationDialog.findViewById(R.id.txt_registration_popup_verification_code)).setTypeface(HBApplication.getInstance().getRegularFont());
		((TextView) _confirmationDialog.findViewById(R.id.txt_registration_popup_press_again)).setTypeface(HBApplication.getInstance().getRegularFont());
		((EditText) _confirmationDialog.findViewById(R.id.edt_registration_popup_verification_code)).setTypeface(HBApplication.getInstance().getRegularFont());
		((TextView) _confirmationDialog.findViewById(R.id.btn_registration_popup_submit)).setTypeface(HBApplication.getInstance().getRegularFont());
		
		ImageView imgClose = (ImageView) _confirmationDialog.findViewById(R.id.img_registration_popup_close);
		imgClose.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				_confirmationDialog.dismiss();
			}
		});
		_confirmationDialog.show();
	}
}
