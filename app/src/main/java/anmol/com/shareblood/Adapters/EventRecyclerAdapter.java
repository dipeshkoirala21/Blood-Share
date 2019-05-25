package anmol.com.shareblood.Adapters;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;



import java.util.ArrayList;
import java.util.List;

import anmol.com.shareblood.Models.Event;
import anmol.com.shareblood.Models.User;
import anmol.com.shareblood.R;

/**
 * Created by sukrit on 20/2/18.
 */

public class EventRecyclerAdapter extends RecyclerView.Adapter<EventRecyclerAdapter.UsersViewHolder> {

    List<Event> arrayList;
    Context context;

    public EventRecyclerAdapter(List<Event> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @Override
    public UsersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        int layoutType =  R.layout.user_list_item;
        View itemView = layoutInflater.inflate(layoutType,parent,false);
        return new UsersViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(UsersViewHolder holder, final int position) {
        holder.name.setText(arrayList.get(position).getName());
        holder.description.setText(arrayList.get(position).getDescription());
        holder.contact.setText(arrayList.get(position).getContact());
        holder.location.setText(arrayList.get(position).getLocation());
        holder.date.setText(arrayList.get(position).getDate());
        holder.time.setText(arrayList.get(position).getTime());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + arrayList.get(position).getContact()));
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

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class UsersViewHolder extends RecyclerView.ViewHolder
    {
        View testView;
        TextView name;
        TextView description;
        TextView contact;
        TextView location;
        TextView date;
        TextView time;
        CardView cardView;
        UsersViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textViewName);
            description = itemView.findViewById(R.id.textViewDescription);
            contact = itemView.findViewById(R.id.textViewContact);
            location = itemView.findViewById(R.id.textViewLocation);
            date = itemView.findViewById(R.id.textViewDate);
            time = itemView.findViewById(R.id.textViewTime);
            cardView=itemView.findViewById(R.id.cardView);
          //  userDonations = itemView.findViewById(R.id.userDonations);
           testView = itemView;
        }
    }
}
