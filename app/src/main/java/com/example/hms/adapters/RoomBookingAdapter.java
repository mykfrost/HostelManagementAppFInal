package com.example.hms.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hms.R;
import com.example.hms.utils.Hostel;

import java.util.List;

public class RoomBookingAdapter extends RecyclerView.Adapter<RoomBookingAdapter.ViewHolder> {
    private List<Hostel> itemList;

    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void RoomBookingAdapterMyAdapter(List<Hostel> itemList, OnItemClickListener listener) {
        this.itemList = itemList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hostels_for_booking, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Hostel  item = itemList.get(position);
        holder.bind(String.valueOf(item));
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewListItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewListItem = itemView.findViewById(R.id.textViewRoomListItem);

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(position);
                    }
                }
            });
        }

        public void bind(String item) {
            textViewListItem.setText(item);
        }
    }
}
