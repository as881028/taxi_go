package com.hitaxi.activities;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.dk.main.R;
import com.hitaxi.adapter.IncomeRecordAdapter;
import com.hitaxi.object.LoginDetail;
import com.hitaxi.object.PersonalDetail;
import com.hitaxi.tools.phpConnection;
import com.hitaxi.base.BaseActivity;
import com.hitaxi.base.GlobalVar;

import java.util.HashMap;
import java.util.Map;

public class IncomeRecordAty extends BaseActivity {


    protected static final String TAG = "IncomeRecordActivity";

    com.hitaxi.object.PersonalDetail PersonalDetail;
    ListView income_record_Listview;
    String[] dates = new String[]{"2017年5月12", "2017年5月13日", "2017年5月14日", "2017年5月15日", "2017年5月16日",
            "2017年5月17日", "2017年5月18日", "2017年5月19日", "2017年5月20日", "2017年5月21日"};
    String[] digitals = {"85", "87", "65", "66", "87", "88", "89", "90", "81", "82"};
    String[] monthes = {"一", "二", "三", "四", "五", "六", "七", "八", "九", "十"};
    String[] moneies = {"1000NT", "1001NT", "1002NT", "1003NT", "1004NT",
            "1005NT", "1006NT", "靜宜", "東海", "逢甲"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setMenuLayout(R.layout.activity_income_record);
        income_record_Listview = (ListView) findViewById(R.id.income_record_Listview);
        //建立自訂的Adapter
        IncomeRecordAdapter adapter = new IncomeRecordAdapter(this, dates, digitals, monthes, moneies);
        //設定ListView 的資源來源
        income_record_Listview.setAdapter(adapter);

        getGlobal();
        PersonalDetail = var.PersonalDetail;

//        PersonalDetail = var.PersonalDetail;
        //與api串接要資料
        Map<String, String> map = new HashMap<String, String>();

        map.put("Token", var.PersonalDetail.getUserToken());
        map.put("UserID", var.PersonalDetail.getUserID());
        HttpPostTradTask mTask = new HttpPostTradTask(var.queryTrad, map);
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
