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

import com.truxapp.fbfire.Adapter.OrderStatusAdapter;
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

public class OrderStatusActivity extends CommonBaseActivity {
    RecyclerView approvedRecyclerView;
    LinearLayout error_message_Layoutapproved, approved_layout;
    TextView approvedTextView;
    ImageView back_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_status_activity_layout);

        approvedTextView = (TextView) findViewById(R.id.approvedTextView);
        approvedRecyclerView = (RecyclerView) findViewById(R.id.approvedRecyclerView);
        error_message_Layoutapproved = (LinearLayout) findViewById(R.id.error_message_Layoutapproved);
        back_btn = (ImageView) findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        approved_layout = (LinearLayout) findViewById(R.id.approved_layout);
        approvedRecyclerView.setHasFixedSize(true);
        approvedRecyclerView.setLayoutManager(new LinearLayoutManager(OrderStatusActivity.this));
        SharedPreferences pref = getApplicationContext().getSharedPreferences(com.truxapp.fbfire.app.Constants.SHARED_PREF, 0);
        String client_id = pref.getString("registration_id", null);
        approvedQuotation(client_id);
    }


    private void approvedQuotation(String client_id) {
        JSONObject reqData = new JSONObject();
        try {
            reqData.put("client_id",client_id);
//            reqData.put("client_id","8da3923f-17c8-436d-89a0-baade80efb1e");
            reqData.put("status", "1");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("reqdata", "" + reqData);
        WebcallMaegerClass.getInstance().approvedQuotation1(OrderStatusActivity.this, Constants.POST_REQUEST, reqData, true);
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
                    OrderStatusAdapter approveQuotation = new OrderStatusAdapter(OrderStatusActivity.this, approveArrayLst);
                    if (approveArrayLst.size()>0) {
                        for (ApprovedQuotationModel p : approveArrayLst){
                            p.setSetSelection("0");
                        }
                        approvedRecyclerView.setAdapter(approveQuotation);
                    }else{

                    }
                }

            } else {
                approved_layout.setVisibility(View.GONE);
                error_message_Layoutapproved.setVisibility(View.VISIBLE);
//                approvedTextView.setText(baseResponse.getErrorMessage());
                approvedTextView.setText("No Order Found");
            }
    }
}
