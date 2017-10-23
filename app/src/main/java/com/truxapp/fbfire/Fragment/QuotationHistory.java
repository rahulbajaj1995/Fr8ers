package com.truxapp.fbfire.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.truxapp.fbfire.Activity.HomeActivity;
import com.truxapp.fbfire.R;

public class QuotationHistory extends Fragment implements View.OnClickListener{
    LinearLayout shared_quotation_layout, pending_quotation_layout, approved_quotation_layout, denied_quotation_layout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quotation_history, container, false);
        shared_quotation_layout = (LinearLayout) view.findViewById(R.id.shared_quotation_layout);
        pending_quotation_layout = (LinearLayout) view.findViewById(R.id.pending_quotation_layout);
        approved_quotation_layout = (LinearLayout) view.findViewById(R.id.approved_quotation_layout);
        denied_quotation_layout = (LinearLayout) view.findViewById(R.id.denied_quotation_layout);
        pending_quotation_layout.setOnClickListener(this);
        shared_quotation_layout.setOnClickListener(this);
        approved_quotation_layout.setOnClickListener(this);
        denied_quotation_layout.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.shared_quotation_layout){
            ((HomeActivity)getActivity()).addFragment(new SharedQuotation(),"Shared Quotation");
        }else if (view.getId() == R.id.pending_quotation_layout){
            ((HomeActivity)getActivity()).addFragment(new PendingQuotation(), "Pending Quotation");
        }else if (view.getId() == R.id.approved_quotation_layout){
            ((HomeActivity)getActivity()).addFragment(new ApprovedQuotation(), "Approved Quotation");
        }else if (view.getId() == R.id.denied_quotation_layout){
            ((HomeActivity)getActivity()).addFragment(new DeniedQuotation(), "Denied Quotation");
        }
    }
}
