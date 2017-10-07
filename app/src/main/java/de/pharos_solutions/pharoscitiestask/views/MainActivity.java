package de.pharos_solutions.pharoscitiestask.views;

import android.app.Activity;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.pharos_solutions.pharoscitiestask.R;
import de.pharos_solutions.pharoscitiestask.adapters.CitiesAdapter;
import de.pharos_solutions.pharoscitiestask.controllers.APIResponseCallback;
import de.pharos_solutions.pharoscitiestask.controllers.NetworkManager;
import de.pharos_solutions.pharoscitiestask.models.CityModel;
import de.pharos_solutions.pharoscitiestask.utils.ConnectionUtils;
import de.pharos_solutions.pharoscitiestask.utils.MessagesUtils;
import retrofit2.Response;

public class MainActivity extends Activity implements APIResponseCallback, SearchView.OnQueryTextListener {

    @BindView(R.id.main_activity_cities_list) ListView citiesList;
    @BindView(R.id.main_activity_search_view) SearchView searchView;
    @BindView(R.id.main_activity_progress_bar) ProgressBar progressBar;
    @BindView(R.id.main_activity_toolbar) Toolbar toolbar;
    @BindView(R.id.main_activity_ll_no_connection) LinearLayout noConnectionLayout;
    @BindView(R.id.main_activity_no_connection_iv) ImageView noConnectionImage;
    @BindView(R.id.main_activity_no_connection_tv) TextView noConnectionText;

    private int page = 1;
    private CitiesAdapter citiesAdapter;
    private ArrayList<CityModel> citiesArrayList = new ArrayList<>();
    private ArrayList<CityModel> citiesArrayListFiltered = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initialization();
    }

    // Initialize all views and views actions
    private void initialization() {
        initializeToolbar();

        citiesAdapter = new CitiesAdapter(this, citiesArrayList);
        citiesList.setAdapter(citiesAdapter);

        citiesList.setOnScrollListener(new AbsListView.OnScrollListener() {

            public int totalItem;
            public int currentVisibleItemCount;
            public int currentFirstVisibleItem;
            public int currentScrollState;

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                this.currentScrollState = scrollState;
                this.isScrollCompleted();
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                this.currentFirstVisibleItem = firstVisibleItem;
                this.currentVisibleItemCount = visibleItemCount;
                this.totalItem = totalItemCount;
            }

            private void isScrollCompleted() {
                if (totalItem - currentFirstVisibleItem == currentVisibleItemCount && this.currentScrollState == SCROLL_STATE_IDLE) {

                    getCities(page);
                }
            }
        });
        getCities(page);

        searchView.setOnQueryTextListener(this);
    }

    // Fire the network call method to get all cities
    private void getCities(int page) {

        if (ConnectionUtils.isInternetConnected(this)) {
            progressBar.setVisibility(View.VISIBLE);
            noConnectionLayout.setVisibility(View.GONE);
            citiesList.setVisibility(View.VISIBLE);
            NetworkManager.getInstance(this).getCities(page, this);
        } else {
            noConnectionLayout.setVisibility(View.VISIBLE);
            citiesList.setVisibility(View.GONE);
        }
    }

    private void initializeToolbar() {
        toolbar.setTitle(getString(R.string.main_activity_title));
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onSuccess(Response response) {
        page++;
        progressBar.setVisibility(View.GONE);
        CityModel[] cityModels = (CityModel[]) response.body();

        // Adding cities to Arraylist to use in adapter
        if (cityModels != null && cityModels.length > 0) {
            for (int i = 0; i < cityModels.length; i++) {
                if (!citiesArrayList.contains(cityModels[i])) {
                    citiesArrayList.add(cityModels[i]);
                }
            }

            Collections.sort(citiesArrayList, new CityModel());

            if (citiesList.getAdapter() == null)
                citiesList.setAdapter(citiesAdapter);
            else {
                citiesAdapter.notifyDataSetChanged();
            }
        } else if (citiesArrayList.size() == 0) {
            citiesList.setVisibility(View.GONE);
            noConnectionLayout.setVisibility(View.VISIBLE);
            noConnectionImage.setVisibility(View.GONE);
            noConnectionText.setVisibility(View.VISIBLE);
            noConnectionText.setText(getString(R.string.no_results));
        }
    }

    @Override
    public void onFailure(Response response) {
        progressBar.setVisibility(View.GONE);
        MessagesUtils.showToast(this, getString(R.string.loading_error));
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (TextUtils.isEmpty(newText)) {
            citiesAdapter = new CitiesAdapter(MainActivity.this, citiesArrayList);
            citiesList.setAdapter(citiesAdapter);
            citiesArrayListFiltered.clear();
            citiesAdapter.notifyDataSetChanged();
        } else {

            if (newText.trim().length() > 0) {
                citiesArrayListFiltered.clear();
                for (int i = 0; i < citiesArrayList.size(); i++) {

                    if (citiesArrayList.get(i).getName().toLowerCase().toString().contains(newText.toLowerCase())){
                        citiesArrayListFiltered.add(citiesArrayList.get(i));
                    }
                }

                if (citiesArrayListFiltered.size()>0) {
                    noConnectionLayout.setVisibility(View.GONE);

                    citiesAdapter = new CitiesAdapter(MainActivity.this, citiesArrayListFiltered);
                    citiesList.setAdapter(citiesAdapter);
                    citiesAdapter.notifyDataSetChanged();
                }else{
                    noConnectionLayout.setVisibility(View.VISIBLE);
                    noConnectionImage.setVisibility(View.GONE);
                    noConnectionText.setVisibility(View.VISIBLE);
                    noConnectionText.setText(R.string.no_results);
                }
            }
        }
        return true;
    }
}
