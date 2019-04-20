package anmol.com.shareblood.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;



import java.util.ArrayList;

import anmol.com.shareblood.Models.Needs;
import anmol.com.shareblood.R;

/**
 * Created by sukrit on 24/2/18.
 */

public class MatchingBloodAdapter extends RecyclerView.Adapter<MatchingBloodAdapter.MatchingViewHolder> {

    ArrayList<Needs> arrayList;
    Context context;

    public MatchingBloodAdapter(ArrayList<Needs> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @Override
    public MatchingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutType = R.layout.user_blood_matching_item;
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView =  layoutInflater.inflate(layoutType,parent);
        return new MatchingViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MatchingViewHolder holder, int position) {
        Needs thisModel = arrayList.get(position);
        holder.userMatchingDetails.setText(thisModel.getName() + " requires "+
                thisModel.getBloodUnits() +" units of "+ thisModel.getBloodGroup() +
                " Blood Group at "+thisModel.getHospitalAddress());
        holder.endDate.setText(thisModel.getEndDate());
        holder.btnDonate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Donate blood.", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class MatchingViewHolder extends RecyclerView.ViewHolder
    {
        View testView;
        TextView userMatchingDetails;
        TextView endDate;
        Button btnDonate;

        public MatchingViewHolder(View itemView) {
            super(itemView);
            userMatchingDetails = itemView.findViewById(R.id.userMatchingDetails);
            endDate = itemView.findViewById(R.id.endMatchingDate);
            btnDonate = itemView.findViewById(R.id.btnDonate);
            testView=itemView;
        }
    }
}
