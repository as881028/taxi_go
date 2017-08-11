package com.hitaxi.activities;

import android.os.Bundle;

import com.dk.main.R;
import com.hitaxi.base.MenuBaseActivity;


/*
Activity Name:      服務中心畫面
creator:            Stanley.li
layout:             activity_service_center
路徑:               選單->服務中心
 */
public class ServiceCenterAty extends MenuBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setMenuLayout(R.layout.activity_service_center);
    }


}
