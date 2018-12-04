package com.example.vanda.agrivolution;

import android.content.Intent;
import android.support.annotation.NonNull;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
    String fname, lname, mob, email, pwd, cpwd,farmNam,farmadd,yearsofexp, spec, selected;

    Boolean passwordMatch = true;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        setupUIViews();

        auth = FirebaseAuth.getInstance();

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
                    String email = Email.getText().toString().trim();
                    String pwd = Password.getText().toString().trim();
                        auth.createUserWithEmailAndPassword(email,pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    sendUserData();
                                    Toast.makeText(Registration.this,"Registration Successful !",Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(Registration.this, Login.class));
                                } else{
                                    try {
                                        throw task.getException();
                                    }
                                    catch(FirebaseAuthWeakPasswordException weakPassowrdEx){
                                        Toast.makeText(Registration.this,"Weak Password !",Toast.LENGTH_SHORT).show();
                                    }
                                    catch(FirebaseAuthInvalidCredentialsException invalidIdEx){
                                        Toast.makeText(Registration.this,"Invalid Credentials !",Toast.LENGTH_SHORT).show();
                                    }
                                    catch(FirebaseAuthUserCollisionException duplicateUserEx){
                                        Toast.makeText(Registration.this,"User Account already Exists !",Toast.LENGTH_SHORT).show();
                                    } catch (Exception e) {
                                        Toast.makeText(Registration.this,"Registration Failed !",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        });

                }else if (!passwordMatch){
                    Toast.makeText(Registration.this, "Passwords Don't match !", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(Registration.this,"Please Enter all the details !",Toast.LENGTH_SHORT).show();
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
        FirstName = findViewById(R.id.etFname);
        LastName = findViewById(R.id.etLastName);
        Mobile = findViewById(R.id.etMob);
        Email = findViewById(R.id.etEmail);
        Password = findViewById(R.id.etPassword);
        ConfirmPassword = findViewById(R.id.etCpassword);
        FarmName = findViewById(R.id.farmName);
        FarmAddress = findViewById(R.id.farmAddress);
        YOE = findViewById(R.id.yoe);
        Specialization = findViewById(R.id.specialization);
        UserSelection = findViewById(R.id.spinnerUserType);
        Register = findViewById(R.id.btnRegister);
        ExistingUser = findViewById(R.id.tvExistingUser);
    }

    private Boolean validate(){

         fname = FirstName.getText().toString();
         lname = LastName.getText().toString();
         mob = Mobile.getText().toString();
         email = Email.getText().toString();
         pwd = Password.getText().toString();
         cpwd = ConfirmPassword.getText().toString();

        if (fname.isEmpty() || lname.isEmpty() || mob.isEmpty() || email.isEmpty() || pwd.isEmpty() || cpwd.isEmpty()) {
            return false;
        }
        passwordMatch = pwd.equals(cpwd);
        if (!passwordMatch) {
            return false;
        }

        if(selected.equals("Farmer")){
            farmNam = FarmName.getText().toString();
            farmadd = FarmAddress.getText().toString();
            yearsofexp = YOE.getText().toString();
            if(farmNam.isEmpty() || farmadd.isEmpty() || yearsofexp.isEmpty()){
                return false;
            }else{
                spec = "";
                return true;
            }
        }
        else if(selected.equals("Expert")){
            spec = Specialization.getText().toString();
            yearsofexp = YOE.getText().toString();
            if(spec.isEmpty() || yearsofexp.isEmpty()){
                return false;
            }
            else{
                farmNam = "";
                farmadd = "";
                return true;
            }
        }
        else if (selected.equals("Select User Type")){
            return false;
        }
        else if(selected.equals("Other")){
            farmNam = "";
            farmadd = "";
            spec = "";
            return true;
        }

        return true;
    }

    public void sendUserData(){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference ref = firebaseDatabase.getReference(auth.getUid());
        UserProfile userProfile = new UserProfile(fname, lname, mob, email, farmNam, farmadd, yearsofexp, spec, selected);
        ref.setValue(userProfile);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {
        selected = UserSelection.getSelectedItem().toString();
        ResetItemSelectionFields();

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
            Specialization.setVisibility(View.VISIBLE);
            YOE.setVisibility(View.VISIBLE);
        }
        else
        {
            FarmName.setVisibility(View.GONE);
            FarmAddress.setVisibility(View.GONE);
            Specialization.setVisibility(View.GONE);
            YOE.setVisibility(View.GONE);
        }
    }

    private void ResetItemSelectionFields() {
        Specialization.setText("");
        FarmName.setText("");
        FarmAddress.setText("");
        YOE.setText("");
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent)
    {
        // Do Nothing
    }

}
