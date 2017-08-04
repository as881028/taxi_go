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

    com.hitaxi.object.PersonalDetail PersonalDetail;
    ListView income_record_Listview;
   public String[] dates = new String[]{"2017年5月12", "2017年5月13日", "2017年5月14日", "2017年5月15日", "2017年5月16日",
            "2017年5月17日", "2017年5月18日", "2017年5月19日", "2017年5月20日", "2017年5月21日"};

    public String[] digitals = {"85", "87", "65", "66", "87", "88", "89", "90", "81", "82"};
    public String[] monthes = {"一", "二", "三", "四", "五", "六", "七", "八", "九", "十"};
    public String[] moneies = {"1000NT", "1001NT", "1002NT", "1003NT", "1004NT",
            "1005NT", "1006NT", "靜宜", "東海", "逢甲"};
    public TradDetail td;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setMenuLayout(R.layout.activity_income_record);


        getGlobal();
        PersonalDetail = var.PersonalDetail;
        td = new TradDetail();
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
                parseJson(result);

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

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            income_record_Listview = (ListView) findViewById(R.id.income_record_Listview);
            //建立自訂的Adapter
//            Log.i(TAG,td.getDateArray().size()+"");
//            Log.i(TAG,td.getDateArray()+"");
            IncomeRecordAdapter adapter = new IncomeRecordAdapter(getApplicationContext(), td.getDateArray(), digitals, monthes, td.getMoneyArray());
            //設定ListView 的資源來源
            income_record_Listview.setAdapter(adapter);
        }
    }

    private void parseJson(String mJSONText) throws JSONException {
        JSONObject jObject = new JSONObject(mJSONText);
        JSONArray jArray = jObject.getJSONArray("TradArray");

        for (int i = 0; i < jArray.length(); i++) {

            String tid = jArray.getJSONObject(i).getString("Tid");
            td.addTid(tid);

            int money = jArray.getJSONObject(i).getInt("Income");
            td.addMoney(money);

            String date = jArray.getJSONObject(i).getString("StartTime");
            String[] dateArray = date.split(" ");
            dateArray = dateArray[0].split("-");
            td.addDate(dateArray[0] + "年" + dateArray[1] + "月" + dateArray[2] + "日");
        }
        Log.i(TAG, td.getTidArray() + "");
        Log.i(TAG, td.getMoneyArray() + "");
        Log.i(TAG, td.getDateArray() + "");
        //
//        String tid = jArray.getJSONObject(0).getString("Tid");
//        String startTime = jArray.getJSONObject(0).getString("StartTime");
//        String endTime = jArray.getJSONObject(0).getString("EndTime");
//        String StartLatitude = jArray.getJSONObject(0).getString("StartLatitude");
//        String StartLongitude = jArray.getJSONObject(0).getString("StartLongitude");
//        String EndLatitude = jArray.getJSONObject(0).getString("EndLatitude");
//        String EndLongitude = jArray.getJSONObject(0).getString("EndLongitude");
//        String EstimetePrice = jArray.getJSONObject(0).getString("EstimetePrice");
//        String ActualPrice = jArray.getJSONObject(0).getString("ActualPrice");
//        String TravelTime = jArray.getJSONObject(0).getString("TravelTime");
//        String Income = jArray.getJSONObject(0).getString("Income");
//        String Evaluation = jArray.getJSONObject(0).getString("Evaluation");
//        String Opinion = jArray.getJSONObject(0).getString("Opinion");
        //


    }


}
