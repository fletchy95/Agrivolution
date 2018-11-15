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
    EditText etxtFarmName;
    EditText etxtFarmAddress;
    EditText etxtYOE;
    EditText etxtSpecialization;
    Spinner spnUserSelection;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        //Dropdown for the User Type
        spnUserSelection = (Spinner) findViewById(R.id.spinnerUserType);
        spnUserSelection.setOnItemSelectedListener(this);
        ArrayAdapter<String> myadapter = new ArrayAdapter<String>(Registration.this, android.R.layout.simple_list_item_1 , getResources().getStringArray(R.array.type));
        myadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnUserSelection.setAdapter(myadapter);
        etxtFarmName = findViewById(R.id.farmName);
        etxtFarmAddress = findViewById(R.id.farmAddress);
        etxtYOE = findViewById(R.id.yoe);
        etxtSpecialization = findViewById(R.id.specialization);
        //Dropdown ends
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {
        String selected = spnUserSelection.getSelectedItem().toString();
        if(selected.equals("Farmer"))
        {
            etxtSpecialization.setVisibility(View.GONE);
            etxtFarmName.setVisibility(View.VISIBLE);
            etxtFarmAddress.setVisibility(View.VISIBLE);
            etxtYOE.setVisibility(View.VISIBLE);
        }
        else if(selected.equals("Expert"))
        {
            etxtFarmName.setVisibility(View.GONE);
            etxtFarmAddress.setVisibility(View.GONE);
            etxtYOE.setVisibility(View.GONE);
            etxtSpecialization.setVisibility(View.VISIBLE);
            etxtYOE.setVisibility(View.VISIBLE);
        }
        else if (selected.equals("Other"))
        {
            etxtFarmName.setVisibility(View.GONE);
            etxtFarmAddress.setVisibility(View.GONE);
            etxtSpecialization.setVisibility(View.GONE);
            etxtYOE.setVisibility(View.GONE);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent)
    {
        // Do Nothing
    }

}
