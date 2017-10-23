package com.truxapp.fbfire.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.truxapp.fbfire.R;
import com.truxapp.fbfire.response.BaseResponse;
import com.truxapp.fbfire.util.Constants;
import com.truxapp.fbfire.web.WebcallMaegerClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class LoadDetails extends CommonBaseActivity implements View.OnClickListener{
    TextView volume_textview;
    EditText weightET, volumeET, lengthET, widthET, heightET;
    Button submitBtn;
    TextInputLayout  weightWrapper, volumeWrapper, lengthWrapper, widthWrapper, heightWrapper;
    String source_id, destination_id, availabilityDateFormat, registration_id;
    JSONArray FrighterArray;
    AutoCompleteTextView cargo_field;

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
        Button submit = (Button) findViewById(R.id.submitBtn);
        String selectCargo = cargo_field.getText().toString();
        String wET = weightET.getText().toString();
        String vET = volumeET.getText().toString();
        String lET = lengthET.getText().toString();
        String wiET = widthET.getText().toString();
        String hET = heightET.getText().toString();

        if (selectCargo.equalsIgnoreCase("") || wET.equals("") || vET.equals("") || lET.equals("") || wiET.equals("") || hET.equals("")){
            submit.setEnabled(false);
            submit.setBackgroundColor(Color.parseColor("#A9A9A9"));
        }else{
            submit.setEnabled(true);
            submit.setBackgroundColor(Color.parseColor("#ed1f24"));
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_details);
        Toolbar tb = (Toolbar) findViewById(R.id.custom_tool_bar);
        setSupportActionBar(tb);
        getSupportActionBar().setTitle("");
        TextView mTitle = (TextView)tb.findViewById(R.id.title_tool_bar);
        mTitle.setText("Load Details");
        final ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        weightET = (EditText) findViewById(R.id.weightET);
        volumeET = (EditText) findViewById(R.id.volumeET);
        lengthET = (EditText) findViewById(R.id.lengthET);
        widthET = (EditText) findViewById(R.id.widthET);
        heightET = (EditText) findViewById(R.id.heightET);
        submitBtn = (Button) findViewById(R.id.submitBtn);

        cargo_field = (AutoCompleteTextView) findViewById(R.id.cargo_field);
        cargo_field.setFocusable(false);
        cargo_field.setClickable(true);
        selectCargoDropdown();
        cargo_field.setOnClickListener(this);

        cargo_field.addTextChangedListener(mTextWatcher);
        weightET.addTextChangedListener(mTextWatcher);
        volumeET.addTextChangedListener(mTextWatcher);
        lengthET.addTextChangedListener(mTextWatcher);
        widthET.addTextChangedListener(mTextWatcher);
        heightET.addTextChangedListener(mTextWatcher);

        weightWrapper = (TextInputLayout) findViewById(R.id.weightWrapper);
        weightWrapper.setHintEnabled(false);
        volumeWrapper = (TextInputLayout) findViewById(R.id.volumeWrapper);
        volumeWrapper.setHintEnabled(false);
        lengthWrapper = (TextInputLayout) findViewById(R.id.lengthWrapper);
        lengthWrapper.setHintEnabled(false);
        widthWrapper = (TextInputLayout) findViewById(R.id.widthWrapper);
        widthWrapper.setHintEnabled(false);
        heightWrapper = (TextInputLayout) findViewById(R.id.heightWrapper);
        heightWrapper.setHintEnabled(false);
        volume_textview = (TextView) findViewById(R.id.volume_textview);
        volume_textview.setText(Html.fromHtml("Total Volume (in meter<sup>3</sup>)"));
        checkFieldsForEmptyValues();

        SharedPreferences pref = getApplicationContext().getSharedPreferences(com.truxapp.fbfire.app.Constants.SHARED_PREF, 0);
        source_id = pref.getString("source_id", null);
        destination_id = pref.getString("destination_id", null);
        availabilityDateFormat = pref.getString("availabilityDateFormat", null);
        registration_id = pref.getString("registration_id", null);

        Intent intent = getIntent();
        String FrightherID = intent.getStringExtra("frightherArray");
        try {
            FrighterArray = new JSONArray(FrightherID);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(weightET.getText().toString().trim())){
                    weightWrapper.setError("Please enter Weight");
                    weightWrapper.setHintEnabled(true);
                    weightWrapper.setHint("Please enter Weight");
                }else if(TextUtils.isEmpty(volumeET.getText().toString().trim())){
                    weightWrapper.setHintEnabled(false);
                    volumeWrapper.setError("Please enter Volume");
                    volumeWrapper.setHintEnabled(true);
                    volumeWrapper.setHint("Please enter Volume");
                }else if(TextUtils.isEmpty(lengthET.getText().toString().trim())){
                    volumeWrapper.setHintEnabled(false);
                    lengthWrapper.setHintEnabled(true);
                    lengthWrapper.setError("Please enter Length");
                } else if(TextUtils.isEmpty(widthET.getText().toString().trim())){
                    lengthWrapper.setHintEnabled(false);
                    widthWrapper.setHintEnabled(true);
                    widthWrapper.setError("Please enter Width");
                }else if(TextUtils.isEmpty(heightET.getText().toString().trim())){
                    widthWrapper.setHintEnabled(false);
                    heightWrapper.setHintEnabled(true);
                    heightWrapper.setError("Please enter Height");
                }else {
                    if (source_id !=null && source_id.length()>0){
                        if (destination_id !=null && destination_id.length()>0){
                            if (availabilityDateFormat!=null && availabilityDateFormat.length()>0){
                                weightWrapper.setHintEnabled(false);
                                volumeWrapper.setHintEnabled(false);
                                lengthWrapper.setHintEnabled(false);
                                widthWrapper.setHintEnabled(false);
                                heightWrapper.setHintEnabled(false);
                                findAvailabilityAPI();
                            }
                        }
                    }
                }
            }
        });
    }


    private void selectCargoDropdown(){
        ArrayList<String> iitem = new ArrayList<>();
        iitem.add("Urgent or time bound cargo");
        iitem.add("Large size Cargo");
        iitem.add("Medical products");
        iitem.add("Fresh Products or Perishable Cargo");
        iitem.add("Live Animals");
        iitem.add("Dangerous goods and Explosives");
        iitem.add("Must go Cargo");
        iitem.add("Valuable Cargo");
        iitem.add("Historical Artifacts");
        iitem.add("Humanitarian Aid Materials");
        iitem.add("Motor Vehicles");
        iitem.add("Military Materials");

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, iitem);
        cargo_field.setAdapter(arrayAdapter);
    }


    private void findAvailabilityAPI(){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("source", source_id);
            jsonObject.put("destination", destination_id);
            jsonObject.put("client_registration_id", registration_id);
            jsonObject.put("payload", weightET.getText().toString().trim());
            jsonObject.put("volume", volumeET.getText().toString().trim());
            jsonObject.put("freighterDetail",FrighterArray);
            jsonObject.put("availability_date", availabilityDateFormat);
            jsonObject.put("length", lengthET.getText().toString().trim());
            jsonObject.put("breadth", widthET.getText().toString().trim());
            jsonObject.put("height", heightET.getText().toString().trim());
            jsonObject.put("luggage_type", cargo_field.getText().toString().trim());
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
            if (baseResponse.getErrorCode().equalsIgnoreCase("201")) {
                mAlertDialogue(baseResponse.getErrorMessage());
       /*         RequestAvailabilityModel requestAvailabilityModel = res.getRequestAvailabilityModel();
                String volume = requestAvailabilityModel.getDestination();
                Toast.makeText(LoadDetails.this, volume, Toast.LENGTH_SHORT).show();*/
            } else {
                mSnackBarMessage(findViewById(R.id.show_error), baseResponse.getErrorMessage());
            }
    }
    public void mAlertDialogue(String message){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.thankyou_screen, null);
        dialogBuilder.setView(dialogView);
//        TextView cross_icon = (TextView) dialogView.findViewById(R.id.cross_icon);
        TextView errorMessageThankYou = (TextView) dialogView.findViewById(R.id.errorMessageThankYou);
        Button searchAgain = (Button) dialogView.findViewById(R.id.searchAgain);

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
        alertDialog.setCancelable(false);

        errorMessageThankYou.setText(message);
        searchAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.hide();
                startActivity(new Intent(LoadDetails.this, HomeActivity.class));
                finish();
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.cargo_field){
            cargo_field.showDropDown();
        }
    }
}
