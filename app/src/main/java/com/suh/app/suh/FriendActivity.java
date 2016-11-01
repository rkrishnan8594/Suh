package com.suh.app.suh;

import android.os.AsyncTask;
import android.os.Bundle;
import java.util.ArrayList;
import android.widget.ImageButton;
import android.widget.ListView;
import android.util.Log;
import android.view.View;
import org.json.JSONException;
import org.json.JSONArray;
import org.json.JSONObject;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.AppCompatActivity;

public class FriendActivity extends AppCompatActivity {
    private ListView lv;
    CustomListAdapter adapter;
    ImageButton filter;
    private static String url = "http://10.0.2.2:3000/api/users";

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_list);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        new FetchData().execute();
    }

    private class FetchData extends AsyncTask<Void, Void, Void> {
        ArrayList<User> friendList;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            WebRequest webreq = new WebRequest();
            String jsonStr = webreq.makeWebServiceCall(url, WebRequest.GETRequest);
            Log.d("Response: ", "> " + jsonStr);
            friendList = ParseJSON(jsonStr);
            return null;
        }

        @Override
        protected void onPostExecute(Void requestresult) {
            super.onPostExecute(requestresult);
            lv = (ListView) findViewById(R.id.listview);
            filter = (ImageButton) findViewById(R.id.button3);
            adapter = new CustomListAdapter(FriendActivity.this, friendList);
            lv.setAdapter(adapter);
            filter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    adapter.filterUnavail();
                }
            });
        }
    }

    private ArrayList<User> ParseJSON(String json) {
        if (json != null) {
            try {
                ArrayList<User> friendList = new ArrayList();
                JSONArray friendsArr = new JSONArray(json);
                for(int i = 0; i < friendsArr.length(); i++) {
                    JSONObject row = friendsArr.getJSONObject(i);
                    friendList.add(new User(row.getInt("id"), row.getString("email"),
                            row.getString("first"), row.getString("last"), row.getBoolean("free"),
                            row.getBoolean("showLocation")));
                }
                return friendList;
            } catch(JSONException e) {
                Log.e("MYAPP", "unexpected JSON exception", e);
            }
        } else {
            System.out.println("Null JSON");
        }
        return null;
    }
}