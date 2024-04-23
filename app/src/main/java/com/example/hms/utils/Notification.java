package com.example.hms.utils;

public class Notification {
    public static final String TABLE_NAME = "notification";
    public static final String KEY_ID = "id";
    public static final String KEY_HOSTEL_ID = "hostel_id";
    public static final String KEY_SENDER_ID = "sender_id";
    public static final String KEY_RECEIVER_ID = "receiver_id";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_SENT_AT = "sent_at";

    private int id;
    private int hostelId;
    private int senderId;
    private int receiverId;
    private String message;
    private String sentAt;

    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + Notification.TABLE_NAME +
            "(" + Notification.KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            Notification.KEY_HOSTEL_ID + " INTEGER," +
            Notification.KEY_SENDER_ID + " INTEGER," +
            Notification.KEY_RECEIVER_ID + " INTEGER," +
            Notification.KEY_MESSAGE + " TEXT," +
            Notification.KEY_SENT_AT + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
            "FOREIGN KEY(" + Notification.KEY_HOSTEL_ID + ") REFERENCES hostel(id)," +
            "FOREIGN KEY(" + Notification.KEY_SENDER_ID + ") REFERENCES user(id)," +
            "FOREIGN KEY(" + Notification.KEY_RECEIVER_ID + ") REFERENCES student(id))";


    public Notification() {
    }

    public Notification(int id, int hostelId, int senderId, int receiverId, String message, String sentAt) {
        this.id = id;
        this.hostelId = hostelId;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.message = message;
        this.sentAt = sentAt;
    }
    public Notification(int id, int senderId, int receiverId, String message, String sentAt) {
        this.id = id;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.message = message;
        this.sentAt = sentAt;
    }

    // Getters and setters for private fields

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHostelId() {
        return hostelId;
    }

    public void setHostelId(int hostelId) {
        this.hostelId = hostelId;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public int getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(int receiverId) {
        this.receiverId = receiverId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSentAt() {
        return sentAt;
    }

    public void setSentAt(String sentAt) {
        this.sentAt = sentAt;
    }
}