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
    ArrayList issueList;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        issueList = new ArrayList();
        // storing string resources into Array
        // here you store the array of string you got from the database

        // Binding Array to ListAdapter
        this.setListAdapter(new ArrayAdapter<String>(this, R.layout.activity_ticket_page, R.id.listView));
        // refer the ArrayAdapter Document in developer.android.com
        ListView lv = getListView();

        // listening to single list item on click
        lv.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                // selected item
                String num = ((TextView) view).getText().toString();

                // Launching new Activity on selecting single List Item
                //Intent i = new Intent(getApplicationContext(), SingleListItem.class);
                // sending data to new activity
                //i.putExtra("number", num);
                //startActivity(i);
            }
        });
    }
}