package com.truxapp.fbfire.Activity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.Holder;
import com.orhanobut.dialogplus.ViewHolder;
import com.truxapp.fbfire.Fragment.ApprovedQuotation;
import com.truxapp.fbfire.Fragment.DeniedQuotation;
import com.truxapp.fbfire.Fragment.MapFragmentt;
import com.truxapp.fbfire.Fragment.PendingQuotation;
import com.truxapp.fbfire.Fragment.ProfileFragment;
import com.truxapp.fbfire.Fragment.QuotationHistory;
import com.truxapp.fbfire.Fragment.SharedQuotation;
import com.truxapp.fbfire.Model.AirportModel;
import com.truxapp.fbfire.Model.FindLoadModel;
import com.truxapp.fbfire.R;
import com.truxapp.fbfire.response.AirportResponse;
import com.truxapp.fbfire.response.ChangeQuoatationResponse;
import com.truxapp.fbfire.response.FindLoadResponse;
import com.truxapp.fbfire.util.Constants;
import com.truxapp.fbfire.web.WebcallMaegerClass;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Stack;

public class HomeActivity extends CommonBaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemClickListener, View.OnClickListener {
    public static final String[] MONTHS = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    Button searchBtn;
    TextView datepickerdialog;
    private int mYear, mMonth, mDay;
    RelativeLayout mapLayout;
    LinearLayout searchLayout, frame_layout_id;
    ImageView crossBtn;
    TextInputLayout sourceWrapper, destinationWrapper, dateWrapper;
    ArrayList<String> resultList = new ArrayList<>();
    AutoCompleteTextView autoCompView, autoCompView1;
    String source_id, source_city, destination_id, destination_city, availabilityDateFormat;
    ArrayList<AirportModel> airportModels;
    LinearLayout.LayoutParams params;
    DrawerLayout drawer;
    public Stack<Fragment> fragmentStack;
    public Fragment currentFragment;


    private TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override
        public void afterTextChanged(Editable editable) {
            // check Fields For Empty Values
            checkFieldsForEmptyValues();
        }
    };

    private void checkFieldsForEmptyValues() {
        Button searchBtn = (Button) findViewById(R.id.searchBtn);
        String autocompString = autoCompView.getText().toString();
        String autocompString1 = autoCompView1.getText().toString();
        String datepickString = datepickerdialog.getText().toString();

        if (autocompString.equals("") || autocompString1.equals("") || datepickString.equalsIgnoreCase("Select Date")){
            searchBtn.setEnabled(false);
            searchBtn.setBackgroundColor(Color.parseColor("#A9A9A9"));
        }else{
            searchBtn.setEnabled(true);
            searchBtn.setBackgroundColor(Color.parseColor("#ed1f24"));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(null);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_home);
        FragmentManager manager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.output, new MapFragmentt());
        transaction.commit();


        Toolbar tb = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        mAddTitle("Search Charter");
        fragmentStack = new Stack<>();

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                this, drawer, tb, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        final SharedPreferences pref = getApplicationContext().getSharedPreferences(com.truxapp.fbfire.app.Constants.SHARED_PREF, 0);
        final SharedPreferences.Editor editor = pref.edit();
        findAirportAPI();
        searchBtn = (Button) findViewById(R.id.searchBtn);
        mapLayout = (RelativeLayout) findViewById(R.id.mapLayout);
//        searchLayout = (LinearLayout) findViewById(R.id.searchLayout);
//        frame_layout_id = (LinearLayout) findViewById(R.id.frame_layout_id);
//        crossBtn = (ImageView) findViewById(R.id.crossBtn);
//        datepickerdialog = (TextView) findViewById(R.id.dateselected);
//        datepickerdialog.addTextChangedListener(mTextWatcher);
//        sourceWrapper = (TextInputLayout) findViewById(R.id.sourceWrapper);
//        sourceWrapper.setHintEnabled(false);
//        destinationWrapper = (TextInputLayout) findViewById(R.id.destinationWrapper);
//        destinationWrapper.setHintEnabled(false);
//        dateWrapper = (TextInputLayout) findViewById(R.id.dateWrapper);

        final Holder holder = new ViewHolder(R.layout.first_slide_up_screen);

        crossBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0,40);
                mapLayout.setLayoutParams(params);
                searchLayout.setVisibility(View.VISIBLE);
                crossBtn.setVisibility(View.GONE);
            }
        });

        datepickerdialog.setOnClickListener(this);
//        autoCompView = (AutoCompleteTextView) findViewById(R.id.fromAutoCompleteTextView);
//        autoCompView.addTextChangedListener(mTextWatcher);
//        autoCompView1 = (AutoCompleteTextView) findViewById(R.id.toAutoCompleteTextView);
//        autoCompView1.addTextChangedListener(mTextWatcher);

        autoCompView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String src = autoCompView.getText().toString();
                for (int i=0; i<airportModels.size(); i++){
                    if (src.equalsIgnoreCase(airportModels.get(i).getCity())){
                        View vieew = HomeActivity.this.getCurrentFocus();
                        if (vieew != null) {
                            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(vieew.getWindowToken(), 0);
                        }
                        source_id = airportModels.get(i).getId();
                        source_city = airportModels.get(i).getCity();
                        String home_src_lat = airportModels.get(i).getLatitude();
                        editor.putString("home_src_lat",home_src_lat);
                        String home_src_lng = airportModels.get(i).getLongitude();
                        editor.putString("home_src_lng",home_src_lng);
                        String cityyy = airportModels.get(i).getCity();
                        autoCompView.setText(cityyy.split("\n")[1]);
                    }
                }
            }
        });

        autoCompView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int positon, long l) {
                String des = autoCompView1.getText().toString();
                for (int i=0; i<airportModels.size(); i++){
                    if (des.equalsIgnoreCase(airportModels.get(i).getCity())){
                        View vieew = HomeActivity.this.getCurrentFocus();
                        if (vieew != null) {
                            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(vieew.getWindowToken(), 0);
                        }
                        destination_id = airportModels.get(i).getId();
                        destination_city = airportModels.get(i).getCity();
                        String home_des_lat = airportModels.get(i).getLatitude();
                        editor.putString("home_des_lat",home_des_lat);
                        String home_des_lng = airportModels.get(i).getLongitude();
                        editor.putString("home_des_lng",home_des_lng);
                        String cityyy = airportModels.get(i).getCity();
                        autoCompView1.setText(cityyy.split("\n")[1]);
                    }
                }
            }
        });

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (autoCompView.getText().length()>0){
                    sourceWrapper.setHintEnabled(false);
                    if (autoCompView1.getText().length()>0){
                        destinationWrapper.setHintEnabled(false);
                        String date = datepickerdialog.getText().toString();
                        if (!date.equals("Select Date")){
                            if(source_id.equalsIgnoreCase(destination_id)){
                                mSnackBarMessage(findViewById(R.id.show_error), "Source & Destination can't be same");
                            }else {
                                editor.putString("source_city",source_city);
                                editor.putString("destination_city", destination_city);
                                editor.putString("availability_date", datepickerdialog.getText().toString());
                                editor.putString("source_id", source_id);
                                editor.putString("destination_id", destination_id);
                                editor.putString("availabilityDateFormat", availabilityDateFormat);
                                editor.commit();
                                findLoadAPI(source_id, destination_id, availabilityDateFormat);
                            }
                        }else{
                            dateWrapper.setError("Please select Date");
                            requestFocus(datepickerdialog);
                        }
                    }else{
                        destinationWrapper.setError("Please select Destination");
                        destinationWrapper.setHintEnabled(true);
                        destinationWrapper.setHint("Enter Destination");
                        requestFocus(autoCompView1);
                    }
                }else{
                    sourceWrapper.setError("Please Select Source");
                    sourceWrapper.setHintEnabled(true);
                    sourceWrapper.setHint("Enter Source");
                    requestFocus(autoCompView);
                }
            }
        });
        checkFieldsForEmptyValues();
    }

    private void slideUPDialogue(int bottom, Holder holder) {
        final DialogPlus dialog = DialogPlus.newDialog(this)
                .setContentHolder(holder)
                .setCancelable(true)
                .setGravity(bottom)
                .setContentHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
                .create();
        dialog.show();
    }

    public void changeVisibility(){
        searchLayout.setVisibility(View.GONE);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        mapLayout.setLayoutParams(params);
        crossBtn.setVisibility(View.VISIBLE);
    }


    public void onItemClick(AdapterView adapterView, View view, int position, long id) {
        String str = (String) adapterView.getItemAtPosition(position);
//        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        if (view == datepickerdialog) {
            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);
            final DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            availabilityDateFormat = year + "-" + (monthOfYear + 1) +  "-" + dayOfMonth;

                            if (dayOfMonth < mDay){
                                mSnackBarMessage(findViewById(R.id.show_error), "Old date selected. Please select correct date..!!");
                            }else {
                                datepickerdialog.setText(dayOfMonth + "-" + MONTHS[monthOfYear] +  "-" + year);
                            }
                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            datePickerDialog.show();
        }
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


    public void changeQuotation(String loadavailabilityid, boolean status){
        String bid_status;

        if (status){
            bid_status = "0";
        }else{
            bid_status = "1";
        }

        JSONObject reqData = new JSONObject();
        try {
            reqData.put("load_availability_id",loadavailabilityid);
            reqData.put("bid_status",bid_status);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("reqdata", "" + reqData);
        WebcallMaegerClass.getInstance().changeQuotation(this, Constants.POST_REQUEST, reqData, true);
    }

    public void rejectQuoation(String loadavailabilityid, boolean status){
        JSONObject reqData = new JSONObject();
        try {
            reqData.put("load_availability_id",loadavailabilityid);
            reqData.put("bid_status","-1");
            reqData.put("reason","");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("reqdata", "" + reqData);
        WebcallMaegerClass.getInstance().changeQuotation(this, Constants.POST_REQUEST, reqData, true);
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


        if (result instanceof FindLoadResponse) {
            FindLoadResponse res = (FindLoadResponse) result;
            if (res.getErrorCode().equalsIgnoreCase("201")) {
                FindLoadModel findLoadModel[] = res.getResult();
                if (findLoadModel != null) {
                    ArrayList<FindLoadModel> findLoadModels;
                    findLoadModels = new ArrayList<>(Arrays.asList(findLoadModel));
                    if (findLoadModels.size() > 0) {
                        startActivity(new Intent(HomeActivity.this, AvailableCharters.class));
                    }
                }
            }else {
                mSnackBarMessage(findViewById(R.id.show_error), res.getErrorMessage());
            }
        }

        if (result instanceof ChangeQuoatationResponse){
            ChangeQuoatationResponse changeQuoatationResponse = (ChangeQuoatationResponse) result;
            if (changeQuoatationResponse.getErrorCode().equalsIgnoreCase("201")) {
//                mSnackBarMessage(findViewById(R.id.show_error), changeQuoatationResponse.getErrorMessage());
                addFragment(new SharedQuotation(), "Shared Quotation");
            }
        }
    }

    private void setAutoCompliteAdapter(ArrayList<AirportModel> airportModels) {
        ArrayList<String> iitem = new ArrayList<String>();
        for (AirportModel p : airportModels) {
            iitem.add(p.getCity());
        }
        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,iitem);
        autoCompView.setAdapter(adapter);
        autoCompView.setThreshold(1);

        ArrayAdapter adapter1 = new ArrayAdapter(this,android.R.layout.simple_list_item_1,iitem);
        autoCompView1.setAdapter(adapter1);
        autoCompView1.setThreshold(1);

    }


    public  void addFragment(Fragment fragment, String mTitle) {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        frame_layout_id.setVisibility(View.VISIBLE);
        fragmentStack.push(fragment);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame, fragment).addToBackStack(mTitle).commit();
        mAddTitle(mTitle);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (fragmentStack.size()>0){
            currentFragment = fragmentStack.elementAt(fragmentStack.size()-1);
        }
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }else if(crossBtn.getVisibility() == View.VISIBLE){
            params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0,40);
            mapLayout.setLayoutParams(params);
            searchLayout.setVisibility(View.VISIBLE);
            crossBtn.setVisibility(View.GONE);
        }
        /*else if(frame_layout_id.getVisibility() == View.VISIBLE ){
            mAddTitle("Search Charter");
            frame_layout_id.setVisibility(View.GONE);
        }*/
        else if (currentFragment instanceof PendingQuotation){
            addFragment(new QuotationHistory(), "Quotation History");
        }else if (currentFragment instanceof SharedQuotation){
            addFragment(new QuotationHistory(), "Quotation History");
        }else if (currentFragment instanceof ApprovedQuotation){
            addFragment(new QuotationHistory(), "Quotation History");
        }else if (currentFragment instanceof DeniedQuotation){
            addFragment(new QuotationHistory(), "Quotation History");
        }else {
            mExitDialog();
        }
    }

    public void  mAddTitle(String mTitle){
        ActionBar actionBar = getSupportActionBar();
        if(actionBar==null)
            return;
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(getLayoutInflater().inflate(R.layout.transparent_action_bar, null), new ActionBar.LayoutParams(
                        ActionBar.LayoutParams.WRAP_CONTENT,
                        ActionBar.LayoutParams.MATCH_PARENT
                        ,Gravity.CENTER
                )
        );
        TextView textViewTitle = (TextView) findViewById(R.id.title_tool_bar1);
        textViewTitle.setText(mTitle);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.book_cargo) {
            startActivity(new Intent(HomeActivity.this, HomeActivity.class));
        } else if (id == R.id.quotation_history) {
            addFragment(new QuotationHistory(), "Quotation History");
        } else if (id == R.id.profile) {
            addFragment(new ProfileFragment(), "Profile");
        }  else if (id == R.id.logout) {
         mLogoutdialog();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
