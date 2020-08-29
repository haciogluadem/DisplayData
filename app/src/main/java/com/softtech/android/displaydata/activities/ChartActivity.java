package com.softtech.android.displaydata.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.softtech.android.displaydata.Enums;
import com.softtech.android.displaydata.R;
import android.support.design.widget.TabLayout;

import com.softtech.android.displaydata.fragments.FragmentAnalysis1;
import com.softtech.android.displaydata.fragments.FragmentAnalysis2;
import com.softtech.android.displaydata.fragments.FragmentAnalysis3;
import com.softtech.android.displaydata.models.BrowserChartInfo;
import com.softtech.android.displaydata.models.BrowserSummaryInfos;
import com.softtech.android.displaydata.models.CountrySummaryInfos;
import com.softtech.android.displaydata.models.DataItem;
import com.softtech.android.displaydata.utils.Keys;
import com.softtech.android.displaydata.utils.StringResources;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChartActivity extends BaseActivity{

    private ArrayList<DataItem> mItems;
    private BrowserSummaryInfos browserSummaryInfos;
    private CountrySummaryInfos countrySummaryInfos;

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charts);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setToolBarInfo(StringResources.loadString(R.string.charts), "", true);

        browserSummaryInfos = getBrowsersInfo(Enums.FilterType.NONE, "");
        countrySummaryInfos = getCountrySummaryInfos();

        viewPager = findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected void getIntentData() {
        super.getIntentData();
        mItems = (ArrayList<DataItem>) getIntent().getSerializableExtra(Keys.KEY_DATA_ITEMS);
    }

    /**
     * Herbir browser ozelinde performans ve rating degerlerini hesaplar
     * @param filterType
     * @param countryName
     * @return
     */
    private BrowserSummaryInfos getBrowsersInfo(Enums.FilterType filterType, String countryName){
        BrowserSummaryInfos bsi = new BrowserSummaryInfos();
        Map<String,BrowserChartInfo> browserMap = new HashMap<>();
        for (DataItem item : mItems){

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

    /**
     * herbir Ulke ozelinde browser'in istatiki verilerini toplar
     * @return
     */
    private CountrySummaryInfos getCountrySummaryInfos(){
        CountrySummaryInfos csi = new CountrySummaryInfos();
        Map<String, BrowserSummaryInfos> browserMap = new HashMap<>();
        for (DataItem item : mItems){
            if(!browserMap.containsKey(item.getComputedLocation())){
                BrowserSummaryInfos bsi = getBrowsersInfo(Enums.FilterType.BY_COUNTRY, item.getComputedLocation());
                browserMap.put(item.getComputedLocation(), bsi);
            }
        }
        csi.setChartInfo(browserMap);
        return csi;
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(FragmentAnalysis1.newInstance(browserSummaryInfos),
                StringResources.loadString(R.string.browser_usage));
        adapter.addFragment(FragmentAnalysis2.newInstance(browserSummaryInfos),
                StringResources.loadString(R.string.browser_performance));
        adapter.addFragment(FragmentAnalysis3.newInstance(countrySummaryInfos),
                StringResources.loadString(R.string.as_country));
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
