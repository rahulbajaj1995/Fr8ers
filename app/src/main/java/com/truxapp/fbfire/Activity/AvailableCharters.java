package com.truxapp.fbfire.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.truxapp.fbfire.Adapter.flight_adapter;
import com.truxapp.fbfire.Fragment.AvailableCharterMap;
import com.truxapp.fbfire.Model.FindLoadModel;
import com.truxapp.fbfire.Model.RealmController;
import com.truxapp.fbfire.R;
import com.truxapp.fbfire.response.BaseResponse;
import com.truxapp.fbfire.response.FindLoadResponse;
import com.truxapp.fbfire.util.Constants;
import com.truxapp.fbfire.web.WebcallMaegerClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

import io.realm.Realm;

public class AvailableCharters extends CommonBaseActivity {
    private RecyclerView recyclerView;
    TextView source_name, destination_name, selected_date, request_quotattion;
    String source_id,source_city, destination_id, destination_city, availabilityDateFormat, available_date;
    private Realm realm;
    CheckBox check_box;
    ArrayList<FindLoadModel> findLoadModels, adapterArrayList;
    flight_adapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_charters);
        FragmentManager manager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.availavle_charter_fragment, new AvailableCharterMap());
        transaction.commit();

        realm = Realm.getDefaultInstance();
        realm = RealmController.with(AvailableCharters.this).getRealm();
        RealmController.with(AvailableCharters.this).refresh();

        Toolbar tb = (Toolbar) findViewById(R.id.custom_tool_bar1);
        setSupportActionBar(tb);
        getSupportActionBar().setTitle("");
        TextView mTitle = (TextView)tb.findViewById(R.id.title_tool_bar1);
        mTitle.setText("Available Charters");
        final ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        source_name = (TextView) findViewById(R.id.source_name);
        destination_name = (TextView) findViewById(R.id.destination_name);
        selected_date = (TextView) findViewById(R.id.selected_date);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        request_quotattion = (TextView) findViewById(R.id.request_quotattion);
        request_quotattion.setEnabled(false);
        request_quotattion.setBackgroundColor(Color.parseColor("#A9A9A9"));
        check_box = (CheckBox) findViewById(R.id.check_box);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        SharedPreferences pref = getApplicationContext().getSharedPreferences(com.truxapp.fbfire.app.Constants.SHARED_PREF, 0);
        source_city= pref.getString("source_city", null);
        source_id = pref.getString("source_id", null);
        destination_city = pref.getString("destination_city", null);
        destination_id = pref.getString("destination_id", null);
        available_date = pref.getString("availability_date", null);
        availabilityDateFormat = pref.getString("availabilityDateFormat", null);

        if (source_city!=null && source_city.length()>0)
            source_name.setText(source_city.split("\n")[0]);

        if (destination_city!=null && destination_city.length()>0)
            destination_name.setText(destination_city.split("\n")[0]);

        if(available_date!=null && available_date.length()>0)
            selected_date.setText(available_date);

        findLoadAPI(source_id, destination_id, availabilityDateFormat);

        request_quotattion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapterArrayList = adapter.getArrayListItem();

                JSONArray freighterDetail = new JSONArray();
                for (FindLoadModel c:  adapterArrayList) {
                    Log.d("frighterID", "" +c.getFreighterid());
                    String id =c.getFreighterid();
                    try {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("freighter_id", id);
                        freighterDetail.put(jsonObject);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                if (adapterArrayList!=null && adapterArrayList.size()>0) {
                    Intent intent = new Intent(AvailableCharters.this, LoadDetails.class);
                    intent.putExtra("frightherArray", freighterDetail.toString());
                    startActivity(intent);
                }else{
//                    Toast.makeText(AvailableCharters.this, "Please select Frighter", Toast.LENGTH_SHORT).show();
                    mSnackBarMessage(findViewById(R.id.show_error), "Please select Frighter");
                }
            }
        });
    }


    private void findLoadAPI(String sourcee, String destinationn, String availabilityDatee) {
        JSONObject reqData = new JSONObject();
        try {
            reqData.put("source",sourcee);
            reqData.put("destination",destinationn);
            reqData.put("availability_date",availabilityDatee);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("reqdata", "" + reqData);
        WebcallMaegerClass.getInstance().findLoad(this, Constants.POST_REQUEST, reqData, true);
    }


    @Override
    public <T> void processResponse(T result) {
        super.processResponse(result);
        BaseResponse baseResponse = (BaseResponse) result;
        if (result instanceof FindLoadResponse) {
            FindLoadResponse res = (FindLoadResponse) result;
            if (baseResponse.getErrorCode().equalsIgnoreCase("201")) {
                FindLoadModel findLoadModel[] = res.getResult();
                if(findLoadModel!=null){
                    findLoadModels = new ArrayList<>(Arrays.asList(findLoadModel));
                    adapter = new flight_adapter(AvailableCharters.this, findLoadModels,realm);
                    recyclerView.setAdapter(adapter);
                }else{
                    mSnackBarMessage(findViewById(R.id.show_error), "No Data Found");
//                    Toast.makeText(AvailableCharters.this, "No Data Found",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(AvailableCharters.this, HomeActivity.class));
                    finish();
                }
            }else {
                mSnackBarMessage(findViewById(R.id.show_error), baseResponse.getErrorCode());
//                Toast.makeText(this, baseResponse.getErrorCode(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void enableBtn(){
        request_quotattion.setEnabled(true);
        request_quotattion.setBackgroundColor(Color.parseColor("#ed1f24"));
    }

    public void disableBtn(){
        request_quotattion.setEnabled(false);
        request_quotattion.setBackgroundColor(Color.parseColor("#A9A9A9"));
    }
}
