package me.yokeyword.ntdhoang.demo_zhihu.assistant.helper;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by FRAMGIA\tran.hoa.binh on 09/04/2018.
 */

public class DownloadFile extends AsyncTask<String, Integer, Long> {

    private Context mContext;

    public DownloadFile(Context context) {
        this.mContext = context;
        mProgressDialog = new ProgressDialog(context);
    }

    private ProgressDialog mProgressDialog;
    // Change Mainactivity.this with your activity name.

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mProgressDialog.setMessage("Downloading");
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setMax(100);
        mProgressDialog.setCancelable(true);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.show();
    }

    @Override
    protected Long doInBackground(String... aurl) {
        int count;
        try {
            URL url = new URL((String) aurl[0]);
            URLConnection conexion = url.openConnection();
            conexion.connect();
            String targetFileName = "Name" + ".jpg";//Change name and subname
            int lenghtOfFile = conexion.getContentLength();
            String downloadFolder = "league";
            String PATH = Environment.getExternalStorageDirectory() + "/" + downloadFolder + "/";
            File folder = new File(PATH);
            if (!folder.exists()) {
                folder.mkdir();//If there is no folder it will be created.
            }
            InputStream input = new BufferedInputStream(url.openStream());
            OutputStream output = new FileOutputStream(PATH + targetFileName);
            byte data[] = new byte[1024];
            long total = 0;
            while ((count = input.read(data)) != -1) {
                total += count;
                publishProgress((int) (total * 100 / lenghtOfFile));
                output.write(data, 0, count);
            }
            output.flush();
            output.close();
            input.close();
            Log.d("BINH", "doInBackground() called with: aurl = [" + PATH + "]");
        } catch (Exception e) {
            Log.d("BINH1", "doInBackground() called with: aurl = [" + e + "]");
        }
        return null;
    }

    protected void onProgressUpdate(Integer... progress) {
        mProgressDialog.setProgress(progress[0]);
        if (mProgressDialog.getProgress() == mProgressDialog.getMax()) {
            mProgressDialog.dismiss();
            Toast.makeText(mContext, "File Downloaded", Toast.LENGTH_SHORT).show();
        }
    }

    protected void onPostExecute(String result) {
    }
}