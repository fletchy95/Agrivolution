package com.example.vanda.agrivolution;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.lang.reflect.Array;

public class Registration extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        //Dropdown for the User Type
        Spinner myspinner = (Spinner) findViewById(R.id.spinnerUserType);

        ArrayAdapter<String> myadapter = new ArrayAdapter<String>(Registration.this,
                android.R.layout.simple_list_item_1 , getResources().getStringArray(R.array.type));
        myadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        myspinner.setAdapter(myadapter);

        //Dropdown ends
        
    }


}
