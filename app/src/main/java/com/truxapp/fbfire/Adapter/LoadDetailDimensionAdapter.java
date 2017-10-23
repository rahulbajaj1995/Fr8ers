package com.truxapp.fbfire.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.truxapp.fbfire.Activity.LoadDetails1;
import com.truxapp.fbfire.Model.LoadDetailDimensionModel;
import com.truxapp.fbfire.R;

import java.util.ArrayList;

public class LoadDetailDimensionAdapter extends RecyclerView.Adapter<LoadDetailDimensionAdapter.ViewHolder>{
    Context context;
    ArrayList<LoadDetailDimensionModel> arrayList;
    ViewHolder holder1;

    public LoadDetailDimensionAdapter(LoadDetails1 loadDetails1, ArrayList<LoadDetailDimensionModel> dimensionArrayList) {
        this.context = loadDetails1;
        this.arrayList = dimensionArrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.box_layout_load_details_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final LoadDetailDimensionModel loadDetailDimensionModel = arrayList.get(position);
        /*holder.linearLayout1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (position>0) {
                    deleteDialogue(position);
                }
                return false;
            }
        });*/

         holder1 = holder;

        if(loadDetailDimensionModel.getLength()!=null && loadDetailDimensionModel.getLength().length()>0 ){
            holder.lengthET.setText(loadDetailDimensionModel.getLength());
        }

        if(loadDetailDimensionModel.getBreadth()!=null && loadDetailDimensionModel.getBreadth().length()>0 ){
            holder.widthET.setText(loadDetailDimensionModel.getBreadth());
        }

         if(loadDetailDimensionModel.getHeight()!=null && loadDetailDimensionModel.getHeight().length()>0 ){
            holder.heightET.setText(loadDetailDimensionModel.getHeight());
        }

         if(loadDetailDimensionModel.getNoOfBox()!=null && loadDetailDimensionModel.getNoOfBox().length()>0 ){
            holder.numbOfBoxesET.setText(loadDetailDimensionModel.getNoOfBox());
        }

        holder.lengthET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                loadDetailDimensionModel.setLength(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        holder.widthET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                loadDetailDimensionModel.setBreadth(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        holder.heightET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                loadDetailDimensionModel.setHeight(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        holder.numbOfBoxesET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                loadDetailDimensionModel.setNoOfBox(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    public void setLengthDrawable(){
        holder1.lengthLayout.setBackgroundResource(R.drawable.red_square_box);
    }
    public void setLengthDrawableBack(){
        holder1.lengthLayout.setBackgroundResource(R.drawable.sqaure_btn);
    }
    public void setBreadthDrawable(){
        holder1.widthLayout.setBackgroundResource(R.drawable.red_square_box);
    }
    public void setBreadthDrawableBack(){
        holder1.widthLayout.setBackgroundResource(R.drawable.sqaure_btn);
    }
    public void setHeightDrawable(){
        holder1.heightLayout.setBackgroundResource(R.drawable.red_square_box);
    }
    public void setHeightDrawableBack(){
        holder1.heightLayout.setBackgroundResource(R.drawable.sqaure_btn);
    }
    public void setNumbOfBoxDrawable(){
        holder1.numbOfBoxLayout.setBackgroundResource(R.drawable.red_square_box);
    }
    public void setNumbOfBoxDrawableBack(){
        holder1.numbOfBoxLayout.setBackgroundResource(R.drawable.sqaure_btn);
    }

    public void deleteRow() {
            arrayList.remove(arrayList.size()-1);
            notifyDataSetChanged();
        }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    public  ArrayList<LoadDetailDimensionModel> getArrayListValue(){
        return arrayList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        EditText lengthET, heightET, widthET, numbOfBoxesET;
        LinearLayout linearLayout1, heightLayout, lengthLayout, widthLayout, numbOfBoxLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            linearLayout1 = (LinearLayout) itemView.findViewById(R.id.linearLayout1);
            heightLayout = (LinearLayout) itemView.findViewById(R.id.heightLayout);
            lengthLayout = (LinearLayout) itemView.findViewById(R.id.lengthLayout);
            widthLayout = (LinearLayout) itemView.findViewById(R.id.widthLayout);
            numbOfBoxLayout = (LinearLayout) itemView.findViewById(R.id.numbOfBoxLayout);
            lengthET  = (EditText) itemView.findViewById(R.id.lengthET);
            widthET = (EditText) itemView.findViewById(R.id.widthET);
            heightET = (EditText) itemView.findViewById(R.id.heightET);
            numbOfBoxesET = (EditText) itemView.findViewById(R.id.numbOfBoxesET);
        }
    }
}
