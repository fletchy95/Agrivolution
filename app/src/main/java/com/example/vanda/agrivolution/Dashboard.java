package com.example.vanda.agrivolution;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;
import android.widget.ImageButton;
import android.view.Menu;

import com.google.firebase.auth.FirebaseAuth;

public class Dashboard extends AppCompatActivity {

    ImageButton btnSubmitIssue;
    ImageButton btnCommunity;
    ImageButton btnShop;
    ImageButton btnHeatMap;
    ImageButton btnEncyclopedia;
    ImageButton btnTicketPage;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        firebaseAuth = FirebaseAuth.getInstance();

        ImageButton btnAccount = findViewById(R.id.btnAccount);
        final PopupMenu dropDownAccount = new PopupMenu(getApplicationContext(), btnAccount);
        final Menu menu = dropDownAccount.getMenu();
        menu.add(0,0,0,"Account Details");
        menu.add(0,1,1,"Change Password");
        menu.add(0,2,2,"Logout");
        dropDownAccount.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch(item.getItemId())
                {
                    case 0:
                        startActivity(new Intent(Dashboard.this , Profile.class));
                        return true;
                    case 1:
                        startActivity(new Intent(Dashboard.this, ChangePassword.class));
                        return true;
                    case 2:
                        firebaseAuth.signOut();
                        finish();
                        Intent intent2 = new Intent(Dashboard.this, Login.class);
                        startActivity(intent2);
                        Toast.makeText(Dashboard.this," Logged out Successfully !", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });
        btnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dropDownAccount.show();
            }
        });
        btnCommunity = findViewById(R.id.btnCommunity);
        btnSubmitIssue = findViewById(R.id.btnSubmitIssue);
        btnShop = findViewById(R.id.btnShop);
        btnHeatMap = findViewById(R.id.btnHeatMap);
        btnEncyclopedia = findViewById(R.id.btnEncyclopedia);
        btnTicketPage = findViewById(R.id.btnTicketPage);
        // Will setup button Listeners once the pages for these have been created.
        btnEncyclopedia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Dashboard.this, Encyclopedia.class));
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
                startActivity(new Intent(Dashboard.this, SubmitIssue.class));
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
        btnTicketPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Dashboard.this, TicketPage.class));
            }
        });    }
}
