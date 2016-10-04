package com.suh.app.suh;

import android.os.Bundle;
import android.app.ListActivity;

public class FriendsListActivity extends ListActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String[] values = new String[] { "Rowan Krishnan", "Katya Malison", "Brooke Weil",
                "Frankie Caiazzo", "Ubuntu" };
        CustomArrayAdapter adapter = new CustomArrayAdapter(this, values);
        setListAdapter(adapter);
    }
}
