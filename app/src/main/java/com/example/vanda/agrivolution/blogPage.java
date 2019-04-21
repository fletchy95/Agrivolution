package com.example.vanda.agrivolution;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class blogPage extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private FirebaseRecyclerAdapter firebaseRecyclerAdapter;
    private RecyclerView mBlogList;
    private String userID;
    private FirebaseAuth firebaseauthObj;
    private TextView Display;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_page);
        Display = findViewById(R.id.tv_displayContent);
        firebaseauthObj = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseauthObj.getCurrentUser();
        userID = user.getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Tickets").child(userID);
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!(dataSnapshot.getValue()==null)){
                    Display.setVisibility(View.GONE);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        mBlogList = findViewById(R.id.blog_list);
        mBlogList.setLayoutManager(new LinearLayoutManager(this));
        mBlogList.setHasFixedSize(true);
        fetch();
    }
    public class BlogViewHolder extends RecyclerView.ViewHolder{
        public TextView txtBlogTitle;
        public TextView txtBlogDescription;
        public TextView txtLocation;
        public TextView txtDate;
        public TextView txtPostedBy;
        public TextView txtStatus;
        public ImageView ImgBlogImage;
        public LinearLayout root;
        public String Key;

        public BlogViewHolder(@NonNull View itemView) {
            super(itemView);
            root = itemView.findViewById(R.id.list_broot);

            txtBlogTitle = itemView.findViewById(R.id.blog_title);
            txtBlogDescription = itemView.findViewById(R.id.blog_Description);
            txtLocation = itemView.findViewById(R.id.blog_location);
            txtPostedBy = itemView.findViewById(R.id.blog_postedby);
            txtDate = itemView.findViewById(R.id.blog_date);
            txtStatus = itemView.findViewById(R.id.blog_status);
            ImgBlogImage = itemView.findViewById(R.id.blog_pestImage);
        }

        public void setTitle(String title){
            txtBlogTitle.setText(title);
        }
        public void setDesc(String desc){
            txtBlogDescription.setText("Description: "+desc);
        }
        public void setLocation(String fName, String fArea, String fState ){
            txtLocation.setText("Location: "+fName+", "+ fArea+", "+fState);
        }
        public void setDate(String date){
            txtDate.setText("Posted On: "+date);
        }
        public void setPostedBy(String name){
            txtPostedBy.setText("Posted By:" +name );
        }
        public void setStatus(String status){
            txtStatus.setText("Status: "+status);
        }
        public void setKey(String key){
            Key = key;
        }
        public void setUrl(String url) {
            Picasso.get().load(url).into(ImgBlogImage);
        }
    }
    public void fetch(){
        FirebaseRecyclerOptions<Ticket> options = null;
        try {
            options =
                    new FirebaseRecyclerOptions.Builder<Ticket>().setQuery(mDatabase, new SnapshotParser<Ticket>() {
                        @NonNull
                        @Override
                        public Ticket parseSnapshot(@NonNull DataSnapshot snapshot) {
                            return new Ticket(snapshot.child("ticketTitle").getValue().toString(),
                                    snapshot.child("description").getValue().toString(),
                                    snapshot.child("farmName").getValue().toString(),
                                    snapshot.child("farmArea").getValue().toString(),
                                    snapshot.child("farmState").getValue().toString(),
                                    snapshot.child("date").getValue().toString(),
                                    snapshot.child("status").getValue().toString(),
                                    snapshot.child("postedBy").getValue().toString(),
                                    snapshot.child("imageURL").getValue().toString(),
                                    snapshot.getKey()
                            );
                        }
                    }).build();
        }
        catch(Exception e) {
            System.out.println(e);
        }

        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Ticket, blogPage.BlogViewHolder>(options) {

            @Override
            protected void onBindViewHolder(@NonNull blogPage.BlogViewHolder holder, int position, @NonNull Ticket model) {
                    holder.setTitle(model.getTicketTitle());
                    holder.setDesc(model.getDescription());
                    holder.setLocation(model.getFarmName(),model.getFarmArea(),model.getFarmState());
                    holder.setDate(model.getDate());
                    holder.setStatus(model.getStatus());
                    holder.setPostedBy(model.getPostedBy());
                    holder.setUrl(model.getImageURL());
                    holder.setKey(model.getKey());

                    holder.root.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(blogPage.this,String.valueOf(position),Toast.LENGTH_LONG).show();
                        }
                    });
            }
            @NonNull
            @Override
            public blogPage.BlogViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.blog_row, viewGroup, false);
                return new blogPage.BlogViewHolder(view);
            }
        };
        mBlogList.setAdapter(firebaseRecyclerAdapter);
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
