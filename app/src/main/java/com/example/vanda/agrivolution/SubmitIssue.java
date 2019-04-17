package com.example.vanda.agrivolution;
import android.annotation.TargetApi;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.Manifest;
import android.content.pm.PackageManager;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.SQLException;
import java.util.ArrayList;
public class SubmitIssue extends AppCompatActivity
{
    Button btnPicture;
    Button btnSubmitIssue;
    Button btnCancelTicket;
    EditText description;
    EditText farmName;
    EditText farmAddress;
    EditText locationDetail;
    EditText ticketTitle;
    EditText optionalContact;
    EditText date;
    EditText email;
    static ArrayList issueList = new ArrayList();
    private static final int CAMERA_REQUEST = 1888;
    private ImageView imgUpload;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;

    private String  tFramName, tFarmAdd, tLoc, tTicketTitle, tDate, temail, tContact, tDesc;
            //userId ;
    private FirebaseAuth firebaseauthObj;
    private DatabaseReference mDatabase;

    @TargetApi(23)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_ticket);
        setupUIViews();

      //  firebaseauthObj = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Tickets");

       // FirebaseUser user = firebaseauthObj.getCurrentUser();
      //  userId = user.getUid();

        btnPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkSelfPermission(Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA},
                            MY_CAMERA_PERMISSION_CODE);
                } else {
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                }
            }
        });
        btnSubmitIssue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate())
                {
//                    try {
//                        addToList();
//                    } catch (SQLException e) {
//                        e.printStackTrace();
//                    }
                    createTicket();
                    Toast.makeText(SubmitIssue.this, "Issue Submitted!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SubmitIssue.this, Dashboard.class));
                }
                else
                {
                    Toast.makeText(SubmitIssue.this,"Please Enter all the details !",Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnCancelTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(SubmitIssue.this, "Ticket Cancelled!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(SubmitIssue.this, Dashboard.class));
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Camera Permission Granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new
                        Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            } else {
                Toast.makeText(this, "Camera Permission Denied", Toast.LENGTH_LONG).show();
            }

        }
    }
    protected void onActivityResult(int requestCode ,int resultCode, Intent data)
    {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imgUpload.setImageBitmap(photo);
        }
    }
    public void createTicket(){
        DatabaseReference newTicket = mDatabase.push();
        Ticket ticketObj = new Ticket(tFramName,tFarmAdd,tLoc,tTicketTitle,tDate,temail,tContact,tDesc);
        newTicket.setValue(ticketObj);
    }
//    private void addToList() throws SQLException
//    {
//        String ticket[] = new String[8];
//        ticket[0] = farmName.toString();
//        ticket[1] = farmAddress.toString();
//        ticket[2] = locationDetail.toString();
//        ticket[3] = ticketTitle.toString();
//        ticket[4] = date.toString();
//        ticket[5] = email.toString();
//        ticket[6] = optionalContact.toString();
//        ticket[7] = description.toString();
//        MySqlUsage.submitTicket(ticket);
//    }
    // TODO validate must be implemented, low Priority, will complete later. For now it will auto accept.
        private void setupUIViews(){
            btnSubmitIssue = findViewById(R.id.btbSubmitTicket);
            btnCancelTicket = findViewById(R.id.btnCancel);
            btnPicture = this.findViewById(R.id.btnPicture);
            imgUpload = this.findViewById(R.id.ImgUpload);

            farmName = findViewById(R.id.farmName);
            farmAddress = findViewById(R.id.farmAddress);
            locationDetail = findViewById(R.id.locationDetail);
            ticketTitle = findViewById(R.id.ticketTitle);
            date = findViewById(R.id.date);
            email = findViewById(R.id.email);
            optionalContact = findViewById(R.id.OptionalContact);
            description = findViewById(R.id.description);

        }
    private boolean validate()
    {
        tFramName = farmName.getText().toString();
        tFarmAdd = farmAddress.getText().toString();
        tLoc = locationDetail.getText().toString();
        tTicketTitle= ticketTitle.getText().toString();
        tDate = date.getText().toString();
        temail = email.getText().toString();
        tContact = optionalContact.getText().toString();
        tDesc = description.getText().toString();


        if (tFramName.isEmpty() || tFarmAdd.isEmpty() || tTicketTitle.isEmpty() || tDate.isEmpty() || tDesc.isEmpty()) {
            return false;
        }else{
            return true;
        }

    }
}