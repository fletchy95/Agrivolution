package com.example.vanda.agrivolution;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageButton;

public class Dashboard extends AppCompatActivity {

    private TextView Logout;
    ImageButton btnSubmitIssue;
    ImageButton btnCommunity;
    ImageButton btnShop;
    ImageButton btnHeatMap;
    ImageButton btnEncyclopedia;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        btnCommunity = findViewById(R.id.btnCommunity);
        btnSubmitIssue = findViewById(R.id.btnSubmitIssue);
        btnShop = findViewById(R.id.btnShop);
        btnHeatMap = findViewById(R.id.btnHeatMap);
        btnEncyclopedia = findViewById(R.id.btnEncyclopedia);
        // Will setup button Listeners once the pages for these have been created.
        btnEncyclopedia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Dashboard.this, placeholder.class));
            }
        });
        btnHeatMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Dashboard.this, placeholder.class));
            }
        });
        btnSubmitIssue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Dashboard.this, placeholder.class));
            }
        });
        btnShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Dashboard.this, placeholder.class));
            }
        });
        btnCommunity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Dashboard.this, placeholder.class));
            }
        });
        Logout = (TextView)findViewById(R.id.tvLogout);
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this, Login.class);
                startActivity(intent);
                Toast.makeText(Dashboard.this," Logged out Successfully !", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
