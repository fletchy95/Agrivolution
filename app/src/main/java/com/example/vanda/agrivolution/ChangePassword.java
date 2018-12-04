package com.example.vanda.agrivolution;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangePassword extends AppCompatActivity {

    private EditText newPassword;
    private Button UpdatePassword;
    private FirebaseUser firebaseUserObj;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        newPassword = findViewById(R.id.etNewPassword);
        UpdatePassword = findViewById(R.id.btnChangePwd);

        firebaseUserObj = FirebaseAuth.getInstance().getCurrentUser();
        UpdatePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newUserPassword = newPassword.getText().toString();
                firebaseUserObj.updatePassword(newUserPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(ChangePassword.this,"Password Updated successfully!",Toast.LENGTH_SHORT).show();
                            finish();
                        }else{
                            Toast.makeText(ChangePassword.this,"Password Updated Failed!",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

    }
}
