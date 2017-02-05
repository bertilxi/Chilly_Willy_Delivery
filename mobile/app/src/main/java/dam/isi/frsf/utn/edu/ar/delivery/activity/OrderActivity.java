package dam.isi.frsf.utn.edu.ar.delivery.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.util.List;

import dam.isi.frsf.utn.edu.ar.delivery.R;
import dam.isi.frsf.utn.edu.ar.delivery.model.Order;
import dam.isi.frsf.utn.edu.ar.delivery.utility.Formatter;

public class OrderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(view ->
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show());

        ViewStub stub = (ViewStub) findViewById(R.id.content_order);
        stub.setLayoutResource(R.layout.listview_order_item);
        ListView kistViewItems = (ListView) stub.inflate();


    }

    class ImageAdapter extends ArrayAdapter<Order.Order_> {
        ImageAdapter(List<Order.Order_> items) {
            super(OrderActivity.this, R.layout.listview_row_order_item, items);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            View row = super.getView(position, convertView, parent);
            ImageView flavorPic = (ImageView) row.findViewById(R.id.flavor_picture);
            TextView textViewContainer = (TextView) row.findViewById(R.id.textview_order_item_container);
            textViewContainer.setText(this.getItem(position).getContainerType());
            TextView textViewFlavors = (TextView) row.findViewById(R.id.textview_order_item_flavors);
            textViewFlavors.setText(Formatter.buildStringFromList(this.getItem(position).getFlavors()));
            TextView textViewAddins = (TextView) row.findViewById(R.id.textview_order_item_addins);
            textViewAddins.setText(Formatter.buildStringFromList(this.getItem(position).getAddins()));
            NumberPicker numberPickerQuantity = (NumberPicker) row.findViewById(R.id.item_quantity);
            //don't forget to set minimum and maximum values for numberpicker
            numberPickerQuantity.setValue(this.getItem(position).getQuantity());


            return (row);
        }
    }
}
