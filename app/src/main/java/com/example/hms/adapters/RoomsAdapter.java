package com.example.hms.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hms.R;
import com.example.hms.utils.Room;
import com.example.hms.views.RoomDetailActivity;

import java.util.List;


public class RoomsAdapter extends RecyclerView.Adapter<RoomsAdapter.ViewHolder>  {
    private List<Room> roomsList;
    private Context context;

    public RoomsAdapter( List<Room> roomsList ,  Context context) {
        this.context = context;
        this.roomsList = roomsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.room_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Room room = roomsList.get(position);
        holder.bind(room);
    }

    @Override
    public int getItemCount() {
        return roomsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView roomType;
        private  TextView hostelName;
        private TextView roomStatus;
        private TextView capacity;
        private TextView description;
        private TextView price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            roomType = itemView.findViewById(R.id.item_txtRoomType);
            hostelName = itemView.findViewById(R.id.item_hostel_name);
            description = itemView.findViewById(R.id.item_description);
            capacity = itemView.findViewById(R.id.item_textCapacity);
            roomStatus = itemView.findViewById(R.id.item_textStatus);
            price = itemView.findViewById(R.id.item_text_price);
          //  booknow = itemView.findViewById(R.id.bookthisroom);
            itemView.setOnClickListener(this);
        }

        public void bind(Room room) {
            if (roomType != null) {
                roomType.setText(room.getRoom_type());
            }
            if (hostelName != null) {
                hostelName.setText(room.getHostel_name());
            }
            if (description != null) {
                description.setText(room.getDescription());
            }
            if (capacity != null) {
                capacity.setText("Capacity: " + room.getCapacity());
            }
            if (roomStatus != null) {
                roomStatus.setText("Status: " + room.getStatus());
            }
            if (price != null) {
                price.setText("Price: " + room.getPrice());
            }
        }
        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Room room = roomsList.get(position);
            // Navigate to another activity to display more details
            Intent intent = new Intent(context, RoomDetailActivity.class);
            intent.putExtra("hostel_name", room.getHostel_name());
            intent.putExtra("hostel_id", room.getHostel_id());
            intent.putExtra("room_id", room.getId());
            intent.putExtra("room_type", room.getRoom_type());
            intent.putExtra("status", room.getStatus());
            intent.putExtra("capacity", room.getCapacity());
            intent.putExtra("description", room.getDescription());
            intent.putExtra("total_price", room.getPrice());
            context.startActivity(intent);
        }
    }
}

