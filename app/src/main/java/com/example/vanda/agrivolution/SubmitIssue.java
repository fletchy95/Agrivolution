package com.example.vanda.agrivolution;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.content.pm.PackageManager;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.ArrayList;
public class SubmitIssue extends AppCompatActivity
{
    Button btnSubmitIssue;
    Button btnCancelTicket;
    ImageButton ticketImgBtn;
    EditText description;
    EditText farmName;
    EditText farmAddress;
    EditText locationDetail;
    EditText ticketTitle;
    EditText optionalContact;
    EditText date;
    EditText email;
    private StorageReference mStorage;
    private Uri ticketURL =null;
    private ProgressDialog tDialog;
    static ArrayList issueList = new ArrayList();
    private static final int CAMERA_REQUEST = 1888;
    private ImageView imgUpload;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    private String tFramName;
    private String tFarmAdd;
    private String tLoc;
    private String tDate;
    private String temail;
    private String tContact;
    private String tDesc;
    private FirebaseAuth firebaseauthObj;
    private DatabaseReference mDatabase;
    private static final int GALLERY_REQUEST =1;

    @TargetApi(23)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_ticket);
        setupUIViews();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Tickets");
        ticketImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent,GALLERY_REQUEST);
            }
        });
        btnSubmitIssue.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(validate())
                {
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
        mStorage = FirebaseStorage.getInstance().getReference();

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
    /*protected void onActivityResult(int requestCode ,int resultCode, Intent data)
    {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imgUpload.setImageBitmap(photo);
        }
    }*/
    public void createTicket(){
        //DatabaseReference newTicket = mDatabase.push();
        //Ticket ticketObj = new Ticket(tFramName,tFarmAdd,tLoc,tTicketTitle,tDate,temail,tContact,tDesc);
        //newTicket.setValue(ticketObj);
        tDialog.setMessage("Sending new Ticket");

        String FarmName = farmName.getText().toString().trim();
        String FarmAddress = farmAddress.getText().toString().trim();
        String LocationDetail = locationDetail.getText().toString().trim();
        String TicketTitle = ticketTitle.getText().toString().trim();
        String Date = date.getText().toString().trim();
        String Email = email.getText().toString().trim();
        String OptionalContact = optionalContact.getText().toString().trim();
        String Description = description.getText().toString().trim();

        if(!TextUtils.isEmpty(FarmName)&& !TextUtils.isEmpty(FarmAddress)&& ticketURL!= null){
            tDialog.show();
            StorageReference filepath = mStorage.child("Encyclopedia_Images").child(ticketURL.getLastPathSegment());
            filepath.putFile(ticketURL).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Task<Uri> downloadUrlTask = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                    downloadUrlTask.addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Uri downloadUri = uri;
                            String imageUrl = downloadUri.toString();
                            DatabaseReference newTicket = mDatabase.push();
                            Ticket ticket = new Ticket(FarmName,FarmAddress,LocationDetail,TicketTitle,Date,Email,OptionalContact,Description, imageUrl);
                            newTicket.setValue(ticket);
                            tDialog.dismiss();
                            Toast.makeText(SubmitIssue.this,"New Ticket Submitted!",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SubmitIssue.this,Dashboard.class));
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(SubmitIssue.this, "Ticket Failed", Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            tDialog.dismiss();
            Toast.makeText(SubmitIssue.this, "Please add image, and/or any other missed information", Toast.LENGTH_SHORT).show();
        }
    }
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GALLERY_REQUEST && resultCode == RESULT_OK){
            ticketURL = data.getData();
            CropImage.activity(ticketURL)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1,1)
                    .start(this);

        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {

                Uri resultUri = result.getUri();
                ticketImgBtn.setImageURI(resultUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }
    // TODO validate must be implemented, low Priority, will complete later. For now it will auto accept.
    private void setupUIViews()
    {
        btnSubmitIssue = findViewById(R.id.btbSubmitTicket);
        btnCancelTicket = findViewById(R.id.btnCancel);
        imgUpload = findViewById(R.id.ImgUpload);
        ticketImgBtn = findViewById(R.id.imageSelect_ticket);
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
        String tTicketTitle = ticketTitle.getText().toString();
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