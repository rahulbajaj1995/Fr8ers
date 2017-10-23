package com.truxapp.fbfire.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.Dash;
import com.google.android.gms.maps.model.Gap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PatternItem;
import com.google.android.gms.maps.model.PolylineOptions;
import com.truxapp.fbfire.Model.FindLoadModel;
import com.truxapp.fbfire.Model.FlightDataRealm;
import com.truxapp.fbfire.Model.RealmController;
import com.truxapp.fbfire.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.realm.Realm;


public class ProjectionMapFragment extends Fragment {
    MapView mMapView;
    private GoogleMap googleMap;
    Realm realm;
    ArrayList<LatLng> points = new ArrayList<LatLng>();
    Double src_lat, src_long, des_lat, des_long;
    LatLng l1, l2, Latlng1, Latlng2;
    FindLoadModel  findLoadModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.projection_map, container, false);
        FlightDataRealm result = RealmController.with(getActivity()).getFlightDataRealm();

        src_lat = Double.valueOf(result.getSourceairportlatitude());
        src_long = Double.valueOf(result.getSourceairportlongitude());

        String sdf = result.getDestinationairportlatitude();

        if(result.getDestinationairportlatitude()!= null && !result.getDestinationairportlatitude().equalsIgnoreCase("null")) {
            des_lat = Double.valueOf(result.getDestinationairportlatitude());
        }
        if (result.getDestinationairportlongitude()!=null && !result.getDestinationairportlongitude().equalsIgnoreCase("null")) {
            des_long = Double.valueOf(result.getDestinationairportlongitude());
        }

        l1=new LatLng(src_lat, src_long);
        if (des_long!=null && des_lat!=null) {
            l2 = new LatLng(des_lat, des_long);
        }

        mMapView = (MapView) rootView.findViewById(R.id.projection_map);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();
        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;
                googleMap.addMarker(new MarkerOptions().position(l1));
                if (l2!=null) {
                    googleMap.addMarker(new MarkerOptions().position(l2));
                    addLines();
                }
            }
        });
        return rootView;
    }

    private void addLines() {
        points.add(l1);
        points.add(l2);
/*        CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(points.get(0).latitude,points.get(0).longitude)).zoom(1).build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));*/
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        Latlng1 = new LatLng(points.get(0).latitude,points.get(0).longitude);
        Latlng2 = new LatLng(points.get(1).latitude,points.get(1).longitude);

        builder.include(Latlng1);
        builder.include(Latlng2);
        LatLngBounds bounds = builder.build();
        googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 20));




        List<PatternItem> dashedPattern = Arrays.asList(new Dash(20), new Gap(20));
        googleMap.addPolyline(new PolylineOptions().addAll(points).width(10).color(Color.BLUE).geodesic(true));
        googleMap.addPolyline(new PolylineOptions().addAll(points).width(10).color(Color.BLUE).geodesic(true).pattern(dashedPattern));
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }
}