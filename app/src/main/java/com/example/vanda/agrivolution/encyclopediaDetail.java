package com.example.vanda.agrivolution;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class encyclopediaDetail extends AppCompatActivity {

    private ImageView PestImage;
    private TextView PestName;
    private TextView PestType;
    private TextView PestHost;
    private TextView PestDesc;
    private TextView PestSymptoms;
    private TextView PestTrigger;
    private TextView PestPrevMeasure;
    private TextView PestBioControl;
    private TextView PestChemControl;

    private String Key;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encyclopedia_detail);
        Intent intent = getIntent();
        Key = intent.getStringExtra("Key");


        mDatabase = FirebaseDatabase.getInstance().getReference().child("Encyclopedia").child(Key);
        setupUIViews();

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                PestEncyclopedia pest = dataSnapshot.getValue(PestEncyclopedia.class);
                PestName.setText(pest.getName());
                PestType.setText("Type: "+pest.getType());
                PestHost.setText("Hosts: "+pest.getHost());
                PestDesc.setText("Description"+pest.getDescription());
                PestSymptoms.setText("Symptoms: "+pest.getSymptom());
                PestTrigger.setText("Trigger"+pest.getTrigger());
                PestPrevMeasure.setText("Preventive Measures: "+pest.getPreventiveMeasure());
                PestBioControl.setText("Biological Control: "+pest.getBiologicalControl());
                PestChemControl.setText(("Chemical Control: "+pest.getChemicalControl()));
                Picasso.get().load(pest.getImageUrl()).into(PestImage);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(encyclopediaDetail.this, databaseError.getCode(),Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void setupUIViews(){
        PestImage = findViewById(R.id.PestImageView);
        PestName = findViewById(R.id.PestNameView);
        PestType = findViewById(R.id.PestTypeView);
        PestHost = findViewById(R.id.PestHostView);
        PestDesc = findViewById(R.id.PestDescView);
        PestSymptoms = findViewById(R.id.PestSymptomsView);
        PestTrigger = findViewById(R.id.PestTriggerView);
        PestPrevMeasure = findViewById(R.id.PestTriggerView);
        PestBioControl = findViewById(R.id.PestBioView);
        PestChemControl = findViewById(R.id.PestChemView);

    }
}