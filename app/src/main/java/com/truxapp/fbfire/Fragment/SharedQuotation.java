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

import com.truxapp.fbfire.Model.SharedQuotationModel;
import com.truxapp.fbfire.R;
import com.truxapp.fbfire.response.BaseResponse;
import com.truxapp.fbfire.response.SharedQuotationResponse;

import org.json.JSONException;
import org.json.JSONObject;

import static com.facebook.FacebookSdk.getApplicationContext;

public class SharedQuotation extends CommonBaseFragment {
    RecyclerView sharedRecyclerView;
    LinearLayout shared_layout, error_message_Layout;
    TextView sharedErrorMessage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shared_quotation, container, false);
        sharedErrorMessage = (TextView) view.findViewById(R.id.sharedErrorMessage);
        sharedRecyclerView = (RecyclerView) view.findViewById(R.id.sharedRecyclerView);
        shared_layout = (LinearLayout) view.findViewById(R.id.shared_layout);
        error_message_Layout = (LinearLayout) view.findViewById(R.id.error_message_Layout);
        sharedRecyclerView.setHasFixedSize(true);
        sharedRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        SharedPreferences pref = getApplicationContext().getSharedPreferences(com.truxapp.fbfire.app.Constants.SHARED_PREF, 0);
        String client_id = pref.getString("registration_id", null);
        sharedQuotation(client_id);
        return view;
    }

    private void sharedQuotation(String client_id) {
        JSONObject reqData = new JSONObject();
        try {
            reqData.put("client_id",client_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("reqdata", "" + reqData);
//        WebcallMaegerClass.getInstance().sharedQuotation(getActivity(), this, Constants.POST_REQUEST, reqData, true);
    }

    @Override
    public <T> void processFragmentResponse(T result) {
        super.processFragmentResponse(result);
        BaseResponse baseResponse = (BaseResponse) result;
        if (result instanceof SharedQuotationResponse)
            if (baseResponse.getErrorCode().equalsIgnoreCase("201")) {
                SharedQuotationResponse res = (SharedQuotationResponse) result;
                SharedQuotationModel shareQuotationModel [] = res.getResult();
                if (shareQuotationModel!=null){
/*                    ArrayList<SharedQuotationModel> sharedArrayLst = new ArrayList<>(Arrays.asList(shareQuotationModel));
                    SharedQuotationAdapter sharedQuotation = new SharedQuotationAdapter(getActivity(),sharedArrayLst);
                    if (sharedArrayLst.size()>0){
                        sharedRecyclerView.setAdapter(sharedQuotation);
                    }*/
                }
            } else {
//                Toast.makeText(getContext(), baseResponse.getErrorCode(), Toast.LENGTH_SHORT).show();
                shared_layout.setVisibility(View.GONE);
                error_message_Layout.setVisibility(View.VISIBLE);
                sharedErrorMessage.setText(baseResponse.getErrorMessage());
            }
    }

}
