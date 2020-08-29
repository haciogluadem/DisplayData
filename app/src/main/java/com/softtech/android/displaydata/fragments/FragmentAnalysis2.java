package com.softtech.android.displaydata.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.softtech.android.displaydata.R;
import com.softtech.android.displaydata.models.BrowserSummaryInfos;
import com.softtech.android.displaydata.utils.Keys;

public class FragmentAnalysis2 extends BaseFragment {

    protected BarChart barChart;

    public FragmentAnalysis2() {

    }

    public static FragmentAnalysis2 newInstance(BrowserSummaryInfos browserSummaryInfos) {
        FragmentAnalysis2 fragment = new FragmentAnalysis2();
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
        return inflater.inflate(R.layout.fragment_charts2, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        barChart = view.findViewById(R.id.chart1);
        initializeChart(barChart, this.browserSummaryInfos.getChartInfo().size());
        setDataForPerformance(barChart);
    }

    protected void setLabelCount(int count){
        if(barChart == null) return;

        XAxis xAxis = barChart.getXAxis();
        xAxis.setLabelCount(count);

        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setLabelCount(count + 1);
    }
}
