package com.example.busyatriadmin;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.busyatriadmin.Common.Common;
import com.example.busyatriadmin.Model.Booking;
import com.example.busyatriadmin.ViewHolder.BookingViewHolder;
import com.example.busyatriadmin.Common.Common;
import com.example.busyatriadmin.Model.Booking;
import com.example.busyatriadmin.ViewHolder.BookingViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BookingStatus extends AppCompatActivity {

    public RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;

    FirebaseRecyclerAdapter<Booking, BookingViewHolder> adapter;

    FirebaseDatabase database;
    DatabaseReference booking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_status);

        database = FirebaseDatabase.getInstance();
        booking = database.getReference("Booking");
        recyclerView = findViewById(R.id.listBookings);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        loadBookings();
    }

    private void loadBookings() {
        adapter = new FirebaseRecyclerAdapter<Booking, BookingViewHolder>(
                Booking.class,
                R.layout.booking_layout,
                BookingViewHolder.class,
                booking
        ) {
            @Override
            protected void populateViewHolder(BookingViewHolder bookingViewHolder, Booking booking, int i) {
                bookingViewHolder.txtBookingId.setText(adapter.getRef(i).getKey());
                bookingViewHolder.txtBookingPhone.setText("Contact number : "+booking.getContactNumber());
                bookingViewHolder.txtBookingName.setText("Passenger name : "+booking.getName());
                bookingViewHolder.txtBookingBusname.setText("Bus name : "+booking.getBusName());
                bookingViewHolder.txtBookingDep.setText(booking.getDeparture());
                bookingViewHolder.txtBookingDest.setText(booking.getDestination());
                bookingViewHolder.txtBookingDate.setText("Travel Date : "+booking.getTravelDate());


            }
        };
       recyclerView.setAdapter(adapter);
    }


}



