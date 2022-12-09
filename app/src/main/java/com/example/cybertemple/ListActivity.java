package com.example.cybertemple;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;


public class ListActivity extends AppCompatActivity {
    private DBOpenHelper helper;

    ListView listView;
    MyAdapter myAdapter;

    FloatingActionButton add_button,back_main,search_button;
    Button all,ongoing,completed;

    List<Event> events;
    int kind=0; //(0-All, 1-Ongoing, 2-Completed) Display all event by default

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        listView = (ListView) findViewById(R.id.listview);
        add_button = findViewById(R.id.add_button);
        back_main=findViewById(R.id.back_main);
        search_button=findViewById(R.id.search);
        all=findViewById(R.id.all);
        ongoing=findViewById(R.id.ongoing);
        completed=findViewById(R.id.completed);


        if(getIntent().hasExtra("kind")){
            kind=getIntent().getIntExtra("kind",0);
        }
        showData(kind);

        //Search
        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ListActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });

        //Back to main page
        back_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ListActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        //turn to add event page
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListActivity.this, AddActivity.class);
                startActivity(intent);

            }
        });

        //Display all event
        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showData(0);

            }
        });

        //Display ongoing data
        ongoing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showData(1);

            }
        });

        //Display completed data
        completed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showData(2);

            }
        });



    }

    //Display data based on status, 0--all， 1--Ongoing（status=0）， 2--Completed(status=1 or 2)
    protected void showData(int kind) {
        helper= new DBOpenHelper(this);
        try {
            showQueryData(kind);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent,View view,int position,long id){
                Event event = events.get(position);
                int status=event.getStatus();
                Intent intent;
                //Jump to detail page if event is ongoing
                if(status==0){
                    intent = new Intent(ListActivity.this, DetailActivity.class);
                }
                //Jump to summary page if event is completed
                else{
                    intent = new Intent(ListActivity.this, SummaryActivity.class);
                }
                intent.putExtra("event", event);
                intent.putExtra("kind",kind);
                ListActivity.this.startActivity(intent);
            }
        });

        //Delete event
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int
                    position, long id) {
                AlertDialog dialog;
                AlertDialog.Builder builder = new AlertDialog.Builder( ListActivity.this)
                        .setMessage("Do you want to delete this event?")
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Event event = events.get(position);
                                if(helper.deleteData(event.getId())){
                                    events.remove(position);
                                    myAdapter.notifyDataSetChanged();
                                    Toast.makeText(ListActivity.this,"Delete successfully",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                dialog =  builder.create();
                dialog.show();
                return true;
            }
        });

    }

    //Get data based on status, 0--all， 1--Ongoing（status=0）， 2--Completed(status=1 or 2)
    private void showQueryData(int kind) throws ParseException {
        events=new ArrayList<>();
        //All
        if(kind==0){
            events = helper.query();
        }
        //Ongoing
        else if(kind==1){
            events = helper.query1();
        }
        //Completed
        else if(kind==2){
            events = helper.query2();
        }
        myAdapter = new MyAdapter(this, events);
        listView.setAdapter(myAdapter);

    }

}