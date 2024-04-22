package com.example.hms.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hms.R;
import com.example.hms.utils.Notification;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {

    private List<Notification> notificationList;

    private Context context;

    public NotificationAdapter(Context context, List<Notification> notificationList) {
        this.notificationList = notificationList;
        this.context = context;
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_item, parent, false);
        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        Notification notification = notificationList.get(position);

        holder.textSender.setText("Sender ID: " + notification.getSenderId());
        holder.textMessage.setText("Message: " + notification.getMessage());
        holder.textSentAt.setText("Sent At: " + notification.getSentAt());
    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    public static class NotificationViewHolder extends RecyclerView.ViewHolder {
        public TextView textSender;
        public TextView textMessage;
        public TextView textSentAt;

        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            textSender = itemView.findViewById(R.id.textSender);
            textMessage = itemView.findViewById(R.id.textMessage);
            textSentAt = itemView.findViewById(R.id.textSentAt);
        }
    }
}