package com.softtech.android.displaydata.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatSpinner;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.softtech.android.displaydata.R;
import com.softtech.android.displaydata.models.BrowserSummaryInfos;
import com.softtech.android.displaydata.models.CountrySummaryInfos;
import com.softtech.android.displaydata.utils.Keys;
import com.softtech.android.displaydata.utils.StringResources;

import java.util.Arrays;
import java.util.Map;

public class FragmentAnalysis3 extends BaseFragment {

    private CountrySummaryInfos countrySummaryInfos;
    private String selectedCountry = "";
    protected BarChart barChartForPerformance;
    protected BarChart barChartForRating;
    private PieChart pieChart;

    public FragmentAnalysis3() {
    }

    public static FragmentAnalysis3 newInstance(CountrySummaryInfos countrySummaryInfos) {
        FragmentAnalysis3 fragment = new FragmentAnalysis3();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Keys.KEY_COUNTRY_INFO, countrySummaryInfos);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.countrySummaryInfos = (CountrySummaryInfos) getArguments().getSerializable(Keys.KEY_COUNTRY_INFO);
        return inflater.inflate(R.layout.fragment_charts3, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        pieChart = view.findViewById(R.id.chart1);
        barChartForPerformance = view.findViewById(R.id.chart2);
        barChartForRating = view.findViewById(R.id.chart3);

        AppCompatSpinner spin1 = view.findViewById(R.id.spinner1);

        selectedCountry = TextUtils.isEmpty(selectedCountry) ? "Turkey" : selectedCountry;
        this.browserSummaryInfos = this.countrySummaryInfos.getChartInfo().get(selectedCountry);
        if(this.browserSummaryInfos == null){
            this.browserSummaryInfos = new BrowserSummaryInfos();
        }

        initializeSpinner1(spin1);
        initializeChart(pieChart, StringResources.loadString(R.string.browser_usage));
        initializeChart(barChartForPerformance, this.browserSummaryInfos.getChartInfo().size());
        initializeChart(barChartForRating, this.browserSummaryInfos.getChartInfo().size());

        setDataForUsage(pieChart);
        setDataForPerformance(barChartForPerformance);
        setDataForRating(barChartForRating);
    }

    private void initializeSpinner1(Spinner spinner){
        String[] countries = new String[this.countrySummaryInfos.getChartInfo().size()];
        int index = 0;
        for(Map.Entry<String, BrowserSummaryInfos> entry : this.countrySummaryInfos.getChartInfo().entrySet()) {
            countries[index++] = entry.getKey();
        }

        Arrays.sort(countries);
        int defaultSelectedIndex = -1;
        for(int i = 0; i < countries.length; i++){
            if(selectedCountry.toLowerCase().equals(countries[i].toLowerCase())){
                defaultSelectedIndex = i;
            }
        }

        ArrayAdapter aa = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_dropdown_item, countries);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(aa);
        spinner.setSelection(defaultSelectedIndex);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(selectedCountry == adapterView.getSelectedItem())
                    return;

                selectedCountry = (String)adapterView.getSelectedItem();
                browserSummaryInfos = countrySummaryInfos.getChartInfo().get(selectedCountry);

                //as browser usage
                clearChart(pieChart);
                setDataForUsage(pieChart);

                //as broser performance
                clearChart(barChartForPerformance);
                setDataForPerformance(barChartForPerformance);

                //as broser rating
                clearChart(barChartForRating);
                setDataForRating(barChartForRating);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

}
