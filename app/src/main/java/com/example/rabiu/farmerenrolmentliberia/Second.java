package com.example.rabiu.farmerenrolmentliberia;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.*;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Rabiu on 7/30/15.
 */
public class Second extends ActionBarActivity implements AdapterView.OnItemSelectedListener {

    EditText village, address;
    Spinner maritalstatus,preferredlanguage, county,district,area,vcr;
    ImageButton back, next;

  //  DBFarmers db = new DBFarmers(getApplicationContext());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second);

        //        action bar toolbar
        Toolbar actionBarToolBar = (Toolbar) findViewById(R.id.my_action_bar_toolbar);
        //        important that this is set first
        setSupportActionBar(actionBarToolBar);

        village = (EditText) findViewById(R.id.farmervillage);
        address = (EditText) findViewById(R.id.farmeraddress);

        county = (Spinner) findViewById(R.id.ecounty);
        county.setOnItemSelectedListener(this);
        district = (Spinner) findViewById(R.id.edistrict);
        district.setOnItemSelectedListener(this);
        area = (Spinner) findViewById(R.id.earea);
        area.setOnItemSelectedListener(this);
        vcr = (Spinner) findViewById(R.id.evcr);

        maritalstatus = (Spinner) findViewById(R.id.maritalstatus);

        preferredlanguage = (Spinner) findViewById(R.id.elanguage);
        next = (ImageButton) findViewById(R.id.next);
        back = (ImageButton) findViewById(R.id.back);


        loadSpinnerData();

        SharedPreferences sharedPreferences = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();


        village.setText(sharedPreferences.getString("farmervillage",""));
        address.setText(sharedPreferences.getString("farmeraddress",""));



        next.setOnClickListener(new Button.OnClickListener() {
            @Override

            public void onClick(final View v)

            {

                final String county_str = county.getSelectedItem().toString();
                final String district_str = district.getSelectedItem().toString();
                final String town_str = area.getSelectedItem().toString();
                final String vcr_str = vcr.getSelectedItem().toString();
                final String address_str = address.getText().toString();
                final String marital_str = maritalstatus.getSelectedItem().toString();
                final String preflang_str = preferredlanguage.getSelectedItem().toString();
                final String village_str = village.getText().toString();

                if (!isValidCounty(county_str)) {
                    TextView errorText = (TextView)county.getSelectedView();
                    errorText.setError("");
                } else if (!isValidDistrict(district_str)) {
                    TextView errorText = (TextView)district.getSelectedView();
                    errorText.setError("");
                } else if (!isValidTown(town_str)) {
                    TextView errorText = (TextView)area.getSelectedView();
                    errorText.setError("");
                } else if (!isValidVcr(vcr_str)) {
                    TextView errorText = (TextView)vcr.getSelectedView();
                    errorText.setError("");
                } else if (!isValidField(village_str)) {
                    village.setError("");
                } else if (!isValidField(address_str)) {
                    address.setError("");
                } else if (!isValidMarital(marital_str)) {
                    TextView errorText = (TextView)maritalstatus.getSelectedView();
                    errorText.setError("");
                } else if (!isValidLan(preflang_str)) {
                    TextView errorText = (TextView)preferredlanguage.getSelectedView();
                    errorText.setError("");
                }

                else {

                    SharedPreferences sharedPreferences = getSharedPreferences("MyData", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    editor.putString("farmervillage", village.getText().toString());
                    editor.putString("farmeraddress", address.getText().toString());
                    editor.putString("maritalstatus", maritalstatus.getSelectedItem().toString());
                    editor.putString("preferredlanguage", preferredlanguage.getSelectedItem().toString());
                    editor.putString("county", county.getSelectedItem().toString());
                    editor.putString("district", district.getSelectedItem().toString());
                    editor.putString("area", area.getSelectedItem().toString());
                    editor.putString("vcr", vcr.getSelectedItem().toString());
                    editor.commit();

                    Intent intent = new Intent(Second.this, Third.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }
            }
        });

        back.setOnClickListener(new Button.OnClickListener() {
            @Override

            public void onClick(final View v)

            {
                //Start another activity by passing an explicit intent

                Intent intent = new Intent(Second.this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_report:
                Intent i = new Intent(this, FarmerList.class);
                startActivity(i);
                return true;
            case R.id.action_sync:
                Intent s = new Intent(this, ManualSync.class);
                startActivity(s);
                return true;
            case R.id.action_exportdb:
                Intent ex = new Intent(this, ExportDB.class);
                startActivity(ex);
                return true;
            case R.id.action_exit:
                Intent e = new Intent(this, Login.class);
                startActivity(e);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
        county.setAdapter(dataAdapter);
        // state.setSelection(position);
    }

    // validating county
    private boolean isValidCounty(String pass) {
        if (!pass.equals("[Select County]")) {
            return true;
        }
        return false;
    }
    // validating district
    private boolean isValidDistrict(String pass) {
        if (!pass.equals("[Select District]")) {
            return true;
        }
        return false;
    }
    // validating town
    private boolean isValidTown(String pass) {
        if (!pass.equals("[Select Town]")) {
            return true;
        }
        return false;
    }

    // validating vcr
    private boolean isValidVcr(String pass) {
        if (!pass.equals("[Select VCR]")) {
            return true;
        }
        return false;
    }
    // validating marital
    private boolean isValidMarital(String pass) {
        if (!pass.equals("[Select Marital Status]")) {
            return true;
        }
        return false;
    }
    // validating language
    private boolean isValidLan(String pass) {
        if (!pass.equals("[Select Preferred Language]")) {
            return true;
        }
        return false;
    }
    // validating field
    private boolean isValidField(String pass) {
        if (pass != null && pass.length() > 3) {
            return true;
        }
        return false;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        ArrayAdapter<CharSequence> adapter = null;

        Spinner spinner = (Spinner) parent;
        int ids = spinner.getId();
        switch (ids) {
            case R.id.ecounty:

                SharedPreferences sharedPreferences = getSharedPreferences("MyData", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                int lgaid = sharedPreferences.getInt("lgaid", 0);

                String labelss = parent.getItemAtPosition(position).toString();

                DBFarmers db = new DBFarmers(getApplicationContext());

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
                district.setAdapter(dataAdapter);
                break;

            case R.id.edistrict:

                String wardlabel = parent.getItemAtPosition(position).toString();

                DBFarmers db1 = new DBFarmers(getApplicationContext());

                // Spinner Drop down elements
                List<String> wlables = db1.getArea(wardlabel);

                // Creating adapter for spinner
                ArrayAdapter<String> wdataAdapter = new ArrayAdapter<String>(this,
                        android.R.layout.simple_spinner_item, wlables);

                // Drop down layout style - list view with radio button
                wdataAdapter
                        .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


                String myStrings = "[Select Town]";
                wdataAdapter.insert(myStrings , 0);

                // attaching data adapter to spinner
                area.setAdapter(wdataAdapter);

                break;
            case R.id.earea:

                String town_str = parent.getItemAtPosition(position).toString();

                DBFarmers db2 = new DBFarmers(getApplicationContext());
                // Spinner Drop down elements
                List<String> vcr_list = db2.getVcr(town_str);

                // Creating adapter for spinner
                ArrayAdapter<String> vcrAdapter = new ArrayAdapter<String>(this,
                        android.R.layout.simple_spinner_item, vcr_list);

                // Drop down layout style - list view with radio button
                vcrAdapter
                        .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


                String vcroption_str = "[Select VCR]";
                vcrAdapter.insert(vcroption_str , 0);

                // attaching data adapter to spinner
                vcr.setAdapter(vcrAdapter);

                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    @Override
    public void onBackPressed() {

    }
}