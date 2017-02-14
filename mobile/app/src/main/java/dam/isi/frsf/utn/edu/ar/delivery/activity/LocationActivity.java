package dam.isi.frsf.utn.edu.ar.delivery.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.koushikdutta.async.future.FutureCallback;

import java.util.ArrayList;
import java.util.List;

import dam.isi.frsf.utn.edu.ar.delivery.R;
import dam.isi.frsf.utn.edu.ar.delivery.model.Location;
import dam.isi.frsf.utn.edu.ar.delivery.model.Order;
import dam.isi.frsf.utn.edu.ar.delivery.service.DataService;

public class LocationActivity extends AppCompatActivity implements
        OnMapReadyCallback {

    private static final LatLng mLocation = new LatLng(-31.619276, -60.683970);
    private GoogleMap googleMap;
    private AlertDialog.Builder noOrders;
    private DataService data;
    private List<Order> orders = new ArrayList<>();
    private ListView mListView;
    private OrderAdapter mOrderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        data = new DataService(LocationActivity.this);

        noOrders = new AlertDialog.Builder(LocationActivity.this)
                .setTitle("No hiciste pedidos recientes")
                .setMessage("Haz un pedido y luego vuelve a intentarlo")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });


        try {
            data.getOrders().setCallback(new FutureCallback<List<Order>>() {
                @Override
                public void onCompleted(Exception e, List<Order> result) {

                    final SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                            .findFragmentById(R.id.map);
                    mapFragment.getMapAsync(LocationActivity.this);
                    mapFragment.getView().setVisibility(View.GONE);

                    if (result == null) {
                        noOrders.show();
                        return;
                    }

                    if (result.size() == 0) {
                        noOrders.show();
                        return;
                    }

                    orders = result;
                    mListView = (ListView) findViewById(R.id.location_order_list);
                    mOrderAdapter = new OrderAdapter(orders);
                    mListView.setAdapter(mOrderAdapter);

                    mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Location destination = orders.get(position).getDestination();
                            Location currentLocation = orders.get(position).getLastLocation();

                            LatLng destinationLatLng =
                                    new LatLng(destination.getLatitude(), destination.getLongitude());
                            LatLng currentLocationLatLng =
                                    new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());


                            mapFragment.getView().setVisibility(View.VISIBLE);
                            updateMap(destinationLatLng, currentLocationLatLng);
                        }
                    });

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onMapReady(GoogleMap mMap) {

        googleMap = mMap;

        googleMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {

            }
        });

        googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {

            }
        });


        googleMap.addMarker(new MarkerOptions()
                .position(mLocation)
                .title("Chilly Willy")
                .snippet("heladeria")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.logo))
        );

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(mLocation, 15);
        googleMap.animateCamera(cameraUpdate);

    }

    private void updateMap(LatLng destination, LatLng currentLocation) {
        googleMap.addMarker(new MarkerOptions()
                .position(destination)
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.home))
                .title(""));

        googleMap.addMarker(new MarkerOptions()
                .position(currentLocation)
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.delivery))
                .title(""));

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(currentLocation, 13);
        googleMap.animateCamera(cameraUpdate);
    }

    class OrderAdapter extends ArrayAdapter<Order> {

        LayoutInflater inflater;

        public OrderAdapter(List<Order> orders) {
            super(LocationActivity.this, R.layout.listview_row_order_item, orders);
            inflater = LayoutInflater.from(LocationActivity.this);
        }

        public View getView(int position, View convertView, ViewGroup parent) {

            View row = convertView;
            if (row == null) {
                row = inflater.inflate(R.layout.listview_row_order, parent, false);
            }

            OrderHolder holder = (OrderHolder) row.getTag();
            if (holder == null) {
                holder = new OrderHolder(row);
                row.setTag(holder);
            }

            holder.dateTextView.setText(orders.get(position).getRequestTime());

            return row;
        }

        class OrderHolder {
            TextView dateTextView = null;

            OrderHolder(View row) {
                this.dateTextView = (TextView) row.findViewById(R.id.textview_order_date);
            }
        }

    }
}
