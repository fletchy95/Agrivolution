package com.example.vanda.agrivolution;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    private EditText UserId;
    private EditText Password;
    private Button Login;
    private TextView signUp;

    private FirebaseAuth auth;
    private int counter =5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        UserId = findViewById(R.id.etuserId);
        Password = findViewById(R.id.etPassword);
        Login = findViewById(R.id.btnLogin);
        signUp = findViewById(R.id.tvRegister);

        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        if(user!= null){
            //finish();
            Intent intent = new Intent(Login.this, Dashboard.class);
        }

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                validate(UserId.getText().toString(), Password.getText().toString());
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Registration.class);
                startActivity(intent);

            }
        });

    }
    private void validate(String userEmail, String userPassword){
        if(!(userEmail.isEmpty()) || !(userPassword.isEmpty())){
            auth.signInWithEmailAndPassword(userEmail,userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(Login.this,"Logged in Successfully !",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Login.this, Dashboard.class));
                    }else{
                        Toast.makeText(Login.this,"Invalid Credentials !",Toast.LENGTH_SHORT).show();
                        counter--;

                        if (counter == 0){
                            Login.setEnabled(false);
                        }

                    }
                }
            });
        }
        else{
            Toast.makeText(Login.this,"Enter all the details !",Toast.LENGTH_SHORT).show();
        }

//        if((userEmail.equals("admin@fairfield.edu")) && (userPassword.equals("1234"))){
//            Intent intent = new Intent(Login.this, Dashboard.class);
//            startActivity(intent);
//            Toast.makeText(this,"Login Successful !", Toast.LENGTH_SHORT).show();
//        }
//        else {
//            counter--;
//
//            if (counter == 0){
//                Login.setEnabled(false);
//            }
//        }

    }
}
