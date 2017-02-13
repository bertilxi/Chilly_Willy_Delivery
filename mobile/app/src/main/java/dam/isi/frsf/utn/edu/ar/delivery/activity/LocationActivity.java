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
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.koushikdutta.async.future.FutureCallback;

import java.util.ArrayList;
import java.util.List;

import dam.isi.frsf.utn.edu.ar.delivery.R;
import dam.isi.frsf.utn.edu.ar.delivery.constants.appConstants;
import dam.isi.frsf.utn.edu.ar.delivery.model.Order;
import dam.isi.frsf.utn.edu.ar.delivery.service.DataService;

public class LocationActivity extends AppCompatActivity implements
        OnMapReadyCallback {

    private final AlertDialog.Builder noOrders = new AlertDialog.Builder(LocationActivity.this)
            .setTitle("No hiciste pedidos recientes")
            .setMessage("Haz un pedido y luego vuelve a intentarlo")
            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // continue with delete
                }
            })
            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // do nothing
                }
            });
    private DataService data;
    private List<Order> orders = new ArrayList<>();
    private ListView mListView;
    private OrderAdapter mOrderAdapter;
    private LatLng mLatLng = new LatLng(-31.619276, -60.683970);

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        data = new DataService(LocationActivity.this);


        try {
            data.getOrders(appConstants.deviceID).setCallback(new FutureCallback<List<Order>>() {
                @Override
                public void onCompleted(Exception e, List<Order> result) {

                    if (result == null) {

                        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                                .findFragmentById(R.id.map);
                        mapFragment.getMapAsync(LocationActivity.this);
                        mapFragment.getView().setVisibility(View.GONE);

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
                            mLatLng = orders.get(position).getLastLocation();

                            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                                    .findFragmentById(R.id.map);
                            mapFragment.getMapAsync(LocationActivity.this);
                        }
                    });

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

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

        // set my desired location
        googleMap.addMarker(new MarkerOptions().position(mLatLng).title(getString(R.string.location_order_label)));
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(mLatLng, 15);
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
