package com.example.vanda.agrivolution;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.lang.reflect.Array;

public class Registration extends AppCompatActivity implements AdapterView.OnItemSelectedListener
{
    //private EditText fname, lname, mob, email, password, confirmpassword;
    //private Button register;
    // private TextView login;
    EditText farmName;
    EditText farmAddress;
    EditText yoe;
    EditText specialization;
    Spinner myspinner;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        //Dropdown for the User Type
        myspinner = (Spinner) findViewById(R.id.spinnerUserType);
        myspinner.setOnItemSelectedListener(this);
        ArrayAdapter<String> myadapter = new ArrayAdapter<String>(Registration.this,
                android.R.layout.simple_list_item_1 , getResources().getStringArray(R.array.type));
        myadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        myspinner.setAdapter(myadapter);
        farmName = findViewById(R.id.farmName);
        farmAddress = findViewById(R.id.farmAddress);
        yoe = findViewById(R.id.yoe);
        specialization = findViewById(R.id.specialization);

        //Dropdown ends

        //login.setOnClickListener(new View.OnClickListener() {
          //  @Override
            //public void onClick(View v) {
              //  Intent intent = new Intent(Registration.this, Login.class);
                //startActivity(intent);
            //}
        //});
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {
        String selected = myspinner.getSelectedItem().toString();
        if(selected.equals("Farmer"))
        {
            specialization.setVisibility(View.GONE);
            farmName.setVisibility(View.VISIBLE);
            farmAddress.setVisibility(View.VISIBLE);
            yoe.setVisibility(View.VISIBLE);
        }
        else if(selected.equals("Expert"))
        {
            farmName.setVisibility(View.GONE);
            farmAddress.setVisibility(View.GONE);
            yoe.setVisibility(View.GONE);
            specialization.setVisibility(View.VISIBLE);
            yoe.setVisibility(View.VISIBLE);
        }
        else if (selected.equals("Other"))
        {
            farmName.setVisibility(View.GONE);
            farmAddress.setVisibility(View.GONE);
            specialization.setVisibility(View.GONE);
            yoe.setVisibility(View.GONE);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent)
    {

    }

}
