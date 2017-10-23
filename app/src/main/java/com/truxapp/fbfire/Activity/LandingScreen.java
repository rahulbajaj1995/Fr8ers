package com.truxapp.fbfire.Activity;

import android.os.Bundle;
import android.widget.AutoCompleteTextView;

import com.truxapp.fbfire.Adapter.AirportSearchAdapter;
import com.truxapp.fbfire.Adapter.PeopleAdapter;
import com.truxapp.fbfire.Model.AirportModel;
import com.truxapp.fbfire.Model.People;
import com.truxapp.fbfire.R;
import com.truxapp.fbfire.response.AirportResponse;
import com.truxapp.fbfire.util.Constants;
import com.truxapp.fbfire.web.WebcallMaegerClass;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LandingScreen extends CommonBaseActivity {

    AutoCompleteTextView txtSearch;
    List<People> mList;

    PeopleAdapter adapter;
    ArrayList<AirportModel> airportModels;
    AirportSearchAdapter airportSearchAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_screen);
        mList = retrievePeople();
        txtSearch = (AutoCompleteTextView) findViewById(R.id.txt_search);

        /*adapter = new PeopleAdapter(this, R.layout.activity_landing_screen, R.id.lbl_name, mList);
        txtSearch.setAdapter(adapter);*/
        findAirportAPI();

    }
    private void findAirportAPI() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", "1");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        WebcallMaegerClass.getInstance().findAirport(this, Constants.POST_REQUEST, jsonObject, true);
    }

    @Override
    public <T> void processResponse(T result) {
        super.processResponse(result);
        if (result instanceof AirportResponse) {
            AirportResponse res = (AirportResponse) result;
            AirportModel airportModel[] = res.getAirports();
            airportModels = new ArrayList<>(Arrays.asList(airportModel));
            setAutoCompliteAdapter(airportModels);
        }
    }
    private void setAutoCompliteAdapter(ArrayList<AirportModel> airportModels) {
        ArrayList<AirportModel> iitem = new ArrayList<>();
        for (AirportModel p : airportModels) {
            iitem.add(p);
        }

        airportSearchAdapter = new AirportSearchAdapter(this, R.layout.activity_landing_screen, R.id.lbl_name, iitem);
        txtSearch.setAdapter(airportSearchAdapter);
        txtSearch.setThreshold(2);
/*        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,iitem);
        autoCompView.setAdapter(adapter);
        autoCompView.setThreshold(1);

        ArrayAdapter adapter1 = new ArrayAdapter(this,android.R.layout.simple_list_item_1,iitem);
        autoCompView1.setAdapter(adapter1);
        autoCompView1.setThreshold(1);*/

    }



        private List<People> retrievePeople() {
        List<People> list = new ArrayList<People>();
        list.add(new People("James Bond", "Bond", 1));
        list.add(new People("Jason", "Bourne", 2));
        list.add(new People("Ethan", "Hunt", 3));
        list.add(new People("Sherlock Holmes", "Holmes", 4));
        list.add(new People("David", "Beckham", 5));
        list.add(new People("Bryan", "Adams", 6));
        list.add(new People("Arjen", "Robben", 7));
        list.add(new People("Van", "Persie", 8));
        list.add(new People("Zinedine", "Zidane", 9));
        list.add(new People("Luis", "Figo", 10));
        list.add(new People("John", "Watson", 11));
        return list;
    }




}
