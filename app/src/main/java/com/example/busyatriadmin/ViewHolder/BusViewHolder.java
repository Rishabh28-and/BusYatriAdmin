package com.example.busyatriadmin.ViewHolder;

import android.view.ContextMenu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.busyatriadmin.Common.Common;
import com.example.busyatriadmin.Interface.ItemClickListener;
import com.example.busyatriadmin.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BusViewHolder extends RecyclerView.ViewHolder implements
        View.OnClickListener,
        View.OnCreateContextMenuListener

{

    public TextView bus_name;
    public ImageView bus_image;
    public TextView dep_dest;
    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public BusViewHolder(@NonNull View itemView) {
        super(itemView);
        bus_name = (TextView)itemView.findViewById(R.id.bus_name);
        bus_image = (ImageView) itemView.findViewById(R.id.bus_image);
        dep_dest=(TextView)itemView.findViewById(R.id.dep_dest);
        itemView.setOnClickListener(this);
        itemView.setOnCreateContextMenuListener(this);
    }


    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition(),false);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("Select the action");

        menu.add(0,0,getAdapterPosition(),Common.UPDATE);
        menu.add(0,1,getAdapterPosition(), Common.DELETE);
    }
}
