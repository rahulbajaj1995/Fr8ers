package com.truxapp.fbfire.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.truxapp.fbfire.Activity.AvailableCharters;
import com.truxapp.fbfire.Model.FindLoadModel;
import com.truxapp.fbfire.Model.FlightDataRealm;
import com.truxapp.fbfire.Model.RealmController;
import com.truxapp.fbfire.R;

import java.util.ArrayList;

import io.realm.Realm;

public class flight_adapter extends RecyclerView.Adapter<flight_adapter.ViewHolder> {
    Context context;
    Realm realm;
    private ArrayList<FindLoadModel> findLoadModels;
    ArrayList<FindLoadModel> arrayListItem = new ArrayList<>();

    public flight_adapter(Context contexts, ArrayList<FindLoadModel> findLoadModels, Realm realm){
        this.context = contexts;
        this.findLoadModels = findLoadModels;
        this.realm = realm;
    }

    @Override
    public flight_adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.flight_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final flight_adapter.ViewHolder holder, final int position) {
        final FindLoadModel findLoadModel = findLoadModels.get(position);
//        holder.source_destination.setText(findLoadModel.getSourceairport());
        holder.availablePayloadTV.setText("" +findLoadModel.getAvailablepayload());
        holder.volumeTV.setText("" + findLoadModel.getMaxcargovol());
        holder.volumeTv.setText(Html.fromHtml("Volume (m<sup>3</sup>)"));
        holder.manufacturerTV.setText(findLoadModel.getManufacturername() + "-" + findLoadModel.getModelname() + "  ");
        /*if (findLoadModel.getDestinationairportname() == null){
            holder.flightLayout.setVisibility(View.GONE);
            holder.viewLayout.setVisibility(View.GONE);
        }*/

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (holder.checkBox.isChecked()){
                    arrayListItem.add(findLoadModel);
                    ((AvailableCharters)context).enableBtn();
            }else{
                    if (arrayListItem!=null && arrayListItem.size()>0){
                        arrayListItem.remove(findLoadModel);
                    }
                }
                if (arrayListItem.size()<=0){
                    ((AvailableCharters)context).disableBtn();
                }
            }
        });
    }

    public ArrayList<FindLoadModel> getArrayListItem(){
        return arrayListItem;
    }

    private void saveDataToDB(FindLoadModel findLoadModel) {
        RealmController.with((Activity) context).clearAllUserData();
        FlightDataRealm flightDataRealm  = new FlightDataRealm();
        flightDataRealm.setSourceairportlatitude(String.valueOf(findLoadModel.getSourceairportlatitude()));
        flightDataRealm.setSourceairportlongitude(String.valueOf(findLoadModel.getSourceairportlongitude()));
        flightDataRealm.setDestinationairportlongitude(String.valueOf(findLoadModel.getDestinationairportlongitude()));
        flightDataRealm.setDestinationairportlatitude(String.valueOf(findLoadModel.getDestinationairportlatitude()));
        realm.beginTransaction();
        realm.copyToRealm(flightDataRealm);
        realm.commitTransaction();
    }

    @Override
    public int getItemCount() {
        return findLoadModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView source_destination, manufacturerTV, availablePayloadTV, volumeTV, volumeTv;
        private LinearLayout viewLayout, flightLayout;
        CheckBox checkBox;

        public ViewHolder(View view) {
            super(view);
//            source_destination = (TextView) view.findViewById(R.id.source_destination);
            availablePayloadTV = (TextView) view.findViewById(R.id.availablePayloadTV);
            volumeTV = (TextView) view.findViewById(R.id.volumeTV);
            volumeTv = (TextView) view.findViewById(R.id.volumeTv);
            manufacturerTV = (TextView) view.findViewById(R.id.manufacturerTV);
/*            viewLayout = (LinearLayout) view.findViewById(R.id.viewLayout);
            flightLayout = (LinearLayout) view.findViewById(R.id.flightLayout);*/
            checkBox = (CheckBox) view.findViewById(R.id.check_box);
        }
    }
}