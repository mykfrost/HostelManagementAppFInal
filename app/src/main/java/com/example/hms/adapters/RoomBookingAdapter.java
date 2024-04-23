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
import com.example.hms.views.HostelDetailsActivity;

import java.util.List;

public class RoomBookingAdapter extends RecyclerView.Adapter<RoomBookingAdapter.ViewHolder> {
    private List<Hostel> itemList;
    private Context context;

    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void RoomBookingAdapterMyAdapter(List<Hostel> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hostels_for_booking, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Hostel  hostel = itemList.get(position);
        holder.hostelNameTextView.setText(hostel.getHostelName());
        holder.itemView.setOnClickListener(v -> {
            // Handle item click here
            Intent intent = new Intent(context, BookRoomActivity.class);
            intent.putExtra("hostel_id", hostel.getId());
            intent.putExtra("hostel_name", hostel.getHostelName());
            context.startActivity(intent);

        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView hostelNameTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            hostelNameTextView = itemView.findViewById(R.id.hostel_name);
        }

    }
}
