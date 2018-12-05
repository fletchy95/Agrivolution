package com.example.vanda.agrivolution;

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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UpdateProfile extends AppCompatActivity {

    private EditText newFname, newLname, newMob, newFarmName, newFarmAdd, newYoe, newSpec;
    private TextView newEmail, newUserType;
    private Button save;
    private FirebaseAuth firebaseauthObj;
    private FirebaseDatabase firebasedatabaseObj;
    String RetrievedUserType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        newFname = findViewById(R.id.etFnameUpdate);
        newLname = findViewById(R.id.etLnameUpdate);
        newMob = findViewById(R.id.etMobUpdate);
        newEmail = findViewById(R.id.tvEmailUpdate);
        newUserType = findViewById(R.id.tvUserTypeUpdate);
        newFarmName = findViewById(R.id.etFarmNameUpdate);
        newFarmAdd = findViewById(R.id.etFarmAddUpdate);
        newYoe = findViewById(R.id.etYoeUpdate);
        newSpec = findViewById(R.id.etSpecUpdate);
        save = findViewById(R.id.btnSave);

      //  getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        firebaseauthObj = FirebaseAuth.getInstance();
        firebasedatabaseObj = FirebaseDatabase.getInstance();

        final DatabaseReference databaserefObj = firebasedatabaseObj.getReference(firebaseauthObj.getUid());
        databaserefObj.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserProfile userprofObj = dataSnapshot.getValue(UserProfile.class);
                newFname.setText(userprofObj.getFirstName());
                newLname.setText(userprofObj.getLastName());
                newMob.setText(userprofObj.getMobile());
                newEmail.setText(userprofObj.getEmail());
                newUserType.setText(userprofObj.getUserType());
                newFarmName.setText(userprofObj.getFarmName());
                newFarmAdd.setText(userprofObj.getFarmAddress());
                newYoe.setText(userprofObj.getYearsOfExperience());
                newSpec.setText(userprofObj.getSpecialization());

                RetrievedUserType  = userprofObj.getUserType();
                if(RetrievedUserType.equals("Farmer"))
                {
                    newSpec.setVisibility(View.GONE);
                    newFarmName.setVisibility(View.VISIBLE);
                    newFarmAdd.setVisibility(View.VISIBLE);
                    newYoe.setVisibility(View.VISIBLE);
                }
                else if(RetrievedUserType.equals("Expert"))
                {
                    newFarmName.setVisibility(View.GONE);
                    newFarmAdd.setVisibility(View.GONE);
                    newSpec.setVisibility(View.VISIBLE);
                    newYoe.setVisibility(View.VISIBLE);
                }
                else
                {
                    newFarmName.setVisibility(View.GONE);
                    newFarmAdd.setVisibility(View.GONE);
                    newSpec.setVisibility(View.GONE);
                    newYoe.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(UpdateProfile.this, databaseError.getCode(),Toast.LENGTH_SHORT).show();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  UserProfile userprofObj = new UserProfile();
                String farmName = "";
                String farmAdd = "";
                String yoe = "";
                String specialization = "";
                String firstname = newFname.getText().toString();
                String lastname = newLname.getText().toString();
                String Mobile = newMob.getText().toString();
                String email = newEmail.getText().toString();
                String type = newUserType.getText().toString();
//                String retrievedUserType = userprofObj.getUserType();
//                String retrievedEmail = userprofObj.getEmail();
                if(RetrievedUserType.equals("Farmer"))
                {
                    farmName = newFarmName.getText().toString();
                    farmAdd = newFarmAdd.getText().toString();
                    yoe = newYoe.getText().toString();
                }
                else if(RetrievedUserType.equals("Expert"))
                {
                    yoe = newYoe.getText().toString();
                    specialization = newSpec.getText().toString();
                }
                UserProfile obj = new UserProfile(firstname, lastname, Mobile,email,farmName, farmAdd, yoe, specialization,type);
                databaserefObj.setValue(obj);
                Toast.makeText(UpdateProfile.this,"Profile Updated successfully!",Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
