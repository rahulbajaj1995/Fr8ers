package com.truxapp.fbfire.Fragment;

import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.truxapp.fbfire.Activity.HomeActivity1;
import com.truxapp.fbfire.Activity.Profile1;
import com.truxapp.fbfire.Model.AircraftModel;
import com.truxapp.fbfire.R;
import com.truxapp.fbfire.response.AvailableAircraftResponse;
import com.truxapp.fbfire.response.BaseResponse;
import com.truxapp.fbfire.util.Constants;
import com.truxapp.fbfire.web.WebcallMaegerClass;

import java.util.ArrayList;
import java.util.Arrays;

import static android.content.Context.LOCATION_SERVICE;

public class MapFragmentt extends CommonBaseFragment{
    MapView mMapView;
    ImageView menu_item;
    LinearLayout first_icon_LL, second_icon_LL, third_icon_LL;
    private GoogleMap googleMap;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_map, container, false);
        mMapView = (MapView) rootView.findViewById(R.id.mapView);
        menu_item = (ImageView) rootView.findViewById(R.id.menu_item);
        first_icon_LL = (LinearLayout) rootView.findViewById(R.id.first_icon_LL);
        second_icon_LL = (LinearLayout) rootView.findViewById(R.id.second_icon_LL);
        third_icon_LL = (LinearLayout) rootView.findViewById(R.id.third_icon_LL);

        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();


        first_icon_LL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((HomeActivity1)getActivity()).callSlider();
            }
        });
        second_icon_LL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), com.truxapp.fbfire.Activity.QuotationHistory.class);
                startActivity(intent);
            }
        });
        third_icon_LL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Profile1.class);
                startActivity(intent);
            }
        });

        menu_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((HomeActivity1)getActivity()).openDrawerMenu();
            }
        });

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;
            }
        });


        checkForValidLocation();
        availableAircraftAPI();

        return rootView;
    }

    private void availableAircraftAPI(){
        WebcallMaegerClass.getInstance().availableAircraft(getActivity(), this, Constants.GET_REQUEST, null, false);
    }

    @Override
    public <T> void processFragmentResponse(T result) {
        super.processFragmentResponse(result);
        BaseResponse baseResponse = (BaseResponse) result;
        if (result instanceof AvailableAircraftResponse)
            if (baseResponse.getErrorCode().equalsIgnoreCase("201")) {
                AvailableAircraftResponse res = (AvailableAircraftResponse) result;
                AircraftModel aircraftModel[] = res.getResult();
                final ArrayList<AircraftModel> aircraftModels = new ArrayList<>(Arrays.asList(aircraftModel));
                if (aircraftModels.size()>0) {
                    for (int i = 0; i < aircraftModels.size(); i++) {

                        googleMap.getUiSettings().setMapToolbarEnabled(false);
                        googleMap.getUiSettings().setCompassEnabled(false);
                        Double latitude = aircraftModels.get(i).getLatitude();
                        Double longitude = aircraftModels.get(i).getLongitude();

                        try {
                            if (latitude==null && longitude==null){

                            }else {
                                drawMarker(new LatLng(latitude, longitude), aircraftModels);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        /*LatLngBounds.Builder builder = new LatLngBounds.Builder();
                            Latlng1 = new LatLng(aircraftModels.get(0).getLatitude(),aircraftModels.get(0).getLongitude());
                            Latlng2 = new LatLng(aircraftModels.get(aircraftModels.size()-1).getLatitude(),aircraftModels.get(aircraftModels.size()-1).getLongitude());

                            builder.include(Latlng1);
                            builder.include(Latlng2);
                            LatLngBounds bounds = builder.build();
//                          googleMap.moveCamera(CameraUpdateFactory.zoomOut());
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds,50));*/

//                        CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(aircraftModels.get(0).getLatitude(), aircraftModels.get(0).getLongitude())).zoom(3).build();
//                       // CameraPosition cameraPosition = new CameraPosition.Builder().target().zoom(1).build();
//                        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

//                        checkForValidLocation();

                       /* final LocationManager locationManager = (LocationManager)this.getActivity().getSystemService(LOCATION_SERVICE);
                        Criteria criteria = new Criteria();
                        bestProvider = locationManager.getBestProvider(criteria, true);
                        location = locationManager.getLastKnownLocation(bestProvider);


                            if (location != null) {
                                onLocationChanged(location);
                            }

                            locationManager.requestLocationUpdates(bestProvider, 20000, 0, this);
*/

                    }
                }
            } else {
                Toast.makeText(getContext(), baseResponse.getErrorCode(), Toast.LENGTH_SHORT).show();
            }
    }


    @Override
    public void getLatLong(double latitude, double longitude) {
        super.getLatLong(latitude, longitude);

        CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(latitude, longitude)).zoom(1).build();
        googleMap.animateCamera (CameraUpdateFactory.newCameraPosition(cameraPosition));
        googleMap.setMyLocationEnabled(true);
        View locationButton = ((View) mMapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
        RelativeLayout.LayoutParams rlp = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();
        rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
        rlp.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
        rlp.setMargins(0, 180, 0, 0);



    }

    private void drawMarker(LatLng point, final ArrayList<AircraftModel> list){
        // Creating an instance of MarkerOptions
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_plane));
        markerOptions.position(point);
        Marker marker = googleMap.addMarker(markerOptions);

        googleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                View v = getLayoutInflater(getArguments()).inflate(R.layout.text_view, null);

                TextView airportDetail = (TextView) v.findViewById(R.id.textView);
                LatLng latLng = marker.getPosition();
                Double lat = latLng.latitude;
                Double lng = latLng.longitude;

                try {
                    for(int  i=0; i<list.size(); i++) {
                        if (list.get(i).getLatitude() == null && list.get(i).getLongitude() == null) {
                        }else {
                            if (lat.doubleValue() == list.get(i).getLatitude().doubleValue()) {
                                if (lng.doubleValue() == list.get(i).getLongitude().doubleValue()) {
                                    airportDetail.setText(list.get(i).getAirportdetail());
                                    break;
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return v;
            }

            @Override
            public View getInfoContents(Marker marker) {
                return null;
            }
        });
        marker.showInfoWindow();
        marker.hideInfoWindow();
//        googleMap.setMyLocationEnabled(true);
        /*googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(point,15));*/
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
