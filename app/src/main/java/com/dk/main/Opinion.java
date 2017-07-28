package com.dk.main;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;

public class opinion extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        replaceInclude(R.layout.activity_opinion);
        initMenuBar();
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


}

