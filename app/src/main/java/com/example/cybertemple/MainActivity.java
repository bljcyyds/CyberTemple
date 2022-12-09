package com.example.cybertemple;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private ImageButton golist;
    private ImageView image;
    private ImageButton btnleft;
    private ImageButton btnright;
    private int images[]={
            R.drawable.kun,
            R.drawable.guake,
            R.drawable.guanyu,
            R.drawable.yangchaoyue,
            R.drawable.fo,
            R.drawable.yesu,
            R.drawable.xym
    };
    private int index=0;
    private ImageButton mbtmuyu;
    private SoundPool soundPool;
    private int soundID;

    private void initsound(){
        soundPool = new SoundPool.Builder().build();
        soundID = soundPool.load(this, R.raw.punch, 1);
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        image=findViewById(R.id.image);
        btnleft=findViewById(R.id.btnleft);
        btnright=findViewById(R.id.btnright);


        btnleft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Previous picture
                if(index > 0 && index<= images.length){
                    index = index-1;
                }
                image.setImageResource(images[index]);
                System.out.println(index);

            }
        });

        btnright.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Next picture
                if(index >= 0 && index < images.length-1){
                    index = index+1;
                }
                image.setImageResource(images[index]);
                System.out.println(index);

            }
        });

        golist = (ImageButton) findViewById(R.id.btn_list);
        golist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ListActivity.class);
                startActivity(intent);
            }
        });

        mbtmuyu = findViewById(R.id.btnmuyu);
        initsound();
        mbtmuyu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playSound();
                showToast(view);
            }
        });

    }
    private void playSound() {
        soundPool.play(
                soundID,
                0.5f,
                0.5f,
                0,
                0,
                1
        );
    }
    public void showToast(View view){
        Toast.makeText(this, "MEIRT+1", Toast.LENGTH_SHORT).show();
    }




}