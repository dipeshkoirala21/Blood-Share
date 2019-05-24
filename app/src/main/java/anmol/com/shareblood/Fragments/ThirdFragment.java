package anmol.com.shareblood.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import anmol.com.shareblood.Adapters.EventRecyclerAdapter;
import anmol.com.shareblood.Models.User;
import anmol.com.shareblood.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ThirdFragment extends Fragment {

    DatabaseReference mDatabaseEvents;
    RecyclerView thirdRV;
    EventRecyclerAdapter usersRecyclerAdapter;
    public static final String TAG="ALL";
    View fragmentRootView;

    public ThirdFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentRootView = inflater.inflate(R.layout.fragment_third,container,false);

        thirdRV = fragmentRootView.findViewById(R.id.recycler_view_third);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        thirdRV.setLayoutManager(layoutManager);
        final ArrayList<User> arrayList = new ArrayList<>();
        mDatabaseEvents = FirebaseDatabase.getInstance().getReference().child("Event");
        mDatabaseEvents.keepSynced(true);

        mDatabaseEvents.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                arrayList.clear();
                for(DataSnapshot userSnapshot: dataSnapshot.getChildren())
                {
                    String name = userSnapshot.child("name").getValue().toString();
                    String email = userSnapshot.child("description").getValue().toString();
                    arrayList.add(new User(name,email));
                    Log.d(TAG, "onDataChange: "+userSnapshot);
                }
                usersRecyclerAdapter = new EventRecyclerAdapter(arrayList,getActivity());
                thirdRV.setAdapter(new EventRecyclerAdapter(arrayList,getActivity()));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        setRecyclerView(arrayList);

        // Inflate the layout for this fragment
        return fragmentRootView;
    }

    public void setRecyclerView(ArrayList<User> arrayList)
    {
        usersRecyclerAdapter = new EventRecyclerAdapter(arrayList,getActivity());
        thirdRV.setLayoutManager(new LinearLayoutManager(getActivity()));
        thirdRV.setAdapter(new EventRecyclerAdapter(arrayList,getActivity()));
    }

}
