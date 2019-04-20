package anmol.com.shareblood.Fragments;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import anmol.com.shareblood.MainActivity;
import anmol.com.shareblood.ProfileActivity;
import anmol.com.shareblood.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SecondFragment extends Fragment implements OnMapReadyCallback {

    private static final int REQUEST_CODE_ASK_PERMISSIONS = 3333;
    Context context;
    GoogleMap mMap;
    int n = 0;
    public static final String TAG = "TAG";


    ProgressDialog progressDialog;
    static double latitude, longitude;
    GridView gridView;
    private FusedLocationProviderApi locationProvider = LocationServices.FusedLocationApi;
    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;
    private Double myLatitude;
    private Double myLongitude;
    private static final int MY_PERMISSION_REQUEST_FINE_LOCATION = 101;
    private static final int MY_PERMISSION_REQUEST_COARSE_LOCATION = 102;
    public static boolean permissionIsGranted = false;
    android.support.v7.widget.Toolbar mToolbar;
    TextView textView;
    public static final Integer REQUEST_CHECK_SETTINGS = 888;

    public SecondFragment() {
        context = getActivity();
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_maps, null, false);

        Log.d(TAG, "onCreateView: " + ProfileActivity.GALLERY_INTENT);
        LocationManager lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;

        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
        }

        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {
        }

        //if (!gps_enabled && !network_enabled) {
            // notify user
            /*AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
            dialog.setMessage(getActivity().getResources().getString(R.string.gps_network_not_enabled));
            dialog.setPositiveButton(getActivity().getResources().getString(R.string.open_location_settings), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    // TODO Auto-generated method stub
                    Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    getActivity().startActivity(myIntent);
                    //get gps
                }
            });
            dialog.setNegativeButton(getActivity().getString(R.string.Cancel), new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    // TODO Auto-generated method stub

                }
            });
            dialog.show();*/
       // }
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat
                    .requestPermissions(getActivity(), new String[]
                                    {Manifest.permission.ACCESS_FINE_LOCATION},
                            REQUEST_CODE_ASK_PERMISSIONS);
            Log.d(TAG, "onCreateView: permissionIsGranted="+permissionIsGranted);
        }
        if(ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "onCreateView: NO PERMISSION");
        }
        else {

        }

        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        final ArrayList<LatLng> latLngArrayList = new ArrayList<>();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,"https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=28.535516,77.391026&radius=5000&type=hospital&key=AIzaSyD8Dq6-5ZHwAYlYyE8-AJBtjcD_IXqnUAg", null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        Log.d(TAG, "onResponse: "+response);
                        n = response.getJSONArray("results").length();
                        for(int i=0;i<response.getJSONArray("results").length();i++){
                            Double lat = response.getJSONArray("results")
                                         .getJSONObject(i)
                                         .getJSONObject("geometry")
                                         .getJSONObject("location")
                                         .getDouble("lat");
                            Double lng = response.getJSONArray("results")
                                    .getJSONObject(i)
                                    .getJSONObject("geometry")
                                    .getJSONObject("location")
                                    .getDouble("lng");
                            if(lat!=null && lng!=null) {
                                LatLng latLng = new LatLng(lat,lng);
                                latLngArrayList.add(latLng);
                                mMap.addMarker(new MarkerOptions().position(latLng).title(response.getJSONArray
                                        ("results").getJSONObject(i).getString("name")).
                                        icon(BitmapDescriptorFactory.fromResource(R.drawable.hospital)));
                            }
                        }
                        LatLng Noida = new LatLng(latLngArrayList.get(0).latitude,latLngArrayList.get(0).longitude);
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(Noida));
                        mMap.setMinZoomPreference(12.2f);
                        mMap.getUiSettings().setScrollGesturesEnabled(false);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Log.d(TAG, "onResponse: "+response);
                }
            }, new Response.ErrorListener() {
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
                    params.put("Authorization", "KEY=AAAAv8t6tlE:APA91bHZ9IIEvWHgxDUT0O5bt4vXUjbUnOO_LWfSjnVLrH2kTDElmIjwpFM5zUfxixlpXSG9Kq1QbWLvo7QgbCS2mBOawPUGACzhLPukqqnJXxmnQ24D2edquGFXc8GIsESiPqDEsr97");
                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
            requestQueue.add(jsonObjectRequest);

        // Add a marker in Sydney and move the camera


        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }



}
