package com.example.busyatriadmin.ViewHolder;

import android.view.View;
import android.widget.TextView;

import com.example.busyatriadmin.Interface.ItemClickListener;
import com.example.busyatriadmin.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BookingViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView txtBookingId ,txtBookingPhone,txtBookingBusname,txtBookingStatus,txtBookingName,txtBookingDep,txtBookingDest,txtBookingDate;
    private ItemClickListener itemClickListener;
    public BookingViewHolder(@NonNull View itemView) {
        super(itemView);

        txtBookingId = itemView.findViewById(R.id.booking_id);
        txtBookingName = itemView.findViewById(R.id.booking_name);
        txtBookingBusname = itemView.findViewById(R.id.booking_busname);
        txtBookingPhone = itemView.findViewById(R.id.booking_contact);
        txtBookingStatus=itemView.findViewById(R.id.nav_booking);

        txtBookingDep = itemView.findViewById(R.id.booking_dep);
        txtBookingDest = itemView.findViewById(R.id.booking_dest);
        txtBookingDate=itemView.findViewById(R.id.booking_date);
        itemView.setOnClickListener(this);
    }



    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition(),false);

    }
}
