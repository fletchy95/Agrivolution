package com.example.vanda.agrivolution;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.app.ListActivity;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class TicketPage extends AppCompatActivity
{
    private DatabaseReference mDatabase;
    private FirebaseRecyclerAdapter firebaseRecyclerAdapter;
    private RecyclerView mTicketList;
    private String userID;
    private FirebaseAuth firebaseauthObj;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_page);
        firebaseauthObj = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseauthObj.getCurrentUser();
        userID = user.getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Tickets").child(userID);
        mTicketList = findViewById(R.id.ticket_list);
        mTicketList.setLayoutManager(new LinearLayoutManager(this));
        mTicketList.setHasFixedSize(true);
        fetch();
    }
    public class TicketViewHolder extends RecyclerView.ViewHolder{
        public TextView txtTicketTitle;
        public TextView txtTicketDescription;
        public TextView txtFarmName;
        public TextView txtDate;
        public TextView txtStatus;
        public ImageView ImgTicketImage;
        public Button BtnCloseTicket;
        public LinearLayout root;
        public String Key;

        public TicketViewHolder(@NonNull View itemView) {
            super(itemView);
            root = itemView.findViewById(R.id.list_troot);

            txtTicketTitle = itemView.findViewById(R.id.ticket_title);
            txtTicketDescription = itemView.findViewById(R.id.ticket_Description);
            txtFarmName = itemView.findViewById(R.id.ticket_farmName);
            txtDate = itemView.findViewById(R.id.ticket_date);
            txtStatus = itemView.findViewById(R.id.ticket_status);
            ImgTicketImage = itemView.findViewById(R.id.ticket_pestImage);
            BtnCloseTicket = itemView.findViewById(R.id.close_ticket);


            BtnCloseTicket.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try{
                        mDatabase.child(Key).child("status").setValue("Closed");

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });
        }

        public void setTitle(String title){
            txtTicketTitle.setText("Ticket Title: "+title);
        }
        public void setDesc(String desc){
            txtTicketDescription.setText("Description: "+desc);
        }
        public void setFarmName(String farmName){
            txtFarmName.setText("Farm: "+farmName);
        }
        public void setDate(String date){
            txtDate.setText("Posted On: "+date);
        }
        public void setStatus(String status){
            txtStatus.setText("Status: "+status);
        }
        public void setKey(String key){
            Key = key;
        }
        public void setUrl(String url) {
            Picasso.get().load(url).into(ImgTicketImage);
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
                                    snapshot.child("date").getValue().toString(),
                                    snapshot.child("status").getValue().toString(),
                                    snapshot.child("imageURL").getValue().toString(),
                                    snapshot.getKey()
                            );
                        }
                    }).build();
        }
        catch(Exception e) {
            System.out.println(e);
        }

        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Ticket, TicketViewHolder>(options) {

            @Override
            protected void onBindViewHolder(@NonNull TicketViewHolder holder, int position, @NonNull Ticket model) {
                holder.setTitle(model.getTicketTitle());
                holder.setDesc(model.getDescription());
                holder.setFarmName(model.getFarmName());
                holder.setDate(model.getDate());
                holder.setStatus(model.getStatus());
                holder.setUrl(model.getImageURL());
                holder.setKey(model.getKey());
                if(model.getStatus().equals("closed")){
                holder.BtnCloseTicket.setVisibility(View.GONE);
            }

                holder.root.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(TicketPage.this,String.valueOf(position),Toast.LENGTH_LONG).show();
                    }
                });
            }
            @NonNull
            @Override
            public TicketViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.ticket_row, viewGroup, false);
                return new TicketViewHolder(view);
            }
        };
        mTicketList.setAdapter(firebaseRecyclerAdapter);
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