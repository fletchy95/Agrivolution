package com.example.vanda.agrivolution;
import android.os.Bundle;
import android.content.Intent;
import android.widget.TextView;
import android.app.Activity;

public class SingleListItem extends Activity{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_ticket_single);

        TextView txtProduct = (TextView) findViewById(R.id.ticketName);

        Intent i = getIntent();
        // getting attached intent data
        String product = i.getStringExtra("number");
        // displaying selected product name
        txtProduct.setText(product);

    }
}