package com.example.rabiu.farmerenrolmentliberia;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Created by Rabiu on 3/6/15.
 */
public class Third extends ActionBarActivity {




    EditText farmsize,farmlocation;
    MultiSelectionSpinner farmerscrop, farmerslivestock,faremersfisheries,cereals,tree_crops,roots,fruits,legumes,vegetables;
    Spinner primary_crop;
    ImageButton next2, back2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.third);

        //        action bar toolbar
        Toolbar actionBarToolBar = (Toolbar) findViewById(R.id.my_action_bar_toolbar);
        //        important that this is set first
        setSupportActionBar(actionBarToolBar);

       // farmerscrop = (EditText)findViewById(R.id.efarmercrop);
        farmsize = (EditText) findViewById(R.id.efarmsize);
        farmlocation = (EditText)findViewById(R.id.efarmlocation);
        primary_crop = (Spinner)findViewById(R.id.primary_crop);

        String[] arrays = {"None","Corn","Rice","Millet","Cassava", "Soybeans","Sorghum",
                "Sesame","Wheat","Groundnut", "Cashew"};

        String[] livestockarray = {"None","Piggery","Poultry", "Sheep and Goat","Dairy","Beef","Leather"};
        String[] fisharray = {"None","Aquaculture","Artisanal"};

        String[] tree_cropsarray ={"None","Cocoa","Oil Palm","Coconut","Coffee"};
        String[] rootsarray ={"None","Cassava","Sweet Potato","Stems","Eddoe"};
        String[] fruitsarray ={"None","Citrus","Banana","Plantain","Pineapple","Watermelon"};
        String[] legumesarray ={"None","Cowpea","Groundnut","Broad Beans"};
        String[]  vegetablearray = {"None","Hot Pepper","Bitter Ball","Eggplat","Okra","Cabbage","Collard","Wheat","Tomato","Cotton","Pumpkin","Lettuce","Squash"};

        //cereals,tree_crops,roots,fruits,legumes,vegetables

        tree_crops = (MultiSelectionSpinner) findViewById(R.id.etreecrops);
        roots      = (MultiSelectionSpinner) findViewById(R.id.eroots);
        fruits     = (MultiSelectionSpinner) findViewById(R.id.efruits);
        legumes    = (MultiSelectionSpinner) findViewById(R.id.elegumes);
        vegetables  = (MultiSelectionSpinner) findViewById(R.id.evegetables);


        farmerscrop = (MultiSelectionSpinner) findViewById(R.id.efarmercrop);
        farmerslivestock = (MultiSelectionSpinner) findViewById(R.id.efarmerlivestock);
        faremersfisheries = (MultiSelectionSpinner) findViewById(R.id.efarmersfisheries);




        farmerscrop.setItems(arrays);
        farmerslivestock.setItems(livestockarray);
        faremersfisheries.setItems(fisharray);
        tree_crops.setItems(tree_cropsarray);
        roots.setItems(rootsarray);
        fruits.setItems(fruitsarray);
        legumes.setItems(legumesarray);
        vegetables.setItems(vegetablearray);

        next2 = (ImageButton) findViewById(R.id.next2);
        back2 = (ImageButton) findViewById(R.id.back2);

        SharedPreferences sharedPreferences = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        farmsize.setText(sharedPreferences.getString("farmsize",""));
        farmlocation.setText(sharedPreferences.getString("farmlocation", ""));

        //Listener added to the submit button
        next2.setOnClickListener(new Button.OnClickListener(){
            @Override

            public void onClick(final View v)

            {

                final String farmsize_str = farmsize.getText().toString();
                final String farmlocation_str = farmlocation.getText().toString();
                final String crop_str = primary_crop.getSelectedItem().toString();

                    if (!isValidCrop(crop_str)) {
                    TextView errorText = (TextView)primary_crop.getSelectedView();
                    errorText.setError("");
                    } else if (!isValidField1(farmsize_str)) {
                        farmsize.setError("");
                        farmsize.requestFocus();
                    } else if (!isValidField(farmlocation_str)) {
                        farmlocation.setError("");
                        farmlocation.requestFocus();
                    }

                    else {
                    //Start another activity by passing an explicit intent
                    SharedPreferences sharedPreferences = getSharedPreferences("MyData", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();


                    editor.putString("primary_crop",primary_crop.getSelectedItem().toString());

                    editor.putString("tree_crops", tree_crops.getSelectedItem().toString());
                    editor.putString("roots", roots.getSelectedItem().toString());
                    editor.putString("fruits", fruits.getSelectedItem().toString());
                    editor.putString("legumes", legumes.getSelectedItem().toString());
                    editor.putString("vegetables",  vegetables.getSelectedItem().toString());

                    editor.putString("farmerscrop", farmerscrop.getSelectedItem().toString());
                    editor.putString("farmsize", farmsize.getText().toString());
                    editor.putString("farmlocation", farmlocation.getText().toString());

                    editor.putString("farmerslivestock", farmerslivestock.getSelectedItem().toString());
                    editor.putString("faremersfisheries", faremersfisheries.getSelectedItem().toString());
                    editor.commit();

                    Intent intent = new Intent(Third.this, Four.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

                }
            }
       });

        back2.setOnClickListener(new Button.OnClickListener() {
            @Override

            public void onClick(final View v)

            {
                //Start another activity by passing an explicit intent

                Intent intent = new Intent(Third.this, Second.class);
                startActivity(intent);

                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

            }
        });


    }

    // validating field
    private boolean isValidField(String pass) {
        if (pass != null && pass.length() >= 2) {
            return true;
        }
        return false;
    }
    // validating field
    private boolean isValidField1(String pass) {
        if (pass != null && pass.length() >= 1) {
            return true;
        }
        return false;
    }
    // validating primary_Crop
    private boolean isValidCrop(String pass) {
        if (!pass.equals("[Select Primary Crop]")) {
            return true;
        }
        return false;
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

    @Override
    public void onBackPressed() {

    }
}


