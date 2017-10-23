package com.truxapp.fbfire.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.truxapp.fbfire.R;
import com.truxapp.fbfire.response.ChangeQuoatationResponse;
import com.truxapp.fbfire.util.Constants;
import com.truxapp.fbfire.web.WebcallMaegerClass;

import org.json.JSONException;
import org.json.JSONObject;

public class RejectReasonActivity extends CommonBaseActivity {
    ImageView back_btn;
    TextView cancel_btn, submit_btn;
    String loadavailabilityid;
    EditText reason_rejectTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reject_reason);
        reason_rejectTV = (EditText) findViewById(R.id.reason_rejectTV);
        back_btn = (ImageView) findViewById(R.id.back_btn);
        cancel_btn = (TextView) findViewById(R.id.cancel_btn);
        submit_btn = (TextView) findViewById(R.id.submit_btn);

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                finish();
            }
        });
        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                finish();
            }
        });

        Intent intent = getIntent();
        if (intent!=null) {
            loadavailabilityid = intent.getStringExtra("loadavailabilityid");
        }

        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (reason_rejectTV.getText().toString()!=null && reason_rejectTV.getText().toString().length()>0){
                    rejectQuoation(reason_rejectTV.getText().toString());
                }else {
                    mSnackBarMessage(findViewById(R.id.show_error), "Please enter some reason");
                }
            }
        });
    }


    public void rejectQuoation(String reason){
        JSONObject reqData = new JSONObject();
        try {
            reqData.put("load_availability_id",loadavailabilityid);
            reqData.put("bid_status","-1");
            reqData.put("reason",reason);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("reqdata", "" + reqData);
        WebcallMaegerClass.getInstance().changeQuotation(this, Constants.POST_REQUEST, reqData, true);
    }

    @Override
    public <T> void processResponse(T result) {
        super.processResponse(result);
        if (result instanceof ChangeQuoatationResponse){
            ChangeQuoatationResponse changeQuoatationResponse = (ChangeQuoatationResponse) result;
            if (changeQuoatationResponse.getErrorCode().equalsIgnoreCase("201")) {
                startActivity(new Intent(RejectReasonActivity.this, SharedQuotation1.class));
                finish();
            }
        }
    }
}
