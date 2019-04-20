package anmol.com.shareblood.Adapters;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import anmol.com.shareblood.Common;
import anmol.com.shareblood.Models.Needs;
import anmol.com.shareblood.R;

/**
 * Created by sukrit on 20/2/18.
 */

public class NeedsRecyclerAdapter extends RecyclerView.Adapter<NeedsRecyclerAdapter.RequiredViewHolder> {

    ArrayList<Needs> arrayList;
    Context context;
    String textLine = "";
    String deviceToken = "";
    String myName = "";

    public static final String TAG = "BLOOD";

    public NeedsRecyclerAdapter(ArrayList<Needs> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @Override
    public RequiredViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        int layoutType = R.layout.required_list_item;
        View itemView = layoutInflater.inflate(layoutType, parent, false);
        return new RequiredViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final RequiredViewHolder holder, int position) {
        final Needs thisModel = arrayList.get(position);

        FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("name")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                            myName = dataSnapshot.getValue().toString();
                        Log.d(TAG, "onDataChange: myName "+myName);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

        if (thisModel.getGender().equalsIgnoreCase("male")) {
            textLine = "Mr." + thisModel.getName() + " is requesting blood for " + thisModel.getRequestFor() + ".";
            holder.requesterText.setText(textLine);
        } else {
            textLine = "Ms." + thisModel.getName() + " is requesting blood for " + thisModel.getRequestFor() + ".";
            holder.requesterText.setText(textLine);
        }

        Log.d("BLOOD", "onBindViewHolder: "+thisModel.getBloodGroup());
        if(thisModel.getBloodGroup().equals("A+")) {
            holder.imageBloodGroup.setImageDrawable(context.getResources().getDrawable(R.drawable.a_positive));
        }
        else if(thisModel.getBloodGroup().equals("A-")) {
            holder.imageBloodGroup.setImageDrawable(context.getResources().getDrawable(R.drawable.a_negative));
        }
         else if(thisModel.getBloodGroup().equals("B+")) {
            holder.imageBloodGroup.setImageDrawable(context.getResources().getDrawable(R.drawable.b_positive));
        }
         else if(thisModel.getBloodGroup().equals("B-")) {
            holder.imageBloodGroup.setImageDrawable(context.getResources().getDrawable(R.drawable.b_negative));
        }
         else if(thisModel.getBloodGroup().equals("AB+")) {
            holder.imageBloodGroup.setImageDrawable(context.getResources().getDrawable(R.drawable.ab_positive));
        }
         else if(thisModel.getBloodGroup().equals("AB-")) {
            holder.imageBloodGroup.setImageDrawable(context.getResources().getDrawable(R.drawable.ab_negative));
        }
        else if(thisModel.getBloodGroup().equals("O+")) {
                holder.imageBloodGroup.setImageDrawable(context.getResources().getDrawable(R.drawable.o_positive));
            }
            else{
                holder.imageBloodGroup.setImageDrawable(context.getResources().getDrawable(R.drawable.o_negative));
            }


        DatabaseReference dbUser = FirebaseDatabase.getInstance().getReference().child("Users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        dbUser.child("name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name = dataSnapshot.getValue().toString();
                if(name.equals(thisModel.getName()))
                {
                    holder.linDropIn.setEnabled(false);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        holder.userNumber.setText(thisModel.getNumber());
        holder.userAddress.setText(thisModel.getAddress());
        holder.endDate.setText(thisModel.getEndDate());
        holder.linDropIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uid = thisModel.getUid();
                Log.d(TAG, "onClick: clicked UID =  "+uid);
                DatabaseReference dbUserClicked = FirebaseDatabase.getInstance().getReference().child("Users").child(uid)
                        .child("deviceToken");
                dbUserClicked.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        volleyFunction(dataSnapshot.getValue().toString());
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                //String myBlood = db.child("")
                Toast.makeText(context, "Replace with your own action", Toast.LENGTH_SHORT).show();
            }
        });
        holder.linRaise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, textLine + "Let's share more so that one life can be saved by all of us.");
                sendIntent.setType("text/plain");
                context.startActivity(sendIntent);
            }
        });
        holder.userCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + thisModel.getNumber()));
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                context.startActivity(intent);
            }
        });

    }

    private void volleyFunction(String deviceToken)
    {
        Log.d(TAG, "volleyFunction: "+deviceToken);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Common.FCM_Url, getUpdateJsonObject(deviceToken), new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, "onResponse: "+response);
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: "+error);
            }
        }){
            @Override
            public String getPostBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", "KEY=AAAAvqQ0Rp4:APA91bGYG7OykaMwzM05OfRWc2an0T4oPk8pT42QVv0oTTU3d1Ti_D_PvNHujJrpNZdBtz5fn6tupxqCwhewNaDnvq3Z0I0hExGW-EG18BqZl2xeXl3J2g43pYoZUtV_MOU79MJ16VvN");
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonObjectRequest);
    }

    public JSONObject getUpdateJsonObject(final String deviceToken)
    {
        final JSONObject notif = new JSONObject();
        final JSONObject obj = new JSONObject();
        try {
            notif.put("title",myName+" will donate blood to you..!");
           // notif.put("text","Distance:"+" , Time:"+" , Address:");
            obj.put("to",deviceToken);
            obj.put("notification",notif);
            obj.put("from", FirebaseInstanceId.getInstance().getToken());
            Log.e(TAG,obj.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "getUpdateJsonObject: "+obj);
        return obj;
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class RequiredViewHolder extends RecyclerView.ViewHolder {
        View testView;
        TextView userDetails;
        TextView endDate;
        LinearLayout linDropIn;
        LinearLayout linRaise;
        TextView userNumber,userAddress,userMessage,requesterText;
        ImageView imageBloodGroup;
        ImageView userCall;

        public RequiredViewHolder(View itemView) {
            super(itemView);
            linDropIn = itemView.findViewById(R.id.linDropIn);
            linRaise = itemView.findViewById(R.id.linRaise);
            endDate = itemView.findViewById(R.id.endDate);
            userNumber = itemView.findViewById(R.id.userNumber);
            userAddress = itemView.findViewById(R.id.userAddress);
            requesterText = itemView.findViewById(R.id.tvRequesterText);
            imageBloodGroup = itemView.findViewById(R.id.imageBloodGroup);
            userMessage = itemView.findViewById(R.id.userMessage);
            userCall = itemView.findViewById(R.id.userCall);
            testView=itemView;
        }
    }
}
