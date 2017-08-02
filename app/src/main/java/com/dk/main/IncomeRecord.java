package com.dk.main;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

public class IncomeRecord extends BaseActivity {


    public class MyAdapter extends BaseAdapter {
        private LayoutInflater myInflater;
        public MyAdapter(Context c){
            myInflater = LayoutInflater.from(c);
        }
        @Override
        public int getCount(){
            return dates.length;
        }
        @Override
        public Object getItem(int position){
            return dates[position];
        }
        public long getItemId(int position){
            return position;
        }
        @Override
        public View getView (int postition,View convertView, ViewGroup parent){
            convertView = myInflater.inflate(R.layout.incomerecord_listview_layout,null);
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
    protected static final String TAG = "IncomeRecordActivity";
    private GlobalVar var;
    PersonalDetail PersonalDetail;
    ListView income_record_Listview;
    String[] dates= new String[] {"2017年5月12","2017年5月13日","2017年5月14日","2017年5月15日","2017年5月16日",
            "2017年5月17日","2017年5月18日","2017年5月19日","2017年5月20日","2017年5月21日"};
    String[] digitals= {"85","87","65","66","87", "88","89","90","81","82"};
    String[] monthes= {"一","二","三","四","五", "六","七","八","九","十"};
    String[] moneies= {"1000NT","1001NT","1002NT","1003NT","1004NT",
            "1005NT","1006NT","靜宜","東海","逢甲"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setMenuLayout(R.layout.activity_income_record);
        income_record_Listview = (ListView)findViewById(R.id.income_record_Listview);
        //建立自訂的Adapter
        MyAdapter adapter=new MyAdapter(this);
        //設定ListView 的資源來源
        income_record_Listview.setAdapter(adapter);

        var = (GlobalVar) getApplicationContext();
        PersonalDetail = var.PersonalDetail;
        //與api串接要資料
        Map<String, String> map = new HashMap<String, String>();
        String url = "";

        map.put("Token",  PersonalDetail.getCallNum());
        map.put("UserID",  PersonalDetail.getCallNum());
        HttpPostTradTask mTask = new HttpPostTradTask(url , map);
        mTask.execute((Void) null);
        //

    }

    //讀取個人資料
    public class HttpPostTradTask extends AsyncTask<Void, Void, String> {

        private final Map<String, String> mMap;
        private final String mUrl;

        HttpPostTradTask(String url, Map<String, String> map) {
            mMap = map;
            mUrl = url;
        }



        @Override
        protected String doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.
            String result = "";
            try {
                //取得PHP回傳值
                result = phpConnection.createConnection(mUrl, mMap);
//                parsePersonalJson(result);

                Log.i(TAG, result);

            } catch (NullPointerException e) {
                if (var.debug) {
                    Log.i(TAG, "Null Point");
                    return null;
                }
            } catch (Exception e) {
                if (var.debug)
                    Log.i(TAG, e.toString());
            }
            // TODO: register the new account here.
            return result;
        }



    }


}
