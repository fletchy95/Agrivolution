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
import android.widget.Toast;

import java.lang.reflect.Array;

public class Registration extends AppCompatActivity implements AdapterView.OnItemSelectedListener
{
    EditText FirstName;
    EditText LastName;
    EditText Mobile;
    EditText Email;
    EditText Password;
    EditText ConfirmPassword;
    EditText FarmName;
    EditText FarmAddress;
    EditText YOE;
    EditText Specialization;
    Spinner UserSelection;
    Button Register;
    TextView ExistingUser;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        setupUIViews();

        //Dropdown for the User Type
        UserSelection.setOnItemSelectedListener(this);
        ArrayAdapter<String> myadapter = new ArrayAdapter<String>(Registration.this, android.R.layout.simple_list_item_1 , getResources().getStringArray(R.array.type));
        myadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        UserSelection.setAdapter(myadapter);
        //Dropdown ends

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(validate()) {
                   //Upload data to database
                   Toast.makeText(Registration.this,"Registration Successful !",Toast.LENGTH_SHORT).show();
               }
            }
        });

        ExistingUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Registration.this, Login.class);
                startActivity(intent);
            }
        });
    }
    private void setupUIViews(){
        FirstName = (EditText)findViewById(R.id.etFname);
        LastName = (EditText)findViewById(R.id.etLastName);
        Mobile = (EditText)findViewById(R.id.etMob);
        Email = (EditText)findViewById(R.id.etEmail);
        Password = (EditText)findViewById(R.id.etPassword);
        ConfirmPassword = (EditText)findViewById(R.id.etCpassword);
        FarmName = findViewById(R.id.farmName);
        FarmAddress = findViewById(R.id.farmAddress);
        YOE = findViewById(R.id.yoe);
        Specialization = findViewById(R.id.specialization);
        UserSelection = (Spinner) findViewById(R.id.spinnerUserType);
        Register = (Button)findViewById(R.id.btnRegister);
        ExistingUser = (TextView)findViewById(R.id.tvExistingUser);

    }

    private Boolean validate(){
        Boolean Result = false;

        String fname = FirstName.getText().toString();
        String lname = LastName.getText().toString();
        String Mob = Mobile.getText().toString();
        String email = Email.getText().toString();
        String pwd = Password.getText().toString();
        String cpwd = ConfirmPassword.getText().toString();

//        if(fname.isEmpty() || lname.isEmpty()||Mob.isEmpty() || email.isEmpty() || pwd.isEmpty() || cpwd.isEmpty()){
//            Toast.makeText(this,"Please enter all the details !", Toast.LENGTH_SHORT).show();
//        }

        if(!(pwd.equals(cpwd))){
            Toast.makeText(this,"Passwords Don't match !", Toast.LENGTH_SHORT).show();
        }else{
            Result = true;
        }
        return Result;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {
        String selected = UserSelection.getSelectedItem().toString();
        if(selected.equals("Farmer"))
        {
            Specialization.setVisibility(View.GONE);
            FarmName.setVisibility(View.VISIBLE);
            FarmAddress.setVisibility(View.VISIBLE);
            YOE.setVisibility(View.VISIBLE);
        }
        else if(selected.equals("Expert"))
        {
            FarmName.setVisibility(View.GONE);
            FarmAddress.setVisibility(View.GONE);
            YOE.setVisibility(View.GONE);
            Specialization.setVisibility(View.VISIBLE);
            YOE.setVisibility(View.VISIBLE);
        }
        else if (selected.equals("Other"))
        {
            FarmName.setVisibility(View.GONE);
            FarmAddress.setVisibility(View.GONE);
            Specialization.setVisibility(View.GONE);
            YOE.setVisibility(View.GONE);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent)
    {
        // Do Nothing
    }

}
