package com.truxapp.fbfire.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.truxapp.fbfire.MainActivity;
import com.truxapp.fbfire.Model.LoginModel;
import com.truxapp.fbfire.R;
import com.truxapp.fbfire.response.BaseResponse;
import com.truxapp.fbfire.response.LoginResponsee;
import com.truxapp.fbfire.web.WebcallMaegerClass;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterProfileActivity extends CommonBaseActivity {
    Button submit_button;
    TextView nameTV, emailTV, alternateEmailTV, mobileNumberTV, alternateMobileNumberTV, companyDetailsTV;
    ImageView backBtn;
    String manufacturer, model, androidOS, devicemac ;
    int androidAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_profile);

        backBtn = (ImageView) findViewById(R.id.backBtn);
        nameTV = (TextView) findViewById(R.id.nameTV);
        emailTV = (TextView) findViewById(R.id.emailTV);
        alternateEmailTV = (TextView) findViewById(R.id.alternateEmailTV);
        mobileNumberTV = (TextView) findViewById(R.id.mobileNumberTV);
        alternateMobileNumberTV = (TextView) findViewById(R.id.alternateMobileNumberTV);
        companyDetailsTV = (TextView)findViewById(R.id.companyDetailsTV);
        submit_button = (Button) findViewById(R.id.submit_button);

        TelephonyManager telephonyManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        devicemac = telephonyManager.getDeviceId();
        manufacturer = Build.MANUFACTURER;
        model = android.os.Build.MODEL;
        androidAPI = Build.VERSION.SDK_INT;
        androidOS = android.os.Build.VERSION.RELEASE;

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                finish();
            }
        });

        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!CommonBaseActivity.isOnline(RegisterProfileActivity.this)) {
                    mSnackBarMessage(findViewById(R.id.show_error), "Please check Internet Connection");
                }else if (emailTV.getText().toString()!=null && emailTV.getText().toString().length()>0){
                    String email = emailTV.getText().toString().trim();
                    String emailPattern = "[a-zA-Z0-9._-]+@[a-zA-Z0-9._-]+\\.+[a-z]+";

                    if (email.matches(emailPattern)){
                        registerLogin();
                    }else {
                        mSnackBarMessage(findViewById(R.id.show_error), "Please enter valid Email Address");
                    }
                }else {
                    mSnackBarMessage(findViewById(R.id.show_error), "Please enter Email Address");
                }
            }
        });
    }

    private void registerLogin(){
        JSONObject reqData = new JSONObject();
        try {
            reqData.put("client_name",companyDetailsTV.getText().toString());
            reqData.put("firstname", nameTV.getText().toString());
            reqData.put("lastname","");
            reqData.put("mobile_no", mobileNumberTV.getText().toString());
            reqData.put("alternate_mobile_no", alternateMobileNumberTV.getText().toString());
            reqData.put("email_id", emailTV.getText().toString().toLowerCase());
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
        Log.d("Json", ""+ reqData);
        WebcallMaegerClass.getInstance().registerLogin(this, com.truxapp.fbfire.util.Constants.POST_REQUEST, reqData, true);
    }

    @Override
    public <T> void processResponse(T result) {
        super.processResponse(result);
        if (result instanceof LoginResponsee) {
            LoginResponsee res = (LoginResponsee) result;
            if (res.getErrorCode().equalsIgnoreCase("201")){
/*                mSnackBarMessage(findViewById(R.id.show_error), "" + baseResponse.getErrorMessage());
                new Handler().postDelayed(new Runnable() {
                     public void run() {
                         startActivity(new Intent(RegisterProfileActivity.this, MainActivity.class));
                     }
                     },
                2000);*/

                LoginModel loginResponse = res.getResult();
                if (loginResponse!=null) {
                    String reg_id = loginResponse.getId();
                    String fName = loginResponse.getFirstname();
                    String lName = loginResponse.getLastname();
                    String email = loginResponse.getEmail_id();
                    String alternateEmail = loginResponse.getAlternate_email_id();
                    String mobileNumber = loginResponse.getMobile_no();
                    String alternateMobileNumber = loginResponse.getAlternate_mobile_no();
                    String companyDetails = loginResponse.getClient_name();

                    savePreferences(reg_id, fName, lName, email, alternateEmail, mobileNumber, alternateMobileNumber, companyDetails);
                    Intent intent = new Intent(RegisterProfileActivity.this, HomeActivity1.class);
                    startActivity(intent);
                    finish();
                }


            }else {
                mSnackBarMessage(findViewById(R.id.show_error), "" + res.getErrorMessage());
                new Handler().postDelayed(new Runnable() {
                                              public void run() {
                                                  startActivity(new Intent(RegisterProfileActivity.this, MainActivity.class));
                                              }
                               },
                        2000);
            }
        }
    }

    private void savePreferences(String reg_id, String fName, String lName, String email, String alternateEmail, String mobileNumber,
                                 String alternateMobileNumber, String companyDetails) {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(com.truxapp.fbfire.app.Constants.SHARED_PREF, 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("registration_id", reg_id);
        editor.putString("fName", fName);
        editor.putString("lName", lName);
        editor.putString("email", email);
        editor.putString("alternateEmail", alternateEmail);
        editor.putString("mobileNumber", mobileNumber);
        editor.putString("alternateMobileNumber", alternateMobileNumber);
        editor.putString("companyDetails", companyDetails);
//        editor.putString("address",address);
        editor.commit();
    }
}
