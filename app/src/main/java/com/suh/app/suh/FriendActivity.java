package com.suh.app.suh;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.ListActivity;
import java.util.ArrayList;
import android.widget.ImageButton;
import android.widget.EditText;
import android.widget.ListView;
import android.view.View;


public class FriendActivity extends Activity {
    private ListView lv;
    CustomListAdapter adapter;
    ImageButton filter;

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_list);

        String[] values = new String[] { "Rowan Krishnan", "Katya Malison", "Brooke Weil",
                "Frankie Caiazzo", "Ming Chow" };
        Boolean[] availabilities = new Boolean[] {true, false, false, true, true};

        ArrayList<User> userArray = new ArrayList();

        for (int i = 0; i < 5; i++) {
            userArray.add(new User(values[i], availabilities[i], i));
        }

        lv = (ListView) findViewById(R.id.listview);
        filter = (ImageButton) findViewById(R.id.button3);

        adapter = new CustomListAdapter(this, userArray);
        lv.setAdapter(adapter);

        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.filterUnavail();
            }
        });
    }
}