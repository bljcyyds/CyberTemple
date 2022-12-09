package com.example.cybertemple;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    SearchView searchView;
    ListView listView;
    MyAdapter myAdapter;
    List<Event> events;
    private DBOpenHelper helper;
    int kind=0;
    FloatingActionButton search_back_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        search_back_list = findViewById(R.id.search_back_list);

        listView = (ListView) findViewById(R.id.listview);
        searchView = (SearchView) findViewById(R.id.search);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(SearchActivity.this,"search："+query,
                        Toast.LENGTH_SHORT).show();

                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                searchData(newText);
                return true;
            }
        });

        //Back to list page
        search_back_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SearchActivity.this, ListActivity.class);
                startActivity(intent);
            }
        });

    }


    protected void searchData(String query) {
        helper = new DBOpenHelper(this);
        try{
            showSearchData(query);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Event event = events.get(position);
                int status = event.getStatus();
                Intent intent;
                //Ongoing event
                if (status == 0) {
                    intent = new Intent(SearchActivity.this, DetailActivity.class);
                }
                //Completed event
                else {
                    intent = new Intent(SearchActivity.this, SummaryActivity.class);
                }
                intent.putExtra("event", event);
                intent.putExtra("kind", kind);
                SearchActivity.this.startActivity(intent);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int
                    position, long id) {
                AlertDialog dialog;
                AlertDialog.Builder builder = new AlertDialog.Builder(SearchActivity.this)
                        .setMessage("Do you want to delete this event?")
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Event event = events.get(position);
                                if (helper.deleteData(event.getId())) {
                                    events.remove(position);
                                    myAdapter.notifyDataSetChanged();
                                    Toast.makeText(SearchActivity.this, "Delete successfully",
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
                dialog = builder.create();
                dialog.show();
                return true;
            }
        });
    }

    private void showSearchData(String query) throws ParseException {
        events = new ArrayList<>();
        if(query.equals("")){
            events= new ArrayList<>();
        }
        else {
            events = helper.search(query);
        }
        myAdapter = new MyAdapter(this, events);
        listView.setAdapter(myAdapter);

    }
}