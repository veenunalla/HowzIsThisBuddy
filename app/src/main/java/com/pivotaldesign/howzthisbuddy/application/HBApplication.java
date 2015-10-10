/**
 * 
 */
package com.pivotaldesign.howzthisbuddy.application;

import com.pivotaldesign.howzthisbuddy.model.HBConstants;

import android.app.Application;
import android.graphics.Typeface;

/**
 * @author Satish Kolawale
 *
 */
public class HBApplication extends Application {

	private static HBApplication _application = null;
	@Override
	public void onCreate() {
		super.onCreate();
		_application = this;
	}
	
	
	public static HBApplication getInstance() {
		return _application;
	}
	
	
	public Typeface getNormalFont() {
		return Typeface.createFromAsset(this.getAssets(), HBConstants.HB_FONT_OPEN_SANS_LIGHT);
	}
	
	public Typeface getRegularFont() {
		return Typeface.createFromAsset(this.getAssets(), HBConstants.HB_FONT_OPEN_SANS_REGULAR);
	}
	
	public Typeface getBoldFont() {
		return Typeface.createFromAsset(this.getAssets(), HBConstants.HB_FONT_OPEN_SANS_BOLD);
	}
}
