package dam.isi.frsf.utn.edu.ar.delivery.activity;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;
import com.koushikdutta.ion.future.ResponseFuture;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.zip.Inflater;

import dam.isi.frsf.utn.edu.ar.delivery.constants.OrderActivityConstants;
import dam.isi.frsf.utn.edu.ar.delivery.R;
import dam.isi.frsf.utn.edu.ar.delivery.model.ContainerType;
import dam.isi.frsf.utn.edu.ar.delivery.model.Flavor;
import dam.isi.frsf.utn.edu.ar.delivery.model.OrderItem;
import dam.isi.frsf.utn.edu.ar.delivery.service.DataService;
import dam.isi.frsf.utn.edu.ar.delivery.utility.Formatter;

public class OrderActivity extends AppCompatActivity {

    List<OrderItem> orderItems = null;
    List<Flavor> flavors = new ArrayList<>();
    ViewGroup insertPoint;
    FlavorsAdapter flavorsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Ion.getDefault(this).configure().setLogging("ion-sample", Log.DEBUG);

        setContentView(R.layout.activity_order);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        insertPoint = (ViewGroup) findViewById(R.id.view_insert_point);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        int contentState;
        if(savedInstanceState != null && savedInstanceState.containsKey(getString(R.string.order_content_key))){
            contentState = (int) savedInstanceState.get(getString(R.string.order_content_key));
        }
        else{
            contentState = OrderActivityConstants.CONTENT_ORDER_ITEMS;
        }

        switch (contentState){
            case OrderActivityConstants.CONTENT_ORDER_ITEMS:
                LayoutInflater inflater = getLayoutInflater();
                View orderItemsView = inflater.inflate(R.layout.listview_order_item,null);
                if(insertPoint.getChildCount() != 0){
                    insertPoint.removeAllViews();
                }
                insertPoint.addView(orderItemsView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

                ListView listViewItems = (ListView) orderItemsView.findViewById(R.id.listview_order_item);

                getSupportActionBar().setTitle("TU PEDIDO");
                orderItems = new ArrayList<OrderItem>();

                //TEST
                List<Flavor> testFlavors = new ArrayList<>();
                testFlavors.add(new Flavor().withLabel("frutilla"));

                OrderItem testOrder = new OrderItem()
                        .withContainerType(new ContainerType()
                                .withLabel("pote 1 kilo")
                                .withImgURL(getString(R.string.test_url)))
                        .withFlavors(testFlavors)
                        .withQuantity(2);

                orderItems.add(testOrder);
                //TEST

                listViewItems.setAdapter(new OrderAdapter(orderItems));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_order_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.moar_icecream:
                LayoutInflater inflater = getLayoutInflater();
                final GridView gridViewFlavors = (GridView) inflater.inflate(R.layout.gridview_flavor_choice,null);
                if(insertPoint.getChildCount() != 0){
                    insertPoint.removeAllViews();
                }
                insertPoint.addView(gridViewFlavors, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

                getSupportActionBar().setTitle("ELEGÍ LOS SABORES");
                DataService dataService = new DataService(OrderActivity.this);
                try {
                    dataService.getFlavors().setCallback(new FutureCallback<List<Flavor>>() {
                        @Override
                        public void onCompleted(Exception e, List<Flavor> result) {
                            flavors.addAll(result);
                            flavorsAdapter = new FlavorsAdapter(flavors);
                            gridViewFlavors.setAdapter(flavorsAdapter);
                            Log.d("MIRAME", "onCompleted: exito! flavors: "+ flavors.size());
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    class FlavorsAdapter extends ArrayAdapter<Flavor> {
        LayoutInflater inflater;

        FlavorsAdapter(List<Flavor> flavors) {
            super(OrderActivity.this, R.layout.gridview_cell_flavor_choice, flavors);
            inflater = LayoutInflater.from(getContext());
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, @NonNull ViewGroup parent) {
            View cell = convertView;
            if(cell == null) {
                cell = inflater.inflate(R.layout.gridview_cell_flavor_choice, parent, false);
            }
            FlavorHolder holder = (FlavorHolder) cell.getTag();
            if(holder == null) {
                holder = new FlavorHolder(cell);
                cell.setTag(holder);
            }

            Ion.with(holder.flavorPic)
                    .fitCenter()
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.error)
                    .load(this.getItem(position).getCompleteImgURL());
            holder.textViewName.setText(this.getItem(position).getLabel());

            return(cell);
        }
    }

    class FlavorHolder {
        ImageView flavorPic = null;
        TextView textViewName = null;
        ImageView checkedIcon = null;

        FlavorHolder(View cell) {
            this.flavorPic = (ImageView) cell.findViewById(R.id.flavor_picture);
            this.textViewName = (TextView) cell.findViewById(R.id.flavor_name);
            this.checkedIcon = (ImageView) cell.findViewById(R.id.flavor_checked);
        }
    }

    class OrderAdapter extends ArrayAdapter<OrderItem> {

        LayoutInflater inflater;

        OrderAdapter(List<OrderItem> orderItems) {
            super(OrderActivity.this, R.layout.listview_row_order_item, orderItems);
            inflater = LayoutInflater.from(getContext());
        }

        @NonNull
        public View getView(int position, View convertView, @NonNull ViewGroup parent) {

            View row = convertView;
            if (row == null) {
                row = inflater.inflate(R.layout.listview_row_order_item, parent, false);
            }
            OrderHolder holder = (OrderHolder)row.getTag();
            if(holder == null){
                holder = new OrderHolder(row);
                row.setTag(holder);
                holder.numberPickerQuantity.setMinValue(1);
                holder.numberPickerQuantity.setMaxValue(10);

                holder.numberPickerQuantity.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                        int myPosition=(int)picker.getTag();
                        getItem(myPosition).setQuantity(newVal);
                    }
                });
            }

            Ion.with(holder.containerPic)
                    .fitCenter()
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.error)
                    .load(this.getItem(position).getContainerType().getImgURL());
            holder.textViewContainer.setText(this.getItem(position).getContainerType().getLabel());
            holder.textViewFlavors.setText(Formatter.buildStringFromList(this.getItem(position).getFlavors()));
            holder.textViewAddins.setText(Formatter.buildStringFromList(this.getItem(position).getAddins()));
            holder.numberPickerQuantity.setValue(this.getItem(position).getQuantity());
            holder.numberPickerQuantity.setTag(position);

            return (row);
        }

        class OrderHolder{
            ImageView containerPic = null;
            TextView textViewContainer = null;
            TextView textViewFlavors = null;
            TextView textViewAddins = null;
            NumberPicker numberPickerQuantity = null;

            OrderHolder(View row){
                this.containerPic = (ImageView) row.findViewById(R.id.imageview_order_item_picture);
                this.textViewContainer = (TextView) row.findViewById(R.id.textview_order_item_container);
                this.textViewFlavors = (TextView) row.findViewById(R.id.textview_order_item_flavors);
                this.textViewAddins = (TextView) row.findViewById(R.id.textview_order_item_addins);
                this.numberPickerQuantity = (NumberPicker) row.findViewById(R.id.item_quantity);
            }
        }
    }
}
