package com.truxapp.fbfire.Adapter;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.truxapp.fbfire.Activity.SharedQuotation1;
import com.truxapp.fbfire.Model.PendingDimension;
import com.truxapp.fbfire.Model.SharedQuotationModel;
import com.truxapp.fbfire.R;

import java.util.ArrayList;
import java.util.Arrays;

public class SharedQuotationAdapter extends RecyclerView.Adapter<SharedQuotationAdapter.ViewHolder> {
    ArrayList<SharedQuotationModel> sharedQuotationModels;
    Context context;
    SharedQuotation1 activity;

    public SharedQuotationAdapter(SharedQuotation1 activity, ArrayList<SharedQuotationModel> sharedArrayLst) {
        this.context = activity;
        this.activity = activity;
        this.sharedQuotationModels = sharedArrayLst;
    }

    @Override
    public SharedQuotationAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shared_quotation_cardview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SharedQuotationAdapter.ViewHolder holder, final int position) {
        final SharedQuotationModel sharedQuotationModel = sharedQuotationModels.get(position);
        if (sharedQuotationModel != null) {
            final boolean status = sharedQuotationModel.isBidtype();
            final String loadavailabilityid = sharedQuotationModel.getLoadavailabilityid();
            String title = "";
            /*if (sharedQuotationModel.isBidtype()) {
                holder.approveTextView.setText("RENEGOTIABLE");
                title = "RENEGOTIABLE";
            } else if (!sharedQuotationModel.isBidtype()) {
                holder.approveTextView.setText("APPROVE");
                title = "APPROVE";
            }
*/
            if (sharedQuotationModel.getSetSelection().equalsIgnoreCase("0")) {
                holder.sharedDetailLayout.setVisibility(View.GONE);
                holder.sharedArrowImageView.setRotation(0);
                holder.sharedDescriptionLayout.setBackgroundResource(R.color.white_color);
            } else {
                holder.sharedDetailLayout.setVisibility(View.VISIBLE);
                holder.sharedArrowImageView.setRotation(180);
                holder.sharedDescriptionLayout.setBackgroundResource(R.color.quotation_background);
            }

            holder.sharedDescriptionLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (sharedQuotationModel.getSetSelection().equalsIgnoreCase("0")) {
                        sharedQuotationModel.setSetSelection("1");
                        notifyDataSetChanged();
                    } else {
                        sharedQuotationModel.setSetSelection("0");
                        notifyDataSetChanged();
                    }
                }
            });
            holder.sharedSource.setText(sharedQuotationModel.getSourceiata());
            holder.sharedDate.setText(sharedQuotationModel.getAvailabilitydate());
            holder.sharedDestination.setText(sharedQuotationModel.getDestinationiata());
            holder.sharedManufacturerName.setText(sharedQuotationModel.getFlightdetail());
            holder.sharedPayload.setText("" + sharedQuotationModel.getPayload());
            holder.sharedVolume.setText("" + sharedQuotationModel.getVolume());
            holder.sharedLuggageType.setText(sharedQuotationModel.getLuggagetype());
            holder.orderIdTV.setText(""+ sharedQuotationModel.getOrderid());


            if (sharedQuotationModel.getLoadprice()!=null) {
                holder.sharedCost.setVisibility(View.VISIBLE);
                holder.sharedCost.setText("" + sharedQuotationModel.getLoadprice());
            }
            holder.sharedCost.setText("" + sharedQuotationModel.getLoadprice());

            PendingDimension[] dimensionModel = sharedQuotationModel.getDimesions();
            final ArrayList<PendingDimension> dimensionArrayList = new ArrayList<>(Arrays.asList(dimensionModel));

            holder.sharedDimension.setText("" + dimensionArrayList.get(0).getLength() + " x " + dimensionArrayList.get(0).getBreadth() + " x " + dimensionArrayList.get(0).getHeight() + "  " + dimensionArrayList.get(0).getNo_of_box());

            if (dimensionArrayList.size() > 1) {
                holder.viewMoreLayout.setVisibility(View.VISIBLE);
            }

            holder.viewMoreLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showPopupDimension(view, dimensionArrayList);
                }
            });

            final String finalTitle = title;
            holder.approveTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    approvedAlertDialogue(loadavailabilityid, status, finalTitle);
                }
            });

            holder.rejectTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    betterPriceDialoge(loadavailabilityid);
                }
            });
        }
    }

    public void betterPriceDialoge(final String loadavailabilityid){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.better_price_alert, null);
        dialogBuilder.setView(dialogView);
        final TextView popUpmsg = (TextView) dialogView.findViewById(R.id.popUpmsg);
        final EditText amountET = (EditText) dialogView.findViewById(R.id.amountET);

        Button okBtn = (Button) dialogView.findViewById(R.id.okBtn);
        Button cancelBtn = (Button) dialogView.findViewById(R.id.cancelBtn);
        final AlertDialog alertDialog = dialogBuilder.create();

        alertDialog.show();
        alertDialog.setCancelable(false);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.hide();
            }
        });

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (amountET.getText().toString()!=null && amountET.getText().toString().length()>0){
                    String ammount = amountET.getText().toString();
                    ((SharedQuotation1)context).betterPriceQuotation(loadavailabilityid, ammount);
                    alertDialog.cancel();
                    InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);

                }else{
                    ((SharedQuotation1)context).callMsnackbar();
                }
            }
        });
    }

    private void showPopupDimension(View view, ArrayList<PendingDimension> dimensionArrayList) {
        Context wrapper = new ContextThemeWrapper(context, R.style.AppTheme);
        final PopupMenu popup = new PopupMenu(wrapper, view);
        for (int i = 1; i < dimensionArrayList.size(); i++) {
            popup.getMenu().add("Dimension : " + dimensionArrayList.get(i).getLength() + " x " + dimensionArrayList.get(i).getBreadth() + " x " + dimensionArrayList.get(i).getHeight() + "  " + dimensionArrayList.get(i).getNo_of_box());
        }
        popup.setGravity(Gravity.BOTTOM);
        popup.show();
    }

    public void approvedAlertDialogue(final String loadavailabilityid, final Boolean status, String message) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.custom_alert_dialogue, null);
        dialogBuilder.setView(dialogView);
        TextView popUpmsg = (TextView) dialogView.findViewById(R.id.popUpmsg);
        Button okBtn = (Button) dialogView.findViewById(R.id.okBtn);
        final Button cancelBtn = (Button) dialogView.findViewById(R.id.cancelBtn);
//        okBtn.setText(message);
        popUpmsg.setText("Are you sure you want to Accept this Quotation");

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
        alertDialog.setCancelable(false);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.cancel();
            }
        });
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.cancel();
                Log.d("error_check", "chdk");
             ((SharedQuotation1)context).changeQuotation(loadavailabilityid, status );

            }
        });
    }

    /*public void rejectAlertDialogue(final String loadavailabilityid, final Boolean status) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.custom_alert_dialogue, null);
        dialogBuilder.setView(dialogView);
        TextView popUpmsg = (TextView) dialogView.findViewById(R.id.popUpmsg);
        Button okBtn = (Button) dialogView.findViewById(R.id.okBtn);
        Button cancelBtn = (Button) dialogView.findViewById(R.id.cancelBtn);
        popUpmsg.setText("Are you sure you want to REJECT this Quotation");

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
        alertDialog.setCancelable(false);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.hide();
            }
        });
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//           ((HomeActivity)context).rejectQuoation(loadavailabilityid, status);
            }
        });
    }*/

    @Override
    public int getItemCount() {
        return sharedQuotationModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView sharedSource, sharedDate, sharedDestination, sharedManufacturerName, sharedPayload,
                sharedVolume, sharedDimension, sharedLuggageType, sharedCost, approveTextView, rejectTextView, orderIdTV;
        LinearLayout sharedDescriptionLayout, sharedDetailLayout, viewMoreLayout;
        ImageView sharedArrowImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            sharedArrowImageView = (ImageView) itemView.findViewById(R.id.sharedArrowImageView);
            sharedDescriptionLayout = (LinearLayout) itemView.findViewById(R.id.sharedDescriptionLayout);
            sharedDetailLayout = (LinearLayout) itemView.findViewById(R.id.sharedDetailLayout);
            viewMoreLayout = (LinearLayout) itemView.findViewById(R.id.viewMoreLayout);

            sharedSource = (TextView) itemView.findViewById(R.id.sharedSource);
            sharedDate = (TextView) itemView.findViewById(R.id.sharedDate);
            sharedDestination = (TextView) itemView.findViewById(R.id.sharedDestination);
            sharedManufacturerName = (TextView) itemView.findViewById(R.id.sharedManufacturerName);
            sharedPayload = (TextView) itemView.findViewById(R.id.sharedPayload);
            sharedVolume = (TextView) itemView.findViewById(R.id.sharedVolume);
            sharedDimension = (TextView) itemView.findViewById(R.id.sharedDimension);
            sharedLuggageType = (TextView) itemView.findViewById(R.id.sharedLuggageType);
            sharedCost = (TextView) itemView.findViewById(R.id.sharedCost);
            rejectTextView = (TextView) itemView.findViewById(R.id.rejectTextView);
            approveTextView = (TextView) itemView.findViewById(R.id.approveTextView);
            orderIdTV = (TextView) itemView.findViewById(R.id.orderIdTV);
        }
    }
}
