package com.pivotaldesign.howzthisbuddy.util;

import java.io.File;
import android.content.Context;

public class FleCache {
	private File cacheDirctry;

	public FleCache(Context context) {
		if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
			cacheDirctry = new File(android.os.Environment.getExternalStorageDirectory(), "HZTB Images");
		else
			cacheDirctry = context.getCacheDir();
		if (!cacheDirctry.exists())
			cacheDirctry.mkdirs();
	}

	public File getimgFile(String url) {
		String filename = String.valueOf(url.hashCode());
		File f = new File(cacheDirctry, filename);
		return f;
	}

	public void clearimgs() {
		File[] files = cacheDirctry.listFiles();
		if (files == null)
			return;
		for (File f : files)
			f.delete();
	}
}
