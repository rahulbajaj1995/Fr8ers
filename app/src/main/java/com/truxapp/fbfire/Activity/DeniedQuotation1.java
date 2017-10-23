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

import com.truxapp.fbfire.Adapter.ApprovedQuotationAdapter;
import com.truxapp.fbfire.Adapter.DeniedQuotationAdapter;
import com.truxapp.fbfire.Model.ApprovedQuotationModel;
import com.truxapp.fbfire.R;
import com.truxapp.fbfire.response.ApprovedQuotationResponse;
import com.truxapp.fbfire.response.BaseResponse;
import com.truxapp.fbfire.util.Constants;
import com.truxapp.fbfire.web.WebcallMaegerClass;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

public class DeniedQuotation1 extends CommonBaseActivity {
    RecyclerView deniedRecyclerView;
    LinearLayout denied_layout, error_message_Layoutdenied;
    TextView deniedTextView;
    ImageView back_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_denied_layout);
        deniedRecyclerView = (RecyclerView) findViewById(R.id.deniedRecyclerView);
        denied_layout = (LinearLayout) findViewById(R.id.denied_layout);
        error_message_Layoutdenied = (LinearLayout) findViewById(R.id.error_message_Layoutdenied);

        deniedTextView = (TextView) findViewById(R.id.deniedTextView);
        deniedRecyclerView.setHasFixedSize(true);
        deniedRecyclerView.setLayoutManager(new LinearLayoutManager(DeniedQuotation1.this));
        back_btn = (ImageView) findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        SharedPreferences pref = getApplicationContext().getSharedPreferences(com.truxapp.fbfire.app.Constants.SHARED_PREF, 0);
        String client_id = pref.getString("registration_id", null);
        deniedQuotation(client_id);
    }

    private void deniedQuotation(String client_id) {
        JSONObject reqData = new JSONObject();
        try {
            reqData.put("client_id",client_id);
//            reqData.put("client_id","8da3923f-17c8-436d-89a0-baade80efb1e");
            reqData.put("status", "-1");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("reqdata", "" + reqData);
        WebcallMaegerClass.getInstance().approvedQuotation1(DeniedQuotation1.this, Constants.POST_REQUEST, reqData, true);
    }

    @Override
    public <T> void processResponse(T result) {
        super.processResponse(result);
        BaseResponse baseResponse = (BaseResponse) result;
        if (result instanceof ApprovedQuotationResponse)
            if (baseResponse.getErrorCode().equalsIgnoreCase("201")) {
                ApprovedQuotationResponse res = (ApprovedQuotationResponse) result;
                ApprovedQuotationModel approveQuotationModel [] = res.getResult();
                if (approveQuotationModel!=null){
                    ArrayList<ApprovedQuotationModel> approveArrayLst = new ArrayList<>(Arrays.asList(approveQuotationModel));
                    DeniedQuotationAdapter approveQuotation = new DeniedQuotationAdapter(DeniedQuotation1.this, approveArrayLst);
                    for (ApprovedQuotationModel p : approveArrayLst){
                        p.setSetSelection("0");
                    }
                    deniedRecyclerView.setAdapter(approveQuotation);
                }
            } else {
                denied_layout.setVisibility(View.GONE);
                error_message_Layoutdenied.setVisibility(View.VISIBLE);
                deniedTextView.setText(baseResponse.getErrorMessage());
            }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
