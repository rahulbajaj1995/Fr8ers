package com.truxapp.fbfire;

import android.content.Context;
import android.os.Bundle;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.crashlytics.android.Crashlytics;
import com.google.firebase.analytics.FirebaseAnalytics;

import io.fabric.sdk.android.Fabric;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MyApplication extends MultiDexApplication {
    public static final String TAG = MyApplication.class.getSimpleName();
    private static MyApplication mInstance;
    public static Context mContext;
    private FirebaseAnalytics mFirebaseAnalytics;
    Boolean check = true;

    public static synchronized MyApplication getInstance() {
        return mInstance;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        mContext = this;

        if (BuildConfig.DEBUG) {
            check = false;
        }
        FabricCheck();

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this)
                .name(Realm.DEFAULT_REALM_NAME)
                .schemaVersion(1)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);
    }

    public void FabricCheck(){
        if (check){
            Fabric.with(this, new Crashlytics());
        }
    }

    public static Context getContext(){
        return mContext;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public void checkFirebaseEvent(int item_id, String item_name) {
        Bundle params = new Bundle();
        params.putInt(FirebaseAnalytics.Param.ITEM_ID, item_id);
        params.putString(FirebaseAnalytics.Param.ITEM_NAME, item_name);
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, params);
        mFirebaseAnalytics.setUserProperty(item_name, item_name);
    }
}


