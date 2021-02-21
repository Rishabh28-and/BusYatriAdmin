package com.example.busyatriadmin;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.util.ULocale;
import android.net.Uri;
import android.os.Bundle;

import com.example.busyatriadmin.Common.Common;
import com.example.busyatriadmin.Interface.ItemClickListener;
import com.example.busyatriadmin.Model.Bus;
import com.example.busyatriadmin.ViewHolder.BusViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.LayoutInflater;
import android.view.View;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;
import java.util.UUID;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView txtFullName;

    FirebaseDatabase database;
    DatabaseReference buses;
    FirebaseStorage storage;
    StorageReference storageReference;
    FirebaseRecyclerAdapter<Bus, BusViewHolder> adapter;
    RecyclerView recycler_menu;
    RecyclerView.LayoutManager layoutManager;
    MaterialEditText editName;
    MaterialEditText editNumber;
    MaterialEditText editDeparture;
    MaterialEditText editDestination;
    MaterialEditText editContact;
    MaterialEditText editPrice;
    Button btnUpload,btnSelect;
    Uri saveUri;
    Bus newbus;
    private final int PICK_IMAGE_REQUEST=71;
    DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Menu Management");
        setSupportActionBar(toolbar);

        database = FirebaseDatabase.getInstance();
        buses = database.getReference("Bus");
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();




        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              showDialog();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        txtFullName=headerView.findViewById(R.id.txtFullName);
        txtFullName.setText(Common.currentUser.getName());
        recycler_menu = findViewById(R.id.recycler_menu);
        recycler_menu.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recycler_menu.setLayoutManager(layoutManager);

        LoadBus();
    }

    private void showDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Home.this);
        alertDialog.setTitle("Add new Bus");
        alertDialog.setMessage("Please fill full information");

        LayoutInflater inflater = this.getLayoutInflater();
        View add_new_bus_layout = inflater.inflate(R.layout.add_new_bus_layout,null);

        editName= add_new_bus_layout.findViewById(R.id.edtName);
        editNumber= add_new_bus_layout.findViewById(R.id.edtNumber);
        editDeparture= add_new_bus_layout.findViewById(R.id.edtDeparture);
        editDestination= add_new_bus_layout.findViewById(R.id.edtDestination);
        editContact= add_new_bus_layout.findViewById(R.id.edtContact);
        editPrice= add_new_bus_layout.findViewById(R.id.edtPrice);
        btnSelect = add_new_bus_layout.findViewById(R.id.btnSelect);
        btnUpload = add_new_bus_layout.findViewById(R.id.btnUpload);
        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });

        alertDialog.setView(add_new_bus_layout);
        alertDialog.setIcon(R.drawable.ic_directions_bus_black_24dp);
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (newbus != null)
                {
                    buses.push().setValue(newbus);
                    Toast.makeText(Home.this, " new Bus "+newbus.getBusName()+" was added ", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }

            }
        });
        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });
        alertDialog.show();




    }

    private void uploadImage() {
        if(saveUri != null){

            final ProgressDialog mDialog = new ProgressDialog(this);
            mDialog.setMessage("Uploading...");
            mDialog.show();
            final String imageName = UUID.randomUUID().toString();
            final StorageReference imagefolder = storageReference.child("image/" +imageName);

            imagefolder.putFile(saveUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    mDialog.dismiss();
                    Toast.makeText(Home.this, "Uploaded !!", Toast.LENGTH_SHORT).show();
                    imagefolder.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            newbus = new Bus();
                            newbus.setBusName(editName.getText().toString());
                            newbus.setBusNumber(editNumber.getText().toString());
                            newbus.setDeparture(editDeparture.getText().toString());
                            newbus.setDestination(editDestination.getText().toString());
                            newbus.setContact(editContact.getText().toString());
                            newbus.setPrice(editPrice.getText().toString());
                            newbus.setImage(uri.toString());



                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    mDialog.dismiss();
                    Toast.makeText(Home.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0* taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                    mDialog.setMessage("Uploaded  "+progress+ "%");
                }
            });
        }
    }



    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Picture"),PICK_IMAGE_REQUEST);

    }

    private void LoadBus() {
        adapter = new FirebaseRecyclerAdapter<Bus, BusViewHolder>(
                Bus.class,R.layout.bus_item,BusViewHolder.class,buses) {
            @Override
            protected void populateViewHolder(BusViewHolder busViewHolder, Bus bus, int i) {
                busViewHolder.bus_name.setText(bus.getBusName());
                busViewHolder.dep_dest.setText(bus.getDeparture()+ "-"+ bus.getDestination());
                Picasso.with(Home.this).load(bus.getImage())
                        .into(busViewHolder.bus_image);
                busViewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean IsLongClick) {


                    }
                });

                busViewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean IsLongClick) {
                        Intent intent = new Intent(Home.this,BusDetail.class);
                        intent.putExtra("BUSID",adapter.getRef(position).getKey());
                        startActivity(intent);
                    }
                });
            }
        };
        adapter.notifyDataSetChanged();
        recycler_menu.setAdapter(adapter);
    }
    @Override
    protected  void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data !=null && data.getData() !=null)
        {
            saveUri = data.getData();
            btnSelect.setText("Image Selected !");
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if(item.getItemId() == R.id.nav_booking){
            startActivity(new Intent(getApplicationContext(),BookingStatus.class));
            finish();

        }
        if(item.getItemId() == R.id.nav_signout){
            startActivity(new Intent(getApplicationContext(),BookingStatus.class));
            finish();

        }



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {

        if(item.getTitle().equals(Common.UPDATE))
        {
            showUpdateDialog(adapter.getRef(item.getOrder()).getKey(),adapter.getItem(item.getOrder()));
        }else if(item.getTitle().equals(Common.DELETE))
        {
            deleteBus(adapter.getRef(item.getOrder()).getKey());
        }
        return super.onContextItemSelected(item);


    }

    private void deleteBus(String key) {
        buses.child(key).removeValue();
    }

    private void showUpdateDialog(final String key, final Bus item) {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Home.this);
        alertDialog.setTitle("Update Bus details");
        alertDialog.setMessage("Please fill full information");

        LayoutInflater inflater = this.getLayoutInflater();
        View add_new_bus_layout = inflater.inflate(R.layout.add_new_bus_layout,null);

        editName= add_new_bus_layout.findViewById(R.id.edtName);
        editNumber= add_new_bus_layout.findViewById(R.id.edtNumber);
        editDeparture= add_new_bus_layout.findViewById(R.id.edtDeparture);
        editDestination= add_new_bus_layout.findViewById(R.id.edtDestination);
        editContact= add_new_bus_layout.findViewById(R.id.edtContact);
        editPrice= add_new_bus_layout.findViewById(R.id.edtPrice);
        btnSelect = add_new_bus_layout.findViewById(R.id.btnSelect);
        btnUpload = add_new_bus_layout.findViewById(R.id.btnUpload);

        editName.setText(item.getBusName());
        editNumber.setText(item.getBusNumber());
        editDeparture.setText(item.getDeparture());
        editDestination.setText(item.getDestination());
        editContact.setText(item.getContact());
        editPrice.setText(item.getPrice());

        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeImage(item);
            }

        });

        alertDialog.setView(add_new_bus_layout);
        alertDialog.setIcon(R.drawable.ic_directions_bus_black_24dp);
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
              dialog.dismiss();
              item.setBusName(editName.getText().toString());
                item.setBusNumber(editNumber.getText().toString());
                item.setDeparture(editDeparture.getText().toString());
                item.setDestination(editDestination.getText().toString());
                item.setContact(editContact.getText().toString());
                item.setPrice(editPrice.getText().toString());
                buses.child(key).setValue(item);
            }
        });
        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });
        alertDialog.show();


    }

    private void changeImage(final Bus item) {
        if(saveUri != null){

            final ProgressDialog mDialog = new ProgressDialog(this);
            mDialog.setMessage("Uploading...");
            mDialog.show();
            final String imageName = UUID.randomUUID().toString();
            final StorageReference imagefolder = storageReference.child("image/" +imageName);

            imagefolder.putFile(saveUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    mDialog.dismiss();
                    Toast.makeText(Home.this, "Uploaded !!", Toast.LENGTH_SHORT).show();
                    imagefolder.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                           item.setImage(uri.toString());


                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    mDialog.dismiss();
                    Toast.makeText(Home.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0* taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                    mDialog.setMessage("Uploaded  "+progress+ "%");
                }
            });
        }
    }
}
