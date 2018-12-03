package com.example.vanda.agrivolution;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Profile extends AppCompatActivity {

    private ImageView profilePic;
    private TextView profFirstName;
    private TextView profLastName;
    private TextView profMobile;
    private TextView profEmail;
    private TextView profUserType;
    private TextView profFarmName;
    private TextView profFarmAdd;
    private TextView profYearsOfExp;
    private TextView profSpecialization;

    private Button profUpdate;

    private FirebaseAuth firebaseauthObj;
    private FirebaseDatabase firebasedatabaseObj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profilePic = findViewById(R.id.ivprofPic);
        profFirstName = findViewById(R.id.tvfName);
        profLastName = findViewById(R.id.tvlname);
        profMobile = findViewById(R.id.tvMobile);
        profEmail = findViewById(R.id.tvEmail);
        profUserType = findViewById(R.id.tvUserType);
        profFarmName = findViewById(R.id.tvFarmName);
        profFarmAdd = findViewById(R.id.tvFarmAdd);
        profYearsOfExp = findViewById(R.id.tvYoe);
        profSpecialization = findViewById(R.id.tvSpec);
        profUpdate =findViewById(R.id.btnEdit);

        firebaseauthObj = FirebaseAuth.getInstance();
        firebasedatabaseObj = FirebaseDatabase.getInstance();

        DatabaseReference databaserefObj = firebasedatabaseObj.getReference(firebaseauthObj.getUid());
        databaserefObj.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    UserProfile userprofObj = dataSnapshot.getValue(UserProfile.class);
                    profFirstName.setText(userprofObj.getFirstName());
                    profLastName.setText(userprofObj.getLastName());
                    profMobile.setText(userprofObj.getMobile());
                    profEmail.setText(userprofObj.getEmail());
                    profUserType.setText(userprofObj.getUserType());
                    profFarmName.setText(userprofObj.getFarmName());
                    profFarmAdd.setText(userprofObj.getFarmAddress());
                    profYearsOfExp.setText(userprofObj.getYearsOfExperience());
                    profSpecialization.setText(userprofObj.getSpecialization());

//                if(profUserType.setText(userprofObj.getUserType()).equals("Farmer"))
//                {
//                    profSpecialization.setVisibility(View.GONE);
//                    profFarmName.setVisibility(View.VISIBLE);
//                    profFarmAdd.setVisibility(View.VISIBLE);
//                    profYearsOfExp.setVisibility(View.VISIBLE);
//                }
//                else if(profUserType.equals("Expert"))
//                {
//                    profFarmName.setVisibility(View.GONE);
//                    profFarmAdd.setVisibility(View.GONE);
//                    profSpecialization.setVisibility(View.VISIBLE);
//                    profYearsOfExp.setVisibility(View.VISIBLE);
//                }
//                else
//                {
//                    profFarmName.setVisibility(View.GONE);
//                    profFarmAdd.setVisibility(View.GONE);
//                    profSpecialization.setVisibility(View.GONE);
//                    profYearsOfExp.setVisibility(View.GONE);
//                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Profile.this, databaseError.getCode(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}
