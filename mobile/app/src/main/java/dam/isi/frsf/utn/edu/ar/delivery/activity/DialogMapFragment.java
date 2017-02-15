package dam.isi.frsf.utn.edu.ar.delivery.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import dam.isi.frsf.utn.edu.ar.delivery.R;

public class DialogMapFragment extends DialogFragment implements
        OnMapReadyCallback {

    private OnCompleteListener mListener;
    private LatLng mLatLng;
    private SupportMapFragment fragment;


    public DialogMapFragment() {
        fragment = new SupportMapFragment();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnCompleteListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnArticleSelectedListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mapdialog, container, false);
        getDialog().setTitle("");
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.map_dialog, fragment).commit();

        return view;
    }

    public SupportMapFragment getFragment() {
        return fragment;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        googleMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                mLatLng = latLng;
            }
        });

    }

    public interface OnCompleteListener {
        void onComplete(LatLng latLng);
    }
}