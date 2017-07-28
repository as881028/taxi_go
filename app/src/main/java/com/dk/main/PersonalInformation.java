package com.dk.main;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static android.provider.BaseColumns._ID;
import static com.dk.main.DBConstants.ACCOUNT;
import static com.dk.main.DBConstants.PASSWORD;
import static com.dk.main.DBConstants.TABLE_NAME;
import static com.dk.main.DBConstants.TOKEN;
import static com.dk.main.DBConstants.USERID;

public class PersonalInformation extends BaseActivity {
    Intent i = new Intent();
    private String mConnResult = null;

    protected static final String TAG = "PersonalInfo";
    private GlobalVar var;
    String uid = "";
    String token = "";
    PersonalDetail PersonalDetail = new PersonalDetail();

    private TextView tvDriverTeam;
    private TextView tvCallNum;

    class PersonalDetail {
        protected String code;
        protected String userArray;
        protected String name;
        protected String team;
        protected String callNum;
        protected String setPicture;

        void setDetail(String code, String UserArray) {
            this.code = code;
            this.userArray = UserArray;
        }

        String getUserArray() {
            return this.userArray;
        }

        void setName(String name) {
            this.name = name;
        }

        String getName() {
            return this.name;
        }

        void setTeam(String team) {
            this.team = team;
        }

        String getTeam() {
            return this.team;
        }

         void setCallNum(String callNum) {
            this.callNum = callNum;
        }
        String getCallNum() {
            return this.team;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setMenuLayout(R.layout.activity_personal_information);
        initView();
        openDatabase();
        var = ((GlobalVar) getApplicationContext());

        Cursor cursor = getCursor();

        while (cursor.moveToNext()) {
            uid = cursor.getString(4);
            token = cursor.getString(3);
            Log.i(TAG, uid);
            Log.i(TAG, token);
        }
        //與資料庫連接
        Map<String, String> map = new HashMap<String, String>();
        map.put("Token", token);
        map.put("UserID", uid);
        String url = var.queryDriver;

        try {
            mConnResult = new HttpPostTask(url, map).execute().get();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        tvCallNum.setText(PersonalDetail.getCallNum());
        tvDriverTeam.setText(PersonalDetail.getTeam());
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class HttpPostTask extends AsyncTask<Void, Void, String> {

        private final Map<String, String> mMap;
        private final String mUrl;

        HttpPostTask(String url, Map<String, String> map) {
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

    }

    private void initView() {
        //navigation drawer個人資訊點擊收入紀錄
        findViewById(R.id.income_record_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                i.setClass(PersonalInformation.this, IncomeRecord.class);
                startActivity(i);
            }
        });
        //navigation drawer個人資訊點擊意見
        findViewById(R.id.income_record_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(PersonalInformation.this, Opinion.class);
                startActivity(i);
            }
        });
        //navigation drawer個人資訊點擊修改密碼
        findViewById(R.id.modify_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(PersonalInformation.this, ModifyPersonalInformation.class);
                startActivity(i);
            }
        });

        //TextView findView
        tvDriverTeam = (TextView) findViewById(R.id.taxiteam_textView);
        tvCallNum = (TextView) findViewById(R.id.callnumer_textView);

    }

    private void parseJson(String mJSONText) throws JSONException {
        JSONObject jObject = new JSONObject(mJSONText);
        String code = jObject.getString("Code");
        String userArray = jObject.getString("UserArray");
        String name = new JSONObject(new JSONObject(mJSONText).getString("UserArray")).getString("Name");
        String team = new JSONObject(new JSONObject(mJSONText).getString("UserArray")).getString("Team");
        String callNum = new JSONObject(new JSONObject(mJSONText).getString("UserArray")).getString("CallNum");
        String picture = new JSONObject(new JSONObject(mJSONText).getString("UserArray")).getString("Picture");

        PersonalDetail.setDetail(code, userArray);
        PersonalDetail.setName(name);
        PersonalDetail.setTeam(team);
        PersonalDetail.setCallNum(callNum);
//        PersonalDetail.setPicture(picture);
//        Log.i(TAG,PersonalDetail.getUserArray());

    }


}
