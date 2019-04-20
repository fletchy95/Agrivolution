package com.example.vanda.agrivolution;

import android.content.Intent;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class Encyclopedia extends AppCompatActivity
{
    private Button Add;
    private DatabaseReference mDatabase;
    private FirebaseRecyclerAdapter firebaseRecyclerAdapter;
    private String userID;
    private FirebaseAuth firebaseauthObj;
    private RecyclerView mPestList;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encyclopedia);
        firebaseauthObj = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseauthObj.getCurrentUser();
        userID = user.getUid();

        mDatabase = FirebaseDatabase.getInstance().getReference().child("User").child(userID);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserProfile userProf = dataSnapshot.getValue(UserProfile.class);
                String userType = userProf.getUserType();
                if(userType.equals("Farmer")){
                    Add.setVisibility(View.GONE);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Add = findViewById(R.id.buttonAdd);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Encyclopedia");
        mPestList = findViewById(R.id.pest_list);
        mPestList.setLayoutManager(new LinearLayoutManager(this));
        mPestList.setHasFixedSize(true);
        fetch();

        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Encyclopedia.this, addEncyclopediaData.class);
                startActivity(intent);

            }
        });
    }

    public class PestViewHolder extends RecyclerView.ViewHolder{
        public TextView txtPestName;
        public TextView txtPestType;
        public ImageView ImgPestImage;
        public LinearLayout root;
        public String Key;

        public PestViewHolder(@NonNull View itemView) {
            super(itemView);
            root = itemView.findViewById(R.id.list_root);
            txtPestName = itemView.findViewById(R.id.post_pestName);
            txtPestType = itemView.findViewById(R.id.post_pestType);
            ImgPestImage = itemView.findViewById(R.id.post_pestImage);

            ImgPestImage.setOnClickListener(v -> {
                Intent intent = new Intent(Encyclopedia.this, encyclopediaDetail.class);
                String key = mDatabase.child("Encyclopedia").push().getKey();
                intent.putExtra("Key",Key);
                startActivity(intent);
            });
        }
        public void setName(String name){
            txtPestName.setText(name);
        }
        public void setType(String type){
            txtPestType.setText(type);
        }
        public void setKey(String key){
            Key = key;
        }

        public void setUrl(String url) {
            Picasso.get().load(url).into(ImgPestImage);
        }

    }
    public void fetch(){
        FirebaseRecyclerOptions<PestEncyclopedia> options = null;
        try {
             options =
                    new FirebaseRecyclerOptions.Builder<PestEncyclopedia>().setQuery(mDatabase, new SnapshotParser<PestEncyclopedia>() {
                        @NonNull
                        @Override
                        public PestEncyclopedia parseSnapshot(@NonNull DataSnapshot snapshot) {
                            return new PestEncyclopedia(snapshot.child("name").getValue().toString(),
                                    snapshot.child("type").getValue().toString(),
                                    snapshot.child("imageUrl").getValue().toString(),
                                    snapshot.getKey()
                            );
                        }
                    }).build();
        }
        catch(Exception e) {
            System.out.println(e);
        }

        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<PestEncyclopedia, PestViewHolder>(options) {

            @Override
            protected void onBindViewHolder(@NonNull PestViewHolder holder, int position, @NonNull PestEncyclopedia model) {
                holder.setName(model.getName());
                holder.setType(model.getType());
                holder.setUrl(model.getImageUrl());
                holder.setKey(model.getKey());
                holder.root.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(Encyclopedia.this,String.valueOf(position),Toast.LENGTH_LONG).show();
                    }
                });
            }

            @NonNull
            @Override
            public PestViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.pest_row, viewGroup, false);
                return new PestViewHolder(view);
            }
        };
        mPestList.setAdapter(firebaseRecyclerAdapter);
    }
    @Override
    protected void onStart() {
        super.onStart();
        firebaseRecyclerAdapter.startListening();
    }
    @Override
    protected void onStop() {
        super.onStop();
        firebaseRecyclerAdapter.stopListening();
    }
}