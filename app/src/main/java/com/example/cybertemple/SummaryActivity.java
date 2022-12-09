package com.example.cybertemple;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;

public class SummaryActivity extends AppCompatActivity {

    TextView name,count,startDate,endDate,duration;
    Button back_list;
    ImageView image;

    Event event;
    int kind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);


        name=findViewById(R.id.finalName);
        count=findViewById(R.id.finalCount);
        startDate=findViewById(R.id.finalStartDate);
        endDate=findViewById(R.id.finalEndDate);
        back_list=findViewById(R.id.back_list);
        duration=findViewById(R.id.duration);
        image=findViewById(R.id.imageView);

        getAndSetIntentData();

        //Back to list page
        back_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SummaryActivity.this, ListActivity.class);
                intent.putExtra("kind",kind);
                startActivity(intent);

            }
        });

    }


    void getAndSetIntentData(){
        if(getIntent().hasExtra("event")){
            event=(Event)getIntent().getSerializableExtra("event");
            name.setText("Name: "+String.valueOf(event.getName()));
            count.setText("Blessing number: "+String.valueOf(event.getCount()));
            SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            startDate.setText("Start Date: "+dataFormat.format(event.getStartDate()));
            endDate.setText("End Date: "+dataFormat.format(event.getEndDate()));
            long interval=event.getEndDate().getTime()-event.getStartDate().getTime();
            long day=interval/1000/60/60/24;
            long hour=interval/1000/60/60-day*24;
            long minute=interval/1000/60-day*24-hour*60;
            long second=interval/1000-day*24-hour*60-minute*60;
            duration.setText("Duration: "+day+" days "+hour+" hours "+minute+" minutes "+second+" seconds");
            if(event.getStatus()==0){
                image.setVisibility(View.GONE);
            }
            else if(event.getStatus()==1){
                image.setVisibility(View.VISIBLE);
                image.setImageResource(R.drawable.success);
            }
            else if(event.getStatus()==2){
                image.setVisibility(View.VISIBLE);
                image.setImageResource(R.drawable.fail);
            }
            kind=getIntent().getIntExtra("kind",0);

        }
    }
}