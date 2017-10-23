package com.truxapp.fbfire.Adapter;

import android.content.Context;
import android.content.Intent;
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
import com.truxapp.fbfire.Activity.OrderStatusActivity;
import com.truxapp.fbfire.Model.ApprovedQuotationModel;
import com.truxapp.fbfire.Model.PendingDimension;
import com.truxapp.fbfire.R;

import java.util.ArrayList;
import java.util.Arrays;

public class OrderStatusAdapter extends RecyclerView.Adapter<OrderStatusAdapter.ViewHolder> {
    Context context;
    ArrayList<ApprovedQuotationModel> arrayList;

    public OrderStatusAdapter(OrderStatusActivity orderStatusActivity, ArrayList<ApprovedQuotationModel> approveArrayList) {
        this.context = orderStatusActivity;
        this.arrayList = approveArrayList;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_status_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ApprovedQuotationModel approvedQuotationModel = arrayList.get(position);
        if (approvedQuotationModel!=null){

            if (approvedQuotationModel.getSetSelection().equalsIgnoreCase("0")) {
                holder.orderStatusDetailLayout.setVisibility(View.GONE);
                holder.orderStatusImageView.setRotation(0);
                holder.orderStatusDescriptionLayout.setBackgroundResource(R.color.white_color);
            } else {
                holder.orderStatusDetailLayout.setVisibility(View.VISIBLE);
                holder.orderStatusDescriptionLayout.setBackgroundResource(R.color.quotation_background);
                holder.orderStatusImageView.setRotation(180);
            }

            holder.orderStatusDescriptionLayout.setOnClickListener(new View.OnClickListener() {
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

            holder.orderStatusSource.setText(approvedQuotationModel.getSourceiata());
            holder.orderStatusDate.setText(approvedQuotationModel.getAvailabilitydate());
            holder.orderStatusDestination.setText(approvedQuotationModel.getDestinationiata());
            holder.orderStatusManufacturerName.setText(approvedQuotationModel.getFlightdetail());
            holder.orderStatusPayload.setText("" + approvedQuotationModel.getPayload());
            holder.orderStatusVolume.setText("" + approvedQuotationModel.getVolume());
            holder.orderStatusLuggageType.setText(approvedQuotationModel.getLuggagetype());
            holder.orderStatusCost.setText("" + approvedQuotationModel.getLoadprice());

            PendingDimension[] dimensionModel = approvedQuotationModel.getDimesions();
            final ArrayList<PendingDimension> dimensionArrayList = new ArrayList<>(Arrays.asList(dimensionModel));

            holder.orderStatusDimension.setText("" + dimensionArrayList.get(0).getLength() + " x " + dimensionArrayList.get(0).getBreadth() + " x " + dimensionArrayList.get(0).getHeight() + "  " + dimensionArrayList.get(0).getNo_of_box());

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

            holder.countText.setText("" + approvedQuotationModel.getChatcount());
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
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView orderStatusSource, orderStatusDate, orderStatusDestination, orderStatusManufacturerName, orderStatusOrderId,
                orderStatusLuggageType, orderStatusPayload, orderStatusVolume, orderStatusDimension, orderStatusCost, countText;
        LinearLayout viewMoreLayout, orderStatusDescriptionLayout, orderStatusDetailLayout;
        ImageView orderStatusImageView;
        Button chatNowBtn;

        public ViewHolder(View itemView) {
            super(itemView);
            orderStatusSource = (TextView) itemView.findViewById(R.id.orderStatusSource);
            orderStatusDate = (TextView) itemView.findViewById(R.id.orderStatusDate);
            orderStatusDestination = (TextView) itemView.findViewById(R.id.orderStatusDestination);
            orderStatusManufacturerName = (TextView) itemView.findViewById(R.id.orderStatusManufacturerName);
            orderStatusOrderId = (TextView) itemView.findViewById(R.id.orderStatusOrderId);
            orderStatusLuggageType = (TextView) itemView.findViewById(R.id.orderStatusLuggageType);
            orderStatusPayload = (TextView) itemView.findViewById(R.id.orderStatusPayload);
            orderStatusVolume = (TextView) itemView.findViewById(R.id.orderStatusVolume);
            orderStatusDimension = (TextView) itemView.findViewById(R.id.orderStatusDimension);
            orderStatusCost = (TextView) itemView.findViewById(R.id.orderStatusCost);
            countText = (TextView) itemView.findViewById(R.id.countText);
            viewMoreLayout = (LinearLayout) itemView.findViewById(R.id.viewMoreLayout);
            orderStatusDescriptionLayout = (LinearLayout) itemView.findViewById(R.id.orderStatusDescriptionLayout);
            orderStatusDetailLayout = (LinearLayout) itemView.findViewById(R.id.orderStatusDetailLayout);
            orderStatusImageView = (ImageView) itemView.findViewById(R.id.orderStatusImageView);
            chatNowBtn = (Button) itemView.findViewById(R.id.chatNowBtn);
        }
    }
}