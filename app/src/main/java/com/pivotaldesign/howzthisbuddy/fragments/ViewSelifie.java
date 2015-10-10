package com.pivotaldesign.howzthisbuddy.fragments;

import java.io.InputStream;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

//DownloadImage AsyncTask
	class ViewSelifie extends AsyncTask<String, Void, Bitmap> {
		ProgressDialog mProgressDialog;
		Context c;
		public ViewSelifie(Context c) {
			// TODO Auto-generated constructor stub
			this.c=c;
		}
		
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// Create a progressdialog
			mProgressDialog = new ProgressDialog(c);
			// Set progressdialog title
			mProgressDialog.setTitle("Loading Image");
			// Set progressdialog message
			mProgressDialog.setMessage("Loading...");
			mProgressDialog.setIndeterminate(false);
			// Show progressdialog
			mProgressDialog.show();
		}

		@Override
		protected Bitmap doInBackground(String... URL) {

			String imageURL = URL[0];

			Bitmap bitmap = null;
			try {
				// Download Image from URL
				InputStream input = new java.net.URL(imageURL).openStream();
				// Decode Bitmap
				bitmap = BitmapFactory.decodeStream(input);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return bitmap;
		}

		@Override
		protected void onPostExecute(Bitmap result) {
			// Set the bitmap into ImageView
			//image.setImageBitmap(result);
			// Close progressdialog
			mProgressDialog.dismiss();
		}
	}
