package com.suh.app.suh;

import android.os.Bundle;
import android.app.ListActivity;
import java.util.ArrayList;
import android.widget.Button;


public class FriendsListActivity extends ListActivity {

    private ArrayList<User> userArray;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        userArray = new ArrayList();

        String[] values = new String[] { "Rowan Krishnan", "Katya Malison", "Brooke Weil",
                "Frankie Caiazzo", "Ubuntu" };
        Boolean[] availabilities = new Boolean[] {true, false, false, true, true};


        for (int i = 0; i < 5; i++) {
            userArray.add(new User(values[i], availabilities[i], i));
        }

        CustomListAdapter adapter = new CustomListAdapter(this, userArray);
        setListAdapter(adapter);
    }
}

