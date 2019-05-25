package anmol.com.shareblood;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {
    CircleImageView profileImage;
    TextView userName;
    public static final int GALLERY_INTENT = 111;
    Uri mImageUri;
    StorageReference mStorageImage;
    String imagePath="";
    TextView navUserName;
    TextView navUserEmail;
    String uName,uEmail;
    DatabaseReference mDatabaseNeeds,mDatabaseUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

      //  userName =findViewById(R.id.userName);

        profileImage = findViewById(R.id.userProfileImage);
        navUserName = findViewById(R.id.userName);
        navUserEmail =findViewById(R.id.userEmail);
        mStorageImage = FirebaseStorage.getInstance().getReference().child("Profile_Images");

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent=new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent,GALLERY_INTENT);

            }
        });

        mDatabaseUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                imagePath = dataSnapshot.child("image").getValue().toString();
                uName = dataSnapshot.child("name").getValue().toString();
                uEmail = dataSnapshot.child("email").getValue().toString();
                navUserName.setText(uName);
                navUserEmail.setText(uEmail);
                if(imagePath.equals("default")) {
                    profileImage.setImageResource(R.drawable.circularclock);
                }
                else {
                    Log.d("IMG", "SetOnDataChange: "+imagePath);
                    Glide.with(getApplicationContext()).load(imagePath).into(profileImage);
                }
                //profileImage.setImageURI(Uri.parse(" https://firebasestorage.googleapis.com/v0/b/lifeshare-d961b.appspot.com/o/Profile_Images%2Fimage%3A94458?alt=media&token=90cee82b-9e43-45a6-b5f4-ce7440f15417"));
                Log.d("IMG", "onDataChange: "+imagePath);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==GALLERY_INTENT && resultCode==RESULT_OK)
        {
            mImageUri=data.getData();
            // start picker to get image for cropping and then use the image in cropping activity
            CropImage.activity(mImageUri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .start(this);

        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                Log.d("IMG", "onActivityResult: "+resultUri);
                // profileImage.setImageURI(resultUri);

                InputStream imageStream = null;
                try {
                    imageStream = getContentResolver().openInputStream(
                            resultUri);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                Bitmap bmp = BitmapFactory.decodeStream(imageStream);

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                try {
                    stream.close();
                    stream = null;
                } catch (IOException e) {

                    e.printStackTrace();
                }
                setUpPicture(getImageUri(getApplicationContext(),bmp));
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 1, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    private void setUpPicture(Uri mImageUri) {
        Log.d("IMG", "mImageUri: "+mImageUri);
        if(mImageUri!=null)
        {
            //upload image first and then set it to image Uri in database...
            final StorageReference filePath= mStorageImage.child(mImageUri.getLastPathSegment());

            filePath.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    String downloadUri=taskSnapshot.getUploadSessionUri().toString();
                    Log.d("IMG", "onSuccess: "+downloadUri);
                    mDatabaseUsers.child("image").setValue(downloadUri);
                    Toast.makeText(ProfileActivity.this, "Uploaded successfully.", Toast.LENGTH_SHORT).show();
//                    Intent loginIntent = new Intent(HomeActivity.this, HomeActivity.class);
//                    loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    finish();
//                    startActivity(loginIntent);
                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(ProfileActivity.this, "Failed To Upload.", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}
