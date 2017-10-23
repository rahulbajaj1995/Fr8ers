package com.truxapp.fbfire.Fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.truxapp.fbfire.Adapter.ApprovedQuotationAdapter;
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

import static com.facebook.FacebookSdk.getApplicationContext;

public class DeniedQuotation extends CommonBaseFragment {
    RecyclerView deniedRecyclerView;
    LinearLayout denied_layout, error_message_Layoutdenied;
    TextView deniedTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_denied_quotation, container, false);
        deniedRecyclerView = (RecyclerView) view.findViewById(R.id.deniedRecyclerView);
        denied_layout = (LinearLayout) view.findViewById(R.id.denied_layout);
        error_message_Layoutdenied = (LinearLayout) view.findViewById(R.id.error_message_Layoutdenied);
        deniedTextView = (TextView) view.findViewById(R.id.deniedTextView);
        deniedRecyclerView.setHasFixedSize(true);
        deniedRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        SharedPreferences pref = getApplicationContext().getSharedPreferences(com.truxapp.fbfire.app.Constants.SHARED_PREF, 0);
        String client_id = pref.getString("registration_id", null);
        deniedQuotation(client_id);
        return view;
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
        WebcallMaegerClass.getInstance().approvedQuotation(getActivity(), this, Constants.POST_REQUEST, reqData, true);
    }

    @Override
    public <T> void processFragmentResponse(T result) {
        super.processFragmentResponse(result);
        BaseResponse baseResponse = (BaseResponse) result;
        if (result instanceof ApprovedQuotationResponse)
            if (baseResponse.getErrorCode().equalsIgnoreCase("201")) {
                ApprovedQuotationResponse res = (ApprovedQuotationResponse) result;
                ApprovedQuotationModel approveQuotationModel [] = res.getResult();
                if (approveQuotationModel!=null){
                    ArrayList<ApprovedQuotationModel> approveArrayLst = new ArrayList<>(Arrays.asList(approveQuotationModel));
                    ApprovedQuotationAdapter approveQuotation = new ApprovedQuotationAdapter(getActivity(), approveArrayLst);
                    deniedRecyclerView.setAdapter(approveQuotation);
                }

            } else {
//                Toast.makeText(getContext(), baseResponse.getErrorCode(), Toast.LENGTH_SHORT).show();
                denied_layout.setVisibility(View.GONE);
                error_message_Layoutdenied.setVisibility(View.VISIBLE);
                deniedTextView.setText(baseResponse.getErrorMessage());
            }
    }

}
