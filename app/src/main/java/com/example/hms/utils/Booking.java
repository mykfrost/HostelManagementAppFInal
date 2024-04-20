package com.example.hms.utils;

public class Booking {
    public static final String TABLE_NAME = "bookings";
    private static final String KEY_ID = "id";
    private static final String KEY_STUDENT_ID = "student_id";
    private static final String KEY_HOSTEL_ID = "hostel_id";
    private static final String KEY_ROOM_ID = "room_id";
    public static final String  KEY_CHECK_IN_DATE = "check_in_date";
    public static final String  KEY_CHECK_OUT_DATE = "check_out_date";
    public static final String KEY_NUMBER_OF_STUDENTS = "number_of_students";
    public static final String KEY_TOTAL_PRICE = "total_price";


    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME +
            "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            KEY_STUDENT_ID + " INTEGER," +
            KEY_ROOM_ID + " INTEGER," +
            KEY_HOSTEL_ID + " INTEGER," +
            KEY_CHECK_IN_DATE + " DATETIME DEFAULT CURRENT_TIMESTAMP," +
            KEY_CHECK_OUT_DATE + " TEXT," +
            KEY_NUMBER_OF_STUDENTS + " INTEGER," +
            KEY_TOTAL_PRICE + " DECIMAL(10,2)," +
            "FOREIGN KEY(" + KEY_STUDENT_ID + ") REFERENCES " + User.TABLE_NAME + "(" + KEY_ID + ")," +
            "FOREIGN KEY(" + KEY_HOSTEL_ID + ") REFERENCES " + Hostel.TABLE_NAME + "(" + Hostel.KEY_ID + ")," +
            "FOREIGN KEY(" + KEY_ROOM_ID + ") REFERENCES " + Hostel.TABLE_NAME + "(" + Hostel.KEY_ID + "))";

    public Booking(int id ,int student_id, int room_id, String check_in, String check_out, String number_of_students, String price) {
       this.id = id;
        this.student_id = student_id;
        this.room_id = room_id;
        this.check_in = check_in;
        this.check_out = check_out;
        this.number_of_students = number_of_students;
        this.price = price;
    }

    public Booking() {
    }
    public int getStudent_id() {
        return student_id;
    }

    public void setStudent_id(int student_id) {
        this.student_id = student_id;
    }

    public int getRoom_id() {
        return room_id;
    }


    public String getCheck_in() {
        return check_in;
    }

    public void setCheck_in(String check_in) {
        this.check_in = check_in;
    }

    public String getCheck_out() {
        return check_out;
    }

    public void setCheck_out(String check_out) {
        this.check_out = check_out;
    }

    public String getNumber_of_students() {
        return number_of_students;
    }

    public void setNumber_of_students(String number_of_students) {
        this.number_of_students = number_of_students;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    private  int id;

    public int getHostel_id() {
        return hostel_id;
    }

    public void setHostel_id(int hostel_id) {
        this.hostel_id = hostel_id;
    }

    public void setRoom_id(int room_id) {
        this.room_id = room_id;
    }

    private  int hostel_id;
    private  int room_id;
    private  int student_id;
    private  String check_in;
    private  String check_out;
    private  String  number_of_students;
    private  String price;
}
