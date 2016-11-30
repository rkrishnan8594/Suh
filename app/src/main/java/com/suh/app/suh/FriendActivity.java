package com.suh.app.suh;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.widget.ImageButton;
import android.widget.ListView;
import android.util.Log;
import android.view.View;
import org.json.JSONException;
import org.json.JSONArray;
import org.json.JSONObject;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;
import android.support.annotation.IdRes;
import android.content.Intent;


public class FriendActivity extends AppCompatActivity {
    private ListView lv;
    CustomListAdapter adapter;
    ImageButton filterr;
    ImageButton filterg;
    Boolean filterred = true;
    Boolean filtergreen = true;
    private DatabaseReference mDatabase;
    ArrayList<User> friendList;

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_list);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        myToolbar.setTitleTextColor(android.graphics.Color.WHITE);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        fetchData(mDatabase);
    }

    public void fetchData(DatabaseReference databaseReference) {
        Query usersQuery = databaseReference.child("users");
        usersQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                friendList = new ArrayList<User>();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    User u = child.getValue(User.class);
                    friendList.add(u);
                }

                renderView();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("The read failed: " , databaseError.getMessage());
            }
        });
    }

    public void renderView() {
        lv = (ListView) findViewById(R.id.listview);
        filterr = (ImageButton) findViewById(R.id.button3);
        filterg = (ImageButton) findViewById(R.id.button4);

        adapter = new CustomListAdapter(FriendActivity.this, friendList);

        lv.setAdapter(adapter);

        filterr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.filterFriends(filtergreen, filterred = !filterred);
            }
        });
        filterg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.filterFriends(filtergreen = !filtergreen, filterred);
            }
        });

        BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        bottomBar.setInActiveTabColor(Color.parseColor("#b498f6"));
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                if (tabId == R.id.tab_profile) {
                    Intent myIntent = new Intent(FriendActivity.this, ProfileActivity.class);
                    startActivity(myIntent);
                } else   if (tabId == R.id.tab_map) {
                    Intent myIntent = new Intent(FriendActivity.this, MapsActivity.class);
                    startActivity(myIntent);
                }
            }
        });
    }
}