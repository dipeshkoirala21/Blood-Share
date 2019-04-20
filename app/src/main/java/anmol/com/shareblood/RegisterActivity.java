package anmol.com.shareblood;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;
import java.util.Map;

import anmol.com.shareblood.Models.Request;
import anmol.com.shareblood.Models.UserDetails;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase, mUserDatabase;
    TextInputLayout tvName, tvEmail, tvAddress, tvPassword;
    String name, email, address, bloodGroup, password, hospitalAddress,donationCount,receivedCount;
    String uid, deviceToken;
    Button mCreateButton;


    ProgressDialog progressDialog;
    ArrayList<Map<String, Request>> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        arrayList=new ArrayList<>();

        bloodGroup = "O+";
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.keepSynced(true);

        tvName = findViewById(R.id.reg_display_name);
        tvEmail = findViewById(R.id.login_email);
        tvAddress = findViewById(R.id.homeAddress);
        tvPassword = findViewById(R.id.password_password);
        mCreateButton = findViewById(R.id.reg_create_btn);
        progressDialog = new ProgressDialog(this);

        mCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = tvName.getEditText().getText().toString();
                email = tvEmail.getEditText().getText().toString();
                address = tvAddress.getEditText().getText().toString();
                password = tvPassword.getEditText().getText().toString();

                if ((!TextUtils.isEmpty(name)) || (!TextUtils.isEmpty(email)) || (!TextUtils.isEmpty(address)) || (!TextUtils.isEmpty(password))) {
                    progressDialog.setTitle("Registering User...");
                    progressDialog.setMessage("Please wait while we create your account...");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();
                    registerUser(name, email, address, bloodGroup, password);
                }
            }
        });
    }

    private void registerUser(final String name, final String email, final String address, final String bloodGroup, final String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                            uid = firebaseUser.getUid();
                            deviceToken = FirebaseInstanceId.getInstance().getToken();
                            mUserDatabase = mDatabase.child("Users").child(uid);
                            mUserDatabase.keepSynced(true);

                            UserDetails userDetails = new UserDetails(name,email,address,address,
                                                        bloodGroup,password,deviceToken,donationCount,
                                                        receivedCount,"default",
                                                        "default",arrayList,"default");

                            mUserDatabase.setValue(userDetails)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                String current_user_id=mAuth.getCurrentUser().getUid();

                                                Toast.makeText(RegisterActivity.this, "You have successfully registered", Toast.LENGTH_SHORT).show();
                                                Intent mainIntent = new Intent(RegisterActivity.this, MainActivity.class);
                                                mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                startActivity(mainIntent);
                                                finish();
                                                progressDialog.dismiss();
                                            }
                                            else {
                                                Toast.makeText(RegisterActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                                                progressDialog.dismiss();
                                            }
                                        }
                                    });
                        }
                    }
                });
    }
}
