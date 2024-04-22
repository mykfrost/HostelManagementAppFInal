package com.example.hms.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hms.BookRoomActivity;
import com.example.hms.R;
import com.example.hms.utils.Hostel;
import com.example.hms.views.AddRoomActivity;
import com.example.hms.views.HostelDetailsActivity;

import java.util.List;

public class SelectHostelAdapter extends RecyclerView.Adapter<SelectHostelAdapter.SelectHostelViewHolder>{
    private List<Hostel> hostelList;
    private Context context;
    public SelectHostelAdapter(List<Hostel> hostelList, Context context) {
        this.hostelList = hostelList;
        this.context = context;

    }

    @NonNull
    @Override
    public SelectHostelAdapter.SelectHostelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_hostel_name, parent, false);
        return new SelectHostelAdapter.SelectHostelViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull SelectHostelAdapter.SelectHostelViewHolder holder, int position) {
        Hostel hostel = hostelList.get(position);
        holder.hostelNameTextView.setText(hostel.getHostelName());


        holder.itemView.setOnClickListener(v -> {
            // Handle item click here
            Intent intent = new Intent(context, AddRoomActivity.class);
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
    public static class SelectHostelViewHolder extends RecyclerView.ViewHolder {
        TextView hostelNameTextView,roomCount , descriptionTextView, addressTextView , cityTextView , countryTextview;

        public SelectHostelViewHolder(@NonNull View itemView) {
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
