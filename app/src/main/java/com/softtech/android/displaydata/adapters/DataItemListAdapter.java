package com.softtech.android.displaydata.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.softtech.android.displaydata.R;
import com.softtech.android.displaydata.custom_views.RatingView;
import com.softtech.android.displaydata.interfaces.IGenericItemClicked;
import com.softtech.android.displaydata.models.DataItem;
import com.softtech.android.displaydata.utils.ColorUtils;

import java.util.ArrayList;

public class DataItemListAdapter extends RecyclerView.Adapter<DataItemListAdapter.CustomViewHolder> {
    private IGenericItemClicked mListener;
    private ArrayList<DataItem> mItems;

    public DataItemListAdapter(ArrayList<DataItem> items) {
        this.mItems = items;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_items, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, final int position) {
        final DataItem item = mItems.get(position);
        holder.set(item);
    }

    public void setOnItemClickListener(IGenericItemClicked clickListener) {
        this.mListener = clickListener;
    }

    public DataItem getItem(int position) {
        if (position < mItems.size() && position > -1)
            return mItems.get(position);
        return null;
    }

    public void setFilter(ArrayList<DataItem> filteredItems){
        this.mItems = filteredItems;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tv1, tv2, tv3, tv4, tv5;
        private LinearLayout root;
        private View indicatorView;
        private RatingView ratingView;

        public CustomViewHolder(View itemView){
            super(itemView);
            root = itemView.findViewById(R.id.root);
            tv1 = itemView.findViewById(R.id.tv1);
            tv2 = itemView.findViewById(R.id.tv2);
            tv3 = itemView.findViewById(R.id.tv3);
            tv4 = itemView.findViewById(R.id.tv4);
            tv5 = itemView.findViewById(R.id.tv5);
            indicatorView = itemView.findViewById(R.id.indicator_view);
            ratingView = itemView.findViewById(R.id.rating_view);
            root.setOnClickListener(this);
        }

        public void set(DataItem item){

            //browser and version
            String str = item.getComputedBrowser().getBrowser();
            str += " V" + item.getComputedBrowser().getVersion();
            tv1.setText(str);

            //platform
            tv2.setText(item.getComputedBrowser().getPlatform());

            //labels
            str = "";
            for (String label: item.getLabels()) {
                str += TextUtils.isEmpty(str) ? "" : ", ";
                str += label;
            }
            tv3.setText((TextUtils.isEmpty(str) ? "-" : str));

            //performance
            tv4.setText("" + item.getPerformance());

            //set rating
            ratingView.setRate(item.getRating());

            //set location info
            str = TextUtils.isEmpty(item.getGeo().getCity()) ? "-" : item.getGeo().getCity();
            str += " / " + (TextUtils.isEmpty(item.getComputedLocation()) ? "-" : item.getComputedLocation());
            tv5.setText(str);

            //indicator view color
            indicatorView.setBackgroundColor(ColorUtils.getInstance().getColor(item.getComputedBrowser().getBrowser()));
        }


        @Override
        public void onClick(View v) {
            if (mListener != null)
                mListener.onItemClick(getItem(getPosition()), getPosition(), -1);
        }
    }
}


