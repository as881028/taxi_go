package com.hitaxi.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.dk.main.R;
import com.hitaxi.adapter.IncomeRecordAdapter;
import com.hitaxi.object.PersonalDetail;
import com.hitaxi.object.TradDetail;
import com.hitaxi.tools.phpConnection;
import com.hitaxi.base.BaseActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


//每月份收入
public class IncomeRecordAty extends BaseActivity {


    protected static final String TAG = "IncomeRecordActivity";

    public com.hitaxi.object.PersonalDetail PersonalDetail;
    ListView income_record_Listview;
    public String[] dates = new String[]{"2017年5月12", "2017年5月13日", "2017年5月14日", "2017年5月15日", "2017年5月16日",
            "2017年5月17日", "2017年5月18日", "2017年5月19日", "2017年5月20日", "2017年5月21日"};

    public String[] digitals = {"85", "87", "65", "66", "87", "88", "89", "90", "81", "82"};
    public String[] monthes = {"一", "二", "三", "四", "五", "六", "七", "八", "九", "十"};
    public String[] moneies = {"1000NT", "1001NT", "1002NT", "1003NT", "1004NT",
            "1005NT", "1006NT", "靜宜", "東海", "逢甲"};
    public TradDetail TradDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setMenuLayout(R.layout.activity_income_record);


        getGlobal();
        PersonalDetail = var.PersonalDetail;
        TradDetail = var.TradDetail;

        income_record_Listview = (ListView) findViewById(R.id.income_record_Listview);
        //建立自訂的Adapter

        IncomeRecordAdapter adapter = new IncomeRecordAdapter(getApplicationContext(), TradDetail.getDateArray(), digitals, monthes, TradDetail.getMoneyArray());
        //設定ListView 的資源來源
        income_record_Listview.setAdapter(adapter);
        //


    }


}
