package com.softtech.android.displaydata.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.PieChart;
import com.softtech.android.displaydata.R;
import com.softtech.android.displaydata.models.BrowserSummaryInfos;
import com.softtech.android.displaydata.utils.Keys;
import com.softtech.android.displaydata.utils.StringResources;

public class FragmentAnalysis1 extends BaseFragment {

    private PieChart chart;

    public FragmentAnalysis1() {
    }

    public static FragmentAnalysis1 newInstance(BrowserSummaryInfos browserSummaryInfos) {
        FragmentAnalysis1 fragment = new FragmentAnalysis1();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Keys.KEY_BROWSER_INFO, browserSummaryInfos);
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
        this.browserSummaryInfos = (BrowserSummaryInfos) getArguments().getSerializable(Keys.KEY_BROWSER_INFO);
        return inflater.inflate(R.layout.fragment_charts1, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        chart = view.findViewById(R.id.chart1);
        initializeChart(chart, StringResources.loadString(R.string.browser_usage));
        setDataForUsage(chart);
    }
}
