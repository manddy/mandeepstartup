package com.mandeep.officialcode;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {
    static int count = 0;
    static int count1 = 0;
    private GoogleMap mMap;
    GoogleApiClient mgoogleApiClient;
    Location mlastlocation;
    LocationRequest mlocationrequest;
    SupportMapFragment mapFragment;
    String naam;
    ImageView iv1;
    final Context context = this;
    private ArrayList<Userinformation> userinformationArrayList = new ArrayList<Userinformation>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MapsActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);
        } else {
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Bundle bundle = getIntent().getExtras();
        naam = bundle.getString("PersonName");
        //   mMap.setOnMarkerClickListener(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        buildGoogleApiClient();
        mMap.setMyLocationEnabled(true);
        if (mMap != null) {
            mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                @Override
                public View getInfoWindow(Marker marker) {
                    return null;
//
//                    return  null;
                }

                @Override
                public View getInfoContents(Marker marker) {
                    View view = getLayoutInflater().inflate(R.layout.markernew, null);
                    TextView name = (TextView)view.findViewById(R.id.tv_locality);
                    iv1=(ImageView)view.findViewById(R.id.imageView1);
                    if(marker.getTitle().toString().equals("Mandeep") ||marker.getTitle().toString().equals("mandeep") ||marker.getTitle().toString().equals("Mandy"))
                    {
                        iv1.setImageResource(R.drawable.mandeep);
                    }
                    if(marker.getTitle().toString().equals("Sushant") ||marker.getTitle().toString().equals("sushant") ||marker.getTitle().toString().equals("Sushi"))
                    {
                        iv1.setImageResource(R.drawable.sushant);
                    }
                    if(marker.getTitle().toString().equals("Vrinda") ||marker.getTitle().toString().equals("vrinda") )
                    {
                        iv1.setImageResource(R.drawable.vrinda);
                    }
                    if(marker.getTitle().toString().equals("Jayant") ||marker.getTitle().toString().equals("jayant") )
                    {
                        iv1.setImageResource(R.drawable.jayant);
                    }

                        name.setText(marker.getTitle());

                    return view;
                }
            });
        }
        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Intent intent=new Intent(MapsActivity.this,abc.class);
                startActivity(intent);
            }
        });

    }

    protected synchronized void buildGoogleApiClient() {
        mgoogleApiClient = new GoogleApiClient.Builder(this).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(LocationServices.API).build();
        mgoogleApiClient.connect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mlocationrequest = new LocationRequest();
        mlocationrequest.setInterval(1000);
        mlocationrequest.setFastestInterval(1000);
        mlocationrequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MapsActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);
        }

        LocationServices.FusedLocationApi.requestLocationUpdates(mgoogleApiClient, mlocationrequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    final int LOCATION_REQUEST_CODE = 1;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case LOCATION_REQUEST_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mapFragment.getMapAsync(this);
                }
                break;
            }

        }
    }

    @Override
    public void onLocationChanged(Location location) {
        mlastlocation = location;
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        //mMap.addMarker(new MarkerOptions().position(latLng));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        // mMap.animateCamera(CameraUpdateFactory.zoomTo(11));
        final DatabaseReference mUsers = FirebaseDatabase.getInstance().getReference("USERS");
        mUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    Userinformation userinformation = (Userinformation) d.getValue(Userinformation.class);
                    if (userinformation.name.equals(naam)) {
                        count++;
                        Toast.makeText(MapsActivity.this, naam, Toast.LENGTH_LONG).show();
                        Userinformation ui = new Userinformation(userinformation.name, userinformation.DatsmeId, userinformation.email, userinformation.age, userinformation.password, mlastlocation.getLatitude(), mlastlocation.getLongitude());
                        mUsers.child(userinformation.DatsmeId).setValue(ui);
                        LatLng latLng1 = new LatLng(mlastlocation.getLatitude(), mlastlocation.getLongitude());
                        if (userinformation.lattitude == mlastlocation.getLatitude() && userinformation.longitude == mlastlocation.getLongitude()) {

                        } else {
                            mMap.clear();
                            MarkerOptions mo = new MarkerOptions().position(latLng1).title(userinformation.name);
                            mMap.addMarker(mo).setIcon(BitmapDescriptorFactory.fromResource(R.drawable.boy));
                            if (count == 1) {
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng1, 12.2f));
                                count = 0;
                            }
                        }


                    }
                    
                    count1++;
                    LatLng latLng = new LatLng(userinformation.lattitude, userinformation.longitude);
                    if (count1 == 1) {
                        MarkerOptions mo = new MarkerOptions().position(latLng).title(userinformation.name);
                        mMap.addMarker(mo).setIcon(BitmapDescriptorFactory.fromResource(R.drawable.boy));
                        count1 = 0;
                    }


                    // Log.d("MapsActivity", "ankur" + userinformation.lattitude);//ruk ja bhai shanti rakh
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }


        });

        //if(mMap!=null)
        //{
        // mMap.clear();
        //}

    }



}

