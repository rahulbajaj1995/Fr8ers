package com.truxapp.fbfire.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.truxapp.fbfire.Fragment.ProjectionMapFragment;
import com.truxapp.fbfire.Model.FindLoadModel;
import com.truxapp.fbfire.R;

public class CharterDetails extends AppCompatActivity {
    Button request_quotation;
    String source_city, destination_city, availabilityDateFormat;
    TextView sourceCity, destinationCity, departureDate;
    FindLoadModel findLoadModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charter_details);
        Toolbar tb = (Toolbar) findViewById(R.id.custom_tool_bar);
        setSupportActionBar(tb);
        getSupportActionBar().setTitle("");
        TextView mTitle = (TextView)tb.findViewById(R.id.title_tool_bar);
        mTitle.setText("Charter Details");
        final ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        if(intent !=null){
           findLoadModel = (FindLoadModel) intent.getSerializableExtra("data");
        }

        Bundle bundle  =  new Bundle();
        bundle.putSerializable("togoFag",findLoadModel );
        FragmentManager manager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction transaction = manager.beginTransaction();
        ProjectionMapFragment projectionMapFragment  = new ProjectionMapFragment();
        projectionMapFragment.setArguments(bundle);

        transaction.replace(R.id.charter_detail_fragment, projectionMapFragment);
        transaction.commit();
        registerView();
        SharedPreferences pref = getApplicationContext().getSharedPreferences(com.truxapp.fbfire.app.Constants.SHARED_PREF, 0);
        source_city= pref.getString("source_city", null);
        destination_city = pref.getString("destination_city", null);
        availabilityDateFormat = pref.getString("availability_date", null);
        if (source_city!=null && source_city.length()>0){
            sourceCity.setText(source_city);
        }
        if (destination_city!=null && destination_city.length()>0){
            destinationCity.setText(destination_city);
        }
        if (availabilityDateFormat!=null && availabilityDateFormat.length()>0){
            departureDate.setText("Date : "+availabilityDateFormat);
        }

        request_quotation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CharterDetails.this, LoadDetails.class);
                startActivity(intent);
            }
        });
    }
    private void registerView(){
        request_quotation = (Button)findViewById(R.id.request_quotation);
        sourceCity = (TextView) findViewById(R.id.sourceCity);
        destinationCity = (TextView) findViewById(R.id.destinationCity);
        departureDate = (TextView) findViewById(R.id.departureDate);

    }
}
