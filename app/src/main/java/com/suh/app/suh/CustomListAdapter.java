package com.suh.app.suh;

import android.widget.ArrayAdapter;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.ImageView;
import java.util.ArrayList;

public class CustomListAdapter extends BaseAdapter {
    private final Context context;
    private ArrayList<User> all_values;
    private ArrayList<User> filter_values;

    public CustomListAdapter(Context context, ArrayList<User> users) {
        this.all_values = users;
        this.filter_values = users;
        this.context = context;
    }

    @Override
    public int getCount() {
        System.out.println("*** 2 getCount method");
        System.out.println(filter_values.size());
        return filter_values.size();
    }
    @Override
    public Object getItem(int position) {
        System.out.println("*** ? getItem method");
        System.out.println(filter_values.get(position));
        return filter_values.get(position);
    }

    @Override
    public long getItemId(int position) {
        System.out.println("*** 3 getItemId method");
        //System.out.println(filter_values.get(position).getId());
        //return filter_values.get(position).getId();
        return 5;
    }

    public void filterFriends(Boolean avail, Boolean unavail) {
        if (avail && unavail) {
            filter_values = all_values;
        } else if (avail) {
            filter_values = new ArrayList<User>();
            for (User cur : all_values) {
                if (cur.getIsAvailable()) {
                    filter_values.add(cur);
                }
            }
        } else if (unavail){
                filter_values = new ArrayList<User>();
                for(User cur : all_values) {
                    if(!cur.getIsAvailable()) {
                        filter_values.add(cur);
                    }
                }
        } else {
            filter_values = new ArrayList<User>();
        }
        notifyDataSetChanged();
        }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.rowlayout, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.label);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        textView.setText(filter_values.get(position).getName());
        Boolean avail = filter_values.get(position).getIsAvailable();

        if (avail) {
            imageView.setImageResource(R.drawable.available);
        } else {
            imageView.setImageResource(R.drawable.unavailable);
        }

        return rowView;
    }
}