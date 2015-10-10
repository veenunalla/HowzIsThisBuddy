package com.pivotaldesign.howzthisbuddy.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.pivotaldesign.howzthisbuddy.R;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

public class DynamicImageLoader {
	MemCache memCache = new MemCache();
	FleCache fleCache;
	private Map<ImageView, String> imgViews = Collections.synchronizedMap(new WeakHashMap<ImageView, String>());
	ExecutorService exeService;

	public DynamicImageLoader(Context context) {
		fleCache = new FleCache(context);
		exeService = Executors.newFixedThreadPool(5);
	}

	final int stb_id = R.drawable.ic_launcher;

	public void DispImage(String url, ImageView imageView) {
		imgViews.put(imageView, url);
		Bitmap bitmap = memCache.get(url);
		if (bitmap != null)
			imageView.setImageBitmap(bitmap);
		else {
			quePhoto(url, imageView);
			imageView.setImageResource(stb_id);
		}
	}

	private void quePhoto(String url, ImageView imageView) {
		PhotoToLoadimage p = new PhotoToLoadimage(url, imageView);
		exeService.submit(new ImgPhotosLoader(p));
	}

	private Bitmap getBitmapimage(String url) {
		File f = fleCache.getimgFile(url);
		// from SD cache
		Bitmap b = dcodeFile(f);
		if (b != null)
			return b;
		// from web
		try {
			Bitmap bitmap = null;
			URL imageUrl = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
			conn.setConnectTimeout(30000);
			conn.setReadTimeout(30000);
			conn.setInstanceFollowRedirects(true);
			InputStream is = conn.getInputStream();
			OutputStream os = new FileOutputStream(f);
		    AppUtilities.CopyStream(is, os);
			os.close();
			bitmap = dcodeFile(f);
			return bitmap;
		} catch (Throwable ex) {
			ex.printStackTrace();
			if (ex instanceof OutOfMemoryError)
				memCache.clear();
			return null;
		}
	}

	// decodes image and scales it to reduce memory consumption
	private Bitmap dcodeFile(File f) {
		try {
			// decode image size
			BitmapFactory.Options o = new BitmapFactory.Options();
			o.inJustDecodeBounds = true;
			BitmapFactory.decodeStream(new FileInputStream(f), null, o);
			// Find the correct scale value. It should be the power of 2.
			final int REQUIRED_SIZE = 70;
			int width_tmp = o.outWidth, height_tmp = o.outHeight;
			int scale = 1;
			while (true) {
				if (width_tmp / 2 < REQUIRED_SIZE || height_tmp / 2 < REQUIRED_SIZE)
					break;
				width_tmp /= 2;
				height_tmp /= 2;
				scale *= 2;
			}
			// decode with inSampleSize
			BitmapFactory.Options o2 = new BitmapFactory.Options();
			o2.inSampleSize = scale;
			return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
		} catch (FileNotFoundException e) {
		}
		return null;
	}

	// Task for the queue
	private class PhotoToLoadimage {
		public String url;
		public ImageView imageView;

		public PhotoToLoadimage(String u, ImageView i) {
			url = u;
			imageView = i;
		}
	}

	class ImgPhotosLoader implements Runnable {
		PhotoToLoadimage photoToLoad;

		ImgPhotosLoader(PhotoToLoadimage photoToLoad) {
			this.photoToLoad = photoToLoad;
		}

		@Override
		public void run() {
			if (imgViewReused(photoToLoad))
				return;
			Bitmap bmp = getBitmapimage(photoToLoad.url);
			memCache.put(photoToLoad.url, bmp);
			if (imgViewReused(photoToLoad))
				return;
			BitmapimgDisplayer bd = new BitmapimgDisplayer(bmp, photoToLoad);
			Activity a = (Activity) photoToLoad.imageView.getContext();
			a.runOnUiThread(bd);
		}
	}

	boolean imgViewReused(PhotoToLoadimage photoToLoad) {
		String tag = imgViews.get(photoToLoad.imageView);
		if (tag == null || !tag.equals(photoToLoad.url))
			return true;
		return false;
	}

	// Used to display bitmap in the UI thread
	class BitmapimgDisplayer implements Runnable {
		Bitmap bitmap;
		PhotoToLoadimage photoToLoad;

		public BitmapimgDisplayer(Bitmap b, PhotoToLoadimage p) {
			bitmap = b;
			photoToLoad = p;
		}

		public void run() {
			if (imgViewReused(photoToLoad))
				return;
			if (bitmap != null)
				photoToLoad.imageView.setImageBitmap(bitmap);
			else
				photoToLoad.imageView.setImageResource(stb_id);
		}
	}

	public void clearCache() {
		memCache.clear();
		fleCache.clearimgs();
	}
}