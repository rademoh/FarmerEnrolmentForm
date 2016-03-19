package com.example.rabiu.farmerenrolmentliberia;


/**
 * Created by Windows on 19-01-2015.
 *//**
 * Created by Windows on 19-01-2015.
 */

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SyncResult;
import android.os.Bundle;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Handle the transfer of data between a server and an
 * app, using the Android sync adapter framework.
 */
public class FarmersSyncAdapter extends AbstractThreadedSyncAdapter {
    public static final String ACTION = "com.example.rabiu.farmerenrolmentform.ACTION_SYNC_COMPLETED";
    public static final String SYNC_OUTCOME = "syncOutcome";

    // Global variables
    // Define a variable to contain a content resolver instance
    ContentResolver mContentResolver;
    DBFarmers controller;
    Context context;
    /**
     * Set up the sync adapter
     */

    public FarmersSyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        /*
         * If your app uses a content resolver, get an instance of it
         * from the incoming Context
         */
        mContentResolver = context.getContentResolver();
        init(context);

        Log.d("RABIU", "syncadapter initialized" + Thread.currentThread().getName());
    }

    /**
     * Set up the sync adapter. This form of the
     * constructor maintains compatibility with Android 3.0
     * and later platform versions
     */
    public FarmersSyncAdapter(
            Context context,
            boolean autoInitialize,
            boolean allowParallelSyncs) {
        super(context, autoInitialize, allowParallelSyncs);
        /*
         * If your app uses a content resolver, get an instance of it
         * from the incoming Context
         */

        mContentResolver = context.getContentResolver();
        init(context);
        Log.d("RABIU", "syncadapter initialized");


    }

    private void init(Context context) {
        this.context=context;
        controller = new DBFarmers(context);
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {

        ArrayList<HashMap<String, String>> userList = controller.getAllUsers();
        if (userList.size() != 0) {
            if (controller.dbSyncCount() != 0) {
                HttpClient client=new DefaultHttpClient();
              //  HttpPost httpPost=new HttpPost("http://nonitravels.comli.com/farmersenrol/insertuser.php");
                HttpPost httpPost=new HttpPost("http://41.206.23.39/LATA/Insert/insertuser.php");

                L.m("onPerformSync Initiated...");
                try {
                    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
                    String jsonData=controller.composeJSONfromSQLite();
                    nameValuePairs.add(new BasicNameValuePair("usersJSON",jsonData));
                    L.m("" + jsonData + " was generated...");
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse response=client.execute(httpPost);
                    L.m(""+response.getStatusLine());
                    String responseString = EntityUtils.toString(response.getEntity(), "UTF-8");
                    Intent intent = new Intent(ACTION);
                    intent.putExtra(SYNC_OUTCOME, responseString);
                    context.sendBroadcast(intent);
                    L.m("Sync Completed...response "+responseString);

                } catch (IOException e) {
                    L.m(e.toString());
                }

            }
        }
    }
}
