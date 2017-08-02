package com.hitaxi.activities;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.dk.main.R;
import com.hitaxi.adapter.OpinionAdapter;
import com.hitaxi.base.MenuBaseActivity;

public class OpinionAty extends MenuBaseActivity {

    ListView opinion_Listview;
    String[] dates= new String[] {"2017年5月12","2017年5月13日","2017年5月14日","2017年5月15日","2017年5月16日",
            "2017年5月17日","2017年5月18日","2017年5月19日","2017年5月20日","2017年5月21日"};
    String[] stars= {"3","2","5","1","1", "2","5","4","4","3"};
    String[] contents= {"台北火車站","台南火車站","台中火車站","台東火車站","永康火車站",
            "新市火車站","善化火車站","大橋火車站","新營火車站","柳營火車站"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setMenuLayout(R.layout.activity_opinion);
//        opinion_Listview = (ListView) findViewById(R.id.opinion_Listview);
//        //建立自訂的Adapter
//        OpinionAdapter adapter = new OpinionAdapter(this,dates,stars,contents);
//
//        //設定ListView 的資源來源
//        opinion_Listview.setAdapter(adapter);
    }


}

