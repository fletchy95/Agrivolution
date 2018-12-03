package com.example.vanda.agrivolution;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Button;

public class Encyclopedia extends AppCompatActivity
{
    ImageButton btnPest1;
    ImageButton btnPest2;
    ImageButton btnPest3;
    ImageButton btnPest4;
    Button btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encyclopedia);
        btnPest1 = findViewById(R.id.btnPest1);
        btnPest2 = findViewById(R.id.btnPest2);
        btnPest3 = findViewById(R.id.btnPest3);
        btnPest4 = findViewById(R.id.btnPest4);
        btnBack = findViewById(R.id.btnBack);
        btnPest1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(Encyclopedia.this, encyclopediaDetail.class);
                startActivity(intent);
            }
        });
        btnPest2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(Encyclopedia.this, encyclopediaDetail.class);
                startActivity(intent);
            }
        });
        btnPest3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(Encyclopedia.this, encyclopediaDetail.class);
                startActivity(intent);
            }
        });
        btnPest4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(Encyclopedia.this, encyclopediaDetail.class);
                startActivity(intent);
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(Encyclopedia.this, Dashboard.class);
                startActivity(intent);
            }
        });
    }
}