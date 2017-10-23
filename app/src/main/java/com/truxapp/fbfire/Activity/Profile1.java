package com.truxapp.fbfire.Activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.truxapp.fbfire.R;
import com.truxapp.fbfire.response.LoginResponsee;
import com.truxapp.fbfire.web.WebcallMaegerClass;

import org.json.JSONException;
import org.json.JSONObject;

public class Profile1 extends CommonBaseActivity {
    TextView nameTV, emailTV, alternateEmailTV, mobileNumberTV, alternateMobileNumberTV, companyDetailsTV, addressTV;
    ImageView backBtn;
    String fName, lName, email, alternateEmail, mobileNumber, alternateMobileNumber, companyDetails, address;
    String manufacturer, model, androidOS, devicemac;
    int androidAPI;
    Button submit_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile1);
        submit_button = (Button) findViewById(R.id.submit_button);

        // Device Info
        TelephonyManager telephonyManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        devicemac = telephonyManager.getDeviceId();
        manufacturer = Build.MANUFACTURER;
        model = android.os.Build.MODEL;
        androidAPI = Build.VERSION.SDK_INT;
        androidOS = android.os.Build.VERSION.RELEASE;
        backBtn = (ImageView) findViewById(R.id.backBtn);
        nameTV = (TextView) findViewById(R.id.nameTV);
        emailTV = (TextView) findViewById(R.id.emailTV);
        alternateEmailTV = (TextView) findViewById(R.id.alternateEmailTV);
        mobileNumberTV = (TextView) findViewById(R.id.mobileNumberTV);
        alternateMobileNumberTV = (TextView) findViewById(R.id.alternateMobileNumberTV);
        companyDetailsTV = (TextView)findViewById(R.id.companyDetailsTV);
//        addressTV = (TextView) view.findViewById(R.id.addressTV);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                finish();
            }
        });

        getProfileData();

        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateLoginDetails();
            }
        });
    }

    private void getProfileData() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(com.truxapp.fbfire.app.Constants.SHARED_PREF, 0);
        fName = pref.getString("fName", null);
        lName = pref.getString("lName", null);
        email = pref.getString("email", null);
        alternateEmail = pref.getString("alternateEmail", null);
        mobileNumber = pref.getString("mobileNumber", null);
        alternateMobileNumber = pref.getString("alternateMobileNumber", null);
        companyDetails = pref.getString("companyDetails", null);
//        address = pref.getString("address", null);

        nameTV.setText(fName + " " + lName);
        emailTV.setText(email);
        alternateEmailTV.setText(alternateEmail);
        mobileNumberTV.setText(mobileNumber);
        alternateMobileNumberTV.setText(alternateMobileNumber);
        companyDetailsTV.setText(companyDetails);
//        addressTV.setText(address);
    }

    private void updateLoginDetails(){
        JSONObject reqData = new JSONObject();
        try {
            reqData.put("client_name", nameTV.getText().toString());
            reqData.put("firstname","");
            reqData.put("lastname","");
            reqData.put("mobile_no", mobileNumberTV.getText().toString());
            reqData.put("alternate_mobile_no", alternateMobileNumberTV.getText().toString());
            reqData.put("email_id", emailTV.getText().toString());
            reqData.put("alternate_email_id", alternateEmailTV.getText().toString());
            reqData.put("mac_id", devicemac);
            reqData.put("device_manufacturer",manufacturer);
            reqData.put("model",model);
            reqData.put("os_version",androidOS);
            reqData.put("os_api",androidAPI);
            reqData.put("login_as", "1");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("ReqData", ""+ reqData);
        WebcallMaegerClass.getInstance().loginAPI(this, com.truxapp.fbfire.util.Constants.POST_REQUEST, reqData, true);
    }


    @Override
    public <T> void processResponse(T result) {
        super.processResponse(result);
        if (result instanceof LoginResponsee) {
            LoginResponsee res = (LoginResponsee) result;
            if (res.getErrorCode().equalsIgnoreCase("200")){
                mSnackBarMessage(findViewById(R.id.show_error), res.getErrorMessage());
            }else{

            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
