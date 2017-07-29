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

public class PersonalInformation extends MenuBaseActivity {
    Intent i = new Intent();
    private String mConnResult = null;

    protected static final String TAG = "PersonalInfo";
    private GlobalVar var;
    String uid = "";
    String token = "";

    private TextView tvDriverTeam;
    private TextView tvCallNum;
    PersonalDetail PersonalDetail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setMenuLayout(R.layout.activity_personal_information);
        initView();
//        openDatabase();
        var = ((GlobalVar) getApplicationContext());
        PersonalDetail = var.PersonalDetail;


        tvCallNum.setText(PersonalDetail.getCallNum());
        tvDriverTeam.setText(PersonalDetail.getTeam());
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




}
