package com.truxapp.fbfire.Fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.android.volley.RequestQueue;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.truxapp.fbfire.R;
import com.truxapp.fbfire.app.Constants;
import com.truxapp.fbfire.service.LocationService;
import com.truxapp.fbfire.util.AppPreference;

import java.util.Locale;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import io.realm.Realm;

public class CommonBaseFragment extends Fragment  implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    public AppPreference prefs;
    public boolean isErrorResponse;
    public LocationRequest mLocationRequest;
    public Location mCurrentLocation;
    public LocationSettingsRequest mLocationSettingsRequest;
    public RequestQueue queue;
    public Map<String, String> params = new ArrayMap<>();
    // public DataBaseHelper mDataBaseHelper;
    private Realm realm;
    public GoogleApiClient mGoogleApiClient;

    public Locale srcLanguage;
    private Timer timer;
    private int counter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefs = new AppPreference(getActivity().getApplicationContext());
        isErrorResponse = false;
    }
    public void nestedReplaceFragment(int container, Fragment newFragment, boolean backStackTag, String fragmentTag) {
        try {
            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
            if (getChildFragmentManager().getBackStackEntryCount() == 0) {
                transaction.add(container, newFragment, fragmentTag);
            } else {
                transaction.replace(container, newFragment, fragmentTag);
            }
            if (backStackTag) {
                transaction.addToBackStack(null);
            }
            transaction.commit();
            getChildFragmentManager().executePendingTransactions();
        } catch (Exception e) {
            Log.e("exception ", " on adding fragment");
            // TODO: handle exception
        }
    }

    public boolean popFragment() {
        Log.e("test", "pop fragment: " + getChildFragmentManager().getBackStackEntryCount());
        boolean isPop = false;
        if (getChildFragmentManager().getBackStackEntryCount() > 0) {
            isPop = true;
            getChildFragmentManager().popBackStack();
        }
        return isPop;
    }

    public void clearStack() {
        if (getChildFragmentManager().getBackStackEntryCount() > 0) {
            getChildFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }


    public <T> void processFragmentResponse(T result) {
        isErrorResponse = false;
        if (result == null) {
            try {
                isErrorResponse = true;
                return;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();

    }

    @Override
    public void onLocationChanged(Location location) {
        mCurrentLocation = location;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (Build.VERSION.SDK_INT >= 23 && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, Constants.REQUEST_COARSE_LOCATION);
        } else {
            mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

            startLocationUpdate();
            buildLocationSettingsRequest();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(Constants.UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setFastestInterval(Constants.FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

    }

    public synchronized void buildGoogleApiClient() {

        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        try {
            mGoogleApiClient.connect();
        } catch (Exception e) {

            e.printStackTrace();
        }
        createLocationRequest();
    }

    private void startLocationUpdate() {
        if (mGoogleApiClient.isConnected()) {
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)

            {
                return;
            }
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }

    }

    public void startTimer() {
        isLocationUpdated();
        if (timer != null) {
            timer.cancel();
        }
        timer = new Timer();
        MyTimerTask myTimerTask = new MyTimerTask();
        timer.schedule(myTimerTask, 1000, 1000);
    }

    private class MyTimerTask extends TimerTask {
        @Override
        public void run() {
            if (mCurrentLocation == null) {
                counter = counter + 1;
            } else if (isLocationUpdated() && mCurrentLocation != null && mCurrentLocation.getLatitude() != 0.0 && mCurrentLocation.getLongitude() != 0.0) {
                if (timer != null) {
                    timer.cancel();
                    timer = null;
                }
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                      //  DialogUtils.removeCustomProgressDialog();
                        getLatLong(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());
                    }
                });
            } else {
                counter = counter + 1;
            }

            if (counter == 30) {
              //  DialogUtils.removeCustomProgressDialog();
                if (timer != null) {
                    timer.cancel();
                }
                counter = 0;
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                       // DialogUtils.removeCustomProgressDialog();
                        showLocationNotGeting();
                    }
                });
            }
        }
    }

    public boolean isLocationUpdated() {
        LocationService locationService = new LocationService(getActivity());
        Location location = locationService.getLocation();
        if (location == null)
            return true;
        else {
            if (mCurrentLocation == null) {
                mCurrentLocation = location;
                return true;
            } else {
                int accuracyDelta = (int) (mCurrentLocation.getAccuracy());
                int newLocation = (int) (location.getAccuracy());
                if (accuracyDelta < newLocation)
                    return true;
                else {
                    mCurrentLocation = location;
                    return true;
                }
            }
        }
    }

    public void checkForValidLocation() {
        if (mGoogleApiClient == null) {
            buildGoogleApiClient();
        } else if (!mGoogleApiClient.isConnected()) {
            buildGoogleApiClient();

        } else if (mGoogleApiClient.isConnected() && mCurrentLocation == null) {
            mGoogleApiClient.disconnect();
            buildGoogleApiClient();

        } else {
            getLatLong(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());
        }

    }

    public void getLatLong(double latitude, double longitude) {

    }

    public void showLocationNotGeting() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
//alertDialog.setTitle(getString(R.string.not_getting_location));
// alertDialog.setMessage(getString(R.string.message_getlocation));

        alertDialog.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
// getMapLocation();

            }
        });
        if (!getActivity().isFinishing())
            alertDialog.show();
    }
    protected void buildLocationSettingsRequest() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        mLocationSettingsRequest = builder.build();
        builder.setAlwaysShow(true);
        final PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient,
                        builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(@NonNull LocationSettingsResult locationSettingsResult) {
                final Status status = locationSettingsResult.getStatus();

                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        startTimer();
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
// try {
//status.startResolutionForResult(BaseActivity.this, Config.REQUEST_LOCATION);
                        startTimer();
//} catch (IntentSender.SendIntentException e) {
// e.printStackTrace();
//}
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:

                        break;
                }
            }


        });
    }
}