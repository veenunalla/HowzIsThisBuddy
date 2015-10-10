package com.pivotaldesign.howzthisbuddy.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.provider.ContactsContract.PhoneLookup;
import android.provider.MediaStore.Images;
import android.util.Base64;
import android.util.Log;

/**
 * @author RAJESH
 * 
 */

@SuppressWarnings("deprecation")
public class AppUtilities {

	private Context _context;
	public static String response = null;
	public final static int GET = 1;
	public final static int POST = 2;
	public final static String SENDER_ID = "24397121204";
	

	public AppUtilities(Context c) {
		this._context = c;
	}

	// Internet availability
	public boolean isNetAvailable() {
		ConnectivityManager conn = (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (conn != null) {
			NetworkInfo[] inf = conn.getAllNetworkInfo();
			if (inf != null)
				for (int i = 0; i < inf.length; i++)
					if (inf[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}

		}
		return false;
	}

	public static void CopyStream(InputStream is, OutputStream os) {
		final int buffer_size = 1024;
		try {
			byte[] bytes = new byte[buffer_size];
			for (;;) {
				int count = is.read(bytes, 0, buffer_size);
				if (count == -1)
					break;
				os.write(bytes, 0, count);
			}
		} catch (Exception ex) {
		}
	}
	/*
	 * Service request URL
	 * 
	 * @url - url to make Service request
	 * 
	 * @method - http request method Post/Get
	 * 
	 * @params - http request params
	 */

	public String makeServiceRequest(String url, int method, List<NameValuePair> params) {
		try {
			// http client
			DefaultHttpClient hClient = new DefaultHttpClient();
			HttpEntity hEntity = null;
			HttpResponse hResponse = null;

			// Checking http request method type
			if (method == POST) {
				HttpPost hPost = new HttpPost(url);
				// adding post params
				if (params != null) {
					hPost.setEntity(new UrlEncodedFormEntity(params));
				}

				hResponse = hClient.execute(hPost);

			} else if (method == GET) {
				// appending params to url
				if (params != null) {
					String paramString = URLEncodedUtils.format(params, "utf-8");
					url += "?" + paramString;
				}
				HttpGet hGet = new HttpGet(url);

				hResponse = hClient.execute(hGet);

			}
			hEntity = hResponse.getEntity();
			response = EntityUtils.toString(hEntity);

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return response;

	}

	public String makePostRequest(String uri, String json) {
		InputStream inputStream = null;
		String result = "";
		try {
			HttpResponse httpResponse = null;
			HttpPost httpPost = new HttpPost(uri);
			httpPost.setEntity(new StringEntity(json));
			httpPost.setHeader("Accept", "application/json");
			httpPost.setHeader("Content-type", "application/json");
			httpResponse = new DefaultHttpClient().execute(httpPost);
			inputStream = httpResponse.getEntity().getContent();
			if (inputStream != null)
				result = convertInputStreamToString(inputStream);
			else
				result = "Did not work!";
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	private String convertInputStreamToString(InputStream inputStream) throws IOException {
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
		String line = "";
		String result = "";
		while ((line = bufferedReader.readLine()) != null)
			result += line;

		inputStream.close();
		return result;

	}

	public ArrayList<String> fetchContacts(Context c) {

		ArrayList<String> numbers = new ArrayList<String>();
		String phoneNumber = null;
		Uri CONTACT_URI = ContactsContract.Contacts.CONTENT_URI;
		String ID = ContactsContract.Contacts._ID;
		String HASPHONENUMBER = ContactsContract.Contacts.HAS_PHONE_NUMBER;
		Uri PhoneCONTENTURI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
		String PhoneCONTACTID = ContactsContract.CommonDataKinds.Phone.CONTACT_ID;
		String NUMBER = ContactsContract.CommonDataKinds.Phone.NUMBER;
		StringBuffer output = new StringBuffer();
		ContentResolver contentResolver = c.getContentResolver();
		Cursor cursor = contentResolver.query(CONTACT_URI, null, null, null, null);
		// Loop for every contact in the phone
		if (cursor.getCount() > 0) {
			while (cursor.moveToNext()) {
				String contact_id = cursor.getString(cursor.getColumnIndex(ID));
				int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex(HASPHONENUMBER)));
				if (hasPhoneNumber > 0) {
					// Query and loop for every phone number of the contact
					Cursor phoneCursor = contentResolver.query(PhoneCONTENTURI, null, PhoneCONTACTID + " = ?",
							new String[] { contact_id }, null);
					while (phoneCursor.moveToNext()) {
						phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(NUMBER));
						if (phoneNumber.length() >= 10) {
							phoneNumber = phoneNumber.replaceFirst("^0+(?!$)", "");
							// phoneNumber=phoneNumber.startsWith("0")?
							// phoneNumber.substring(1) : phoneNumber;

							numbers.add(((phoneNumber.replaceAll("\\+", "")).replaceAll("-", "")).replaceAll(" ", "").replaceAll("\\(", "").replaceAll("\\)",""));
						}
						System.out.println(numbers.toString());
						output.append("\n Phone number:" + phoneNumber);
					}
					phoneCursor.close();
				}
				output.append("\n");
			}
		}
		return numbers;

	}

	public Bitmap decodeBase64(String input) {
		byte[] decodedByte = Base64.decode(input, 0);
		return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
	}

	public String encodeTobase64(Bitmap image) {
		Bitmap immagex = image;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		immagex.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		byte[] b = baos.toByteArray();
		String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);

		Log.e("LOOK", imageEncoded);
		return imageEncoded;
	}

	public Bitmap StringToBitMap(String encodedString) {
		try {
			byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
			Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
			return bitmap;
		} catch (Exception e) {
			e.getMessage();
			return null;
		}
	}

	public String makeRequeststatusline(String uri, String json) {
		try {
			HttpResponse httpResponse = null;
			HttpPost httpPost = new HttpPost(uri);
			httpPost.setEntity(new StringEntity(json));
			httpPost.setHeader("Accept", "application/json");
			httpPost.setHeader("Content-type", "application/json");
			httpResponse = new DefaultHttpClient().execute(httpPost);
			response = httpResponse.getStatusLine().toString();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return response;
	}

	public int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {
			final int heightRatio = Math.round((float) height / (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		}
		final float totalPixels = width * height;
		final float totalReqPixelsCap = reqWidth * reqHeight * 2;

		while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
			inSampleSize++;
		}

		return inSampleSize;
	}

	public Uri getImageUri(Context inContext, Bitmap inImage) {
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
		String path = Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
		return Uri.parse(path);
	}

	public String getRealPath(Context c, Uri uri) {
		Cursor cursor = c.getContentResolver().query(uri, null, null, null, null);
		cursor.moveToFirst();
		int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
		return cursor.getString(idx);
	}

	
	public String encryptedImage(String encimage){
		String encImage;
		File imagefile = new File(encimage);
		if(imagefile.exists()){
			FileInputStream fis = null;
			
			
			try {
			    fis = new FileInputStream(imagefile);
			    } catch (FileNotFoundException e) {
			    e.printStackTrace();
			    
			}

			Bitmap bm = BitmapFactory.decodeStream(fis);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();  
			bm.compress(Bitmap.CompressFormat.JPEG, 100 , baos);    
			byte[] b = baos.toByteArray(); 
			encImage = Base64.encodeToString(b, Base64.DEFAULT);
		}else{
			encImage="";
		}
		return encImage;
	}


	public String getContactName(Context context, String phoneNumber) {
	    ContentResolver cr = context.getContentResolver();
	    Uri uri = Uri.withAppendedPath(PhoneLookup.CONTENT_FILTER_URI, Uri.encode(phoneNumber));
	    Cursor cursor = cr.query(uri, new String[]{PhoneLookup.DISPLAY_NAME}, null, null, null);
	    if (cursor == null) {
	        return null;
	    }
	    String contactName = phoneNumber;
	    if(cursor.moveToFirst()) {
	        contactName = cursor.getString(cursor.getColumnIndex(PhoneLookup.DISPLAY_NAME));
	    }

	    if(cursor != null && !cursor.isClosed()) {
	        cursor.close();
	    }

	    return contactName;
	}







}
