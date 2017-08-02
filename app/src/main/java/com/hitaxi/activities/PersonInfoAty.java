package com.hitaxi.activities;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.TextView;

import com.dk.main.R;
import com.hitaxi.base.GlobalVar;
import com.hitaxi.base.MenuBaseActivity;

public class PersonInfoAty extends MenuBaseActivity {
    Intent i = new Intent();
    private String mConnResult = null;

    protected static final String TAG = "PersonalInfo";
    private GlobalVar var;
    String uid = "";
    String token = "";

    private TextView tvDriverTeam;
    private TextView tvCallNum;
    com.hitaxi.object.PersonalDetail PersonalDetail;


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

                i.setClass(PersonInfoAty.this, IncomeRecordAty.class);
                startActivity(i);
            }
        });
        //navigation drawer個人資訊點擊意見
        findViewById(R.id.income_record_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(PersonInfoAty.this, IncomeRecordAty.class);
                startActivity(i);
            }
        });
        findViewById(R.id.opinion_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(PersonInfoAty.this, OpinionAty.class);
                startActivity(i);
            }
        });
        //navigation drawer個人資訊點擊修改密碼
        findViewById(R.id.modify_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(PersonInfoAty.this, ModPersonInfoAty.class);
                startActivity(i);
            }
        });

        //TextView findView
        tvDriverTeam = (TextView) findViewById(R.id.taxiteam_textView);
        tvCallNum = (TextView) findViewById(R.id.callnumer_textView);

    }




}
