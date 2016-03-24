package com.example.rabiu.farmerenrolmentliberia;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.TextView;
import android.content.Intent;

import java.util.List;

public class Register extends Activity implements AdapterView.OnItemSelectedListener
{
    EditText editTextfullName,editTextMobileNumber, editTextUserName,editTextPassword,editTextConfirmPassword;
    Spinner state, lga;
   ImageButton btnCreateAccount;


   // String statesid,lgasid;


    // GPSTracker class
    GPSTracker gps;

    DBFarmers controller;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

///
        TextView loginScreen = (TextView) findViewById(R.id.link_to_login);

        // Listening to Login Screen link
        loginScreen.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                // Closing registration screen
                // Switching to Login Screen/closing register screen
                finish();
            }
        });

        // get Instance  of Database Adapter
        controller =new DBFarmers(this);

        // Get Refferences of Views
        editTextUserName=(EditText)findViewById(R.id.username);
        editTextPassword=(EditText)findViewById(R.id.password);
        editTextConfirmPassword=(EditText)findViewById(R.id.confirmpassword);
        editTextfullName = (EditText)findViewById(R.id.fullname);
        editTextMobileNumber = (EditText)findViewById(R.id.mobilenumber);
        state = (Spinner)findViewById(R.id.state);
        state.setOnItemSelectedListener(this);
        lga = (Spinner) findViewById(R.id.lga);
        lga.setOnItemSelectedListener(this);



        btnCreateAccount=(ImageButton)findViewById(R.id.createadmin);
        loadSpinnerData();



        btnCreateAccount.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub

                String userName=editTextUserName.getText().toString();
                String password=editTextPassword.getText().toString();
                String fullName=editTextfullName.getText().toString();
                String mobileNumber =editTextMobileNumber.getText().toString();
                String confirmPassword=editTextConfirmPassword.getText().toString();
                String statename =   state.getSelectedItem().toString();
                String lganame =   lga.getSelectedItem().toString();


                // check if any of the fields are vaccant
                if(userName.equals("")||password.equals("")||confirmPassword.equals("")||fullName.equals("")
                        ||mobileNumber.equals(""))
                {
                    Toast customtoast = new Toast(getBaseContext());
                    customtoast = Toast.makeText(getBaseContext(), "Vacant Field", Toast.LENGTH_SHORT);
                    customtoast.setGravity(Gravity.CENTER, 0, 0);
                    customtoast.show();
                    return;
                }
                // check if both password matches
                if(!password.equals(confirmPassword))
                {
                    Toast customtoast = new Toast(getBaseContext());
                    customtoast = Toast.makeText(getBaseContext(), "Password does not match", Toast.LENGTH_SHORT);
                    customtoast.setGravity(Gravity.CENTER, 0, 0);
                    customtoast.show();
                    return;
                }


                if(userName.equals(controller.getUser(userName))){Toast customtoast = new Toast(getBaseContext());
                    customtoast = Toast.makeText(getBaseContext(), "Username Already Exist", Toast.LENGTH_SHORT);
                    customtoast.setGravity(Gravity.CENTER, 0, 0);
                    customtoast.show();
                    return;}
                else
                {


                    TelephonyManager  tm=(TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
                    String imei = tm.getDeviceId();

                    SharedPreferences sharedPreferences = getSharedPreferences("AdminData", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    editor.putString("fullName", fullName);
                    editor.putString("mobileNumber", mobileNumber);
                    editor.putString("county", statename);
                    editor.putString("district", lganame);
                    editor.putString("imei", imei);


                    editor.commit();



                    // Save the Data in Database
                    controller.insertEntry(fullName, mobileNumber,userName, password, statename, lganame ,imei);
                    Toast customtoast = new Toast(getBaseContext());
                    customtoast = Toast.makeText(getBaseContext(), "Account Successfully Created", Toast.LENGTH_SHORT);
                    customtoast.setGravity(Gravity.CENTER, 0, 0);
                    customtoast.show();
                    Intent objIntent = new Intent(getApplicationContext(), Login.class);
                    startActivity(objIntent);
                }
            }
        });



    }


    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();

        controller.close();
    }
    /**
     * Function to load the spinner data from SQLite database
     * */
    private void loadSpinnerData() {
        // database handler
      DBFarmers db = new DBFarmers(getApplicationContext());

        // Spinner Drop down elements
        List<String> lables = db.getAllCounty();

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, lables);

        // Drop down layout style - list view with radio button
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        String myString = "[Select County]";
        // int position = dataAdapter.getPosition(myString);
        // dataAdapter.add(myString);
        dataAdapter.insert(myString , 0);

        // attaching data adapter to spinner
        state.setAdapter(dataAdapter);
        // state.setSelection(position);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        SharedPreferences sharedPreferences = getSharedPreferences("AdminData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        ArrayAdapter<CharSequence> adapter = null;

        Spinner spinner = (Spinner) parent;
        int ids = spinner.getId();
        switch (ids) {
            case R.id.state:



                String labelss = parent.getItemAtPosition(position).toString();

                DBFarmers db = new DBFarmers(getApplicationContext());
                //statesid =  db.getStateId(labelss);

                // Spinner Drop down elements
                List<String> lables = db.getDistrict(labelss);

                // Creating adapter for spinner
                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                        android.R.layout.simple_spinner_item, lables);

                // Drop down layout style - list view with radio button
                dataAdapter
                        .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                String myString = "[Select District]";
                dataAdapter.insert(myString, 0);
                // attaching data adapter to spinner
                lga.setAdapter(dataAdapter);

                break;
           /* case R.id.lga:

                String wardlabel = parent.getItemAtPosition(position).toString();

                DBFarmers db1 = new DBFarmers(getApplicationContext());

             //lgasid =  db1.getLGAId(wardlabel);

                // Spinner Drop down elements
               // List<String> wlables = db1.getWard(wardlabel);
*/
               // break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


}
