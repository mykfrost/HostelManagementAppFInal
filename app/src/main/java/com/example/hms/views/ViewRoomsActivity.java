package com.example.hms.views;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hms.R;
import com.example.hms.adapters.RoomBookingAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewRoomsActivity extends AppCompatActivity implements RoomBookingAdapter.OnItemClickListener {
    private RecyclerView recyclerView;
    private RoomBookingAdapter adapter;
    private List<String> itemList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_rooms);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        itemList = new ArrayList<>();
        itemList.add("Item 1");
        itemList.add("Item 2");
        itemList.add("Item 3");
        itemList.add("Item 4");
        itemList.add("Item 5");

        recyclerView = findViewById(R.id.recyclerviewRoomsRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RoomBookingAdapter();

        recyclerView.setAdapter(adapter);
        // Set item click listener for the adapter
       // adapter.OnItemClickListener(this);
    }
    @Override
    public void onItemClick(int position) {
        String selectedItem = itemList.get(position);
        Toast.makeText(this, "Selected Item: " + selectedItem, Toast.LENGTH_SHORT).show();
        // You can perform further actions based on the selected item here
    }
}