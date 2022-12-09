package com.example.cybertemple;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Date;

public class DetailActivity extends AppCompatActivity {

    TextView event_name,event_description,count;
    Button edit,back;
    ImageView add_count,success,fail;
    Event event;
    int kind; //(0-All, 1-Ongoing, 2-Completed)


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        event_name=findViewById(R.id.event_name);
        event_description=findViewById(R.id.event_description);
        count=findViewById(R.id.count);
        add_count=findViewById(R.id.event_add_count);
        success=findViewById(R.id.success);
        fail=findViewById(R.id.fail);
        edit=findViewById(R.id.edit);
        back=findViewById(R.id.back_list2);


        getAndSetIntentData();

        //Count+1
        add_count.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBOpenHelper db= new DBOpenHelper(DetailActivity.this);
                event.setCount(event.getCount()+1);
                int result=db.editCount(event);
                if (result != -1) {

                    Toast toast=Toast.makeText(DetailActivity.this, "+1", Toast.LENGTH_SHORT);
                    toast.show();
                    getAndSetIntentData();
                } else {
                    Toast toast=Toast.makeText(DetailActivity.this, "Failed!", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

        //Set event as succeed
        success.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBOpenHelper db= new DBOpenHelper(DetailActivity.this);
                event.setStatus(1);
                Date end_date=new Date();
                event.setEndDate(end_date);
                System.out.println(event);
                int result=db.editStatus(event);
                if (result != -1) {
                    //Jump to summary page
                    Intent intent= new Intent(DetailActivity.this,SummaryActivity.class);
                    intent.putExtra("event",event);
                    intent.putExtra("kind",kind);
                    startActivity(intent);

                } else {
                    Toast toast=Toast.makeText(DetailActivity.this, "Failed to change status!", Toast.LENGTH_SHORT);
                    toast.show();
                }

            }
        });

        //Set event as failed
        fail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBOpenHelper db= new DBOpenHelper(DetailActivity.this);
                event.setStatus(2);
                Date end_date=new Date();
                event.setEndDate(end_date);
                System.out.println(event);
                int result=db.editStatus(event);
                if (result != -1) {
                    //Jump to summary page
                    Intent intent= new Intent(DetailActivity.this,SummaryActivity.class);
                    intent.putExtra("event",event);
                    intent.putExtra("kind",kind);
                    startActivity(intent);

                } else {
                    Toast toast=Toast.makeText(DetailActivity.this, "Failed to change status!", Toast.LENGTH_SHORT);
                    toast.show();
                }

            }
        });

        //Jump to edit page
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(DetailActivity.this, EditActivity.class);
                intent.putExtra("event",event);
                intent.putExtra("kind",kind);
                startActivity(intent);
            }
        });

        //Back to list page
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(DetailActivity.this,ListActivity.class);
                intent.putExtra("kind",kind);
                startActivity(intent);
            }
        });




    }

    //Display data
    void getAndSetIntentData(){
        if(getIntent().hasExtra("event")){
            event=(Event)getIntent().getSerializableExtra("event");

            event_name.setText(String.valueOf(event.getName()));
            event_description.setText(String.valueOf(event.getDescription()));
            count.setText(String.valueOf(event.getCount()));
            kind=getIntent().getIntExtra("kind",0);

        }
    }






}