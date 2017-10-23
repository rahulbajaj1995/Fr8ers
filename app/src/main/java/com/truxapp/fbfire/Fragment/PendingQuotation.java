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

import static com.facebook.FacebookSdk.getApplicationContext;

public class PendingQuotation extends CommonBaseFragment {
    RecyclerView pendingRecyclerView;
    TextView penidngTextView;
    LinearLayout pending_layout, error_message_Layoutpending;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pending_quotation, container, false);
        pendingRecyclerView = (RecyclerView) view.findViewById(R.id.pendingRecyclerView);
        pending_layout = (LinearLayout) view.findViewById(R.id.pending_layout);
        error_message_Layoutpending = (LinearLayout) view.findViewById(R.id.error_message_Layoutpending);
        penidngTextView = (TextView) view.findViewById(R.id.penidngTextView);
        pendingRecyclerView.setHasFixedSize(true);
        pendingRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        SharedPreferences pref = getApplicationContext().getSharedPreferences(com.truxapp.fbfire.app.Constants.SHARED_PREF, 0);
        String client_id = pref.getString("registration_id", null);
        pendingQuotation(client_id);

        return view;
    }

    private void pendingQuotation(String client_id) {
        JSONObject reqData = new JSONObject();
        try {
            reqData.put("client_id",client_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("reqdata", "" + reqData);
        WebcallMaegerClass.getInstance().pendingQuotation(getActivity(), this, Constants.POST_REQUEST, reqData, true);
    }
    @Override
    public <T> void processFragmentResponse(T result) {
        super.processFragmentResponse(result);
        BaseResponse baseResponse = (BaseResponse) result;
        if (result instanceof PendingQuotationResponse)
            if (baseResponse.getErrorCode().equalsIgnoreCase("201")) {
                PendingQuotationResponse res = (PendingQuotationResponse) result;
                PendingQuotationModel pendingQuotationModel [] = res.getResult();
                if (pendingQuotationModel!=null){
                    ArrayList<PendingQuotationModel> pendingArrayLst = new ArrayList<>(Arrays.asList(pendingQuotationModel));
                    PendingQuotationAdapter pendingQuotationHistory = new PendingQuotationAdapter(getActivity(),pendingArrayLst);
                    pendingRecyclerView.setAdapter(pendingQuotationHistory);
                }

            } else {
//                Toast.makeText(getContext(), baseResponse.getErrorCode(), Toast.LENGTH_SHORT).show();
                pending_layout.setVisibility(View.GONE);
                error_message_Layoutpending.setVisibility(View.VISIBLE);
                penidngTextView.setText(baseResponse.getErrorMessage());
            }
    }
}
