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
import com.example.hms.utils.Room;
import com.example.hms.views.BookingDetailsActivity;

import java.util.List;


public class ViewBookingsAdapter extends RecyclerView.Adapter<ViewBookingsAdapter.BookingViewHolder>{
    private List<Booking> bookinglist;
    private Context context;


    public ViewBookingsAdapter(List<Booking> hostelList, Context context) {
        this.bookinglist = hostelList;
        this.context = context;
    }
    @NonNull
    @Override
    public ViewBookingsAdapter.BookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_hostel_name, parent, false);
        return new BookingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewBookingsAdapter.BookingViewHolder holder, int position) {
        Booking booking = bookinglist.get(position);

        holder.hostelName.setText(booking.getHostel_name());
        holder.student_name.setText(booking.getStudentId());
      //  holder.checkin.setText(booking.setCheckInDate());
        holder.checkout.setText(booking.getCheckOutDate());
        holder.roomtType.setText(booking.getRoomType());
       // holder.price.setText(booking.getTotalPrice());

        // Set price, check-in date, and check-out date
        holder.price.setText(String.valueOf(booking.getTotalPrice()));
        holder.checkin.setText(booking.getCheckInDate());
        holder.checkout.setText(booking.getCheckOutDate());



        holder.itemView.setOnClickListener(v -> {
            // Handle item click here
            Intent intent = new Intent(context, BookingDetailsActivity.class);
//            intent.putExtra("hostel_id", hostel.getId());
//            intent.putExtra("hostel_name", hostel.getHostelName());
//            intent.putExtra("description", hostel.getDescription());
//            intent.putExtra("address", hostel.getAddress());
//            intent.putExtra("city", hostel.getCity());
//            intent.putExtra("country", hostel.getCountry());
//            intent.putExtra("capacity", hostel.getCapacity());
            context.startActivity(intent);


       });
    }
    @Override
    public int getItemCount() {
        return bookinglist.size();
    }
    public static class BookingViewHolder extends RecyclerView.ViewHolder {
        TextView hostelName, student_name , checkin , checkout , roomtType, price;

        public BookingViewHolder(@NonNull View itemView) {
            super(itemView);
            hostelName = itemView.findViewById(R.id.item_hostel_name);
//            descriptionTextView = itemView.findViewById(R.id.item_description);
//            addressTextView = itemView.findViewById(R.id.item_address);
//            cityTextView = itemView.findViewById(R.id.item_city);
//            countryTextview = itemView.findViewById(R.id.item_country);

            // roomCount = itemView.findViewById(R.id.rooms_count);
        }
    }

}