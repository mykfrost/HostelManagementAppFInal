package com.example.hms.utils;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hms.R;
import com.example.hms.SessionManager;
import com.example.hms.database.DatabaseHandler;
import com.example.hms.views.HostelDetailsActivity;

import java.util.List;

public class HostelAdapter extends RecyclerView.Adapter<HostelAdapter.HostelViewHolder>{
    private List<Hostel> hostelList;
    private Context context;


public HostelAdapter(List<Hostel> hostelList, Context context) {
    this.hostelList = hostelList;
    this.context = context;

}


    @NonNull
    @Override
    public HostelAdapter.HostelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_hostel_name, parent, false);
        return new HostelViewHolder(view);
    }



@Override
public void onBindViewHolder(@NonNull HostelAdapter.HostelViewHolder holder, int position) {
    Hostel hostel = hostelList.get(position);
    holder.hostelNameTextView.setText(hostel.getHostelName());


    holder.itemView.setOnClickListener(v -> {
        // Handle item click here
        Intent intent = new Intent(context, HostelDetailsActivity.class);
        intent.putExtra("hostel_id", hostel.getId());
        intent.putExtra("hostel_name", hostel.getHostelName());
        intent.putExtra("description", hostel.getDescription());
        intent.putExtra("address", hostel.getAddress());
        intent.putExtra("city", hostel.getCity());
        intent.putExtra("country", hostel.getCountry());
        intent.putExtra("capacity", hostel.getCapacity());
        context.startActivity(intent);

    });
}
//    @Override
//    public int getItemCount() {
//        return 0;
//    }
@Override
public int getItemCount() {
    return hostelList.size();
}
    public static class HostelViewHolder extends RecyclerView.ViewHolder {
        TextView hostelNameTextView,roomCount , descriptionTextView, addressTextView , cityTextView , countryTextview;

        public HostelViewHolder(@NonNull View itemView) {
            super(itemView);
//            hostelNameTextView = itemView.findViewById(R.id.item_hostel_name);
//            descriptionTextView = itemView.findViewById(R.id.item_description);
//            addressTextView = itemView.findViewById(R.id.item_address);
//            cityTextView = itemView.findViewById(R.id.item_city);
//            countryTextview = itemView.findViewById(R.id.item_country);
            hostelNameTextView = itemView.findViewById(R.id.hostel_name);
           // roomCount = itemView.findViewById(R.id.rooms_count);
        }
    }



}
