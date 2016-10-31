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
    private ArrayList<User> values;
    private ArrayList<User> filter_values;

    public CustomListAdapter(Context context, ArrayList<User> users) {
        this.values = users;
        this.context = context;
    }

    @Override
    public int getCount() {
        System.out.println("*** 2 getCount method");
        System.out.println(values.size());
        return values.size();
    }
    @Override
    public Object getItem(int position) {
        System.out.println("*** ? getItem method");
        System.out.println(values.get(position));
        return values.get(position);
    }

    @Override
    public long getItemId(int position) {
        System.out.println("*** 3 getItemId method");
        System.out.println(values.get(position).getId());
        return values.get(position).getId();
    }

    public void filterUnavail() {
        filter_values = new ArrayList<>();
        for(User cur : values) {
            if(cur.getAvailability()) {
                filter_values.add(cur);
            }
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
        textView.setText(values.get(position).getName());
        Boolean avail = values.get(position).getAvailability();

        if (avail) {
            imageView.setImageResource(R.drawable.available);
        } else {
            imageView.setImageResource(R.drawable.unavailable);
        }

        return rowView;
    }
}