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

import java.util.ArrayList;

public class SubmitIssue extends AppCompatActivity
{
    Button btnPicture;
    Button btnSubmitIssue;
    Button btnCancelTicket;
    EditText sub_desc;
    EditText sub_farmName;
    EditText sub_farmAddress;
    EditText locationDetails;
    EditText sub_addInfo;
    static ArrayList issueList = new ArrayList();
    private static final int CAMERA_REQUEST = 1888;
    private ImageView imgUpload;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;

    @TargetApi(23)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_ticket);
        btnSubmitIssue = findViewById(R.id.btbSubmitTicket);
        btnCancelTicket = findViewById(R.id.btnCancel);
        imgUpload = this.findViewById(R.id.ImgUpload);
        btnPicture = this.findViewById(R.id.btnPicture);
        sub_desc = findViewById(R.id.sub_Disc);
        sub_farmName = findViewById(R.id.farmName);
        sub_farmAddress = findViewById(R.id.farmAddress);
        this.locationDetails = findViewById(R.id.locationDetail);
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

    private void addToList()
    {
        String list[] = new String[5];
        list[0] = sub_farmName.toString();
        list[1] = sub_farmAddress.toString();
        list[2] = sub_addInfo.toString();
        list[3] = locationDetails.toString();
        list[4] = sub_desc.toString();
        issueList.add(list);
    }

    private boolean validate()
    {
        sub_farmAddress = findViewById(R.id.farmAddress);
        locationDetails = findViewById(R.id.locationDetail);
        String desc = sub_desc.getText().toString();
        String farmName = sub_farmName.getText().toString();
        String farmAddress = sub_farmAddress.getText().toString();
        String hypothesis = locationDetails.getText().toString();
        String addInfo = sub_addInfo.getText().toString();
        return !desc.equals("") && !farmName.equals("") && !farmAddress.equals("") && !hypothesis.equals("") && !addInfo.equals("");
    }
    public static ArrayList sendList()
    {
        return issueList;
    }
}