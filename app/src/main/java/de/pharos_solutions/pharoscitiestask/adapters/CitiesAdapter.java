package de.pharos_solutions.pharoscitiestask.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import de.pharos_solutions.pharoscitiestask.models.CityModel;
import de.pharos_solutions.pharoscitiestask.views.CityRowItem;

/**
 * Created by muhammadkorany on 10/5/17.
 */

public class CitiesAdapter extends BaseAdapter implements Filterable {

    private ArrayList<CityModel> cityModelArrayList;
    private Context context;
    private CityRowItem cityRowItem;

    // Adapter constructor
    public CitiesAdapter(Context context, ArrayList<CityModel> cityModelArrayList){
        this.context = context;
        this.cityModelArrayList = cityModelArrayList;
    }

    // Return array list elements count
    @Override
    public int getCount() {
        return cityModelArrayList.size();
    }

    // Return single city model
    @Override
    public CityModel getItem(int position) {
        return cityModelArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    // Here is where the views loaded and become visible
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            cityRowItem = new CityRowItem(context, cityModelArrayList.get(position));
        }else{
            cityRowItem = (CityRowItem) convertView;
        }

        cityRowItem.setData(cityModelArrayList.get(position));

        return cityRowItem;
    }

    // Function to implement search functionality using the search view
    @Override
    public Filter getFilter() {
        return new ItemsFilter(this, cityModelArrayList);
    }

    // Filtering algorithm
    private static class ItemsFilter extends android.widget.Filter {

        private CitiesAdapter citiesAdapter;
        private List<CityModel> originalList;
        private ArrayList<CityModel> filteredList;

        public ItemsFilter(CitiesAdapter citiesAdapter, List<CityModel> originalList) {
            super();
            this.citiesAdapter = citiesAdapter;
            this.originalList = new LinkedList<>(originalList);
            this.filteredList = new ArrayList<>();
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            filteredList.clear();
            final FilterResults results = new FilterResults();

            if (constraint.length() == 0) {
                filteredList.addAll(originalList);
            } else {
                final String filterPattern = constraint.toString().trim();

                for (final CityModel item : originalList) {
                    if (item.getName().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            results.values = filteredList;
            results.count = filteredList.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            citiesAdapter.cityModelArrayList.clear();
            citiesAdapter.cityModelArrayList.addAll((ArrayList<CityModel>) results.values);
            this.performFiltering(constraint);
            citiesAdapter.notifyDataSetChanged();
        }
    }
}
