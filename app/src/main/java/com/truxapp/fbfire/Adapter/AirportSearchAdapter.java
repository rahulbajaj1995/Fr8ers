package com.truxapp.fbfire.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.truxapp.fbfire.Model.AirportModel;
import com.truxapp.fbfire.R;

import java.util.ArrayList;
import java.util.List;


public class AirportSearchAdapter extends ArrayAdapter<AirportModel>{
    Context context;
    int resource, textViewResourceId;
    ArrayList<AirportModel> item, tempItems, suggestions;

    public AirportSearchAdapter(Context context, int resource, int textViewResourceId, ArrayList<AirportModel> airportList) {
        super(context, resource);
        this.context = context;
        this.resource = resource;
        this.textViewResourceId = textViewResourceId;
        this.item = airportList;
        tempItems = new ArrayList<AirportModel>(item);
        suggestions = new ArrayList<AirportModel>();
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.search_autocomplete_layout, parent, false);
        }
        AirportModel airportModel = item.get(position);
        if (airportModel != null) {
            TextView lblName = (TextView) view.findViewById(R.id.lbl_name);
            if (lblName != null)
                lblName.setText(airportModel.getCity());
        }
        return view;
    }

    @Override
    public Filter getFilter() {
        return nameFilter;
    }

    /**
     * Custom Filter implementation for custom suggestions we provide.
     */
    Filter nameFilter = new Filter() {
        @Override
        public CharSequence convertResultToString(Object resultValue) {
            String str = ((AirportModel) resultValue).getCity();
            return str;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null) {
                suggestions.clear();
                for (AirportModel airportModel : tempItems) {
                    if (airportModel.getCity().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        suggestions.add(airportModel);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            List<AirportModel> filterList = (ArrayList<AirportModel>) results.values;
            if (results != null && results.count > 0) {
                clear();
                for (AirportModel airportModel : filterList) {
                    add(airportModel);
                    notifyDataSetChanged();
                }
            }
        }
    };
}
