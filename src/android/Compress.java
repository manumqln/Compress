package com.custom.compress;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.cordova.CordovaWebView;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;  

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.widget.Toast;
import android.os.Environment;

/**
 * Class for compress a data.
 */
public class Compress extends CordovaPlugin {
	private static JSONArray dirNames;
	/** 
     * Override the plugin initialise method and set the Activity as an 
     * instance variable.
     */
    @Override
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
    }

	@Override
	public boolean execute(String action, JSONArray rawArgs, CallbackContext callbackContext) throws JSONException {
		boolean stat = false;
		this.dirNames = rawArgs;
		String ExternalStorageDirectoryPath = Environment.getExternalStorageDirectory().getAbsolutePath();
		try {
    		if (action.equals("zip")) {
				String targetPath = ExternalStorageDirectoryPath + "/RADMS/attachments/";
				File zipFile = new File(ExternalStorageDirectoryPath + "/RADMS", "exportdata.zip");
				if (zipFile.exists()) {
					zipFile.delete();
				} 
				FileOutputStream fos = new FileOutputStream(ExternalStorageDirectoryPath + "/RADMS/exportdata.zip");
				ZipOutputStream zos = new ZipOutputStream(fos);
				addDirToZipArchive(zos, new File(targetPath), null);
				zos.flush();
				fos.flush();
				zos.close();
				fos.close();
				callbackContext.success();  
				stat = true;
			}
		} catch (Exception e) {
            String result = e.getMessage();  
            PluginResult.Status status = PluginResult.Status.ERROR;  
            PluginResult pluginResult = new PluginResult(status, result);  
            callbackContext.error(""+e);  
			stat = false;
		}
		return stat;
	}

	private void addDirToZipArchive(ZipOutputStream zos, File fileToZip, String parentDirectoryName) throws Exception {
		String zipEntryName = fileToZip.getName();
		boolean flag = false;
		if (parentDirectoryName != null && !parentDirectoryName.isEmpty()) {
			zipEntryName = parentDirectoryName + "/" + fileToZip.getName();
		}

		if (fileToZip.isDirectory()) {
			if(parentDirectoryName != null) {
				for (int i = 0; i < dirNames.length(); i++) {
					if(dirNames.getString(i).equals(fileToZip.getName())) {
						flag = true;
						break;
					}
				}
				if(!flag) {
					return;
				}
			}
			for (File file : fileToZip.listFiles()) {
				addDirToZipArchive(zos, file, zipEntryName);
			}
		} 
		else {
			byte[] buffer = new byte[1024];
			FileInputStream fis = new FileInputStream(fileToZip);
			zos.putNextEntry(new ZipEntry(zipEntryName));
			int length;
			while ((length = fis.read(buffer)) > 0) {
				zos.write(buffer, 0, length);
			}
			zos.closeEntry();
			fis.close();
		}
	}
}