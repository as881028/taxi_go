package com.hitaxi.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.dk.main.R;
import com.hitaxi.adapter.OpinionAdapter;
import com.hitaxi.base.MenuBaseActivity;
import com.hitaxi.object.PersonalDetail;
import com.hitaxi.object.TradDetail;

import org.w3c.dom.Text;

/*
Activity Name:      付款畫面
creator:            Stanley.li
layout:             activity_opinion
路徑:               交易結束

 */
public class PayAty extends MenuBaseActivity {

    public com.hitaxi.object.PersonalDetail PersonalDetail;
    public com.hitaxi.object.TradDetail TradDetail;

    int[] rBtn = new int[]{R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9, R.id.btn0};
    Button[] btnArray = new Button[rBtn.length];
    Button btnSubmit, btnClear;
    TextView tvTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setMenuLayout(R.layout.activity_pay);
//        setContentView(R.layout.activity_pay);


        getGlobal();
        PersonalDetail = var.PersonalDetail;
        TradDetail = var.TradDetail;

        initView();
        btnSetOnClick();

        Bundle bundle = this.getIntent().getExtras();

        if (bundle != null) {
            int total = bundle.getInt("total");
            tvTotal.setText(total + "");
        }


    }

    private void btnSetOnClick() {
        for (int i = 0; i < btnArray.length; i++) {
            btnArray[i].setOnClickListener(btnClick);
        }
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearTotal();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitTotal();
            }
        });

    }

    public void submitTotal() {
        int total = Integer.parseInt(tvTotal.getText().toString());
        Intent it = new Intent();
        Bundle bundle = new Bundle();
        bundle.putInt("total", Integer.parseInt(tvTotal.getText().toString()));
        it.putExtras(bundle);
        it.setClass(this, CheckMoneyAty.class);
        startActivity(it);
    }

    public void clearTotal() {
        tvTotal.setText("0");
    }

    View.OnClickListener btnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            for (int i = 0; i < rBtn.length; i++) {
                if (rBtn[i] == view.getId()) {

                    if (Integer.parseInt(tvTotal.getText().toString()) == 0) {
                        tvTotal.setText("");

                    }
                    String btn = btnArray[i].getText().toString();
                    tvTotal.setText(tvTotal.getText().toString() + btn);


                }
            }

        }
    };

    private void initView() {
        for (int i = 0; i < rBtn.length; i++) {
            btnArray[i] = (Button) findViewById(rBtn[i]);
        }
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        btnClear = (Button) findViewById(R.id.btnClear);
        tvTotal = (TextView) findViewById(R.id.tvTotal);
    }


}



