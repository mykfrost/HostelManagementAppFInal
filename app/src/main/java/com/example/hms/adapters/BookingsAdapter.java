package com.example.hms.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hms.R;
import com.example.hms.utils.Booking;
import com.example.hms.views.BookingDetailsActivity;
import com.example.hms.views.ViewBookingsActivity;

import java.util.List;

public class BookingsAdapter extends RecyclerView.Adapter<BookingsAdapter.ViewHolder> {
    private List<Booking> bookingsList;
    private Context context;

    public BookingsAdapter( List<Booking> bookingsList ,  Context context) {
        this.context = context;
        this.bookingsList = bookingsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_booking, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Booking booking = bookingsList.get(position);
        holder.bind(booking);
    }

    @Override
    public int getItemCount() {
        return bookingsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView studentNameTextView;
        private TextView checkInDateTextView;
        private TextView checkOutDateTextView;
        private TextView roomTypeTextView;
        private TextView totalPriceTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            studentNameTextView = itemView.findViewById(R.id.studentNameTextView);
            checkInDateTextView = itemView.findViewById(R.id.checkInDateTextView);
            checkOutDateTextView = itemView.findViewById(R.id.checkOutDateTextView);
            roomTypeTextView = itemView.findViewById(R.id.roomTypeTextView);
            totalPriceTextView = itemView.findViewById(R.id.totalPriceTextView);

            itemView.setOnClickListener(this);
        }

        public void bind(Booking booking) {
            studentNameTextView.setText(booking.getStudentId());
            checkInDateTextView.setText("Check-In Date: " + booking.getCheckInDate());
            checkOutDateTextView.setText("Check-Out Date: " + booking.getCheckOutDate());
            roomTypeTextView.setText("Room Type: " + booking.getRoomType());
            totalPriceTextView.setText("Total Price: $" + booking.getTotalPrice());
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Booking booking = bookingsList.get(position);
            // Navigate to another activity to display more details
            Intent intent = new Intent(context, ViewBookingsActivity.class);
            intent.putExtra("booking_id", booking.getId());
            intent.putExtra("student_name", booking.getStudentId());
            intent.putExtra("check_in_date", booking.getCheckInDate());
            intent.putExtra("check_out_date", booking.getCheckOutDate());
            intent.putExtra("room_type", booking.getRoomType());
            intent.putExtra("total_price", booking.getTotalPrice());
            context.startActivity(intent);
        }
    }
}

