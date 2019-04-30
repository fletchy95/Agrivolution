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
import com.google.firebase.auth.FirebaseUser;
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
    private String userID;
    private FirebaseAuth auth;
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

//      //  getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        firebaseauthObj = FirebaseAuth.getInstance();
//        firebasedatabaseObj = FirebaseDatabase.getInstance();
//
//        final DatabaseReference databaserefObj = firebasedatabaseObj.getReference(firebaseauthObj.getUid());

        firebaseauthObj = FirebaseAuth.getInstance();
        firebasedatabaseObj = FirebaseDatabase.getInstance();
        final DatabaseReference databaseRefObj = firebasedatabaseObj.getReference();
        FirebaseUser user = firebaseauthObj.getCurrentUser();
        userID = user.getUid();
        auth = FirebaseAuth.getInstance();

        databaseRefObj.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    UserProfile userProf = new UserProfile();
                    userProf.setFirstName(ds.child(userID).getValue(UserProfile.class).getFirstName());
                    userProf.setLastName(ds.child(userID).getValue(UserProfile.class).getLastName());
                    userProf.setMobile(ds.child(userID).getValue(UserProfile.class).getMobile());
                    userProf.setEmail(ds.child(userID).getValue(UserProfile.class).getEmail());
                    userProf.setUserType(ds.child(userID).getValue(UserProfile.class).getUserType());
                    userProf.setFarmName(ds.child(userID).getValue(UserProfile.class).getFarmName());
                    userProf.setFarmAddress(ds.child(userID).getValue(UserProfile.class).getFarmAddress());
                    userProf.setYearsOfExperience(ds.child(userID).getValue(UserProfile.class).getYearsOfExperience());
                    userProf.setSpecialization(ds.child(userID).getValue(UserProfile.class).getSpecialization());

                newFname.setText(userProf.getFirstName());
                newLname.setText(userProf.getLastName());
                newMob.setText(userProf.getMobile());
                newEmail.setText(userProf.getEmail());
                newUserType.setText(userProf.getUserType());
                newFarmName.setText(userProf.getFarmName());
                newFarmAdd.setText(userProf.getFarmAddress());
                newYoe.setText(userProf.getYearsOfExperience());
                newSpec.setText(userProf.getSpecialization());

                    RetrievedUserType  = userProf.getUserType();
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

                FirebaseDatabase firebaseDatabaseObj = FirebaseDatabase.getInstance();
                DatabaseReference ref = firebaseDatabaseObj.getReference();
                UserProfile userProfileObj = new UserProfile(firstname, lastname, Mobile,email,farmName, farmAdd, yoe, specialization,type);
                ref.child("User").child(auth.getUid()).setValue(userProfileObj);


//                UserProfile obj = new UserProfile(firstname, lastname, Mobile,email,farmName, farmAdd, yoe, specialization,type);
//                databaseRefObj.setValue(obj);
                Toast.makeText(UpdateProfile.this,"Profile Updated successfully!",Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
