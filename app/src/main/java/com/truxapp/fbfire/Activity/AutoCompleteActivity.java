package com.truxapp.fbfire.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.truxapp.fbfire.Adapter.PeopleAdapter;
import com.truxapp.fbfire.Model.AirportModel;
import com.truxapp.fbfire.R;

import java.util.ArrayList;
import java.util.List;

public class AutoCompleteActivity extends CommonBaseActivity {
    ImageView backBtn;
    TextView titleTextView;
    Button submitBTn;
    String title, hint;
    AutoCompleteTextView autoComplete;
    ArrayList<AirportModel> airportModels;
    ArrayList<String> iitem = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_complete);
        backBtn = (ImageView) findViewById(R.id.backBtn);
        titleTextView = (TextView) findViewById(R.id.titleTextView);
        autoComplete = (AutoCompleteTextView) findViewById(R.id.autoComplete);
        submitBTn = (Button) findViewById(R.id.submitBTn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                finish();
            }
        });

        final Intent intent = getIntent();
        if (intent!=null) {
            title = intent.getStringExtra("title");
            hint = intent.getStringExtra("hint");
            String listString = prefs.getStringValueForTag("listString");
            Log.d("li", listString);
            Gson gson = new Gson();
            airportModels = gson.fromJson(listString, new TypeToken<List<AirportModel>>(){}.getType());
            setAutoCompliteAdapter(airportModels);
        }
        titleTextView.setText("" + title + " Airport");
        autoComplete.setHint(hint);

        submitBTn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    if (autoComplete.getText().toString() != null && autoComplete.getText().toString().length() > 0) {
                        if (iitem.contains(autoComplete.getText().toString())){
                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                                String airportValue = autoComplete.getText().toString();
                                if (title.equalsIgnoreCase("Origin")) {
                                    String originID = null;
                                    for (int i = 0; i < airportModels.size(); i++) {
                                        if (airportValue.equalsIgnoreCase(airportModels.get(i).getCity())) {
                                            originID = airportModels.get(i).getId();
                                        }
                                    }

                                    Intent intent1 = new Intent();
                                    intent1.putExtra("originAirportValue", airportValue);
                                    intent1.putExtra("originID", originID);
                                    setResult(2, intent1);
                                    finish();
                                } else if (title.equalsIgnoreCase("Destination")) {
                                    String destinationID = null;
                                    for (int i = 0; i < airportModels.size(); i++) {
                                        if (airportValue.equalsIgnoreCase(airportModels.get(i).getCity())) {
                                            destinationID = airportModels.get(i).getId();
                                        }
                                    }

                                    Intent intent1 = new Intent(AutoCompleteActivity.this, HomeActivity1.class);
                                    intent1.putExtra("departureAirportValue", airportValue);
                                    intent1.putExtra("destinationID", destinationID);
                                    setResult(3, intent1);
                                    finish();
                            }
                        }else {
                            mSnackBarMessage(findViewById(R.id.show_error), "Please select valid Airport");
                        }

                    } else {
                        mSnackBarMessage(findViewById(R.id.show_error), "Please Select Airport");
                    }

            }
        });
    }

    private void setAutoCompliteAdapter(ArrayList<AirportModel> airportModels) {
        if (airportModels!=null){
            for (AirportModel p : airportModels) {
                iitem.add(p.getCity());
            }
            PeopleAdapter peopleAdapter = new PeopleAdapter(this, R.layout.activity_auto_complete, R.id.lbl_name, airportModels);
//            ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,iitem);
            autoComplete.setAdapter(peopleAdapter);
            autoComplete.setThreshold(3);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
