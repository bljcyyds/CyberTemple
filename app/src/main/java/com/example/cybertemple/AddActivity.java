package com.example.cybertemple;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Date;

public class AddActivity extends AppCompatActivity {

    EditText Name, Description;
    Button Add_button, back_list1;

    Date Start_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);


        Name=findViewById(R.id.Name);
        Description=findViewById(R.id.Description);
        Add_button=findViewById(R.id.Add_button);
        back_list1=findViewById(R.id.back_list_button);

        //Back to list page
        back_list1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AddActivity.this, ListActivity.class);
                startActivity(intent);
            }
        });

        //Add event function
        Add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Start_date=new Date();
                DBOpenHelper db= new DBOpenHelper(AddActivity.this);
                Event event=new Event();
                event.setName(Name.getText().toString().trim());
                event.setDescription(Description.getText().toString().trim());
                event.setStartDate(Start_date);
                event.setStatus(0);
                event.setCount(0);

                //Name should not be null
                if(event.getName().isEmpty()){
                    Toast toast=Toast.makeText(AddActivity.this, "Name should not be null!", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }
                else{
                    long result=db.add(event);
                    if (result != -1) {

                        Toast toast=Toast.makeText(AddActivity.this, "Add successfully!", Toast.LENGTH_SHORT);
                        toast.show();
                        Intent intent= new Intent(AddActivity.this, ListActivity.class);
                        startActivity(intent);
                    } else {
                        Toast toast=Toast.makeText(AddActivity.this, "Failed to add!", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }

            }
        });

    }
}