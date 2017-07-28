package com.dk.main;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

/**
 * Created by DK on 2017/7/28.
 */

public class BaseActivity extends AppCompatActivity {
    //取代include_menu 用來替換畫面
    public void replaceInclude(int layout) {


        View include = findViewById(R.id.include_menu);
        ViewGroup parent = (ViewGroup) include.getParent();
        int index = parent.indexOfChild(include);
        parent.removeView(include);
        include = getLayoutInflater().inflate(layout, parent, false);
        parent.addView(include, index);
    }

    //初始化相關MENU元件 及 相關事件
    public void setMenuLayout() {
        //起始畫面activity_menu
        setContentView(R.layout.activity_menu);

        //畫面不關
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);


        menu_click();
        initMenuBar();
    }
    //初始化相關MENU元件 及 相關事件
    public void setMenuLayout(int layout) {
        //起始畫面activity_menu
        setContentView(R.layout.activity_menu);

        //畫面不關
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        replaceInclude(layout);
        menu_click();
        initMenuBar();
    }

    //MENU必要的宣告
    public void initMenuBar() {
        //menu tool bar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
    }

    //MENU相關事件
    public void menu_click() {
        //navigation drawer個人資訊點擊
        findViewById(R.id.imageButton2).setOnClickListener(new View.OnClickListener() { // i= (Button) findViewById(R.id.latest_news),   i.setOnClickListener(new View.OnClickListener()
            @Override
            public void onClick(View v) {

                Intent i = new Intent();
                i.setClass(getApplicationContext(), Personal_information.class);
                startActivity(i);
            }
        });
        //navigation drawer 選單內點擊
        findViewById(R.id.latest_news).setOnClickListener(new View.OnClickListener() { // i= (Button) findViewById(R.id.latest_news),   i.setOnClickListener(new View.OnClickListener()
            @Override
            public void onClick(View v) {

                Intent i = new Intent();
                i.setClass(getApplicationContext(), latest_news.class);
                startActivity(i);
            }
        });
//        navigation drawer 選單內點擊接單紀錄
        findViewById(R.id.order_record).setOnClickListener(new View.OnClickListener() { // i= (Button) findViewById(R.id.latest_news),   i.setOnClickListener(new View.OnClickListener()
            @Override
            public void onClick(View v) {

                Intent i = new Intent();
                i.setClass(getApplicationContext(), order_record.class);
                startActivity(i);
            }
        });
    }
}
