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
import java.util.StringTokenizer;

public class PeopleAdapter extends ArrayAdapter<AirportModel> {

    Context context;
    int resource, textViewResourceId;
    List<AirportModel> items, tempItems, suggestions;

    public PeopleAdapter(Context context, int resource, int textViewResourceId, List<AirportModel> items) {
        super(context, resource, textViewResourceId, items);
        this.context = context;
        this.resource = resource;
        this.textViewResourceId = textViewResourceId;
        this.items = items;
        tempItems = new ArrayList<AirportModel>(items);
        suggestions = new ArrayList<AirportModel>();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.row_people, parent, false);
        }
        AirportModel people = items.get(position);
        if (people != null) {
            TextView lblName = (TextView) view.findViewById(R.id.lbl_name);
            if (lblName != null)
                lblName.setText(people.getCity());
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
            String strj = ((AirportModel) resultValue).getCity();
            return strj;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null) {
                suggestions.clear();
                for (AirportModel people : tempItems) {

                    if(constraint.toString().length()==3){
                        StringTokenizer st = new StringTokenizer(people.getCity(),"-");
                        String values = st.nextToken();
                        if (values.toLowerCase().contains(constraint.toString().toLowerCase())) {
                            suggestions.add(people);
                        }
                    }else {
                        if (people.getCity().toLowerCase().contains(constraint.toString().toLowerCase())) {
                            suggestions.add(people);
                        }
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
                for (int i=0; i<filterList.size(); i++) {
                    AirportModel model = filterList.get(i);
                    add(model);
                    notifyDataSetChanged();
                }
            }
        }
    };
}