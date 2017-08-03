package com.hitaxi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dk.main.R;

import java.util.ArrayList;

/**
 * Created by DK on 2017/8/3.
 */


public class IncomeRecordAdapter extends BaseAdapter {
    private LayoutInflater myInflater;
    String[] dates;
    String[] digitals;
    String[] monthes;
    String[] moneies;

    public IncomeRecordAdapter(Context c, String[] dates, String[] digitals, String[] monthes, String[] moneies) {
        myInflater = LayoutInflater.from(c);
        this.dates = dates;
        this.digitals = digitals;
        this.monthes = monthes;
        this.moneies = moneies;
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
        convertView = myInflater.inflate(R.layout.incomerecord_listview_layout, null);
        //取得XML內容
        TextView date = ((TextView)
                convertView.findViewById(R.id.date));
        TextView score = ((TextView)
                convertView.findViewById(R.id.score));
        TextView digital = ((TextView)
                convertView.findViewById(R.id.digital));
        TextView month = ((TextView)
                convertView.findViewById(R.id.month));
        TextView month_textView = ((TextView)
                convertView.findViewById(R.id.month_textView));
        TextView money = ((TextView)
                convertView.findViewById(R.id.money));

        date.setText(dates[postition]);
        digital.setText(digitals[postition]);
        month.setText(monthes[postition]);
        money.setText(moneies[postition]);
        return convertView;
    }
}

