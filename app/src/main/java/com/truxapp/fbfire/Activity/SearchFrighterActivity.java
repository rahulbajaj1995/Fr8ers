package com.truxapp.fbfire.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.truxapp.fbfire.Adapter.SearchFrighterAdapter;
import com.truxapp.fbfire.Model.SearchFrighterModel;
import com.truxapp.fbfire.R;
import com.truxapp.fbfire.response.SerchFrighterResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

public class SearchFrighterActivity extends CommonBaseActivity {
    private CollapsingToolbarLayout collapsingToolbarLayout;
    ArrayList<SearchFrighterModel> searchArrayList, adapterArrayList;
    RecyclerView recyclerView;
    Button requestQuoteBtn;
    TextView source_city, destination_city, dateTV;
    ImageView arrowImage;
    String originID, destinationID, originAirportValue, departureAirportValue, availabilityDateFormat, dateFormat;
    SearchFrighterAdapter searchFrighterAdapter;
    RelativeLayout recycler_viewRelativeLayout, requestQuoteRelativeLayout;
    Animation fade_in, fade_out;
    LinearLayout selectAllLayout;
    CheckBox check_box;

    public void hideRequestBtn() {
        requestQuoteRelativeLayout.setVisibility(View.GONE);
        requestQuoteRelativeLayout.startAnimation(fade_out);
//        requestQuoteRelativeLayout.clearAnimation();
    }

    public void showRequestBtn() {
        if (requestQuoteRelativeLayout.getVisibility() == View.GONE) {
            requestQuoteRelativeLayout.setVisibility(View.VISIBLE);
            requestQuoteRelativeLayout.startAnimation(fade_in);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_frighter);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        requestQuoteBtn = (Button) findViewById(R.id.requestQuoteBtn);
        check_box = (CheckBox) findViewById(R.id.check_box);
        requestQuoteRelativeLayout = (RelativeLayout) findViewById(R.id.requestQuoteRelativeLayout);
        recycler_viewRelativeLayout = (RelativeLayout) findViewById(R.id.recycler_viewRelativeLayout);
        selectAllLayout = (LinearLayout) findViewById(R.id.selectAllLayout);
        RelativeLayout.LayoutParams layoutParams1 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams1.setMargins(15, 0, 15, 0);
        recycler_viewRelativeLayout.setLayoutParams(layoutParams1);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        fade_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        fade_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_out);

        Intent intent = getIntent();
        if (intent != null) {
            originID = intent.getStringExtra("originID");
            destinationID = intent.getStringExtra("destinationID");
            originAirportValue = intent.getStringExtra("originAirportValue");
            departureAirportValue = intent.getStringExtra("departureAirportValue");
            availabilityDateFormat = intent.getStringExtra("availabilityDateFormat");
            dateFormat = intent.getStringExtra("dateFormat");
            SerchFrighterResponse res = (SerchFrighterResponse) intent.getSerializableExtra("searchFrighterData");
            SearchFrighterModel[] model = res.getResult();
            searchArrayList = new ArrayList<>(Arrays.asList(model));
            searchFrighterAdapter = new SearchFrighterAdapter(searchArrayList, SearchFrighterActivity.this);
            recyclerView.setAdapter(searchFrighterAdapter);
        }

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle("Search Freighter");
        collapsingToolbarLayout.setExpandedTitleMarginStart(30);

        source_city = (TextView) findViewById(R.id.source_city);
        destination_city = (TextView) findViewById(R.id.destination_city);
        dateTV = (TextView) findViewById(R.id.dateTV);
        arrowImage = (ImageView) findViewById(R.id.arrowImage);

        source_city.setText("" + originAirportValue.split("\n")[0]);
        destination_city.setText("" + departureAirportValue.split("\n")[0]);
        dateTV.setText(availabilityDateFormat);

        if (searchArrayList!=null && searchArrayList.size()>0){

            ArrayList<SearchFrighterModel> ArrayList = new ArrayList<SearchFrighterModel>();
            for(SearchFrighterModel p:searchArrayList){
                if (!p.isAlreadyRequest()){
                    ArrayList.add(p);

                }
            }
            if (ArrayList!=null && ArrayList.size()>1){
                selectAllLayout.setVisibility(View.VISIBLE);
            }else {
                selectAllLayout.setVisibility(View.GONE);
            }
        }


        final LinearLayout titleLayout = (LinearLayout) findViewById(R.id.titleLayout);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(15, 35, 15, 0);
        titleLayout.setLayoutParams(layoutParams);
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    source_city.setTextColor(Color.parseColor("#ffffff"));
                    destination_city.setTextColor(Color.parseColor("#ffffff"));
                    dateTV.setTextColor(Color.parseColor("#ffffff"));
                    arrowImage.setImageResource(R.mipmap.ic_arrow_large_white);
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    titleLayout.setLayoutParams(layoutParams);
                    titleLayout.setBackgroundColor(Color.parseColor("#000000"));

                    isShow = true;
                } else if (isShow) {
                    source_city.setTextColor(Color.parseColor("#000000"));
                    destination_city.setTextColor(Color.parseColor("#000000"));
                    dateTV.setTextColor(Color.parseColor("#000000"));
                    arrowImage.setImageResource(R.mipmap.ic_arrow_large);
                    titleLayout.setBackgroundResource(R.drawable.top_round);
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    layoutParams.setMargins(15, 35, 15, 0);
                    titleLayout.setLayoutParams(layoutParams);
                    isShow = false;
                }
            }
        });

        ViewGroup mContentContainer = (ViewGroup) findViewById(R.id.content_container);
        mContentContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        check_box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectAllFunction();
            }
        });

        selectAllLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectAllFunction();
            }
        });


        requestQuoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                adapterArrayList = searchFrighterAdapter.getArrayListItem();
                ArrayList<String> modelNameArrayList = new ArrayList<String>();
                JSONArray freighterDetail = new JSONArray();
                for (SearchFrighterModel c : adapterArrayList) {
                    Log.d("frighterID", "" + c.getFreighterid());
                    String id = c.getFreighterid();
                    String record = c.getRecordid();
                    String name = c.getManufacturername() + " " + c.getModelname();
                    modelNameArrayList.add(name);

                    try {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("freighter_id", id);
                        jsonObject.put("aircraft_availability_id", record);
                        freighterDetail.put(jsonObject);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                if (adapterArrayList != null && adapterArrayList.size() > 0) {
                    Intent intent = new Intent(SearchFrighterActivity.this, LoadDetails1.class);
                    intent.putExtra("originID", originID);
                    intent.putExtra("destinationID", destinationID);
                    intent.putExtra("originAirportValue", originAirportValue);
                    intent.putExtra("departureAirportValue", departureAirportValue);
                    intent.putExtra("availabilityDateFormat", availabilityDateFormat);
                    intent.putExtra("dateFormat", dateFormat);
                    intent.putExtra("frightherArray", freighterDetail.toString());
                    intent.putExtra("modelNameArrayList", modelNameArrayList);
                    startActivity(intent);
                } else {
//                    mSnackBarMessage(findViewById(R.id.show_error), "Please select Frighter");
                }
            }
        });
    }

    private void selectAllFunction(){
        ArrayList<SearchFrighterModel> ArrayList = new ArrayList<SearchFrighterModel>();
        for(SearchFrighterModel p:searchArrayList){
            if (!p.isAlreadyRequest()){
                ArrayList.add(p);

            }
        }


        if (ArrayList!=null && ArrayList.size()>0) {
            ArrayList<String> modelNameArrayList = new ArrayList<String>();
            JSONArray freighterDetail = new JSONArray();
            for (SearchFrighterModel c : ArrayList) {
                Log.d("frighterID", "" + c.getFreighterid());
                String id = c.getFreighterid();
                String record = c.getRecordid();
                String name = c.getManufacturername() + " " + c.getModelname();
                modelNameArrayList.add(name);

                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("freighter_id", id);
                    jsonObject.put("aircraft_availability_id", record);
                    freighterDetail.put(jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            if (ArrayList != null && ArrayList.size() > 0) {
                Intent intent = new Intent(SearchFrighterActivity.this, LoadDetails1.class);
                intent.putExtra("originID", originID);
                intent.putExtra("destinationID", destinationID);
                intent.putExtra("originAirportValue", originAirportValue);
                intent.putExtra("departureAirportValue", departureAirportValue);
                intent.putExtra("availabilityDateFormat", availabilityDateFormat);
                intent.putExtra("dateFormat", dateFormat);
                intent.putExtra("frightherArray", freighterDetail.toString());
                intent.putExtra("modelNameArrayList", modelNameArrayList);
                startActivity(intent);
            } else {
//                    mSnackBarMessage(findViewById(R.id.show_error), "Please select Frighter");
            }
        }
    }


}
