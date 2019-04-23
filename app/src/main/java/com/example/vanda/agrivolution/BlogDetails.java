package com.example.vanda.agrivolution;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class BlogDetails extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private DatabaseReference UserDb;
    private DatabaseReference CommentDb;
    private FirebaseAuth firebaseauthObj;
    private FirebaseRecyclerAdapter firebaseRecyclerAdapter;
    private RecyclerView mBlogList;
    private String tComment;
    private String UserId;
    private String postedBy;

    private TextView Display;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_details);
        firebaseauthObj = FirebaseAuth.getInstance();
        UserId = firebaseauthObj.getUid();
        UserDb = FirebaseDatabase.getInstance().getReference().child("User").child(UserId);
        UserDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String fname = dataSnapshot.child("firstName").getValue().toString();
                String lname = dataSnapshot.child("lastName").getValue().toString();
                postedBy = fname.concat(" ").concat(lname);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        Display = findViewById(R.id.tv_displayContent);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Blog");
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
        public EditText edtComment;
        public Button btnPostComment;
        public LinearLayout root;
        public String TicketId;
        public String userID;

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
            edtComment = itemView.findViewById(R.id.blog_comments);
            btnPostComment = itemView.findViewById(R.id.blog_postComment);


            btnPostComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tComment = edtComment.getText().toString();
                    CommentDb = FirebaseDatabase.getInstance().getReference().child("Comments").child(TicketId);
                    DatabaseReference newComment = CommentDb.push();
                    Comments c = new Comments(tComment,postedBy,UserId);
                    newComment.setValue(c);
                    Toast.makeText(BlogDetails.this,"New Comment Added!",Toast.LENGTH_SHORT).show();
                    edtComment.setText("");
                }
            });
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
            TicketId = key;
        }
        public void setUrl(String url) {
            Picasso.get().load(url).into(ImgBlogImage);
        }
        public void setUserID(String id){ userID = id;}
    }
    public void fetch(){
        FirebaseRecyclerOptions<Blog> options = null;
        try {
            options =
                    new FirebaseRecyclerOptions.Builder<Blog>().setQuery(mDatabase, new SnapshotParser<Blog>() {
                        @NonNull
                        @Override
                        public Blog parseSnapshot(@NonNull DataSnapshot snapshot) {
                            return new Blog(snapshot.child("ticketTitle").getValue().toString(),
                                    snapshot.child("description").getValue().toString(),
                                    snapshot.child("farmName").getValue().toString(),
                                    snapshot.child("farmArea").getValue().toString(),
                                    snapshot.child("farmState").getValue().toString(),
                                    snapshot.child("date").getValue().toString(),
                                    snapshot.child("status").getValue().toString(),
                                    snapshot.child("postedBy").getValue().toString(),
                                    snapshot.child("imageURL").getValue().toString(),
                                    snapshot.child("userId").getValue().toString(),
                                    snapshot.getKey()
                            );
                        }
                    }).build();
        }
        catch(Exception e) {
            System.out.println(e);
        }

        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Blog, BlogDetails.BlogViewHolder>(options) {

            @Override
            protected void onBindViewHolder(@NonNull BlogDetails.BlogViewHolder holder, int position, @NonNull Blog model) {
                holder.setTitle(model.getTicketTitle());
                holder.setDesc(model.getDescription());
                holder.setLocation(model.getFarmName(),model.getFarmArea(),model.getFarmState());
                holder.setDate(model.getDate());
                holder.setStatus(model.getStatus());
                holder.setPostedBy(model.getPostedBy());
                holder.setUrl(model.getImageURL());
                holder.setKey(model.getKey());
                holder.setUserID(model.getUserId());

                holder.root.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(BlogDetails.this,String.valueOf(position),Toast.LENGTH_LONG).show();
                    }
                });
            }
            @NonNull
            @Override
            public BlogDetails.BlogViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.blog_row, viewGroup, false);
                return new BlogViewHolder(view);
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
