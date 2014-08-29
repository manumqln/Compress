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
import com.custom.compress.MainActivity;
// import org.apache.cordova.PluginResult;  

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.widget.Toast;
import android.os.Environment;

/**
 * Class for compress a data.
 */
public class Compress extends CordovaPlugin {
	/** 
     * Override the plugin initialise method and set the Activity as an 
     * instance variable.
     */
    @Override
    public void initialize(CordovaInterface cordova, CordovaWebView webView) 
    {
        super.initialize(cordova, webView);

        // Set the Activity.
        this.activity = (MainActivity) cordova.getActivity();
    }

	@Override
	public boolean execute(String action, JSONArray rawArgs, CallbackContext callbackContext) throws JSONException {
    	if (action.equals("zip")) {

		Toast.makeText(this.cordova.getActivity(), "Text", Toast.LENGTH_LONG)
    .show();
		boolean stat = false;  
			callbackContext.success();  
				stat = true;
				return true;
		}
		/*String ExternalStorageDirectoryPath = Environment.getExternalStorageDirectory().getAbsolutePath();
		String targetPath = ExternalStorageDirectoryPath + "/RADMS/attachments";
		try {
			FileOutputStream fos = new FileOutputStream(ExternalStorageDirectoryPath + "/RADMS/attachments.zip");
			ZipOutputStream zos = new ZipOutputStream(fos);
    		if (action.equals("addDirToZipArchive")) {
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
		return stat;*/
	}

	public static void addDirToZipArchive(ZipOutputStream zos, File fileToZip,
			String parrentDirectoryName) throws Exception {
		if (fileToZip == null || !fileToZip.exists()) {
			return;
		}

		String zipEntryName = fileToZip.getName();
		if (parrentDirectoryName != null && !parrentDirectoryName.isEmpty()) {
			zipEntryName = parrentDirectoryName + "/" + fileToZip.getName();
		}

		if (fileToZip.isDirectory()) {
			System.out.println("+" + zipEntryName);
			for (File file : fileToZip.listFiles()) {
				addDirToZipArchive(zos, file, zipEntryName);
			}
		} else {
			System.out.println("   " + zipEntryName);
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
