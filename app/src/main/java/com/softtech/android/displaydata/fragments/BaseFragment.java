package com.softtech.android.displaydata.fragments;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;
import com.softtech.android.displaydata.models.BrowserChartInfo;
import com.softtech.android.displaydata.models.BrowserSummaryInfos;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

public class BaseFragment extends Fragment {

    protected Typeface tfRegular ;
    protected Typeface tfLight;

    protected BrowserSummaryInfos browserSummaryInfos;

    public BaseFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tfRegular = Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Regular.ttf");
        tfLight = Typeface.createFromAsset(getActivity().getAssets(), "OpenSans-Light.ttf");
    }

    protected void clearChart(PieChart chart){
        if(chart != null)
            chart.clear();
    }

    protected void clearChart(BarChart chart){
        if(chart != null)
            chart.clear();
    }

    protected void initializeChart(PieChart chart, String centerText){
        clearChart(chart);
        chart.setTouchEnabled(false);
        chart.setUsePercentValues(true);
        chart.getDescription().setEnabled(false);
        chart.setExtraOffsets(5, 10, 5, 5);

        chart.setDragDecelerationFrictionCoef(0.95f);

        chart.setCenterTextTypeface(tfLight);
        chart.setCenterText(generateCenterSpannableText(centerText));

        chart.setDrawHoleEnabled(true);
        chart.setHoleColor(Color.WHITE);

        chart.setTransparentCircleColor(Color.WHITE);
        chart.setTransparentCircleAlpha(110);
        chart.setHoleRadius(58f);
        chart.setTransparentCircleRadius(61f);
        chart.setDrawCenterText(true);
        chart.setRotationAngle(0);
        chart.setRotationEnabled(true);
        chart.setHighlightPerTapEnabled(true);

        chart.animateY(1000, Easing.EaseInOutQuad);

        Legend l = chart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);

        chart.setEntryLabelColor(Color.BLACK);
        chart.setEntryLabelTypeface(tfRegular);
        chart.setEntryLabelTextSize(12f);
    }

    protected void initializeChart(BarChart chart, int count){
        clearChart(chart);
        chart.getDescription().setEnabled(false);
        chart.setPinchZoom(false);
        chart.setTouchEnabled(false);
        chart.setDrawBarShadow(false);
        chart.setDrawGridBackground(false);
        chart.setFitBars(true);

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(true);
        xAxis.setLabelCount(count);
        xAxis.setAxisMinimum(0);
        xAxis.setSpaceMax(0.5f);
        xAxis.setDrawLabels(false);

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setLabelCount(count + 1);
        leftAxis.setDrawLabels(false);

        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setGranularity(1.0f);
        rightAxis.setGranularityEnabled(true);
        rightAxis.setAxisMaximum(5);
        rightAxis.setAxisMinimum(0);

        Legend l = chart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(true);
        l.setTypeface(tfLight);
        l.setYOffset(0f);
        l.setXOffset(10f);
        l.setYEntrySpace(0f);
        l.setTextSize(10f);
        chart.animateY(1000);
    }

    protected void setDataForUsage(PieChart chart) {
        ArrayList<PieEntry> entries = new ArrayList<>();
        for(Map.Entry<String, BrowserChartInfo> entry : browserSummaryInfos.getChartInfo().entrySet()) {
            String key = entry.getKey();
            float percentage = browserSummaryInfos.getBrowserPercentage(entry.getKey());
            entries.add(new PieEntry(percentage/100.0f, key));
        }
        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setDrawIcons(false);
        dataSet.setSliceSpace(3f);
        dataSet.setIconsOffset(new MPPointF(0, 40));
        dataSet.setSelectionShift(5f);

        ArrayList<Integer> colors = getColors();

        dataSet.setColors(colors);
        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter(chart));
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.BLACK);
        data.setValueTypeface(tfLight);
        chart.setData(data);
        chart.highlightValues(null);
        chart.invalidate();
    }

    protected void setDataForPerformance(BarChart chart) {
        BarData data = new BarData();
        int i = 1;
        for(Map.Entry<String, BrowserChartInfo> entry : browserSummaryInfos.getChartInfo().entrySet()) {
            ArrayList<BarEntry> values = new ArrayList<>();
            String key = entry.getKey();

            BrowserChartInfo value = entry.getValue();
            values.add(new BarEntry(i++, value.getAveragePerformance()));

            BarDataSet set1;
            if (chart.getData() != null && chart.getData().getDataSetCount() > 0) {
                set1 = (BarDataSet) chart.getData().getDataSetByIndex(0);
            }else {
                set1 = new BarDataSet(values, key);
            }
            Random r = new Random();
            set1.setColor(Color.rgb(r.nextInt(255), r.nextInt(255), r.nextInt(255)));
            data.addDataSet(set1);
        }

        data.setBarWidth(0.5f);
        data.setValueTextSize(11f);
        data.setValueTypeface(tfLight);
        data.setValueTextColor(Color.WHITE);
        chart.setData(data);
        chart.invalidate();
    }

    protected void setDataForRating(BarChart chart) {
        BarData data = new BarData();
        int i = 1;
        for(Map.Entry<String, BrowserChartInfo> entry : browserSummaryInfos.getChartInfo().entrySet()) {
            ArrayList<BarEntry> values = new ArrayList<>();
            String key = entry.getKey();
            BrowserChartInfo value = entry.getValue();

            values.add(new BarEntry(i++, value.getAverageRating()));

            BarDataSet set1;
            if (chart.getData() != null && chart.getData().getDataSetCount() > 0) {
                set1 = (BarDataSet) chart.getData().getDataSetByIndex(0);
            }else {
                set1 = new BarDataSet(values, key);
            }
            Random r = new Random();
            set1.setColor(Color.rgb(r.nextInt(255), r.nextInt(255), r.nextInt(255)));
            data.addDataSet(set1);
        }

        data.setBarWidth(0.5f);
        data.setValueTextSize(11f);
        data.setValueTypeface(tfLight);
        data.setValueTextColor(Color.WHITE);
        chart.setData(data);

        chart.invalidate();
    }

    private SpannableString generateCenterSpannableText(String text) {

        SpannableString s = new SpannableString(text);
        s.setSpan(new RelativeSizeSpan(1.5f), 0, s.length(), 0);
        s.setSpan(new StyleSpan(Typeface.NORMAL), 0, s.length(), 0);
        s.setSpan(new ForegroundColorSpan(Color.GRAY), 0, s.length(), 0);
        s.setSpan(new RelativeSizeSpan(.65f), 0, s.length(), 0);
        s.setSpan(new StyleSpan(Typeface.ITALIC), s.length(), s.length(), 0);
        s.setSpan(new ForegroundColorSpan(ColorTemplate.getHoloBlue()), s.length(), s.length(), 0);
        return s;
    }

    protected ArrayList<Integer>  getColors(){
        ArrayList<Integer> colors = new ArrayList<>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());
        return colors;
    }
}
