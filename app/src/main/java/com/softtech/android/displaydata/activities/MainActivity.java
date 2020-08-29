package com.softtech.android.displaydata.activities;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.softtech.android.displaydata.Enums;
import com.softtech.android.displaydata.R;
import com.softtech.android.displaydata.adapters.DataItemListAdapter;
import com.softtech.android.displaydata.interfaces.IGenericItemClicked;
import com.softtech.android.displaydata.models.DataItem;
import com.softtech.android.displaydata.models.DataItems;
import com.softtech.android.displaydata.utils.ActivityController;
import com.softtech.android.displaydata.utils.AlertDialogUtils;
import com.softtech.android.displaydata.utils.FileOperations;
import com.softtech.android.displaydata.utils.Keys;
import com.softtech.android.displaydata.utils.StringResources;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends BaseActivity implements View.OnClickListener, SearchView.OnQueryTextListener {

    private ArrayList<DataItem> mItems;
    private ArrayList<DataItem> mFilteredItems;
    private RecyclerView recyclerView;
    private Enums.SortType sortBy = Enums.SortType.BY_BROWSERNAME;
    private DataItemListAdapter mAdapter;
    private TextView filterResultTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setToolBarInfo(StringResources.loadString(R.string.list), "", false);

        recyclerView = findViewById(R.id.list_rw);
        filterResultTv = findViewById(R.id.filter_info_tv);
        filterResultTv.setVisibility(View.GONE);
        Button showAnalysisBtn = findViewById(R.id.analysis_btn);
        showAnalysisBtn.setOnClickListener(this);

        SearchView searchView = findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(this);

        fetchAndPrepareData();
        initializeList();
    }

    private void fetchAndPrepareData(){
        String jsonString = FileOperations.loadFromAsset(getBaseContext(), R.raw.demo);
        if(TextUtils.isEmpty(jsonString)){
            AlertDialogUtils.showAlertDialogOneButton(this, StringResources.loadString(R.string.error),
                    StringResources.loadString(R.string.error_message1), null);

            mFilteredItems = mItems = new ArrayList<>();
            return;
        }

        try {
            Gson g = new Gson();
            DataItems dataItems = g.fromJson(jsonString, DataItems.class);
            mFilteredItems = mItems = dataItems.getItems();
            sortBy = Enums.SortType.BY_BROWSERNAME;
            sort();
        }catch (Exception e){
            AlertDialogUtils.showAlertDialogOneButton(this, StringResources.loadString(R.string.error),
                    StringResources.loadString(R.string.error_message2), null);

            mFilteredItems = mItems = new ArrayList<>();
        }
    }

    private void initializeList(){
        mAdapter = new DataItemListAdapter(mFilteredItems);
        recyclerView.setAdapter(mAdapter);
        GridLayoutManager linearLayoutManager = new GridLayoutManager(this,1);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    private void updateList(){
        if(mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_sort:
                clickedActionSort();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    private void clickedActionSort(){
        String [] sortTypeItems = new String[] {
                Enums.SortType.BY_BROWSERNAME.toString(),
                Enums.SortType.BY_PLATFORM.toString(),
                Enums.SortType.BY_COUNTRY.toString(),
                Enums.SortType.BY_RATING_ASCENDING.toString(),
                Enums.SortType.BY_RATING_DESCENDING.toString(),
                Enums.SortType.BY_PERFORMANCE_ASCENDING.toString(),
                Enums.SortType.BY_PERFORMANCE_DESCENDING.toString(),
        };

        AlertDialogUtils.showAlertListWithoutButtons(this, StringResources.loadString(R.string.sort_by),
                sortBy.getVal()-1, sortTypeItems, new IGenericItemClicked() {
            @Override
            public void onItemClick(Object item, int position, int id) {
                if(position != (sortBy.getVal()-1)){
                    sortBy = Enums.SortType.fromInteger(position+1);
                    sort();
                }
            }
        });
    }

    private void sort(){
        Comparator comp = new Comparator<DataItem>(){
            @Override
            public int compare(DataItem d1, DataItem d2)
            {
                switch (sortBy){
                    case BY_BROWSERNAME:
                        return d1.getComputedBrowser().getBrowser().compareTo(d2.getComputedBrowser().getBrowser());
                    case BY_PLATFORM:
                        return d1.getComputedBrowser().getPlatform().compareTo(d2.getComputedBrowser().getPlatform());
                    case BY_COUNTRY:
                        return d1.getComputedLocation().compareTo(d2.getComputedLocation());
                    case BY_RATING_ASCENDING:
                        return d1.getRating() == d2.getRating() ? 0 : d1.getRating() < d2.getRating() ? -1 : 1;
                    case BY_RATING_DESCENDING:
                        return d1.getRating() == d2.getRating() ? 0 : d1.getRating() < d2.getRating() ? 1 : -1;
                    case BY_PERFORMANCE_ASCENDING:
                        return d1.getPerformance() == d2.getPerformance() ? 0 : d1.getPerformance() < d2.getPerformance() ? -1 : 1;
                    case BY_PERFORMANCE_DESCENDING:
                        return d1.getPerformance() == d2.getPerformance() ? 0 : d1.getPerformance() < d2.getPerformance() ? 1 : -1;
                    default:
                        return d1.getComputedBrowser().getPlatform().compareTo(d2.getComputedBrowser().getPlatform());
                }
            }
        };

        Collections.sort(mFilteredItems, comp);
        updateList();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.analysis_btn:
                ActivityController.startActivity(this, ChartActivity.class,
                        Keys.KEY_DATA_ITEMS, new DataItems(mItems), false);
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onQueryTextSubmit(final String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        if(TextUtils.isEmpty(s)){
            mFilteredItems = mItems;
            filterResultTv.setVisibility(View.GONE);
        }
        else {
            mFilteredItems = new ArrayList<>();
            for (DataItem dataItem : mItems) {
                if (dataItem.getComputedBrowser().getPlatform().toLowerCase().contains(s.toLowerCase())
                        || dataItem.getComputedBrowser().getBrowser().toLowerCase().contains(s.toLowerCase())) {
                    mFilteredItems.add(dataItem);
                } else {
                    for (String label : dataItem.getLabels()) {
                        if (label.toLowerCase().contains(s.toLowerCase())) {
                            mFilteredItems.add(dataItem);
                        }
                    }
                }
            }
            if(filterResultTv.getVisibility() !=  View.VISIBLE)
                filterResultTv.setVisibility(View.VISIBLE);

            String text = getResources().getString(R.string.filter_info, mFilteredItems.size());
            filterResultTv.setText(text);
        }
        mAdapter.setFilter(mFilteredItems);

        return false;
    }
}
