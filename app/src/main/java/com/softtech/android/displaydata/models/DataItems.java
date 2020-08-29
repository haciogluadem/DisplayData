package com.softtech.android.displaydata.models;

import com.softtech.android.displaydata.Enums;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DataItems implements Serializable {
    private ArrayList<DataItem> items;

    public DataItems(){
       this(new ArrayList<DataItem>());
    }

    public DataItems(ArrayList<DataItem> items){
        this.items = items;
    }

    public ArrayList<DataItem> getItems() {
        return items;
    }

    public void setItems(ArrayList<DataItem> items) {
        this.items = items;
    }

    /**
     * herbir Ulke ozelinde browser'in istatiki verilerini toplar
     * @return
     */
    public CountrySummaryInfos getCountrySummaryInfos(){
        CountrySummaryInfos csi = new CountrySummaryInfos();
        Map<String, BrowserSummaryInfos> browserMap = new HashMap<>();
        for (DataItem item : this.items){
            if(!browserMap.containsKey(item.getComputedLocation())){
                BrowserSummaryInfos bsi = getBrowsersInfo(Enums.FilterType.BY_COUNTRY, item.getComputedLocation());
                browserMap.put(item.getComputedLocation(), bsi);
            }
        }
        csi.setChartInfo(browserMap);
        return csi;
    }

    /**
     * Herbir browser ozelinde performans ve rating degerlerini hesaplar
     * @param filterType
     * @param countryName
     * @return
     */
    public BrowserSummaryInfos getBrowsersInfo(Enums.FilterType filterType, String countryName){
        BrowserSummaryInfos bsi = new BrowserSummaryInfos();
        Map<String,BrowserChartInfo> browserMap = new HashMap<>();
        for (DataItem item : this.items){

            if(filterType == Enums.FilterType.BY_COUNTRY && countryName.compareTo(item.getComputedLocation()) != 0){
                continue;
            }
            if(!browserMap.containsKey(item.getComputedBrowser().getBrowser())){
                BrowserChartInfo chartInfo = new BrowserChartInfo();
                chartInfo.setBrowserName(item.getComputedBrowser().getBrowser());
                chartInfo.setBrowserCount(0);
                chartInfo.setSumOfPerformance(0);
                chartInfo.setSumOfRating(0);

                browserMap.put(item.getComputedBrowser().getBrowser(),chartInfo);
            }

            BrowserChartInfo bci = browserMap.get(item.getComputedBrowser().getBrowser());
            bci.setBrowserCount(bci.getBrowserCount()+1);
            bci.setSumOfPerformance(bci.getSumOfPerformance() + item.getPerformance());
            bci.setSumOfRating(bci.getSumOfRating() + item.getRating());
        }
        bsi.setChartInfo(browserMap);
        return bsi;
    }
}
