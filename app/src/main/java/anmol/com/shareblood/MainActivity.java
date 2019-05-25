package anmol.com.shareblood;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ViewFlipper;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import anmol.com.shareblood.Adapters.ViewPagerAdapter;

public class MainActivity extends AppCompatActivity {
    ViewPagerAdapter viewPagerAdapter;
    ViewPager viewPager;
    TabLayout tabLayout;
    FloatingActionButton fabDonateBlood, fabRequestBlood;
    DatabaseReference mDatabaseNeeds,mDatabaseUsers;
    String uid;
    String bloodGroup;
    FirebaseUser currentUser;
    FirebaseAuth mAuth;
    public static final int  REQUEST_CODE_ASK_PERMISSIONS = 1234;


    ViewFlipper viewFlipper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FirebaseApp.initializeApp(this);

        if ((ActivityCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)||(ActivityCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)||
                (ActivityCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            {
                ActivityCompat
                        .requestPermissions(this, new String[]
                                        {Manifest.permission.CALL_PHONE,
                                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                                Manifest.permission.ACCESS_FINE_LOCATION},
                                REQUEST_CODE_ASK_PERMISSIONS);
            }
        }

        mAuth = FirebaseAuth.getInstance();
        uid = mAuth.getCurrentUser().getUid();

        mDatabaseNeeds = FirebaseDatabase.getInstance().getReference().child("Needs");
        mDatabaseUsers = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);

        mDatabaseNeeds.keepSynced(true);
        mDatabaseUsers.keepSynced(true);

        viewFlipper = findViewById(R.id.viewFlipper);
        viewFlipper.setAutoStart(true);
        viewFlipper.setFlipInterval(5500);
        viewFlipper.startFlipping();
        fabRequestBlood = findViewById(R.id.fabRequestBlood);
        fabRequestBlood.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RequestBloodActivity.class);
                intent.putExtra("id", uid);
                startActivity(intent);
            }
        });

        viewPager = findViewById(R.id.viewPager);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

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
        if (id == R.id.action_settings) {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(MainActivity.this,LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
            return true;

        }else if (id == R.id.action_profile){
            Intent intent = new Intent(MainActivity.this,ProfileActivity.class);
            startActivity(intent);
        }else if (id == R.id.action_feedback){
            Intent intent = new Intent(MainActivity.this,FeedbackActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.action_events){
            Intent intent = new Intent(MainActivity.this,EventsActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.action_about){
            Intent intent = new Intent(MainActivity.this,AboutActivity.class);
            startActivity(intent);
        }


        return super.onOptionsItemSelected(item);
    }




}
