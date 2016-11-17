package com.suh.app.suh;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.os.AsyncTask;
import java.util.ArrayList;
import android.widget.ImageButton;
import android.widget.ListView;
import android.util.Log;
import android.view.View;
import org.json.JSONException;
import org.json.JSONArray;
import org.json.JSONObject;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;
import android.support.annotation.IdRes;
import android.content.Intent;

public class ProfileActivity extends AppCompatActivity {
    private static String url = "http://10.0.2.2:3000/api/users";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("My Profile");
        toolbar.setTitleTextColor(android.graphics.Color.WHITE);
        new FetchData().execute();
    }

    private class FetchData extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            //WebRequest webreq = new WebRequest();
            //String jsonStr = webreq.makeWebServiceCall(url, WebRequest.GETRequest);
            //Log.d("Response: ", "> " + jsonStr);
            return null;
        }

        @Override
        protected void onPostExecute(Void requestresult) {
            super.onPostExecute(requestresult);

            BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBar);
            bottomBar.setDefaultTab(R.id.tab_profile);
            bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
                @Override
                public void onTabSelected(@IdRes int tabId) {
                    if (tabId == R.id.tab_friendlist) {
                        Intent myIntent = new Intent(ProfileActivity.this, FriendActivity.class);
                        startActivity(myIntent);
                    } else if (tabId == R.id.tab_map) {
                        Intent myIntent = new Intent(ProfileActivity.this, MapsActivity.class);
                        startActivity(myIntent);
                    }
                }
            });
        }
    }
}
       
