package com.suh.app.suh;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.google.firebase.database.ChildEventListener;
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
import android.support.annotation.NonNull;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;
import android.view.Menu;
import android.view.MenuItem;


import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity implements
        GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {

    private FirebaseAuth mAuth;
    private GoogleApiClient mGoogleApiClient;
    private DatabaseReference mDatabase;
    private static final String TAG = "ProfileActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        findViewById(R.id.sign_out_button).setOnClickListener(this);
        findViewById(R.id.sign_out_button).setVisibility(View.VISIBLE);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("160737502147-tain45m40qno6pbsruhcil185mt6q4r8.apps.googleusercontent.com")
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("My Profile");
        toolbar.setTitleTextColor(android.graphics.Color.WHITE);

        BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        bottomBar.setDefaultTab(R.id.tab_profile);
        bottomBar.setInActiveTabColor(Color.parseColor("#b498f6"));
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

        setUpProfile();
        setUpSwitchListeners();
    }

    public void setUpProfile() {
        // Profile Photo
        Uri photoUrl = mAuth.getCurrentUser().getPhotoUrl();
        CircleImageView c = (CircleImageView) findViewById(R.id.profile_image);
        Picasso.with(this).load(photoUrl).into(c);

        // Switches
        Query userQuery = mDatabase.child("users").child(mAuth.getCurrentUser().getEmail().split("@")[0]);

        userQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Switch s1 = (Switch) findViewById(R.id.switch1);
                Switch s2 = (Switch) findViewById(R.id.switch2);

                if(dataSnapshot.child("isAvailable").getValue().toString() == "true") {
                    Log.d(TAG, "AVAILABLE IS TRUE");
                    s1.setChecked(true);
                } else {
                    Log.d(TAG, "AVAILABLE IS FALSE");
                    s1.setChecked(false);
                }

                if(dataSnapshot.child("showLocation").getValue().toString() == "true") {
                    Log.d(TAG, "LOCATION IS TRUE");
                    s2.setChecked(true);
                } else {
                    Log.d(TAG, "LOCATION IS FALSE");
                    s2.setChecked(false);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("The read failed: " , databaseError.getMessage());
            }
        });
    }

    public void setUpSwitchListeners() {
        Switch s1 = (Switch) findViewById(R.id.switch1);
        Switch s2 = (Switch) findViewById(R.id.switch2);

        s1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    mDatabase.child("users").child(mAuth.getCurrentUser().getEmail().split("@")[0])
                            .child("isAvailable").setValue(true);
                } else {
                    mDatabase.child("users").child(mAuth.getCurrentUser().getEmail().split("@")[0])
                            .child("isAvailable").setValue(false);
                }
            }
        });

        s2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    mDatabase.child("users").child(mAuth.getCurrentUser().getEmail().split("@")[0])
                            .child("showLocation").setValue(true);
                } else {
                    mDatabase.child("users").child(mAuth.getCurrentUser().getEmail().split("@")[0])
                            .child("showLocation").setValue(false);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_edit) {
            Intent myIntent = new Intent(ProfileActivity.this, ProfileEditActivity.class);
            startActivity(myIntent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.sign_out_button) {
            signOut();
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
        Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show();
    }

    private void signOut() {
        // Firebase sign out
        mAuth.signOut();

        // Google sign out
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        Intent mainPage = new Intent (ProfileActivity.this, SignInActivity.class);
                        startActivity(mainPage);
                    }
                });
    }
}
       
