package com.softtech.android.displaydata.custom_views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.softtech.android.displaydata.R;

public class RatingView extends LinearLayout {
    public RatingView(Context context) {
        super(context);
    }

    public RatingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.layout_rating, this, true);
    }

    public void setRate(int rate){

        ImageView star1 =  findViewById(R.id.star1);
        ImageView star2 =  findViewById(R.id.star2);
        ImageView star3 =  findViewById(R.id.star3);
        ImageView star4 =  findViewById(R.id.star4);
        ImageView star5 =  findViewById(R.id.star5);

        star1.setColorFilter(getResources().getColor(R.color.icon_gray));
        star2.setColorFilter(getResources().getColor(R.color.icon_gray));
        star3.setColorFilter(getResources().getColor(R.color.icon_gray));
        star4.setColorFilter(getResources().getColor(R.color.icon_gray));
        star5.setColorFilter(getResources().getColor(R.color.icon_gray));

        if(rate <= 0){
            return;
        }

        if(rate > 0){
            star1.setColorFilter(getResources().getColor(R.color.icon_colored));
        }

        if(rate > 1){
            star2.setColorFilter(getResources().getColor(R.color.icon_colored));
        }

        if(rate > 2){
            star3.setColorFilter(getResources().getColor(R.color.icon_colored));
        }

        if(rate > 3){
            star4.setColorFilter(getResources().getColor(R.color.icon_colored));
        }

        if(rate > 4){
            star5.setColorFilter(getResources().getColor(R.color.icon_colored));
        }

    }
}
