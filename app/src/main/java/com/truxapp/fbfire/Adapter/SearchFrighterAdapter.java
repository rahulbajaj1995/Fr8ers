package com.truxapp.fbfire.Adapter;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.truxapp.fbfire.Activity.SearchFrighterActivity;
import com.truxapp.fbfire.Model.SearchFrighterContainerModel;
import com.truxapp.fbfire.Model.SearchFrighterModel;
import com.truxapp.fbfire.Model.SearchFrighterPalletModel;
import com.truxapp.fbfire.R;

import java.util.ArrayList;
import java.util.Arrays;

public class SearchFrighterAdapter extends RecyclerView.Adapter<SearchFrighterAdapter.ViewHolder>{
    ArrayList<SearchFrighterModel> searchArray;
    Context context;
    ArrayList<SearchFrighterModel> arrayListItem = new ArrayList<>();

    public SearchFrighterAdapter(ArrayList<SearchFrighterModel> searchArrayList, Context context) {
        this.searchArray = searchArrayList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_frighter_adapter_card_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.viewLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.availableLinearLayout.getVisibility() == View.GONE &&
                        holder.downLayout.getVisibility() == View.GONE){
                    holder.red_arrow_IV.setRotation(180);
                    holder.availableLinearLayout.setVisibility(View.VISIBLE);
                    holder.downLayout.setVisibility(View.VISIBLE);
                }else{
                    holder.red_arrow_IV.setRotation(0);
                    holder.availableLinearLayout.setVisibility(View.GONE);
                    holder.downLayout.setVisibility(View.GONE);
                }
            }
        });

        final SearchFrighterModel searchFrighterModel = searchArray.get(position);
        if (searchFrighterModel.getManufacturername()!=null && searchFrighterModel.getManufacturername().length()>0){
            holder.manufacturerTV.setText("" + searchFrighterModel.getManufacturername());
            if (searchFrighterModel.getModelname()!=null && searchFrighterModel.getModelname().length()>0){
                holder.manufacturerTV.setText("" + searchFrighterModel.getManufacturername() + " " + searchFrighterModel.getModelname());
            }
        }

        holder.availableLoadTV.setText("" + searchFrighterModel.getAvailablepayload());
        holder.volumeTV.setText("" + searchFrighterModel.getMaxcargovol());

        if (searchFrighterModel.isAlreadyRequest()){
            holder.check_box.setVisibility(View.GONE);
            holder.searchFrighterTV.setVisibility(View.GONE);
            holder.searchFrighterReplaceTV.setVisibility(View.VISIBLE);
            holder.main_layout.setBackgroundResource(R.color.quotation_background);
        }

        if (searchFrighterModel.getTargetprice()!=null && searchFrighterModel.getTargetprice().length()>0){
            holder.targetPriceTV.setVisibility(View.VISIBLE);
            holder.targetPriceTV.setText("Cost (USD) - " + searchFrighterModel.getTargetprice());
        }

        final ArrayList<String> loadTypeList =  searchFrighterModel.getLoadtype();
        holder.loadTypeTV.setText(loadTypeList.get(0));
        if (loadTypeList!=null && loadTypeList.size()>1){
            holder.viewMoreLayout.setVisibility(View.VISIBLE);
        }

        holder.viewMoreLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(view, loadTypeList);
            }
        });

        SearchFrighterPalletModel [] model = searchFrighterModel.getPalletdetail();
        final ArrayList<SearchFrighterPalletModel> palletModels = new ArrayList<>(Arrays.asList(model));
        if (palletModels!=null && palletModels.size()>0){
            holder.palletTV.setText("Pallet: "+ palletModels.get(0).getPalletType() +"\nAvailability: " +palletModels.get(0).getPalletValue());
            if (palletModels.size()>1){
                holder.viewMorePalletLayout.setVisibility(View.VISIBLE);
            }
        }else{
            holder.availablePalletLinearLayout.setVisibility(View.GONE);
        }

        holder.viewMorePalletLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupPallet(view, palletModels);
            }
        });

        final SearchFrighterContainerModel [] containerModel = searchFrighterModel.getContainerdetail();
        final ArrayList<SearchFrighterContainerModel> containerList = new ArrayList<>(Arrays.asList(containerModel));
        if (containerList!=null && containerList.size()>0){
            holder.containerTV.setText("Container: " + containerList.get(0).getContrainerType() + "\nAvailablity: " + containerList.get(0).getContrainerValue());
            if (containerList.size()>1){
                holder.viewMoreConatinerLayout.setVisibility(View.VISIBLE);
            }
        }else{
            holder.availableContainerLinearLayout.setVisibility(View.GONE);
        }

        holder.viewMoreConatinerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupContainer(view, containerList);
            }
        });

/*        //Dynamic View
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(loadTypeList!=null && loadTypeList.size()>0){
            for (int i=0; i<loadTypeList.size(); i++) {
                View view1 = layoutInflater.inflate(R.layout.text_view, null);
                TextView awb_left = (TextView)view1.findViewById(R.id.textView);
                awb_left.setText(loadTypeList.get(i));
                holder.innerCargoLayout.addView(view1);
            }
        }*/

        holder.check_box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (holder.check_box.isChecked()) {
                    arrayListItem.add(searchFrighterModel);
                    ((SearchFrighterActivity)context).showRequestBtn();
                }else {
                    if (arrayListItem!=null && arrayListItem.size()>0){
                        arrayListItem.remove(searchFrighterModel);
                    }
                }
                if (arrayListItem.size()<=0){
                    ((SearchFrighterActivity)context).hideRequestBtn();
                }
            }
        });
    }

    private void showPopupContainer(View view, ArrayList<SearchFrighterContainerModel> containerrList) {
        Context wrapper = new ContextThemeWrapper(context, R.style.AppTheme);
        final PopupMenu popup = new PopupMenu(wrapper, view);
        for (int i=1; i<containerrList.size(); i++){
            popup.getMenu().add("Container: "+ containerrList.get(i).getContrainerType() +", Availability: " +containerrList.get(i).getContrainerValue());
        }
        popup.setGravity(Gravity.BOTTOM);
        popup.show();
    }

    private void showPopupPallet(View view, ArrayList<SearchFrighterPalletModel> palletList) {
        Context wrapper = new ContextThemeWrapper(context, R.style.AppTheme);
        final PopupMenu popup = new PopupMenu(wrapper, view);
        for (int i=1; i<palletList.size(); i++){
            popup.getMenu().add("Pallet: "+ palletList.get(i).getPalletType() +", Availability: " +palletList.get(i).getPalletValue());
        }
        popup.setGravity(Gravity.BOTTOM);
        popup.show();
    }

    private void showPopupMenu(View view, ArrayList<String> loadTypeList) {
        Context wrapper = new ContextThemeWrapper(context, R.style.AppTheme);
        final PopupMenu popup = new PopupMenu(wrapper, view);
        for (int i=1; i<loadTypeList.size(); i++){
            popup.getMenu().add(loadTypeList.get(i));
        }
        popup.setGravity(Gravity.BOTTOM);
        popup.show();
    }

    public ArrayList<SearchFrighterModel> getArrayListItem(){
        return arrayListItem;
    }


    @Override
    public int getItemCount() {
        return searchArray.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView manufacturerTV, loadTypeTV, palletTV, containerTV, targetPriceTV, searchFrighterTV, searchFrighterReplaceTV,
                availableLoadTV, volumeTV;
        ImageView red_arrow_IV;
        CheckBox check_box;
        LinearLayout viewLinearLayout, availableLinearLayout, availableCargoLinearLayout, viewMoreLayout,main_layout,
                availablePalletLinearLayout, downLayout, viewMorePalletLayout, availableContainerLinearLayout, viewMoreConatinerLayout, selectFrighterLayout;

        public ViewHolder(View view) {
            super(view);
            loadTypeTV =(TextView) view.findViewById(R.id.loadTypeTV);
            palletTV =(TextView) view.findViewById(R.id.palletTV);
            manufacturerTV =(TextView) view.findViewById(R.id.manufacturerTV);
            containerTV =(TextView) view.findViewById(R.id.containerTV);
            targetPriceTV =(TextView) view.findViewById(R.id.targetPriceTV);
            searchFrighterTV =(TextView) view.findViewById(R.id.searchFrighterTV);
            searchFrighterReplaceTV =(TextView) view.findViewById(R.id.searchFrighterReplaceTV);
            availableLoadTV =(TextView) view.findViewById(R.id.availableLoadTV);
            volumeTV =(TextView) view.findViewById(R.id.volumeTV);

            check_box =(CheckBox) view.findViewById(R.id.check_box);
            red_arrow_IV = (ImageView) view.findViewById(R.id.red_arrow_IV);
            viewLinearLayout =(LinearLayout) view.findViewById(R.id.viewLinearLayout);
            availableLinearLayout =(LinearLayout) view.findViewById(R.id.availableLinearLayout);
            availableCargoLinearLayout =(LinearLayout) view.findViewById(R.id.availableCargoLinearLayout);
            viewMoreLayout =(LinearLayout) view.findViewById(R.id.viewMoreLayout);
            availablePalletLinearLayout =(LinearLayout) view.findViewById(R.id.availablePalletLinearLayout);
            downLayout =(LinearLayout) view.findViewById(R.id.downLayout);
            viewMorePalletLayout =(LinearLayout) view.findViewById(R.id.viewMorePalletLayout);
            availableContainerLinearLayout =(LinearLayout) view.findViewById(R.id.availableContainerLinearLayout);
            viewMoreConatinerLayout =(LinearLayout) view.findViewById(R.id.viewMoreConatinerLayout);
            selectFrighterLayout =(LinearLayout) view.findViewById(R.id.selectFrighterLayout);
            main_layout = (LinearLayout) view.findViewById(R.id.main_layout);
        }
    }
}
