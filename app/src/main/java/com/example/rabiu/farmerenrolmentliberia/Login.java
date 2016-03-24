package com.example.rabiu.farmerenrolmentliberia;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;
import android.view.Gravity;
import android.app.Activity;


public class Login extends Activity {

    DBFarmers controller;
    ImageButton btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        // get Instance  of Database Adapter
        controller = new DBFarmers(this);

        // Get The Refference Of Buttons
        btnLogin = (ImageButton) findViewById(R.id.btnLogin);

        // get the Refferences of views

        final EditText username = (EditText) findViewById(R.id.eusername);
        final EditText password1 = (EditText) findViewById(R.id.epassword);

// Set On ClickListener
        btnLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // get The User name and Password
                String userName = username.getText().toString();
                String password = password1.getText().toString();

                // fetch the Password form database for respective user name
                String storedPassword = controller.getSinlgeEntry(userName);

                // check if the Stored password matches with  Password entered by user
                if (password.equals(storedPassword)) {


                    Toast customtoast = new Toast(getBaseContext());
                    customtoast = Toast.makeText(getBaseContext(), "Login Successfully", Toast.LENGTH_SHORT);
                    customtoast.setGravity(Gravity.CENTER, 0, 0);
                    customtoast.show();
                    Intent objIntent = new Intent(getApplicationContext(), MainActivity.class);
                    objIntent.putExtra("userName", userName);
                    startActivity(objIntent);

                } else {
                    Toast customtoast = new Toast(getBaseContext());
                    customtoast = Toast.makeText(getBaseContext(), "User Name or Password does not match", Toast.LENGTH_SHORT);
                    customtoast.setGravity(Gravity.CENTER, 0, 0);
                    customtoast.show();
                }
            }
        });




       ///here
        TextView registerScreen = (TextView) findViewById(R.id.link_to_register);

        // Listening to register new account link

        registerScreen.setOnClickListener(new View.OnClickListener()
        {

            public void onClick(View v) {
                // Switching to Register screen
                Intent i = new Intent(getApplicationContext(), Register.class);
                startActivity(i);
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Close The Database
        controller.close();
    }
}




