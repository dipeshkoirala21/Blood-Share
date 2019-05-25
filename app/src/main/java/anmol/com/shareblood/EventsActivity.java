package anmol.com.shareblood;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import anmol.com.shareblood.Models.Event;
import anmol.com.shareblood.Models.Needs;
import anmol.com.shareblood.Models.Request;
import anmol.com.shareblood.Models.UserDetails;

public class EventsActivity extends AppCompatActivity {

    EditText etName,etDescription,etLocation,etDate,etTime,etContact;
    Button btnSubmit;
    String description,name,location,date,time,uid, organizer,contact;


    DatabaseReference mDatabaseNeeds,mDatabaseUsers,mDatabaseEvents;
    Event event;
    Request requestModel;
    RadioGroup radioGroup;
    RadioButton radioButton;
    UserDetails userDetails;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);


        initializeViews();
        uid = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();

        mDatabaseNeeds = FirebaseDatabase.getInstance().getReference().child("Needs");
        mDatabaseUsers = FirebaseDatabase.getInstance().getReference().child("Users");
        mDatabaseEvents = FirebaseDatabase.getInstance().getReference().child("Event");

        Log.d("BLOOD", "onCreate: "+name);


        mDatabaseNeeds.keepSynced(true);
        mDatabaseUsers.keepSynced(true);
        mDatabaseEvents.keepSynced(true);




        radioGroup = (RadioGroup) findViewById(R.id.OrgradioGroup);


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId1 = radioGroup.getCheckedRadioButtonId();
                if (selectedId1 > -1) {
                    radioButton = findViewById(selectedId1);
                }

                int selectedId2 = radioGroup.getCheckedRadioButtonId();
                if (selectedId2 > -1) {
                    radioButton = findViewById(selectedId2);
                }

                if (radioButton != null) {
                    organizer = radioButton.getText().toString();
                }

                name = etName.getText().toString();
                description = etDescription.getText().toString();
                location = etLocation.getText().toString();
                date = etDate.getText().toString();
                time = etTime.getText().toString();
                contact=etContact.getText().toString();

//                Log.d("TTT", "onClick: bloodFor = " + bloodFor + "\n" +
//                        "bloodGroup = " + bloodGroup + "\n" +
//                        "bloodUnits = " + bloodUnits + "\n" +
//                        "name = " + name + "\n" +
//                        "number = " + number + "\n" +
//                        "hospitalAddress = " + hospitalAddress + "\n" +
//                        "gender = " + gender + "\n" +
//                        "address = " + address + "\n" +
//                        "rb1 = " + rb1 + "\n" +
//                        "rb2 = " + rb2 + "\n");

                if (TextUtils.isEmpty(organizer) ||
                        TextUtils.isEmpty(description) ||
                        TextUtils.isEmpty(name) ||
                        TextUtils.isEmpty(contact) ||
                        TextUtils.isEmpty(location) ||
                        TextUtils.isEmpty(date) ||
                        TextUtils.isEmpty(time) ||
                        radioButton == null) {
                    Toast.makeText(EventsActivity.this, "All details Required !!", Toast.LENGTH_SHORT).show();
                } else {
                    organizer = radioButton.getText().toString();
                    event = new Event(name, description, location, date, time,uid,contact,organizer);
                    //requestModel = new Request(bloodUnits, bloodGroup, "20-06-2018", "false", bloodFor, gender);

                    mDatabaseEvents.push().setValue(event)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(EventsActivity.this, "Events Recorded Successfully", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(EventsActivity.this, MainActivity.class));
                                    }
                                }
                            });
                  //  mDatabaseUsers.child(uid).child("requests").push().setValue(requestModel);
                }
            }
        });
    }


    public void initializeViews()
    {
        etName = findViewById(R.id.etName);
        etContact = findViewById(R.id.etContact);
        etDescription = findViewById(R.id.etDescription);
        etLocation = findViewById(R.id.etLocation);
        etDate = findViewById(R.id.etDate);
        btnSubmit = findViewById(R.id.btnSubmit);
        etTime = findViewById(R.id.etTime);
    }
}
