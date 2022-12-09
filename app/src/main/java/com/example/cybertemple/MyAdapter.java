package com.example.cybertemple;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import java.text.SimpleDateFormat;
import java.util.List;

public class MyAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private List<Event> events;


    public MyAdapter(Context context,List<Event> events){
        this.layoutInflater=LayoutInflater.from(context);
        this.events=events;

    }

    @Override
    public int getCount() {
        return events==null? 0: events.size();
    }

    @Override
    public Object getItem(int i) {
        return events.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView==null){
            convertView=layoutInflater.inflate(R.layout.my_row,null);
            viewHolder=new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else {
            viewHolder=(ViewHolder) convertView.getTag();
        }

        Event event=(Event)getItem(position);

        viewHolder.Ename.setText(event.getName());
        viewHolder.Edescription.setText(event.getDescription());
        SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        viewHolder.EstartDate.setText(dataFormat.format(event.getStartDate()));

        //Display status picture based on event status
        if(event.getStatus()==0){
            viewHolder.image.setVisibility(View.GONE);
        }
        else if(event.getStatus()==1){
            viewHolder.image.setVisibility(View.VISIBLE);
            viewHolder.image.setImageResource(R.drawable.success);
        }
        else if(event.getStatus()==2){
            viewHolder.image.setVisibility(View.VISIBLE);
            viewHolder.image.setImageResource(R.drawable.fail);
        }

        return convertView;

    }

    public class ViewHolder {
        TextView Ename,Edescription,EstartDate;
        ImageView image;
        public ViewHolder(@NonNull View itemView) {
            Ename=itemView.findViewById(R.id.tv_name);
            Edescription=itemView.findViewById(R.id.tv_description);
            EstartDate=itemView.findViewById(R.id.tv_start_date);
            image=itemView.findViewById(R.id.imageView);

        }
    }
}

