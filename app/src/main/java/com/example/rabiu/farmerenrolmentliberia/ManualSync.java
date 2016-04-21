package com.example.rabiu.farmerenrolmentliberia;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;




import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

import cz.msebera.android.httpclient.Header;

/**
 * Created by rabiu on 15/03/2016.
 */
public class ManualSync  extends ActionBarActivity {


    TextView unsyncedLabel;
    //Progress Dialog Object
    ProgressDialog prgDialog;
    DBFarmers controller = new DBFarmers(this);
    Button syncButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manualsync);

        unsyncedLabel = (TextView) findViewById(R.id.unsync);
        syncButton = (Button) findViewById(R.id.syncButton);

        //Initialize Progress Dialog properties
        prgDialog = new ProgressDialog(this);
        prgDialog.setMessage("Synching SQLite Data with Remote DB. Please wait...");
        prgDialog.setCancelable(false);

        unsyncedRecords();

        syncButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                syncSQLiteMySQLDB();
            }
        });


    }

    private void unsyncedRecords() {

        int unsynced_no = controller.dbSyncCount();
        String unsynced_str = Integer.toString(unsynced_no);
        unsyncedLabel.setText(unsynced_str);
    }


    public void backButton(View view)
    {
        finish();
    }

    public void syncSQLiteMySQLDB() {

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        ArrayList<HashMap<String, String>> userList =  controller.getAllUsers();
        if(userList.size()!=0){
            if(controller.dbSyncCount() != 0){

                prgDialog.show();

                params.put("usersJSON", controller.composeJSONfromSQLite());

                params.toString().length();
                client.post("http://41.206.23.39/LATA/Insert/insertuser.php",params ,new AsyncHttpResponseHandler() {

                    String response_str;
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] response) {

                        try {
                            response_str = new String(response, "UTF-8");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        System.out.println(response_str);

                        prgDialog.hide();

                        try {
                            int successful_update = 0;
                            JSONArray arr = new JSONArray(response_str);
                            System.out.println(arr.length());
                            for(int i=0; i<arr.length();i++){
                                JSONObject obj = (JSONObject)arr.get(i);
                                System.out.println(obj.get("id"));
                                System.out.println(obj.get("status"));
                                controller.updateSyncStatus(obj.get("id").toString(),obj.get("status").toString());

                                if(obj.get("status").equals("yes")){
                                    successful_update++;
                                }
                            }

                           Toast.makeText(getApplicationContext(), successful_update + " Records Synced", Toast.LENGTH_LONG).show();
                            unsyncedRecords();

                            if(controller.dbSyncCount() != 0){

                                syncSQLiteMySQLDB();
                            }

                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            Toast.makeText(getApplicationContext(), "Server's JSON response might be invalid!", Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                    }

                   /* @Override
                    public void onProgress(long bytesWritten, long totalSize) {

                        Log.v(LOG_TAG, String.format("Progress %d from %d (%2.0f%%)", bytesWritten, totalSize, (totalSize > 0) ? (bytesWritten * 1.0 / totalSize) * 100 : -1));

                        System.out.println("totalsize::::" + totalSize );
                        System.out.println("bytesWritten::::" + bytesWritten );
                        //  super.onProgress(bytesWritten, totalSize);
                        int totProgress = (int) (((float) bytesWritten * 100) / totalSize);
                       // long totProgress = (long)100*bytesWritten/totalSize;
                        Log.i("Progress::::", "" + totProgress);
                        System.out.println("ProgressSystem::::" + totProgress);
                        if (totProgress > 0) {
                            progressBar.setProgress(totProgress);

                        }

                    }*/

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                        // TODO Auto-generated method stub
                        prgDialog.hide();
                        if(statusCode == 404){
                            Toast.makeText(getApplicationContext(), "Requested resource not found", Toast.LENGTH_LONG).show();
                        }else if(statusCode == 500){
                            Toast.makeText(getApplicationContext(), "Something went wrong at server end", Toast.LENGTH_LONG).show();
                        } else{
                            Toast.makeText(getApplicationContext(), "Device might not be connected to Internet", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            } else {
               Toast.makeText(getApplicationContext(), "SQLite and Remote Database are in Sync!", Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(getApplicationContext(), "No data in SQLite DB", Toast.LENGTH_LONG).show();
        }


    }

    @Override
    public void onBackPressed() {

    }
}
