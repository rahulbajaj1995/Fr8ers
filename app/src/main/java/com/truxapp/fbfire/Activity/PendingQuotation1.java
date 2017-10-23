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

import com.truxapp.fbfire.Adapter.PendingQuotationAdapter;
import com.truxapp.fbfire.Model.PendingQuotationModel;
import com.truxapp.fbfire.R;
import com.truxapp.fbfire.response.BaseResponse;
import com.truxapp.fbfire.response.PendingQuotationResponse;
import com.truxapp.fbfire.util.Constants;
import com.truxapp.fbfire.web.WebcallMaegerClass;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

public class PendingQuotation1 extends CommonBaseActivity {
    RecyclerView pendingRecyclerView;
    TextView penidngTextView;
    LinearLayout pending_layout, error_message_Layoutpending;
    ImageView back_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_quotation1);
        pendingRecyclerView = (RecyclerView) findViewById(R.id.pendingRecyclerView);
        pending_layout = (LinearLayout) findViewById(R.id.pending_layout);
        error_message_Layoutpending = (LinearLayout) findViewById(R.id.error_message_Layoutpending);
        penidngTextView = (TextView) findViewById(R.id.penidngTextView);
        pendingRecyclerView.setHasFixedSize(true);
        pendingRecyclerView.setLayoutManager(new LinearLayoutManager(PendingQuotation1.this));
        back_btn = (ImageView) findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        SharedPreferences pref = getApplicationContext().getSharedPreferences(com.truxapp.fbfire.app.Constants.SHARED_PREF, 0);
        String client_id = pref.getString("registration_id", null);
        pendingQuotation(client_id);
    }

    private void pendingQuotation(String client_id) {
        JSONObject reqData = new JSONObject();
        try {
            reqData.put("client_id",client_id);
//            reqData.put("client_id","8da3923f-17c8-436d-89a0-baade80efb1e");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("reqdata", "" + reqData);
        WebcallMaegerClass.getInstance().pendingQuotation1(PendingQuotation1.this, Constants.POST_REQUEST, reqData, true);
    }

    @Override
    public <T> void processResponse(T result) {
        super.processResponse(result);
        BaseResponse baseResponse = (BaseResponse) result;
        if (result instanceof PendingQuotationResponse)
            if (baseResponse.getErrorCode().equalsIgnoreCase("201")) {
                PendingQuotationResponse res = (PendingQuotationResponse) result;
                PendingQuotationModel pendingQuotationModel [] = res.getResult();
                if (pendingQuotationModel!=null){
                    ArrayList<PendingQuotationModel> pendingArrayLst = new ArrayList<>(Arrays.asList(pendingQuotationModel));
                    for (PendingQuotationModel p : pendingArrayLst){
                        p.setSetSelection("0");
                    }

                    PendingQuotationAdapter pendingQuotationHistory = new PendingQuotationAdapter(PendingQuotation1.this, pendingArrayLst);
                    pendingRecyclerView.setAdapter(pendingQuotationHistory);
                }
            } else {
                pending_layout.setVisibility(View.GONE);
                error_message_Layoutpending.setVisibility(View.VISIBLE);
                penidngTextView.setText(baseResponse.getErrorMessage());
            }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
