package com.truxapp.fbfire.Model;

import android.app.Activity;
import android.app.Application;
import android.app.Fragment;

import io.realm.Realm;

/**
 * Created by bc9vq1 on 8/18/17.
 */

public class RealmController {
    private static RealmController instance;
    private final Realm realm;
    public RealmController(Application application) {
        realm = Realm.getDefaultInstance();
    }
    public static RealmController with(Fragment fragment) {
        if (instance == null) {
            instance = new RealmController(fragment.getActivity().getApplication());
        }
        return instance;
    }
    public static RealmController with(Activity activity) {

        if (instance == null) {
            instance = new RealmController(activity.getApplication());
        }
        return instance;
    }

    public static RealmController with(Application application) {

        if (instance == null) {
            instance = new RealmController(application);
        }
        return instance;
    }
    public static RealmController getInstance() {
        return instance;
    }
    public Realm getRealm() {
        return realm;
    }
    public void refresh() {
        realm.refresh();
    }


    public void clearAllUserData() {
        realm.beginTransaction();
        realm.clear(FlightDataRealm.class);
        realm.commitTransaction();
    }
    public FlightDataRealm getFlightDataRealm () {
        return realm.where(FlightDataRealm.class).findFirst();
    }

}
