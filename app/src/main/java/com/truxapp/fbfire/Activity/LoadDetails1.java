package com.truxapp.fbfire.Activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.truxapp.fbfire.Adapter.LoadDetailDimensionAdapter;
import com.truxapp.fbfire.Model.AircraftLoadTypeModel;
import com.truxapp.fbfire.Model.LoadDetailDimensionModel;
import com.truxapp.fbfire.R;
import com.truxapp.fbfire.response.AircraftLoadTypeResponse;
import com.truxapp.fbfire.response.BaseResponse;
import com.truxapp.fbfire.util.Constants;
import com.truxapp.fbfire.web.WebcallMaegerClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

public class LoadDetails1 extends CommonBaseActivity implements View.OnClickListener{
    AutoCompleteTextView cargoTypeET;
    JSONArray FrighterArray;
    Button submitBtn;
    ImageView image;
    TextView source_city, destination_city, dateTV, addMoreTV, deleteTV, replace_deleteTV;
    RecyclerView load_details_dimension_recycler_view;
    String originAirportValue, departureAirportValue, availabilityDateFormat, dateFormat, destinationID, originID, registration_id, cargoId;
    EditText requestLoadET, selectFrighterET, volumeET;
    int count = 0;
    ArrayList<String> modelNameArrayList = new ArrayList<String>();
    ArrayList<AircraftLoadTypeModel> aircraftLoadTypeList;
    LoadDetailDimensionAdapter loadDetailDimensionAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load1);
        cargoTypeET = (AutoCompleteTextView) findViewById(R.id.cargoTypeET);
        image = (ImageView) findViewById(R.id.image);

        source_city = (TextView) findViewById(R.id.source_city);
        destination_city = (TextView) findViewById(R.id.destination_city);
        dateTV = (TextView) findViewById(R.id.dateTV);
        addMoreTV = (TextView) findViewById(R.id.addMoreTV);

        deleteTV = (TextView) findViewById(R.id.deleteTV);
        replace_deleteTV = (TextView) findViewById(R.id.replace_deleteTV);

        load_details_dimension_recycler_view = (RecyclerView) findViewById(R.id.load_details_dimension_recycler_view);
        requestLoadET = (EditText) findViewById(R.id.requestLoadET);
        selectFrighterET = (EditText) findViewById(R.id.selectFrighterET);
        volumeET = (EditText) findViewById(R.id.volumeET);
        submitBtn = (Button) findViewById(R.id.submitBtn);
        load_details_dimension_recycler_view.setHasFixedSize(true);
        load_details_dimension_recycler_view.setLayoutManager(new LinearLayoutManager(LoadDetails1.this));

        cargoTypeET.setClickable(true);
        aircraftLoadTypeAPI();
        cargoTypeET.setOnClickListener(this);
        submitBtn.setOnClickListener(this);
        image.setOnClickListener(this);
        cargoTypeET.setFocusable(false);

        SharedPreferences pref = getApplicationContext().getSharedPreferences(com.truxapp.fbfire.app.Constants.SHARED_PREF, 0);
        registration_id = pref.getString("registration_id", null);


        Intent intent = getIntent();
        modelNameArrayList = intent.getStringArrayListExtra("modelNameArrayList");
        String FrightherID = intent.getStringExtra("frightherArray");
        try {
            FrighterArray = new JSONArray(FrightherID);
            originID = intent.getStringExtra("originID");
            destinationID = intent.getStringExtra("destinationID");
            originAirportValue = intent.getStringExtra("originAirportValue");
            departureAirportValue = intent.getStringExtra("departureAirportValue");
            availabilityDateFormat = intent.getStringExtra("availabilityDateFormat");
            dateFormat = intent.getStringExtra("dateFormat");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (modelNameArrayList!=null && modelNameArrayList.size()>0){
            StringBuilder builder = new StringBuilder();

                for (String details : modelNameArrayList) {
                    builder.append(details + ", ");
                }
                String trimm = builder.toString();
                String finalString = trimm.substring(0, trimm.length()-2);
                selectFrighterET.setText(finalString);
        }


        source_city.setText("" + originAirportValue.split("\n")[0]);
        destination_city.setText("" + departureAirportValue.split("\n")[0]);
        dateTV.setText(""+ availabilityDateFormat);
        addDimension();

        deleteTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadDetailDimensionAdapter.deleteRow();
                ArrayList<LoadDetailDimensionModel>  list = loadDetailDimensionAdapter.getArrayListValue();
                if (list.size()==1){
                    deleteTV.setVisibility(View.GONE);
                    replace_deleteTV.setVisibility(View.VISIBLE);
                }
            }
        });

        addMoreTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<LoadDetailDimensionModel>  list = loadDetailDimensionAdapter.getArrayListValue();
                    if (list.get(list.size()-1).getLength() == null){
                        loadDetailDimensionAdapter.setLengthDrawable();
                    }else {
                        loadDetailDimensionAdapter.setLengthDrawableBack();
                    }
                    if (list.get(list.size()-1).getBreadth() == null){
                        loadDetailDimensionAdapter.setBreadthDrawable();
                    }else {
                        loadDetailDimensionAdapter.setBreadthDrawableBack();
                    }
                    if (list.get(list.size()-1).getHeight() == null){
                        loadDetailDimensionAdapter.setHeightDrawable();
                    }else {
                        loadDetailDimensionAdapter.setHeightDrawableBack();
                    }
                    if (list.get(list.size()-1).getNoOfBox() == null){
                        loadDetailDimensionAdapter.setNumbOfBoxDrawable();
                    }else {
                        loadDetailDimensionAdapter.setNumbOfBoxDrawableBack();
                    }
                    if (list.get(list.size()-1).getLength() != null && list.get(list.size()-1).getBreadth() != null &&
                        list.get(list.size()-1).getHeight() != null && list.get(list.size()-1).getNoOfBox() != null){
                        addDimension();
                        replace_deleteTV.setVisibility(View.GONE);
                        deleteTV.setVisibility(View.VISIBLE);
                    }
            }
        });
    }

    private void aircraftLoadTypeAPI() {
        WebcallMaegerClass.getInstance().aircraftLoadType(this, Constants.GET_REQUEST, null, true);
    }


    private void addDimension(){
        ArrayList<LoadDetailDimensionModel> dimensionArrayList = new ArrayList<>();
        for(int i=0; i<=count;i++){
            LoadDetailDimensionModel  loadDetailDimensionModel  = new LoadDetailDimensionModel();
            loadDetailDimensionModel.setId(String.valueOf(i));
            dimensionArrayList.add(loadDetailDimensionModel);
        }
        if(loadDetailDimensionAdapter !=null){
            ArrayList<LoadDetailDimensionModel>  list = loadDetailDimensionAdapter.getArrayListValue();
            if(list !=null && list.size()>0){
                dimensionArrayList.addAll(0,list);
            }
        }
        loadDetailDimensionAdapter = new LoadDetailDimensionAdapter(LoadDetails1.this, dimensionArrayList);
        load_details_dimension_recycler_view.setAdapter(loadDetailDimensionAdapter);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.cargoTypeET){
            cargoTypeET.showDropDown();
        } else if (view.getId() == R.id.submitBtn){
            if (!CommonBaseActivity.isOnline(LoadDetails1.this)) {
                mSnackBarMessage(findViewById(R.id.show_error), "Please check Internet Connection");
            } else if (cargoTypeET.getText().toString().equalsIgnoreCase("")){
                mSnackBarMessage(findViewById(R.id.show_error), "Please select Cargo Type");
            }else if (requestLoadET.getText().toString().equalsIgnoreCase("")) {
                mSnackBarMessage(findViewById(R.id.show_error), "Please enter Request Load");
            }else if (volumeET.getText().toString().equalsIgnoreCase("")) {
                mSnackBarMessage(findViewById(R.id.show_error), "Please enter Volume");
            }else {
                submitValidation();
            }
        } else if (view.getId() == R.id.image){
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            finish();
        }
    }

    private void submitValidation() {
        ArrayList<LoadDetailDimensionModel>  list = loadDetailDimensionAdapter.getArrayListValue();
        if (list.get(list.size()-1).getLength() == null){
            loadDetailDimensionAdapter.setLengthDrawable();
        }else {
            loadDetailDimensionAdapter.setLengthDrawableBack();
        }
        if (list.get(list.size()-1).getBreadth() == null){
            loadDetailDimensionAdapter.setBreadthDrawable();
        }else {
            loadDetailDimensionAdapter.setBreadthDrawableBack();
        }
        if (list.get(list.size()-1).getHeight() == null){
            loadDetailDimensionAdapter.setHeightDrawable();
        }else {
            loadDetailDimensionAdapter.setHeightDrawableBack();
        }
        if (list.get(list.size()-1).getNoOfBox() == null){
            loadDetailDimensionAdapter.setNumbOfBoxDrawable();
        }else {
            loadDetailDimensionAdapter.setNumbOfBoxDrawableBack();
        }
        if (list.get(list.size()-1).getLength() != null && list.get(list.size()-1).getBreadth() != null &&
                list.get(list.size()-1).getHeight() != null && list.get(list.size()-1).getNoOfBox() != null){
//            mSnackBarMessage(findViewById(R.id.show_error), "All Filled");
            submitAPI();
        }
    }

    public void submitAPI(){


        String cargoName= cargoTypeET.getText().toString().trim();
        if (cargoName!=null && cargoName.length()>0) {
            for (int i=0; i<aircraftLoadTypeList.size(); i++){
                if (cargoName.equalsIgnoreCase(aircraftLoadTypeList.get(i).getLoad_type())) {
                    cargoId = aircraftLoadTypeList.get(i).getId();
                }
            }
        }

        JSONObject jsonObject = new JSONObject();
        ArrayList<LoadDetailDimensionModel>  list = loadDetailDimensionAdapter.getArrayListValue();
        JSONArray jsonArray = null;
        Gson gson = new Gson();
        String listString = gson.toJson(list, new TypeToken<ArrayList<LoadDetailDimensionModel>>() {}.getType());
        try {
            jsonArray = new JSONArray(listString);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            jsonObject.put("volume", volumeET.getText().toString().trim());
            jsonObject.put("freighterDetail",FrighterArray);
            jsonObject.put("client_registration_id", registration_id);
            jsonObject.put("source", originID);
            jsonObject.put("boxDimension", jsonArray);
            jsonObject.put("destination", destinationID);
            jsonObject.put("availability_date", dateFormat);
            jsonObject.put("luggage_type", cargoId);
            jsonObject.put("payload", requestLoadET.getText().toString().trim());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("Json","" + jsonObject);
        WebcallMaegerClass.getInstance().requestForAvailablity(this, Constants.POST_REQUEST, jsonObject, true);
    }
    @Override
    public <T> void processResponse(T result) {
        super.processResponse(result);
        BaseResponse baseResponse = (BaseResponse) result;
        if (baseResponse.getErrorCode().equalsIgnoreCase("202")) {
            mAlertDialogue(baseResponse.getErrorMessage());
        } else if (baseResponse.getErrorCode().equalsIgnoreCase("102")){
            mSnackBarMessage(findViewById(R.id.show_error), baseResponse.getErrorMessage());
        }

        if (result instanceof AircraftLoadTypeResponse) {
            AircraftLoadTypeResponse res = (AircraftLoadTypeResponse) result;
            AircraftLoadTypeModel aircraftLoadTypeModels[] = res.getResult();
            aircraftLoadTypeList = new ArrayList<>(Arrays.asList(aircraftLoadTypeModels));
            ArrayList<String> item = new ArrayList<>();
            for (AircraftLoadTypeModel p : aircraftLoadTypeList){
                item.add(p.getLoad_type());
            }
            ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, item);
            cargoTypeET.setAdapter(arrayAdapter);
        }
    }

    public void mAlertDialogue(String message){
       final Dialog dialogBuilder = new Dialog(this);
        dialogBuilder.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogBuilder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogBuilder.setContentView(R.layout.thankyou_screen);
//        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this, R.style.TransparentTheme);
//        dialogBuilder.get.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

      /*  LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.thankyou_screen, null);
        dialogBuilder.setView(dialogView);*/
        TextView errorMessageThankYou = (TextView) dialogBuilder.findViewById(R.id.errorMessageThankYou);
        Button searchAgain = (Button) dialogBuilder.findViewById(R.id.searchAgain);

        dialogBuilder.setCancelable(false);

        errorMessageThankYou.setText(message);
        searchAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogBuilder.cancel();
                startActivity(new Intent(LoadDetails1.this, QuotationHistory.class));
                finish();
            }
        });

        dialogBuilder.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
