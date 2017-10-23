package com.truxapp.fbfire.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
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
import com.truxapp.fbfire.Activity.ApprovedQuotation1;
import com.truxapp.fbfire.Activity.DeniedQuotation1;
import com.truxapp.fbfire.Activity.PendingQuotation1;
import com.truxapp.fbfire.Activity.SharedQuotation1;
import com.truxapp.fbfire.Model.AircraftModel;
import com.truxapp.fbfire.Model.CountQuotationHistoryModel;
import com.truxapp.fbfire.R;
import com.truxapp.fbfire.response.AvailableAircraftResponse;
import com.truxapp.fbfire.response.CountQuotationHistoryResponse;
import com.truxapp.fbfire.util.Constants;
import com.truxapp.fbfire.web.WebcallMaegerClass;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

import static android.content.Context.LOCATION_SERVICE;
import static com.facebook.FacebookSdk.getApplicationContext;

public class QuotationHistoryMap extends CommonBaseFragment {
    MapView mMapView;
    private GoogleMap googleMap;
    TextView pendingText, sharedText, approvedText, deniedText;
    LinearLayout shared_quotation_layout, pending_quotation_layout, approved_quotation_layout, denied_quotation_layout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_quotation_history_map, container, false);
        mMapView = (MapView) rootView.findViewById(R.id.mapView);
        shared_quotation_layout = (LinearLayout) rootView.findViewById(R.id.shared_quotation_layout);
        pending_quotation_layout = (LinearLayout) rootView.findViewById(R.id.pending_quotation_layout);
        approved_quotation_layout = (LinearLayout) rootView.findViewById(R.id.approved_quotation_layout);
        denied_quotation_layout = (LinearLayout) rootView.findViewById(R.id.denied_quotation_layout);
        pendingText = (TextView) rootView.findViewById(R.id.pendingText);
        sharedText = (TextView) rootView.findViewById(R.id.sharedText);
        approvedText = (TextView) rootView.findViewById(R.id.approvedText);
        deniedText = (TextView) rootView.findViewById(R.id.deniedText);


        shared_quotation_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SharedQuotation1.class);
                startActivity(intent);
            }
        });

        pending_quotation_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), PendingQuotation1.class);
                startActivity(intent);
            }
        });

        approved_quotation_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ApprovedQuotation1.class);
                startActivity(intent);
            }
        });

        denied_quotation_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), DeniedQuotation1.class);
                startActivity(intent);
            }
        });

        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();

        checkForValidLocation();
        availableAircraftAPI();


        SharedPreferences pref = getApplicationContext().getSharedPreferences(com.truxapp.fbfire.app.Constants.SHARED_PREF, 0);
        String client_id = pref.getString("registration_id", null);
        badgeAPI(client_id);

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
        return rootView;
    }

    private void badgeAPI(String client_id){
        JSONObject jsonObject = new JSONObject();
        try {
//            Log.d("client_id", client_id);
            jsonObject.put("client_registration_id",client_id);
//            jsonObject.put("client_registration_id","8da3923f-17c8-436d-89a0-baade80efb1e");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("json", ""+ jsonObject);
        WebcallMaegerClass.getInstance().countQuotationHistory(getActivity(), this, Constants.POST_REQUEST, jsonObject, false);
    }

    private void availableAircraftAPI(){
        WebcallMaegerClass.getInstance().availableAircraft(getActivity(), this, Constants.GET_REQUEST, null, false);
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

    @Override
    public <T> void processFragmentResponse(T result) {
        super.processFragmentResponse(result);
        if (result instanceof AvailableAircraftResponse) {
            AvailableAircraftResponse availableAircraftResponse = (AvailableAircraftResponse) result;
            if (availableAircraftResponse.getErrorCode().equalsIgnoreCase("201")) {
                AircraftModel aircraftModel[] = availableAircraftResponse.getResult();
                final ArrayList<AircraftModel> aircraftModels = new ArrayList<>(Arrays.asList(aircraftModel));
                if (aircraftModels.size() > 0) {
                    for (int i = 0; i < aircraftModels.size(); i++) {
                        CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(aircraftModels.get(0).getLatitude(), aircraftModels.get(0).getLongitude())).zoom(2).build();
                        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
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
                    }
                }
            } else {
                Toast.makeText(getContext(), availableAircraftResponse.getErrorCode(), Toast.LENGTH_SHORT).show();
            }
        }

        if (result instanceof CountQuotationHistoryResponse){
            CountQuotationHistoryResponse res  = (CountQuotationHistoryResponse) result;
            if (res.getErrorCode().equalsIgnoreCase("201")){
                CountQuotationHistoryModel countQuotationHistoryModel [] = res.getResult();
                ArrayList<CountQuotationHistoryModel> araylist = new ArrayList<>(Arrays.asList(countQuotationHistoryModel));
                if (araylist!=null && araylist.size()>0){
                    pendingText.setText(araylist.get(0).getPendingcount());
                    sharedText.setText(araylist.get(0).getSharedcount());
                    approvedText.setText(araylist.get(0).getApprovecount());
                    deniedText.setText(araylist.get(0).getRejectcount());
                }
            }
        }
    }


    private void drawMarker(LatLng point, final ArrayList<AircraftModel> aircraftModels){
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
                    for(int  i=0; i<aircraftModels.size(); i++){
                        if (aircraftModels.get(i).getLatitude() == null && aircraftModels.get(i).getLongitude() == null) {
                        }else {
                            if (lat.doubleValue() == aircraftModels.get(i).getLatitude().doubleValue()) {
                                if (lng.doubleValue() == aircraftModels.get(i).getLongitude().doubleValue()) {
                                    airportDetail.setText(aircraftModels.get(i).getAirportdetail());
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
