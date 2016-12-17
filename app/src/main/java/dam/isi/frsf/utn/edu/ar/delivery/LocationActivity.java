package dam.isi.frsf.utn.edu.ar.delivery;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;

public class LocationActivity extends AppCompatActivity implements
        OnMapReadyCallback {

    private static final int PERMISSION_REQUEST_ACCESS_FINE_LOCATION = 1;
    private static final int CODIGO_RESULTADO_ALTA_RECLAMO = 1;
    private GoogleMap mMap;
    private LocationManager locationManager;
    private ArrayList<LatLng> reclamos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager()
                        .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // Make sure the request was successful
        if (resultCode == RESULT_OK) {

        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {

            }
        });

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(final Marker marker) {

            }
        });

        setMyLocationEnabled();

        // set my desired location
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(-31.619276, -60.683970), 15);
        mMap.animateCamera(cameraUpdate);

    }

    @Override
    @SuppressWarnings("MissingPermission")
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case LocationActivity.PERMISSION_REQUEST_ACCESS_FINE_LOCATION: {
                // si el request es cancelado el arreglo es vacio.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mMap.setMyLocationEnabled(true);
                    // tengo el permiso!!!.
                } else {
                    Toast.makeText(this, "No Permiso", Toast.LENGTH_SHORT).show();
                    // no tenemos permisos
                }
            }
        }
    }

    public void setMyLocationEnabled() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(LocationActivity.this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(LocationActivity.this,
                        android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(LocationActivity.this);
                    builder.setTitle("Leer Contactos!");
                    builder.setPositiveButton(android.R.string.ok, null);
                    builder.setMessage("Puedo acceder a tus contactos?");
                    builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @TargetApi(Build.VERSION_CODES.M)
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            requestPermissions(
                                    new String[]
                                            {android.Manifest.permission.ACCESS_FINE_LOCATION}
                                    , PERMISSION_REQUEST_ACCESS_FINE_LOCATION);
                        }
                    });
                    builder.show();
                } else {
                    ActivityCompat.requestPermissions(LocationActivity.this,
                            new String[]
                                    {android.Manifest.permission.ACCESS_FINE_LOCATION},
                            PERMISSION_REQUEST_ACCESS_FINE_LOCATION);
                }
            } else {
                // ya tenia el permiso no lo tuve que pedir
                mMap.setMyLocationEnabled(true);
            }
        } else {
            // la versión alcanza con tenerlo declarado
            mMap.setMyLocationEnabled(true);
        }
    }

}
