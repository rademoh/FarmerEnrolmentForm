package com.example.rabiu.farmerenrolmentliberia;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends ActionBarActivity implements View.OnClickListener {


    EditText surname, firstname, middlename, telephone, telephone2;
    Spinner education, title;
    ImageButton button;
    RadioGroup gender;


    public static final String DEFAULT = "";

    DBFarmers controller = new DBFarmers(this);
    //UI References
    private EditText doBirth;
    private DatePickerDialog fromDatePickerDialog;
    private SimpleDateFormat dateFormatter;

    public static GoogleAnalytics analytics;
    public static Tracker tracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newone);
        //        action bar toolbar
        Toolbar actionBarToolBar = (Toolbar) findViewById(R.id.my_action_bar_toolbar);
        //        important that this is set first
        setSupportActionBar(actionBarToolBar);

        analytics = GoogleAnalytics.getInstance(this);
        analytics.setLocalDispatchPeriod(1800);

        tracker = analytics.newTracker("UA-61976873-2"); //  tracker/property Id
        tracker.enableExceptionReporting(true);
        tracker.enableAdvertisingIdCollection(true);
        tracker.enableAutoActivityTracking(true);

        title = (Spinner) findViewById(R.id.etitle);
        surname = (EditText) findViewById(R.id.esurname);
        firstname = (EditText) findViewById(R.id.efirstname);
        middlename = (EditText) findViewById(R.id.emiddlename);
        telephone = (EditText) findViewById(R.id.etelephone);
        telephone2 = (EditText) findViewById(R.id.etelephone2);
        education = (Spinner) findViewById(R.id.eeducation);
        gender = (RadioGroup) findViewById(R.id.egender);
        button = (ImageButton) findViewById(R.id.next);

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        findViewsById();
        setDateTimeField();

        SharedPreferences sharedPreferences = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();


        surname.setText(sharedPreferences.getString("surname", ""));
        firstname.setText(sharedPreferences.getString("firstname", ""));
        middlename.setText(sharedPreferences.getString("middlename", ""));
        telephone.setText(sharedPreferences.getString("telephone", ""));
        telephone2.setText(sharedPreferences.getString("telephone2", ""));


        button.setOnClickListener(new Button.OnClickListener() {
            @Override

            public void onClick(final View v)

            {
                //Start another activity by passing an explicit intent

                int radioId = gender.getCheckedRadioButtonId();
                final RadioButton radioGender = (RadioButton) findViewById(radioId);

                final String title_str = title.getSelectedItem().toString();
                final String surname_str = surname.getText().toString();
                final String firstname_str = firstname.getText().toString();
                final String telephone_str = telephone.getText().toString();
                final String education_str = education.getSelectedItem().toString();
                final String dob_str = doBirth.getText().toString();

                if (!isValidTitle(title_str)) {
                    TextView errorText = (TextView) title.getSelectedView();
                    errorText.setError("");
                } else if (!isValidField(surname_str)) {
                    surname.setError("");
                } else if (!isValidField(firstname_str)) {
                    firstname.setError("");
                } else if (!checkDob(dob_str)) {
                    doBirth.setError("Farmer must be above 18 years");
                } else if (!isValidMobile(telephone_str)) {
                    telephone.setError("");
                } else if (!isValidEdu(education_str)) {
                    TextView errorText = (TextView) education.getSelectedView();
                    errorText.setError("");
                } else {

                    SharedPreferences sharedPreferences = getSharedPreferences("MyData", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    editor.putString("title", title.getSelectedItem().toString());
                    editor.putString("surname", surname.getText().toString());
                    editor.putString("firstname", firstname.getText().toString());
                    editor.putString("middlename", middlename.getText().toString());
                    editor.putString("telephone", telephone.getText().toString());
                    editor.putString("telephone2", telephone2.getText().toString());
                    editor.putString("dob", doBirth.getText().toString());
                    editor.putString("education", education.getSelectedItem().toString());
                    editor.putString("gender", radioGender.getText().toString());
                    editor.commit();


                    Intent intent = new Intent(MainActivity.this, Second.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

                }

            }


            //}
        });


    }

    // validating field
    private boolean isValidField(String pass) {
        if (pass != null && pass.length() > 3) {
            return true;
        }
        return false;
    }

    // validating title
    private boolean isValidTitle(String pass) {
        if (!pass.equals("[Select Title]")) {
            return true;
        }
        return false;
    }

    // validating telephone number
    private boolean isValidMobile(String pass) {
        if (pass != null && pass.length() >= 10) {
            return true;
        }
        return false;
    }

    // validating education
    private boolean isValidEdu(String pass) {
        if (!pass.equals("[Select Education]")) {
            return true;
        }
        return false;
    }

    private boolean checkDob(String dob_str) {

        try {
            SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            Date validDate = df.parse("01-01-1997");
            Date inputDate = df.parse(dob_str);

            if (inputDate.before(validDate)) {
                return true;
            }
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
            return  false;
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
      //  int id = item.getItemId();

        //noinspection SimplifiableIfStatement
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

    private void findViewsById() {
        doBirth = (EditText) findViewById(R.id.dob);
        doBirth.setInputType(InputType.TYPE_NULL);
        doBirth.requestFocus();
    }

    private void setDateTimeField() {
        doBirth.setOnClickListener(this);


        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                doBirth.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

    }


    @Override
    public void onClick(View v) {
        if(v == doBirth) {
            fromDatePickerDialog.show();
        }
    }

    @Override
    public void onBackPressed() {

    }
}










