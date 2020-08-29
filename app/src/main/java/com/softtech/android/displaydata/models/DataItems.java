package com.softtech.android.displaydata.models;

import java.util.ArrayList;

public class DataItems {
    private ArrayList<DataItem> items;

    public DataItems(){
        items = new ArrayList<>();
    }

    public ArrayList<DataItem> getItems() {
        return items;
    }

    public void setItems(ArrayList<DataItem> items) {
        this.items = items;
    }
}
