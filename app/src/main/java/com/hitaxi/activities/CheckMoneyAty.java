package com.hitaxi.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.dk.main.R;
import com.hitaxi.base.MenuBaseActivity;

import org.w3c.dom.Text;

/*
Activity Name:      付款畫面
creator:            Stanley.li
layout:             activity_opinion
路徑:               交易結束

 */
public class CheckMoneyAty extends MenuBaseActivity {

    public com.hitaxi.object.PersonalDetail PersonalDetail;
    public com.hitaxi.object.TradDetail TradDetail;
    TextView tvTotal;
    public int total;
    public Button btnModify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setMenuLayout(R.layout.activity_chk_money);
//        setContentView(R.layout.activity_pay);
        Bundle bundle = this.getIntent().getExtras();
        total = bundle.getInt("total");
        getGlobal();
        PersonalDetail = var.PersonalDetail;
        TradDetail = var.TradDetail;
        initView();

    }

    private void initView() {
        tvTotal = (TextView) findViewById(R.id.tvTotal);
        tvTotal.setText(total + "");
        btnModify = (Button) findViewById(R.id.btnModify);
        btnModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent();
                it.setClass(getApplicationContext(), PayAty.class);
                Bundle bundle = new Bundle();
                bundle.putInt("total", total);
                it.putExtras(bundle);
                startActivity(it);
            }
        });
    }


}



