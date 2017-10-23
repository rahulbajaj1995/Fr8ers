package com.truxapp.fbfire.Activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.truxapp.fbfire.Adapter.SharedQuotationAdapter;
import com.truxapp.fbfire.Model.SharedQuotationModel;
import com.truxapp.fbfire.R;
import com.truxapp.fbfire.response.ChangeQuoatationResponse;
import com.truxapp.fbfire.response.SharedQuotationResponse;
import com.truxapp.fbfire.util.Constants;
import com.truxapp.fbfire.web.WebcallMaegerClass;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

public class SharedQuotation1 extends CommonBaseActivity {
    RecyclerView sharedRecyclerView;
    LinearLayout shared_layout, error_message_Layout;
    TextView sharedErrorMessage;
    ImageView back_btn;
    String client_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_quotation1);
        sharedErrorMessage = (TextView) findViewById(R.id.sharedErrorMessage);
        sharedRecyclerView = (RecyclerView) findViewById(R.id.sharedRecyclerView);
        back_btn = (ImageView) findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        shared_layout = (LinearLayout) findViewById(R.id.shared_layout);
        error_message_Layout = (LinearLayout) findViewById(R.id.error_message_Layout);
        sharedRecyclerView.setHasFixedSize(true);
        sharedRecyclerView.setLayoutManager(new LinearLayoutManager(SharedQuotation1.this));
        SharedPreferences pref = getApplicationContext().getSharedPreferences(com.truxapp.fbfire.app.Constants.SHARED_PREF, 0);
        client_id = pref.getString("registration_id", null);
        sharedQuotation(client_id);
    }

    private void sharedQuotation(String client_id) {
        JSONObject reqData = new JSONObject();
        try {
            reqData.put("client_id",client_id);
//            reqData.put("client_id","8da3923f-17c8-436d-89a0-baade80efb1e");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("reqdata", "" + reqData);
        WebcallMaegerClass.getInstance().sharedQuotation1(SharedQuotation1.this, Constants.POST_REQUEST, reqData, true);
    }

    public void changeQuotation(String loadavailabilityid, boolean status){

        JSONObject reqData = new JSONObject();
        try {
            reqData.put("load_availability_id",loadavailabilityid);
            reqData.put("bid_status","1");
            reqData.put("negotiate_amount","");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("reqdata", "" + reqData);
        WebcallMaegerClass.getInstance().changeQuotation(this, Constants.POST_REQUEST, reqData, true);
    }

    /*public void rejectQuoation(String loadavailabilityid){
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
    }*/


    @Override
    public <T> void processResponse(T result) {
        super.processResponse(result);
        if (result instanceof SharedQuotationResponse) {
            SharedQuotationResponse res = (SharedQuotationResponse) result;
            if (res.getErrorCode().equalsIgnoreCase("201")) {
                SharedQuotationModel shareQuotationModel[] = res.getResult();
                if (shareQuotationModel != null) {
                    ArrayList<SharedQuotationModel> sharedArrayLst = new ArrayList<>(Arrays.asList(shareQuotationModel));
                    SharedQuotationAdapter sharedQuotation = new SharedQuotationAdapter(SharedQuotation1.this, sharedArrayLst);
                    if (sharedArrayLst.size() > 0) {
                        for (SharedQuotationModel p : sharedArrayLst) {
                            p.setSetSelection("0");
                        }
                        sharedRecyclerView.setAdapter(sharedQuotation);
                    }
                }
            } else {
                shared_layout.setVisibility(View.GONE);
                error_message_Layout.setVisibility(View.VISIBLE);
                sharedErrorMessage.setText(res.getErrorMessage());
            }
        }

        if (result instanceof ChangeQuoatationResponse){
            ChangeQuoatationResponse changeQuoatationResponse = (ChangeQuoatationResponse) result;
            if (changeQuoatationResponse.getErrorCode().equalsIgnoreCase("201")) {
                sharedQuotation(client_id);
            }else {
                mSnackBarMessage(findViewById(R.id.show_error), changeQuoatationResponse.getErrorMessage());
            }
        }


    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void callMsnackbar(){
        mSnackBarMessage(findViewById(R.id.show_error), "Please enter amount");
    }

    public void betterPriceQuotation(String loadavailabilityid, String amount){
        JSONObject reqData = new JSONObject();
        try {
            reqData.put("load_availability_id",loadavailabilityid);
            reqData.put("bid_status","0");
            reqData.put("negotiate_amount",amount);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("reqdata", "" + reqData);
        WebcallMaegerClass.getInstance().changeQuotation(this, Constants.POST_REQUEST, reqData, true);
    }
}
