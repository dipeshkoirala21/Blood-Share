package anmol.com.shareblood.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

import anmol.com.shareblood.Adapters.NeedsRecyclerAdapter;
import anmol.com.shareblood.Models.Needs;
import anmol.com.shareblood.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FirstFragment extends Fragment {

    RecyclerView firstRV;
    View fragmentRootView;
    DatabaseReference mDatabaseRequests;
    NeedsRecyclerAdapter needsRecyclerAdapter;

    public FirstFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentRootView = inflater.inflate(R.layout.fragment_first, container, false);
        firstRV = fragmentRootView.findViewById(R.id.recycler_view_first);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        firstRV.setLayoutManager(linearLayoutManager);

        //Offline Data

        mDatabaseRequests = FirebaseDatabase.getInstance().getReference().child("Needs");
        mDatabaseRequests.keepSynced(true);

        final ArrayList<Needs> arrayList = new ArrayList<>();
        mDatabaseRequests.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                arrayList.clear();
                for(DataSnapshot needSnapshot : dataSnapshot.getChildren())
                {
                    Needs needs = needSnapshot.getValue(Needs.class);
                    arrayList.add(needs);
                }
                Collections.reverse(arrayList);
                needsRecyclerAdapter = new NeedsRecyclerAdapter(arrayList,getActivity());
                firstRV.setAdapter(needsRecyclerAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        setRecyclerView(arrayList);
        return fragmentRootView;
    }
    public void setRecyclerView(ArrayList<Needs> arrayList)
    {
        firstRV.setLayoutManager(new LinearLayoutManager(getContext()));
        firstRV.setAdapter(new NeedsRecyclerAdapter(arrayList,getContext()));
    }

}
