package com.truxapp.fbfire.Activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.Holder;
import com.orhanobut.dialogplus.ViewHolder;
import com.truxapp.fbfire.Fragment.MapFragmentt;
import com.truxapp.fbfire.MainActivity;
import com.truxapp.fbfire.Model.AirportModel;
import com.truxapp.fbfire.Model.SearchFrighterModel;
import com.truxapp.fbfire.R;
import com.truxapp.fbfire.response.AirportResponse;
import com.truxapp.fbfire.response.ChangeQuoatationResponse;
import com.truxapp.fbfire.response.SerchFrighterResponse;
import com.truxapp.fbfire.util.AppPreference;
import com.truxapp.fbfire.util.Constants;
import com.truxapp.fbfire.web.WebcallMaegerClass;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.StringTokenizer;

public class HomeActivity1 extends CommonBaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public static final String[] MONTHS = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    private int mYear, mMonth, mDay;
    RelativeLayout mapLayout;
    RelativeLayout layoutIDID;
    DrawerLayout drawer;
    AirportResponse airportResponse;
    String originID, destinationID, originAirportValue, departureAirportValue, availabilityDateFormat, dateFormat;
    AutoCompleteTextView originAirportAutoComp, desinationAirportAutoComp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        prefs = new AppPreference(this.getApplicationContext());
        FragmentManager manager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.output, new MapFragmentt());
        transaction.commit();
        Toolbar tb = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        mAddTitle("Search Charter");

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                this, drawer, tb, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        mapLayout = (RelativeLayout) findViewById(R.id.mapLayout);
        layoutIDID = (RelativeLayout) findViewById(R.id.layoutIDID);

        try {
            if (prefs.getStringValueForTag(com.truxapp.fbfire.app.Constants.splashValue).equalsIgnoreCase("true")) {
                findAirportAPI();
                prefs.setStringValueForTag(com.truxapp.fbfire.app.Constants.splashValue, "false");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        final Holder holder = new ViewHolder(R.layout.first_slide_up_screen);
        layoutIDID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                slideUPDialogue(Gravity.BOTTOM, holder);
            }
        });
    }

    public void callSlider() {
        final Holder holder = new ViewHolder(R.layout.first_slide_up_screen);
        slideUPDialogue(Gravity.BOTTOM, holder);
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

    public void openDrawerMenu() {
        drawer.openDrawer(GravityCompat.START);
    }

    public void slideUPDialogue(int bottom, Holder holder) {
        final DialogPlus dialog = DialogPlus.newDialog(this)
                .setContentHolder(holder)
                .setCancelable(true)
                .setGravity(bottom)
                .setMargin(20, 0, 20, 0)
                .setContentBackgroundResource(R.drawable.top_round)
                .setContentHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
                .create();

        originAirportAutoComp = (AutoCompleteTextView) dialog.findViewById(R.id.originAirportAutoComp);
        desinationAirportAutoComp = (AutoCompleteTextView) dialog.findViewById(R.id.departureAirportAutoComp);
        final TextView datePickerTV = (TextView) dialog.findViewById(R.id.datePickerTV);
        Button searchBtn = (Button) dialog.findViewById(R.id.searchBtn);

        originAirportAutoComp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity1.this, AutoCompleteActivity.class);
                intent.putExtra("title", "Origin");
                intent.putExtra("hint", "New York, US - John F.Airport (JFK)");
                startActivityForResult(intent, 2);
            }
        });

        desinationAirportAutoComp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity1.this, AutoCompleteActivity.class);
                intent.putExtra("title", "Destination");
                intent.putExtra("hint", "London, GB - Heathrow Airport (LHR)");
                startActivityForResult(intent, 3);
            }
        });

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                findLoadAPI("e86891d1-2977-4f89-8237-10f634bfa67e", "a40fb198-e14f-4c4f-a7d1-e0f9a34877bc","2017-10-19");
//                findLoadAPI("48599c02-43c9-4309-996f-b5c1a4c3457b","d0d48e43-4fad-4b41-9189-633354850b6d", "28-Sep-2017");

                if (!CommonBaseActivity.isOnline(HomeActivity1.this)) {
                    mSnackBarMessage(findViewById(R.id.show_error), "Please check Internet Connection");
                } else if (originAirportAutoComp.getText().toString() != null && originAirportAutoComp.getText().toString().length() > 0) {
                    if (desinationAirportAutoComp.getText().toString() != null && desinationAirportAutoComp.getText().toString().length() > 0) {
                        if (datePickerTV.getText().toString() != null && datePickerTV.getText().toString().length() > 0) {
                            String dateSelected = datePickerTV.getText().toString();
                            findLoadAPI(originID, destinationID, dateFormat);
                        } else {
                            mSnackBarMessage(findViewById(R.id.show_error), "Please select Departure Date");
                        }
                    } else {
                        mSnackBarMessage(findViewById(R.id.show_error), "Please select Destination Airport");
                    }
                } else {
                    mSnackBarMessage(findViewById(R.id.show_error), "Please select Origin Airport");
                }
            }
        });

        datePickerTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                final DatePickerDialog datePickerDialog = new DatePickerDialog(HomeActivity1.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                availabilityDateFormat = dayOfMonth + "-" + MONTHS[monthOfYear] + "-" + year;
                                dateFormat = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;

                            /*availabilityDateFormat = year + "-" + (monthOfYear + 1) +  "-" + dayOfMonth;

                            if (dayOfMonth < mDay){
                                mSnackBarMessage(findViewById(R.id.show_error), "Old date selected. Please select correct date..!!");
                            }else {
                                datePickerTV.setText(dayOfMonth + "-" + MONTHS[monthOfYear] +  "-" + year);
                            }*/
                                datePickerTV.setText(dayOfMonth + " - " + MONTHS[monthOfYear] + " - " + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
            }
        });

        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) {
            if (null != data) {
                originAirportValue = data.getStringExtra("originAirportValue");
                originID = data.getStringExtra("originID");
                StringTokenizer st = new StringTokenizer(originAirportValue, "-");
                String firstValue = st.nextToken();
                String secondValue = st.nextToken();

                originAirportAutoComp.setText("" + originAirportValue.split("\n")[1]);
            }
        }
        if (requestCode == 3) {
            if (null != data) {
                departureAirportValue = data.getStringExtra("departureAirportValue");
                destinationID = data.getStringExtra("destinationID");

                StringTokenizer st = new StringTokenizer(departureAirportValue, "-");
                String firstValue = st.nextToken();
                String secondValue = st.nextToken();
//                desinationAirportAutoComp.setText(secondValue);
                desinationAirportAutoComp.setText("" + departureAirportValue.split("\n")[1]);
            }
        }
    }

    public void changeQuotation(String loadavailabilityid, boolean status) {
        String bid_status;

        if (status) {
            bid_status = "0";
        } else {
            bid_status = "1";
        }

        JSONObject reqData = new JSONObject();
        try {
            reqData.put("load_availability_id", loadavailabilityid);
            reqData.put("bid_status", bid_status);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("reqdata", "" + reqData);
        WebcallMaegerClass.getInstance().changeQuotation(this, Constants.POST_REQUEST, reqData, true);
    }

    private void findLoadAPI(String sourcee, String destinationn, String availabilityDatee) {
        JSONObject reqData = new JSONObject();
        try {
            reqData.put("source", sourcee);
            reqData.put("destination", destinationn);
            reqData.put("availability_date", availabilityDatee);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("reqdata", "" + reqData);
        WebcallMaegerClass.getInstance().findLoad(this, Constants.POST_REQUEST, reqData, true);
    }

    @Override
    public <T> void processResponse(T result) {
        super.processResponse(result);
        if (result instanceof SerchFrighterResponse) {
            SerchFrighterResponse res = (SerchFrighterResponse) result;
            if (res.getErrorCode().equalsIgnoreCase("201")) {
                SearchFrighterModel searchFrighterModel[] = res.getResult();
                if (searchFrighterModel != null) {
                    ArrayList<SearchFrighterModel> findLoadModels;
                    findLoadModels = new ArrayList<>(Arrays.asList(searchFrighterModel));
                    if (findLoadModels.size() > 0) {
                        Intent intent = new Intent(new Intent(HomeActivity1.this, SearchFrighterActivity.class));
                        intent.putExtra("originID", originID);
                        intent.putExtra("destinationID", destinationID);
                        intent.putExtra("originAirportValue", originAirportValue);
                        intent.putExtra("departureAirportValue", departureAirportValue);
                        intent.putExtra("availabilityDateFormat", availabilityDateFormat);
                        intent.putExtra("dateFormat", dateFormat);
/*                        intent.putExtra("originID","48599c02-43c9-4309-996f-b5c1a4c3457b");
                        intent.putExtra("destinationID", "d0d48e43-4fad-4b41-9189-633354850b6d");
                        intent.putExtra("originAirportValue", "Truxapp");
                        intent.putExtra("departureAirportValue", "Delhi");
                        intent.putExtra("availabilityDateFormat", "20 - October - 2017");
                        intent.putExtra("dateFormat", "2017-09-06");*/
                        intent.putExtra("searchFrighterData", res);
                        startActivity(intent);
                    }
                }
            } else {
                customAlertDialogue(res.getErrorMessage());
            }
        }

        if (result instanceof ChangeQuoatationResponse) {
            ChangeQuoatationResponse changeQuoatationResponse = (ChangeQuoatationResponse) result;
            if (changeQuoatationResponse.getErrorCode().equalsIgnoreCase("201")) {
//                mSnackBarMessage(findViewById(R.id.show_error), changeQuoatationResponse.getErrorMessage());
//                addFragment(new SharedQuotation(), "Shared Quotation");
            }
        }

        if (result instanceof AirportResponse) {
            airportResponse = (AirportResponse) result;
            AirportModel airportModel[] = airportResponse.getAirports();

            ArrayList<AirportModel> airportModels = new ArrayList<>(Arrays.asList(airportModel));
            Gson gson = new Gson();
            String listString = gson.toJson(airportModels, new TypeToken<ArrayList<AirportModel>>() {
            }.getType());
            prefs.setStringValueForTag("listString", listString);
        }

    }

    private void customAlertDialogue(String message) {
        final Dialog dialogBuilder = new Dialog(this);
        dialogBuilder.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogBuilder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogBuilder.setContentView(R.layout.thankyou_screen);
        TextView errorMessageThankYou = (TextView) dialogBuilder.findViewById(R.id.errorMessageThankYou);
        TextView thankYou = (TextView) dialogBuilder.findViewById(R.id.thankYou);
        Button searchAgain = (Button) dialogBuilder.findViewById(R.id.searchAgain);

        thankYou.setVisibility(View.GONE);
        searchAgain.setText("Ok");

        dialogBuilder.setCancelable(false);
        errorMessageThankYou.setText(message);
        searchAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogBuilder.cancel();
            }
        });

        dialogBuilder.show();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            mExitDialog();
        }
    }

    public void mAddTitle(String mTitle) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar == null)
            return;
        actionBar.setDisplayShowCustomEnabled(true);

        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(getLayoutInflater().inflate(R.layout.transparent_action_bar, null), new ActionBar.LayoutParams(
                        ActionBar.LayoutParams.MATCH_PARENT,
                        ActionBar.LayoutParams.MATCH_PARENT
                        , Gravity.CENTER
                )
        );
        TextView textViewTitle = (TextView) findViewById(R.id.title_tool_bar1);
        textViewTitle.setText(mTitle);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.book_cargo) {
            startActivity(new Intent(HomeActivity1.this, HomeActivity1.class));
        } else if (id == R.id.quotation_history) {
            startActivity(new Intent(HomeActivity1.this, com.truxapp.fbfire.Activity.QuotationHistory.class));
        } else if (id == R.id.order_status) {
            startActivity(new Intent(HomeActivity1.this, OrderStatusActivity.class));
        } else if (id == R.id.profile) {
            startActivity(new Intent(HomeActivity1.this, Profile1.class));
        } else if (id == R.id.logout) {
            mLogoutdialog();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
