package com.hitaxi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.dk.main.R;

/**
 * Created by DK on 2017/8/3.
 */


public class OpinionAdapter extends BaseAdapter {
    private LayoutInflater myInflater;

    String[] dates;
    String[] stars;
    String[] contents;

    public OpinionAdapter(Context c, String[] dates, String[] stars, String[] contents) {
        myInflater = LayoutInflater.from(c);
        this.dates = dates;
        this.stars = stars;
        this.contents = contents;

    }

    @Override
    public int getCount() {
        return dates.length;
    }

    @Override
    public Object getItem(int position) {
        return dates[position];
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int postition, View convertView, ViewGroup parent) {
        convertView = myInflater.inflate(R.layout.reservation_listview_layout, null);
        //取得XML內容
        TextView tvDate = ((TextView)
                convertView.findViewById(R.id.date));
        TextView tvScore = ((TextView)
                convertView.findViewById(R.id.score));
        RatingBar rbRatingBar = ((RatingBar)
                convertView.findViewById(R.id.ratingBar));
        TextView tvOpinionContent = ((TextView)
                convertView.findViewById(R.id.opinion_content));

        tvDate.setText(dates[postition]);
        //ratingBar.setRating(stars[postition]);
        tvOpinionContent.setText(contents[postition]);
        return convertView;
    }
}