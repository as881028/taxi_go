package com.dk.main;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;

public class Personal_information extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setMenuLayout(R.layout.activity_personal_information);
//        replaceInclude(R.layout.activity_personal_information);
        //navigation drawer個人資訊點擊收入紀錄
        findViewById(R.id.income_record_button).setOnClickListener(new View.OnClickListener() { 
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(Personal_information.this, IncomeRecord.class);
                startActivity(i);
            }
        });
        //navigation drawer個人資訊點擊意見
        findViewById(R.id.income_record_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(Personal_information.this, opinion.class);
                startActivity(i);
            }
        });
        //navigation drawer個人資訊點擊修改密碼
        findViewById(R.id.modify_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(Personal_information.this, modify_personal_information.class);
                startActivity(i);
            }
        });
    }


}
