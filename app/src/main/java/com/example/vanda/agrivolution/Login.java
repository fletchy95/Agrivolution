package com.example.vanda.agrivolution;

import android.content.Intent;
import android.icu.text.IDNA;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Login extends AppCompatActivity {

    private EditText UserId;
    private EditText Password;
    private Button Login;
    private TextView signUp;

    private int counter =5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        UserId = (EditText)findViewById(R.id.etuserId);
        Password = (EditText)findViewById(R.id.etPassword);
        Login = (Button)findViewById(R.id.btnLogin);
        signUp = (TextView)findViewById(R.id.tvRegister);

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
        if((userEmail.equals("Admin")) && (userPassword.equals("1234"))){
            Intent intent = new Intent(Login.this, Dashboard.class);
            startActivity(intent);
        }
        else {
            counter--;

            if (counter == 0){
                Login.setEnabled(false);
            }
        }

    }
}
