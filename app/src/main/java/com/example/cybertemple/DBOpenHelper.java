package com.example.cybertemple;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DBOpenHelper extends SQLiteOpenHelper {
    private SQLiteDatabase db;
    SimpleDateFormat sdf = new SimpleDateFormat("EE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);

    DBOpenHelper(Context context) {
        super(context, "Event.db", null, 1);
        db = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS Event(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT NOT NULL," +
                "description TEXT," +
                "startDate TEXT," +
                "endDate TEXT," +
                "count INTEGER," +
                "status INTEGER)"); //0-ongoing, 1-succeed, 2-fail
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Event");
        onCreate(db);
    }
    //Add event
    public long add(Event event){

        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name",event.getName());
        values.put("description",event.getDescription());
        values.put("startDate",event.getStartDate().toString().trim());
        values.put("status",event.getStatus());
        values.put("count",event.getCount());
        return db.insert("Event",null,values);

    }

    //Delete event
    public boolean deleteData(Integer id){
        String sql="id"+"=?";
        String[] contentValuesArray=new String[]{String.valueOf(id)};
        return db.delete("Event",sql,contentValuesArray)>0;
    }

    //Edit event information
    public int editData(Event event){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id",event.getId());
        values.put("name",event.getName());
        values.put("description",event.getDescription());
        return db.update("Event", values, "id like ?", new String[]{event.getId().toString()});
    }

    //Edit event status
    public int editStatus(Event event){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id",event.getId());
        values.put("status",event.getStatus());
        values.put("endDate",event.getEndDate().toString().trim());
        return db.update("Event",values,"id like ?",new String[]{event.getId().toString()});

    }

    //Count+1
    public int editCount(Event event){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id",event.getId());
        values.put("count",event.getCount());
        return db.update("Event",values,"id like ?",new String[]{event.getId().toString()});

    }



    //Query all events
    @SuppressLint("Range")
    public List<Event> query() throws ParseException {
        List<Event> list = new ArrayList<Event>();
        Cursor cursor = db.query("Event", null, null, null,
                null, null, "id desc");
        if (cursor != null) {
            while (cursor.moveToNext()) {
                Event eventInfo = new Event();
                @SuppressLint("Range") Integer id = cursor.getInt(cursor.getColumnIndex("id"));
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
                @SuppressLint("Range") String description= cursor.getString(cursor.getColumnIndex("description"));
                @SuppressLint("Range") String startDate = cursor.getString(cursor.getColumnIndex("startDate"));
                @SuppressLint("Range") String endDate = cursor.getString(cursor.getColumnIndex
                        ("endDate"));
                @SuppressLint("Range") Integer status = cursor.getInt(cursor.getColumnIndex
                        ("status"));
                @SuppressLint("Range") Integer count = cursor.getInt(cursor.getColumnIndex("count"));
                eventInfo.setId(id);
                eventInfo.setName(name);
                eventInfo.setDescription(description);
                eventInfo.setStartDate(sdf.parse(startDate));
                if(endDate!=null){
                    eventInfo.setEndDate(sdf.parse(endDate));
                }
                eventInfo.setStatus(status);
                eventInfo.setCount(count);
                list.add(eventInfo);
            }
            cursor.close();
        }
        return list;
    }

    //Query ongoing events
    public List<Event> query1() throws ParseException {
        List<Event> list = new ArrayList<Event>();
        Cursor cursor = db.query("Event", null, "status=0", null,
                null, null, "id desc");
        if (cursor != null) {
            while (cursor.moveToNext()) {
                Event eventInfo = new Event();
                @SuppressLint("Range") Integer id = cursor.getInt(cursor.getColumnIndex("id"));
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
                @SuppressLint("Range") String description= cursor.getString(cursor.getColumnIndex("description"));
                @SuppressLint("Range") String startDate = cursor.getString(cursor.getColumnIndex
                        ("startDate"));
                @SuppressLint("Range") String endDate = cursor.getString(cursor.getColumnIndex
                        ("endDate"));
                @SuppressLint("Range") Integer status = cursor.getInt(cursor.getColumnIndex
                        ("status"));
                @SuppressLint("Range") Integer count = cursor.getInt(cursor.getColumnIndex("count"));
                eventInfo.setId(id);
                eventInfo.setName(name);
                eventInfo.setDescription(description);
                eventInfo.setStartDate(sdf.parse(startDate));
                if(endDate!=null){
                    eventInfo.setEndDate(sdf.parse(endDate));
                }
                eventInfo.setStatus(status);
                eventInfo.setCount(count);
                list.add(eventInfo);
            }
            cursor.close();
        }
        return list;
    }

    //Query completed events
    public List<Event> query2() throws ParseException {
        List<Event> list = new ArrayList<Event>();
        Cursor cursor = db.query("Event", null, "status=1 or status=2", null,
                null, null, "id desc");
        if (cursor != null) {
            while (cursor.moveToNext()) {
                Event eventInfo = new Event();
                @SuppressLint("Range") Integer id = cursor.getInt(cursor.getColumnIndex("id"));
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
                @SuppressLint("Range") String description= cursor.getString(cursor.getColumnIndex("description"));
                @SuppressLint("Range") String startDate = cursor.getString(cursor.getColumnIndex
                        ("startDate"));
                @SuppressLint("Range") String endDate = cursor.getString(cursor.getColumnIndex
                        ("endDate"));
                @SuppressLint("Range") Integer status = cursor.getInt(cursor.getColumnIndex
                        ("status"));
                @SuppressLint("Range") Integer count = cursor.getInt(cursor.getColumnIndex("count"));
                eventInfo.setId(id);
                eventInfo.setName(name);
                eventInfo.setDescription(description);
                eventInfo.setStartDate(sdf.parse(startDate));
                if(endDate!=null){
                    eventInfo.setEndDate(sdf.parse(endDate));
                }
                eventInfo.setStatus(status);
                eventInfo.setCount(count);
                list.add(eventInfo);
            }
            cursor.close();
        }
        return list;
    }

    //Search
    public List<Event> search(String query) throws ParseException {
        List<Event> list = new ArrayList<Event>();
        Cursor cursor = db.query("Event", null, "name like '%"+query+"%'", null,
                null, null, "id desc");
        if (cursor != null) {
            while (cursor.moveToNext()) {
                Event eventInfo = new Event();
                @SuppressLint("Range") Integer id = cursor.getInt(cursor.getColumnIndex("id"));
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
                @SuppressLint("Range") String description= cursor.getString(cursor.getColumnIndex("description"));
                @SuppressLint("Range") String startDate = cursor.getString(cursor.getColumnIndex
                        ("startDate"));
                @SuppressLint("Range") String endDate = cursor.getString(cursor.getColumnIndex
                        ("endDate"));
                @SuppressLint("Range") Integer status = cursor.getInt(cursor.getColumnIndex
                        ("status"));
                @SuppressLint("Range") Integer count = cursor.getInt(cursor.getColumnIndex("count"));
                eventInfo.setId(id);
                eventInfo.setName(name);
                eventInfo.setDescription(description);
                eventInfo.setStartDate(sdf.parse(startDate));
                if(endDate!=null){
                    eventInfo.setEndDate(sdf.parse(endDate));
                }
                eventInfo.setStatus(status);
                eventInfo.setCount(count);
                list.add(eventInfo);
            }
            cursor.close();
        }
        return list;
    }

}

