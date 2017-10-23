package com.truxapp.fbfire.Adapter;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.truxapp.fbfire.Model.PendingDimension;
import com.truxapp.fbfire.Model.PendingQuotationModel;
import com.truxapp.fbfire.R;

import java.util.ArrayList;
import java.util.Arrays;

public class PendingQuotationAdapter extends RecyclerView.Adapter<PendingQuotationAdapter.ViewHolder> {
    Context context;
    private ArrayList<PendingQuotationModel> pendingQuotationModels;

    public PendingQuotationAdapter(FragmentActivity activity, ArrayList<PendingQuotationModel> pendingArrayLst) {
        this.context = activity;
        this.pendingQuotationModels = pendingArrayLst;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pending_quotation_cardview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final PendingQuotationModel pendingQuotationModel = pendingQuotationModels.get(position);
        if (pendingQuotationModel != null) {
            if (pendingQuotationModel.getSetSelection().equalsIgnoreCase("0")) {
                holder.pendingDetailLayout.setVisibility(View.GONE);
                holder.pendingArrowImageView.setRotation(0);
                holder.pendingDescriptionLayout.setBackgroundResource(R.color.white_color);
            } else {
                holder.pendingDetailLayout.setVisibility(View.VISIBLE);
                holder.pendingArrowImageView.setRotation(180);
                holder.pendingDescriptionLayout.setBackgroundResource(R.color.quotation_background);
            }

            holder.pendingDescriptionLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (pendingQuotationModel.getSetSelection().equalsIgnoreCase("0")) {
                        pendingQuotationModel.setSetSelection("1");
                        notifyDataSetChanged();
                    } else {
                        pendingQuotationModel.setSetSelection("0");
                        notifyDataSetChanged();
                    }
                }
            });
            holder.manufacturerName.setText(pendingQuotationModel.getFlightdetail());
            holder.pendingSource.setText(pendingQuotationModel.getSourceiata());
            holder.pendingDate.setText(pendingQuotationModel.getAvailabilitydate());
            holder.pendingDestination.setText(pendingQuotationModel.getDestinationiata());
            holder.pendingPayload.setText("" + pendingQuotationModel.getPayload());
            holder.pendingVolume.setText("" + pendingQuotationModel.getVolume());
            holder.pendingLuggageType.setText(pendingQuotationModel.getLuggagetype());
            holder.pendingCostTV.setText("" + pendingQuotationModel.getTarget_price());
            holder.orderIdTV.setText("" + pendingQuotationModel.getOrderid());

            PendingDimension[] dimensionModel = pendingQuotationModel.getDimesions();
            final ArrayList<PendingDimension> dimensionArrayList = new ArrayList<>(Arrays.asList(dimensionModel));

            holder.pendingDimension.setText("" + dimensionArrayList.get(0).getLength() + " x " + dimensionArrayList.get(0).getBreadth() + " x " + dimensionArrayList.get(0).getHeight() + "  " + dimensionArrayList.get(0).getNo_of_box());

            if (dimensionArrayList.size() > 1) {
                holder.viewMoreLayout.setVisibility(View.VISIBLE);
            }

            holder.viewMoreLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showPopupDimension(view, dimensionArrayList);
                }
            });
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
        return pendingQuotationModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout pendingDescriptionLayout, pendingDetailLayout, viewMoreLayout;
        ImageView pendingArrowImageView;
        TextView manufacturerName, pendingSource, pendingDate, pendingDestination, pendingPayload, pendingVolume, pendingDimension,
                pendingLuggageType, pendingCostTV, orderIdTV;

        public ViewHolder(View itemView) {
            super(itemView);
            pendingDescriptionLayout = (LinearLayout) itemView.findViewById(R.id.pendingDescriptionLayout);
            viewMoreLayout = (LinearLayout) itemView.findViewById(R.id.viewMoreLayout);
            pendingDetailLayout = (LinearLayout) itemView.findViewById(R.id.pendingDetailLayout);
            manufacturerName = (TextView) itemView.findViewById(R.id.pendingManufacturerName);
            pendingSource = (TextView) itemView.findViewById(R.id.pendingSource);
            pendingDate = (TextView) itemView.findViewById(R.id.pendingDate);
            pendingDestination = (TextView) itemView.findViewById(R.id.pendingDestination);
            pendingPayload = (TextView) itemView.findViewById(R.id.pendingPayload);
            pendingVolume = (TextView) itemView.findViewById(R.id.pendingVolume);
            pendingDimension = (TextView) itemView.findViewById(R.id.pendingDimension);
            pendingLuggageType = (TextView) itemView.findViewById(R.id.pendingLuggageType);
            pendingCostTV = (TextView) itemView.findViewById(R.id.pendingCostTV);
            orderIdTV = (TextView) itemView.findViewById(R.id.orderIdTV);
            pendingArrowImageView = (ImageView) itemView.findViewById(R.id.pendingArrowImageView);
        }
    }
}
