package com.example.cybertemple;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class EditActivity extends AppCompatActivity {
    EditText newName,newDescription;
    Button editButton,list_back_detail;

    Event event;
    int kind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        newName=findViewById(R.id.newName);
        newDescription=findViewById(R.id.newDescription);
        editButton=findViewById(R.id.update_button);
        list_back_detail=findViewById(R.id.back_detail_button);


        getAndSetIntentData();

        //Back to detail page
        list_back_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(EditActivity.this, DetailActivity.class);
                intent.putExtra("event", event);
                intent.putExtra("kind",kind);
                startActivity(intent);
            }
        });

        //Edit event
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBOpenHelper db=new DBOpenHelper(EditActivity.this);
                event.setName(newName.getText().toString().trim());
                event.setDescription(newDescription.getText().toString().trim());


                //Name should not be null
                if(event.getName().isEmpty()){
                    Toast toast=Toast.makeText(EditActivity.this, "Name should not be null!", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }
                else{
                    int result=db.editData(event);
                    if (result != -1) {

                        Toast toast=Toast.makeText(EditActivity.this, "Update successfully!", Toast.LENGTH_SHORT);
                        toast.show();
                        Intent intent= new Intent(EditActivity.this, DetailActivity.class);
                        intent.putExtra("event",event);
                        intent.putExtra("kind",kind);
                        startActivity(intent);
                    } else {
                        Toast toast=Toast.makeText(EditActivity.this, "Failed to update!", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }

            }
        });




    }

    //Display original data
    void getAndSetIntentData(){
        if(getIntent().hasExtra("event")){
            event=(Event)getIntent().getSerializableExtra("event");
            newName.setText(String.valueOf(event.getName()));
            newDescription.setText(String.valueOf(event.getDescription()));

            kind=getIntent().getIntExtra("kind",0);

        }

    }
}