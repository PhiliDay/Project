package com.philiday.projectapplication;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ActivitiesAdapter extends BaseAdapter {

    private ArrayList<RunDetails> runList;
    private Context context;

    public ActivitiesAdapter(ArrayList<RunDetails> list, Context cont){
        this.runList = list;
        this.context = cont;
    }

    @Override
    public int getCount() {
        return this.runList.size();
    }

    @Override
    public Object getItem(int position) {
        return this.runList.get(position);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        if(convertView == null){
            LayoutInflater inf = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inf.inflate(R.layout.activity_list, null);

            holder = new ViewHolder();
            holder.date = (TextView)convertView.findViewById(R.id.date);
            holder.time = (TextView)convertView.findViewById(R.id.time);
            holder.distance = (TextView)convertView.findViewById(R.id.distance);
            holder.walkingDist = (TextView)convertView.findViewById(R.id.walkingDist);
            holder.ranDist = (TextView)convertView.findViewById(R.id.ranDist);

            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder)convertView.getTag();
        }

        RunDetails stu = runList.get(position);
        holder.date.setText(stu.getDate());
        holder.time.setText(stu.getTime());
        holder.distance.setText(stu.getDistance());
        holder.walkingDist.setText(stu.getWalkingDist());
        holder.ranDist.setText(stu.getRanDist());
        Log.i("date", "date" + stu.getDate());

        Log.i("walkingDist", "walkingDist2" + stu.getWalkingDist());


        return convertView;
    }

    private static class ViewHolder{
        public TextView date;
        public TextView time;
        public TextView distance;
        public TextView walkingDist;
        public TextView ranDist;

    }
}