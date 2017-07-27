package com.dk.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;

public class Personal_information extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
            //取代app_bar的include ， 傳Layout
        replaceInclude(R.layout.activity_personal_information);
        initMenuBar();
        menu_click();
    }

    private void replaceInclude(int layout) {
        View include = findViewById(R.id.include_menu);
        ViewGroup parent = (ViewGroup) include.getParent();
        int index = parent.indexOfChild(include);
        parent.removeView(include);
        include = getLayoutInflater().inflate(layout, parent, false);
        parent.addView(include, index);
    }
    private void initMenuBar() {
        //menu tool bar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
    }

    private void menu_click() {
        //navigation drawer個人資訊點擊收入紀錄
        findViewById(R.id.income_record_button).setOnClickListener(new View.OnClickListener() { // i= (Button) findViewById(R.id.latest_news),   i.setOnClickListener(new View.OnClickListener()
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(Personal_information.this, income_record.class);
                startActivity(i);
            }
        });
        //navigation drawer個人資訊點擊意見
        findViewById(R.id.income_record_button).setOnClickListener(new View.OnClickListener() { // i= (Button) findViewById(R.id.latest_news),   i.setOnClickListener(new View.OnClickListener()
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(Personal_information.this, opinion.class);
                startActivity(i);
            }
        });
        //navigation drawer個人資訊點擊修改密碼
        findViewById(R.id.modify_button).setOnClickListener(new View.OnClickListener() { // i= (Button) findViewById(R.id.latest_news),   i.setOnClickListener(new View.OnClickListener()
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(Personal_information.this, modify_personal_information.class);
                startActivity(i);
            }
        });
    }

}
