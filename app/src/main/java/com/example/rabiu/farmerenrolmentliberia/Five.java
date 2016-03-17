package com.example.rabiu.farmerenrolmentliberia;


import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.*;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import android.util.Base64;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

/////nfc import
import android.content.IntentFilter;

/////barcode import
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

/**
 * Created by Rabiu on 3/6/15.
 */
public class Five extends ActionBarActivity {

    SyncReceiver receiver;

    EditText tagnumber;
    ImageButton back4, btnTakePhoto, next4;
    public static final String DEFAULT = "NULL";
    ImageView imgTakenPhoto, preview;
    private static final int CAM_REQUEST = 1313;
    private byte[] img = null;
    private byte[] barcodeimg = null;



    final Context context = this;

    public static final String OUTCOME = "outcome";
    // The authority for the sync adapter's content provider
    public static final String AUTHORITY = "com.example.rabiu.farmerenrolmentformprovider";
    // An account type, in the form of a domain name
    public static final String ACCOUNT_TYPE = "farmerenrolmentform.rabiu.example.com";
    // The account name
    public static final String ACCOUNT = "farmersaccount";
    // Instance fields
    Account mAccount;

    DBFarmers controller = new DBFarmers(this);


    public class SyncReceiver extends BroadcastReceiver {


        public void onReceive(Context context, Intent intent) {

            Bundle extras = intent.getExtras();
            L.m("onReceive called");
            if (extras != null) {
                //do something
                String outcome = extras.getString(FarmersSyncAdapter.SYNC_OUTCOME);

                try {
                    JSONArray arr = new JSONArray(outcome);
                   // System.out.println(arr.length());
                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject obj = (JSONObject) arr.get(i);
                        controller.updateSyncStatus(obj.get("id").toString(), obj.get("status").toString());
                    }
                    Toast.makeText(getApplicationContext(), "Data Sent to Server", Toast.LENGTH_LONG).show();

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    Toast.makeText(getApplicationContext(), "Error Occured !", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
                L.m(outcome);


            }
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.five);

        //        action bar toolbar
        Toolbar actionBarToolBar = (Toolbar) findViewById(R.id.my_action_bar_toolbar);
        //        important that this is set first
        setSupportActionBar(actionBarToolBar);

        back4 = (ImageButton) findViewById(R.id.back4);
        next4 = (ImageButton) findViewById(R.id.next4);

        btnTakePhoto = (ImageButton) findViewById(R.id.takepic);
        imgTakenPhoto = (ImageView) findViewById(R.id.imageView);
        tagnumber = (EditText) findViewById(R.id.tagnumber);

        receiver = new SyncReceiver();
        IntentFilter filter = new IntentFilter(FarmersSyncAdapter.ACTION);
        registerReceiver(receiver, filter);




        btnTakePhoto.setOnClickListener(new btnTakePhotoClicker());

        back4.setOnClickListener(new Button.OnClickListener() {
            @Override

            public void onClick(final View v)

            {
                //Start another activity by passing an explicit intent

                Intent intent = new Intent(Five.this, Four.class);

                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

            }
        });

        next4.setOnClickListener(new Button.OnClickListener() {
            @Override

            public void onClick(final View v)

            {

                Intent data = new Intent();

                //else
                if (img != null && tagnumber.getText().toString().trim().length() != 0) {

                    String tagno = tagnumber.getText().toString();

                    if (tagno.equals(controller.checkRecord(tagno)))

                    {
                        Toast customtoast = new Toast(getBaseContext());
                        customtoast = Toast.makeText(getBaseContext(), "Farmer ID Already Captured", Toast.LENGTH_SHORT);
                        customtoast.setGravity(Gravity.CENTER, 0, 0);
                        customtoast.show();
                        return;

                    } else {


                        SharedPreferences sharedPreferences = getSharedPreferences("MyData", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();

                        editor.putString("tagnumber", tagnumber.getText().toString());
                        editor.commit();

                        String imageDataString = Base64.encodeToString(img, Base64.DEFAULT);

                        SharedPreferences sp = getSharedPreferences("AdminData", Context.MODE_PRIVATE);
                        SharedPreferences.Editor ed = sp.edit();

                        HashMap<String, String> queryValues = new HashMap<String, String>();


                        queryValues.put("title", sharedPreferences.getString("title", DEFAULT));
                        queryValues.put("surname", sharedPreferences.getString("surname", DEFAULT));
                        queryValues.put("firstname", sharedPreferences.getString("firstname", DEFAULT));
                        queryValues.put("middlename", sharedPreferences.getString("middlename", DEFAULT));
                        queryValues.put("phoneNumber", sharedPreferences.getString("telephone", DEFAULT));
                        queryValues.put("phoneNumber2", sharedPreferences.getString("telephone2", DEFAULT));
                        queryValues.put("dob", sharedPreferences.getString("dob", DEFAULT));
                        queryValues.put("educationlevel", sharedPreferences.getString("education", DEFAULT));
                        queryValues.put("gender", sharedPreferences.getString("gender", DEFAULT));


                        queryValues.put("farmervillage", sharedPreferences.getString("farmervillage", DEFAULT));
                        queryValues.put("farmeraddress", sharedPreferences.getString("farmeraddress", DEFAULT));
                        queryValues.put("maritalstatus", sharedPreferences.getString("maritalstatus", DEFAULT));
                        queryValues.put("preferredlanguage", sharedPreferences.getString("preferredlanguage", DEFAULT));
                        queryValues.put("county", sharedPreferences.getString("county", DEFAULT));
                        queryValues.put("district", sharedPreferences.getString("district", DEFAULT));
                        queryValues.put("area", sharedPreferences.getString("area", DEFAULT));

                        queryValues.put("primary_crop", sharedPreferences.getString("primary_crop", DEFAULT));
                        queryValues.put("farmerscrop", sharedPreferences.getString("farmerscrop", DEFAULT));
                        queryValues.put("farmsize", sharedPreferences.getString("farmsize", DEFAULT));
                        queryValues.put("farmlocation", sharedPreferences.getString("farmlocation", DEFAULT));
                        queryValues.put("farmerslivestock", sharedPreferences.getString("farmerslivestock", DEFAULT));
                        queryValues.put("farmersfisheries", sharedPreferences.getString("farmersfisheries", DEFAULT));

                        queryValues.put("member_question", sharedPreferences.getString("member_question", DEFAULT));
                        queryValues.put("farmersgroup", sharedPreferences.getString("farmersgroup", DEFAULT));
                        queryValues.put("groupleadername", sharedPreferences.getString("groupleadername", DEFAULT));
                        queryValues.put("groupleaderphone", sharedPreferences.getString("groupleaderphone", DEFAULT));

                        queryValues.put("farmerID", sharedPreferences.getString("tagnumber", DEFAULT));
                        queryValues.put("image", imageDataString);

                        queryValues.put("adminfullname", sp.getString("fullName", DEFAULT));
                        queryValues.put("adminphone", sp.getString("mobileNumber", DEFAULT));
                        queryValues.put("admincounty", sp.getString("county", DEFAULT));
                        queryValues.put("admindistrict", sp.getString("district", DEFAULT));
                        queryValues.put("imei", sp.getString("imei", DEFAULT));


                       // Intent data = new Intent();

                        Bundle extras = new Bundle();

                        ContentResolver.setSyncAutomatically(createSyncAccount(getApplicationContext()), AUTHORITY, true);

                        ContentResolver.addPeriodicSync(createSyncAccount(getApplicationContext()), AUTHORITY, new Bundle(), 360);

                        controller.insertUser(queryValues, createSyncAccount(getApplicationContext()), AUTHORITY, extras);

                        data.putExtra(OUTCOME, true);

                        setResult(RESULT_OK, data);

                       /* final Dialog dialog = new Dialog(context);
                        dialog.setContentView(R.layout.custom);
                        dialog.setTitle("Scan Barcode");
                       // final ImageView myImage = (ImageView) dialog.findViewById(R.id.image);
                        //byte[] decodedByte = Base64.decode(barcodeImage, 0);
                        //Bitmap bitmap = BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
                        //myImage.setImageBitmap(bitmap);

                        Button print = (Button) dialog.findViewById(R.id.print);
                        Button home = (Button) dialog.findViewById(R.id.returnButton);

                        *//*print.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                SharedPreferences sharedPreferences = getSharedPreferences("MyData", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                String id = sharedPreferences.getString("tagnumber", DEFAULT);
                                editor.commit();
                                //controller.updateBarcodeStatus(id);
                                Toast customtoast = new Toast(getBaseContext());
                                customtoast = Toast.makeText(getBaseContext(), "Confirmed", Toast.LENGTH_SHORT);
                                customtoast.setGravity(Gravity.CENTER, 0, 0);
                                customtoast.show();

                            }
                        });*//*
                        home.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                SharedPreferences sharedPreferences = getSharedPreferences("MyData", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.clear();
                                editor.commit();

                                Intent intent = new Intent(Five.this, MainActivity.class);
                                startActivity(intent);
                            }
                        });
                        dialog.show();*/
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                                context);

                        // set title
                        alertDialogBuilder.setTitle("");

                        // set dialog message
                        alertDialogBuilder
                                .setMessage("Click Yes to Register another farmer")
                                .setCancelable(false)
                                .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        // if this button is clicked, close
                                        // current activity
                                      //  Five.this.finish();
                                        SharedPreferences sharedPreferences = getSharedPreferences("MyData", Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.clear();
                                        editor.commit();

                                        Intent intent = new Intent(Five.this, MainActivity.class);
                                        startActivity(intent);
                                    }
                                })
                                .setNegativeButton("No",new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        // if this button is clicked, just close
                                        // the dialog box and do nothing
                                        Intent intent = new Intent(Five.this, Login.class);
                                        startActivity(intent);
                                       // dialog.cancel();
                                    }
                                });

                        // create alert dialog
                        AlertDialog alertDialog = alertDialogBuilder.create();

                        // show it
                        alertDialog.show();

                    }

                } else {

                       data.putExtra(OUTCOME, false);
                      setResult(RESULT_CANCELED, data);
                    Toast customtoast = new Toast(getBaseContext());
                    customtoast = Toast.makeText(getBaseContext(), "Farmer Picture or Farmer ID is blank", Toast.LENGTH_SHORT);
                    customtoast.setGravity(Gravity.CENTER, 0, 0);
                    customtoast.show();
                }
            }

        });


    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_report:
                Intent i = new Intent(this, FarmerList.class);
                startActivity(i);
                return true;
            case R.id.action_sync:
                Intent s = new Intent(this, ManualSync.class);
                startActivity(s);
                return true;
            case R.id.action_exit:
                Intent e = new Intent(this, Login.class);
                startActivity(e);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



    public static Account createSyncAccount(Context context) {
        // Create the account type and default account
        Account newAccount = new Account(
                ACCOUNT, ACCOUNT_TYPE);
        // Get an instance of the Android account manager
        AccountManager accountManager =
                (AccountManager) context.getSystemService(
                        ACCOUNT_SERVICE);
        /*
         * Add the account and account type, no password or user data
         * If successful, return the Account object, otherwise report an error.
         */
        if (accountManager.addAccountExplicitly(newAccount, null, null)) {
            /*
             * If you don't set android:syncable="true" in
             * in your <provider> element in the manifest,
             * then call context.setIsSyncable(account, AUTHORITY, o1)
             * here.
             */
        } else {
            /*
             * The account exists or some other error occurred. Log this, report it,
             * or handle it internally.
             */
        }

        return newAccount;
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        //retrieve scan result
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if (scanningResult != null) {
            //we have a result
            String scanContent = scanningResult.getContents();
            String scanFormat = scanningResult.getFormatName();

            tagnumber.setText(scanContent);
        }/*else{
            Toast toast = Toast.makeText(getApplicationContext(),"No scan data received!", Toast.LENGTH_SHORT);
            toast.show();
        }
*/

            switch(requestCode){
            case CAM_REQUEST:
                switch (resultCode){
                    case Activity.RESULT_OK:
                        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                     imgTakenPhoto.setImageBitmap(thumbnail);
                        ByteArrayOutputStream bos=new ByteArrayOutputStream();
                        thumbnail.compress(Bitmap.CompressFormat.JPEG, 70, bos);
                        img=bos.toByteArray();
                       // String imageDataString1 = Base64.encodeToString(img,Base64.DEFAULT);


                }break;
            case Activity.RESULT_CANCELED:
                finish();
                //no blather
                break;
            default:
              //  Toast.makeText(this, "Unexpected resultCode: " + resultCode, Toast.LENGTH_LONG).show();
        }
    }


    class btnTakePhotoClicker implements Button.OnClickListener
    {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            Intent cameraintent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraintent, CAM_REQUEST);
        }
    }

    public void scanQR(View view){
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        integrator.setPrompt(String.valueOf(R.string.txt_scan_qr_code));
        integrator.setResultDisplayDuration(0);
        integrator.setCameraId(0);  // Use a specific camera of the device
        integrator.initiateScan();


        //Intent intent = new Intent("com.google.zxing.client.android.SCAN");
        //intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
        //startActivityForResult(intent, 000);

    }

    /*public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        //retrieve scan result
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);

        if (scanningResult != null) {
            //we have a result
            String scanContent = scanningResult.getContents();
            String scanFormat = scanningResult.getFormatName();

            // display it on screen
            formatTxt.setText("FORMAT: " + scanFormat);
            contentTxt.setText("CONTENT: " + scanContent);

        }else{
            Toast toast = Toast.makeText(getApplicationContext(),"No scan data received!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }*/

    @Override
    public void onBackPressed() {

    }
}
