package com.example.rabiu.farmerenrolmentliberia;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Created by Rabiu on 3/6/15.
 */
public class Four extends ActionBarActivity implements AdapterView.OnItemSelectedListener {

    EditText farmersgroup,groupleadername,groupleaderphone;
    Spinner member_question;
    ImageButton next3, back3;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.four);

        //        action bar toolbar
        Toolbar actionBarToolBar = (Toolbar) findViewById(R.id.my_action_bar_toolbar);
        //        important that this is set first
        setSupportActionBar(actionBarToolBar);


        farmersgroup  = (EditText)findViewById(R.id.efarmergroup);
        groupleadername = (EditText) findViewById(R.id.egroupleadername);
        groupleaderphone = (EditText)findViewById(R.id.egroupleaderphone);
        member_question = (Spinner) findViewById(R.id.emember);
        member_question.setOnItemSelectedListener(this);

        next3 = (ImageButton) findViewById(R.id.next3);
        back3 = (ImageButton) findViewById(R.id.back3);

        SharedPreferences sharedPreferences = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        farmersgroup.setText(sharedPreferences.getString("farmersgroup",""));
        groupleadername.setText(sharedPreferences.getString("groupleadername",""));
        groupleaderphone.setText(sharedPreferences.getString("groupleaderphone",""));

                next3.setOnClickListener(new Button.OnClickListener() {
                    @Override

                    public void onClick(final View v)

                    {

                        // if (10 > 2)

                        // {

                        //Start another activity by passing an explicit intent
                        SharedPreferences sharedPreferences = getSharedPreferences("MyData", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();

                        editor.putString("member_question", member_question.getSelectedItem().toString());
                        editor.putString("farmersgroup", farmersgroup.getText().toString());
                        editor.putString("groupleadername", groupleadername.getText().toString());
                        editor.putString("groupleaderphone", groupleaderphone.getText().toString());
                        editor.commit();

                        Intent intent = new Intent(Four.this, Five.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

                       /* } else {

                            Toast customtoast = new Toast(getBaseContext());
                            customtoast = Toast.makeText(getBaseContext(), "Incomplete Fields", Toast.LENGTH_SHORT);
                            customtoast.setGravity(Gravity.CENTER, 0, 0);
                            customtoast.show();


                }*/


            }
        });

        back3.setOnClickListener(new Button.OnClickListener() {
            @Override

            public void onClick(final View v)

            {
                //Start another activity by passing an explicit intent
                Intent intent = new Intent(Four.this, Third.class);
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        String selected_str = parent.getItemAtPosition(position).toString();
        if (selected_str.equals("None")){

            farmersgroup.setVisibility(view.INVISIBLE);
            groupleadername.setVisibility(view.INVISIBLE);
            groupleaderphone.setVisibility(view.INVISIBLE);
        } else {
            farmersgroup.setVisibility(view.VISIBLE);
            groupleadername.setVisibility(view.VISIBLE);
            groupleaderphone.setVisibility(view.VISIBLE);

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onBackPressed() {

    }
}
