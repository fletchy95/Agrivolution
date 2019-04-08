package com.example.vanda.agrivolution;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class addEncyclopediaData extends AppCompatActivity {
    private ImageButton mSelectImage;
    private EditText mName;
    private EditText mType;
    private EditText mHost;
    private EditText mDescription;
    private EditText mSymptom;
    private EditText mTrigger;
    private EditText mPreventiveMeasure;
    private EditText mBiologicalControl;
    private EditText mChemicalControl;
    private Button mSubmit;

    private Uri mImageUri =null;
    private StorageReference mStorage;
    private DatabaseReference mDatabase;
    private ProgressDialog mProgress;

    private static final int GALLERY_REQUEST =1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_encyclopedia_data);


        mSelectImage =(ImageButton) findViewById(R.id.imageSelect);
        mName = (EditText)findViewById(R.id.nameField);
        mType = (EditText)findViewById(R.id.typeField);
        mHost = (EditText)findViewById(R.id.hostField);
        mDescription = (EditText)findViewById(R.id.descField);
        mSymptom = (EditText)findViewById(R.id.symptomsField);
        mTrigger = (EditText)findViewById(R.id.triggerField);
        mPreventiveMeasure = (EditText)findViewById(R.id.prevMeasureField);
        mBiologicalControl =(EditText)findViewById(R.id.bioField);
        mChemicalControl = (EditText)findViewById(R.id.chemField);
        mSubmit = (Button) findViewById(R.id.submitButton);

        mStorage = FirebaseStorage.getInstance().getReference();
        mProgress = new ProgressDialog(this);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Encyclopedia");

        mSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent,GALLERY_REQUEST);
            }
        });

        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPosting();
            }
        });
    }

    private void startPosting() {
       mProgress.setMessage("Posting to Encyclopedia");


        String PestName = mName.getText().toString().trim();
        String PestType = mType.getText().toString().trim();
        String PestHost = mHost.getText().toString().trim();
        String PestDesc =mDescription.getText().toString().trim();
        String PestSymptom = mSymptom.getText().toString().trim();
        String PestTrigger = mTrigger.getText().toString().trim();
        String PestPrevMeasure = mPreventiveMeasure.getText().toString().trim();
        String PestBioControl =mBiologicalControl.getText().toString().trim();
        String PestChemControl =mChemicalControl.getText().toString().trim();


        if(!TextUtils.isEmpty(PestName)&& !TextUtils.isEmpty(PestType)&& mImageUri!= null){
            mProgress.show();
            StorageReference filepath = mStorage.child("Encyclopedia_Images").child(mImageUri.getLastPathSegment());
            filepath.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Task<Uri> downloadUrlTask = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                    downloadUrlTask.addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Uri downloadUri = uri;
                            String imageUrl = downloadUri.toString();
                            DatabaseReference newPost = mDatabase.push();
                            PestEncyclopedia newPest = new PestEncyclopedia(PestName,PestType,PestHost,PestDesc,PestSymptom,PestTrigger,PestPrevMeasure,PestBioControl,PestChemControl, imageUrl);
                            newPost.setValue(newPest);
                            mProgress.dismiss();
                            Toast.makeText(addEncyclopediaData.this,"Added new Pathogen Successfully !",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(addEncyclopediaData.this,Encyclopedia.class));
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(addEncyclopediaData.this, "Upload Fail", Toast.LENGTH_SHORT).show();
                }
            });
        }else{
          mProgress.dismiss();
            Toast.makeText(addEncyclopediaData.this, "Please add image, name and the type of the pathogen", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode== GALLERY_REQUEST && resultCode == RESULT_OK){
            mImageUri = data.getData();
            CropImage.activity(mImageUri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1,1)
                    .start(this);

        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {

                Uri resultUri = result.getUri();
                mSelectImage.setImageURI(resultUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }
}
