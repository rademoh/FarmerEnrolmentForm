package com.example.rabiu.farmerenrolmentliberia;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Rabiu on 7/31/15.
 */
public class FarmerList extends ActionBarActivity {

    DBFarmers controller = new DBFarmers(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.farmerslist);

        //Get User records from SQLite DB
        ArrayList<HashMap<String, String>> userList =  controller.getAllUsers();
        //
        if(userList.size()!=0){
            //Set the User Array list in ListView
            ListAdapter adapter = new SimpleAdapter( FarmerList.this,userList, R.layout.farmer_list, new String[] {"firstname", "surname","updateStatus","farmerID"},
                    new int[] { R.id.firstName, R.id.lastName, R.id.updateStatus,R.id.farmerid});
            ListView myList=(ListView)findViewById(android.R.id.list);
          ///  Helper.getListViewSize(myList);
            myList.setAdapter(adapter);
            //Display Sync status of SQLite DB
          //  Toast.makeText(getApplicationContext(), controller.getSyncStatus(), Toast.LENGTH_LONG).show();
        }
    }

    public void backButton(View v) {

        finish();

    }
    public void deleteAll(View view){

       // controller.updateStatus();
       String msg = controller.delete();
       // Integer msg = controller.dbDeleteCount();
        System.out.println("This is the count" + msg);
        Toast customtoast = new Toast(getBaseContext());
        customtoast = Toast.makeText(getBaseContext(), msg, Toast.LENGTH_SHORT);
        customtoast.setGravity(Gravity.CENTER, 0, 0);
        customtoast.show();


    }

    @Override
    public void onBackPressed() {

    }
}
