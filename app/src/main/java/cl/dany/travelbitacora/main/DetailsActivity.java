package cl.dany.travelbitacora.main;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import cl.dany.travelbitacora.R;
import cl.dany.travelbitacora.models.Place;

public class DetailsActivity extends AppCompatActivity {

    public static final int REQUEST_CODE = 1000;
    public static final String PLACE = "cl.dany.prueba_3.main.KEY.PLACE";
    private static final int RC_GEOLOCATION = 1234;
    private Place place;
    private FusedLocationProviderClient fusedLocationProviderClient;
    LocationRequest locationRequest;
    LocationCallback locationCallback;
    private com.github.clans.fab.FloatingActionButton button,btnmaps;


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_CODE: {
                if (grantResults.length > 0) {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this, "Permisos Concedidos", Toast.LENGTH_SHORT).show();
                    } else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                        Toast.makeText(this, "Permisos Denegados", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        place = (Place) this.getIntent().getSerializableExtra(MainActivityFragment.PLACE);

        btnmaps = findViewById(R.id.locationBtn);
        button = findViewById(R.id.btnSaveLocation);
        if (ActivityCompat.shouldShowRequestPermissionRationale(DetailsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)) {
            ActivityCompat.requestPermissions(DetailsActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
        } else {
            buildLocationRequest();
            buildLocationCallBack();
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        }


        if(place.getLatitude() != "" && place.getLongitude() != "") {
           button.setLabelText("Actualizar ubicación");
        }

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (ActivityCompat.checkSelfPermission(DetailsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION};
                        requestPermissions(permissions, RC_GEOLOCATION);
                    }
                    fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
                    button.setLabelText("Actualizar ubicación");

                }
            });

        btnmaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri location = Uri.parse("geo:"+place.getLatitude()+","+place.getLongitude()+"?z=14"); // z param is zoom level
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, location);
                startActivity(mapIntent);
            }
        });



    }

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    public void buildLocationCallBack() {
        locationCallback = new LocationCallback() {
            public void onLocationResult(LocationResult locationResult) {


                for (Location location : locationResult.getLocations()) {/*
                    textViewLat.setText(String.valueOf(location.getLatitude()));
                    textViewLon.setText(String.valueOf(location.getLongitude()));*/
                    Toast.makeText(DetailsActivity.this, "Ubicación guardada \n "+ String.valueOf(location.getLatitude())+" "+String.valueOf(location.getLongitude()), Toast.LENGTH_SHORT).show();
                    place.setLongitude(String.valueOf(location.getLongitude()));
                    place.setLatitude(String.valueOf(location.getLatitude()));



                    if (ActivityCompat.checkSelfPermission(DetailsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION};
                        requestPermissions(permissions, RC_GEOLOCATION);
                    }
                    fusedLocationProviderClient.removeLocationUpdates(locationCallback);
                }
            }
        };
    }
    public void buildLocationRequest()
    {
        locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(3000);
        locationRequest.setSmallestDisplacement(10);
    }

}
