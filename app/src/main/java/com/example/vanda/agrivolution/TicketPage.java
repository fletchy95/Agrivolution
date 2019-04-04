package com.example.vanda.agrivolution;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.app.ListActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.TextView;

import java.util.ArrayList;


public class TicketPage extends ListActivity
{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_page);
    }
}