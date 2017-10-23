package com.truxapp.fbfire.Fragment;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.truxapp.fbfire.R;

import static com.facebook.FacebookSdk.getApplicationContext;

public class ProfileFragment extends Fragment {
    TextView nameTV, emailTV, alternateEmailTV, mobileNumberTV, alternateMobileNumberTV, companyDetailsTV, addressTV;
    String fName, lName, email, alternateEmail, mobileNumber, alternateMobileNumber, companyDetails, address;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        nameTV = (TextView) view.findViewById(R.id.nameTV);
        emailTV = (TextView) view.findViewById(R.id.emailTV);
        alternateEmailTV = (TextView) view.findViewById(R.id.alternateEmailTV);
        mobileNumberTV = (TextView) view.findViewById(R.id.mobileNumberTV);
        alternateMobileNumberTV = (TextView) view.findViewById(R.id.alternateMobileNumberTV);
        companyDetailsTV = (TextView) view.findViewById(R.id.companyDetailsTV);
//        addressTV = (TextView) view.findViewById(R.id.addressTV);

        getProfileData();

        return view;
    }

    private void getProfileData() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(com.truxapp.fbfire.app.Constants.SHARED_PREF, 0);
        fName = pref.getString("fName", null);
        lName = pref.getString("lName", null);
        email = pref.getString("email", null);
        alternateEmail = pref.getString("alternateEmail", null);
        mobileNumber = pref.getString("mobileNumber", null);
        alternateMobileNumber = pref.getString("alternateMobileNumber", null);
        companyDetails = pref.getString("companyDetails", null);
//        address = pref.getString("address", null);

        nameTV.setText(fName + " " + lName);
        emailTV.setText(email);
        alternateEmailTV.setText(alternateEmail);
        mobileNumberTV.setText(mobileNumber);
        alternateMobileNumberTV.setText(alternateMobileNumber);
        companyDetailsTV.setText(companyDetails);
//        addressTV.setText(address);
    }
}
