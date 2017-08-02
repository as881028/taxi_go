package com.dk.main;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import static android.provider.BaseColumns._ID;
import static com.dk.main.DBConstants.ACCOUNT;
import static com.dk.main.DBConstants.PASSWORD;
import static com.dk.main.DBConstants.TABLE_NAME;
import static com.dk.main.DBConstants.TOKEN;
import static com.dk.main.DBConstants.USERID;

/**
 * Created by DK on 2017/7/28.
 */

public class BaseActivity extends AppCompatActivity {

    public DBHelper userDBHelper;

    //個人資料物件
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
            return this.callNum;
        }
    }

    public void openDatabase() {
        userDBHelper = new DBHelper(this);
    }

    public void closeDatabase() {
        userDBHelper.close();
    }


    public Cursor getCursor() {
        SQLiteDatabase db = userDBHelper.getReadableDatabase();
        String[] columns = {_ID, ACCOUNT, PASSWORD, TOKEN, USERID};
        //從Sqlite取資料出來用法
        //cursor.getString(1) = 第一個欄位(ACCOUNT)
        //cursor.getString(2) = 第一個欄位(PASSWORD)
        //cursor.getString(3) = 第一個欄位(TOKEN)

        Cursor cursor = db.query(TABLE_NAME, columns, null, null, null, null, null);
        if (cursor != null) {
            startManagingCursor(cursor);
        }
        return cursor;
    }


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
        menu_text_set();
    }


    //MENU必要的宣告
    private void initMenuBar() {
        //menu tool bar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            public void onDrawerClosed(View view) {

            }

            public void onDrawerOpened(View drawerView) {
                menu_text_set();
            }

        };
        drawer.setDrawerListener(toggle);
        toggle.syncState();
    }

    public void closeDrawers(DrawerLayout drawer) {
        drawer.closeDrawers();
    }

    //MENU相關事件
    private void menu_click() {

        //navigation drawer個人資訊點擊
        findViewById(R.id.imageButton2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent();
                i.setClass(getApplicationContext(), PersonalInformation.class);
                startActivity(i);
            }
        });
        //navigation drawer 選單內點擊
        findViewById(R.id.latest_news).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent();
                i.setClass(getApplicationContext(), LatestNews.class);
                startActivity(i);
            }
        });
//        navigation drawer 選單內點擊接單紀錄
        findViewById(R.id.order_record).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent();
                i.setClass(getApplicationContext(), OrderRecord.class);
                startActivity(i);
            }
        });
        findViewById(R.id.service_center_Button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent();
                i.setClass(getApplicationContext(), ServiceCenter.class);
                startActivity(i);
            }
        });
        findViewById(R.id.reservation_Button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent();
                i.setClass(getApplicationContext(), Reservation.class);
                startActivity(i);
            }
        });


    }

    public void menu_text_set() {
        GlobalVar var = (GlobalVar) getApplicationContext();

        TextView tvDriverName = (TextView) findViewById(R.id.driver_name);
        TextView tvDriverNum = (TextView) findViewById(R.id.driver_number);
        TextView tvDriverTeam = (TextView) findViewById(R.id.taxiteam_name);
        if (var.PersonalDetail.getName() != null) {
            tvDriverName.setText("姓名: " + var.PersonalDetail.getName());
            tvDriverNum.setText("呼號: " + var.PersonalDetail.getCallNum());
            tvDriverTeam.setText("車隊: " + var.PersonalDetail.getTeam());
        }
    }
}
