package dam.isi.frsf.utn.edu.ar.delivery.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import dam.isi.frsf.utn.edu.ar.delivery.constants.OrderActivityConstants;
import dam.isi.frsf.utn.edu.ar.delivery.R;
import dam.isi.frsf.utn.edu.ar.delivery.model.Order;
import dam.isi.frsf.utn.edu.ar.delivery.utility.Formatter;

public class OrderActivity extends AppCompatActivity {

    List<Order.Order_> items = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_order);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        ViewStub stub = (ViewStub) findViewById(R.id.content_order);

        int contentState = (int) savedInstanceState.get(getString(R.string.order_content_key));

        switch (contentState){
            case OrderActivityConstants.CONTENT_ORDER_ITEMS:
                stub.setLayoutResource(R.layout.listview_order_item);
                ListView listViewItems = (ListView) stub.inflate();
                getSupportActionBar().setTitle("TU PEDIDO");
                items = getItems();
                listViewItems.setAdapter(new OrderAdapter(items));
        }


    }

    private List<Order.Order_> getItems(){
        //TODO Descargar items del server
        return new ArrayList<>();
    }

    class OrderAdapter extends ArrayAdapter<Order.Order_> {

        LayoutInflater inflater;

        OrderAdapter(List<Order.Order_> items) {
            super(OrderActivity.this, R.layout.listview_row_order_item, items);
            inflater = LayoutInflater.from(getContext());
        }

        public View getView(int position, View convertView, ViewGroup parent) {

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
                        // this.getItem(position).setQuantity(newVal);
                    }
                });
            }

            //read about glide here https://github.com/bumptech/glide

            holder.textViewContainer.setText(this.getItem(position).getContainerType().getName());
            holder.textViewFlavors.setText(Formatter.buildStringFromList(this.getItem(position).getFlavors()));
            holder.textViewAddins.setText(Formatter.buildStringFromList(this.getItem(position).getAddins()));
            holder.numberPickerQuantity.setValue(this.getItem(position).getQuantity());

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
