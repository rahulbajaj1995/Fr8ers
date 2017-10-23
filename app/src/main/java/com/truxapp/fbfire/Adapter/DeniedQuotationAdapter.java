package com.truxapp.fbfire.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.truxapp.fbfire.Activity.ChatActivity;
import com.truxapp.fbfire.Model.ApprovedQuotationModel;
import com.truxapp.fbfire.Model.PendingDimension;
import com.truxapp.fbfire.R;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by hp on 18/10/17.
 */

public class DeniedQuotationAdapter extends RecyclerView.Adapter<DeniedQuotationAdapter.ViewHolder>{
    Context context;
    ArrayList<ApprovedQuotationModel> approvedQuotationModels;

    public DeniedQuotationAdapter(FragmentActivity activity, ArrayList<ApprovedQuotationModel> approveArrayLst) {
        this.context = activity;
        this.approvedQuotationModels = approveArrayLst;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.denied_cardview_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final DeniedQuotationAdapter.ViewHolder holder, int position) {
        final ApprovedQuotationModel approvedQuotationModel = approvedQuotationModels.get(position);
        if (approvedQuotationModel != null) {
            if (approvedQuotationModel.getSetSelection().equalsIgnoreCase("0")) {
                holder.approvedDetailLayout.setVisibility(View.GONE);
                holder.approvedArrowImageView.setRotation(0);
                holder.approvedDescriptionLayout.setBackgroundResource(R.color.white_color);
            } else {
                holder.approvedDetailLayout.setVisibility(View.VISIBLE);
                holder.approvedDescriptionLayout.setBackgroundResource(R.color.quotation_background);
                holder.approvedArrowImageView.setRotation(180);
            }

            holder.approvedDescriptionLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (approvedQuotationModel.getSetSelection().equalsIgnoreCase("0")) {
                        approvedQuotationModel.setSetSelection("1");
                        notifyDataSetChanged();
                    } else {
                        approvedQuotationModel.setSetSelection("0");
                        notifyDataSetChanged();
                    }
                }
            });

            holder.approvedSource.setText(approvedQuotationModel.getSourceiata());
            holder.approveDate.setText(approvedQuotationModel.getAvailabilitydate());
            holder.approveDestination.setText(approvedQuotationModel.getDestinationiata());
            holder.approvedManufacturerName.setText(approvedQuotationModel.getFlightdetail());
            holder.approvedPayload.setText("" + approvedQuotationModel.getPayload());
            holder.approvedVolume.setText("" + approvedQuotationModel.getVolume());
            holder.approvedCargoType.setText(approvedQuotationModel.getLuggagetype());
            holder.approvedCost.setText("" + approvedQuotationModel.getLoadprice());
            holder.orderIdTV.setText("" + approvedQuotationModel.getOrderid());

            PendingDimension[] dimensionModel = approvedQuotationModel.getDimesions();
            final ArrayList<PendingDimension> dimensionArrayList = new ArrayList<>(Arrays.asList(dimensionModel));

            holder.approvedDimension.setText("" + dimensionArrayList.get(0).getLength() + " x " + dimensionArrayList.get(0).getBreadth() + " x " + dimensionArrayList.get(0).getHeight() + "  " + dimensionArrayList.get(0).getNo_of_box());

            if (dimensionArrayList.size() > 1) {
                holder.viewMoreLayout.setVisibility(View.VISIBLE);
            }

            holder.viewMoreLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showPopupDimension(view, dimensionArrayList);
                }
            });

            holder.chatNowBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ChatActivity.class);
                    intent.putExtra("Order_id", approvedQuotationModel.getOrderid());
                    context.startActivity(intent);
                }
            });

            if (approvedQuotationModel.getChatcount()==null){

            }else {
                holder.countText.setText("" + approvedQuotationModel.getChatcount());
            }
        }
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

    @Override
    public int getItemCount() {
        return approvedQuotationModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout approvedDescriptionLayout, approvedDetailLayout, viewMoreLayout;
        ImageView approvedArrowImageView;
        TextView approvedSource, approveDate, approveDestination, approvedManufacturerName, approvedPayload, approvedVolume,
                approvedDimension, approvedCargoType, approvedCost, orderIdTV, countText;
        Button chatNowBtn;

        public ViewHolder(View itemView) {
            super(itemView);
            approvedDescriptionLayout = (LinearLayout) itemView.findViewById(R.id.approvedDescriptionLayout);
            approvedDetailLayout = (LinearLayout) itemView.findViewById(R.id.approvedDetailLayout);
            viewMoreLayout = (LinearLayout) itemView.findViewById(R.id.viewMoreLayout);
            approvedArrowImageView = (ImageView) itemView.findViewById(R.id.approvedArrowImageView);
            approvedSource = (TextView) itemView.findViewById(R.id.approvedSource);
            approveDate = (TextView) itemView.findViewById(R.id.approveDate);
            approveDestination = (TextView) itemView.findViewById(R.id.approveDestination);
            approvedManufacturerName = (TextView) itemView.findViewById(R.id.approvedManufacturerName);
            approvedPayload = (TextView) itemView.findViewById(R.id.approvedPayload);
            approvedVolume = (TextView) itemView.findViewById(R.id.approvedVolume);
            approvedDimension = (TextView) itemView.findViewById(R.id.approvedDimension);
            approvedCargoType = (TextView) itemView.findViewById(R.id.approvedCargoType);
            approvedCost = (TextView) itemView.findViewById(R.id.approvedCost);
            orderIdTV = (TextView) itemView.findViewById(R.id.orderIdTV);
            countText = (TextView) itemView.findViewById(R.id.countText);
            chatNowBtn = (Button) itemView.findViewById(R.id.chatNowBtn);
        }
    }
}
