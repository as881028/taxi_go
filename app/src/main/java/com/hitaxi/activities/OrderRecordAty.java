package com.hitaxi.activities;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.dk.main.R;
import com.hitaxi.base.MenuBaseActivity;

public class OrderRecordAty extends MenuBaseActivity {
    ListView order_record_ListView;
    String[] month_days= new String[] {"5月12","5月13日","5月14日","5月15日","5月16日",
            "5月17日","5月18日","5月19日","5月20日","5月21日"};
    String[] moneys= {"10000","20000","30000","40000","50000",
            "60000","70000","80000","90000","100000"};
    String[] getin_textViews= {"台北火車站","台南火車站","台中火車站","台東火車站","永康火車站",
            "新市火車站","善化火車站","大橋火車站","新營火車站","柳營火車站"};
    String[] getout_textViews= {"桃園","苗栗","新竹","嘉義","龍井",
            "龍津","龍泉","靜宜","東海","逢甲"};
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setMenuLayout(R.layout.activity_order_record);
            order_record_ListView = (ListView)findViewById(R.id.order_record_ListView);
            //建立自訂的Adapter
            com.hitaxi.activities.OrderRecordAty.MyAdapter adapter=new com.hitaxi.activities.OrderRecordAty.MyAdapter(this);
            //設定ListView 的資源來源
            order_record_ListView.setAdapter(adapter);
        }
        public class MyAdapter extends BaseAdapter {
            private LayoutInflater myInflater;
            public MyAdapter(Context c){
                myInflater = LayoutInflater.from(c);
            }
            @Override
            public int getCount(){
                return month_days.length;
            }
            @Override
            public Object getItem(int position){
                return month_days[position];
            }
            public long getItemId(int position){
                return position;
            }
            @Override
            public View getView(int postition, View convertView, ViewGroup parent) {
                convertView = myInflater.inflate(R.layout.orderrecord_listview_layout, null);
                //取得XML內容
                TextView endtime_textView = ((TextView)
                        convertView.findViewById(R.id.endtime_textView));
                TextView month_day = ((TextView)
                        convertView.findViewById(R.id.month_day));
                TextView fare_textView = ((TextView)
                        convertView.findViewById(R.id.fare_textView));
                TextView money = ((TextView)
                        convertView.findViewById(R.id.money));
                TextView getin_textView = ((TextView)
                        convertView.findViewById(R.id.getin_textView));
                TextView getout_textView = ((TextView)
                        convertView.findViewById(R.id.getout_textView));

                month_day.setText(month_days[postition]);
                money.setText(moneys[postition]);
                getin_textView.setText(getin_textViews[postition]);
                getout_textView.setText(getout_textViews[postition]);
                return convertView;
            }
        }
    }
