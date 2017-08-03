package com.hitaxi.object;

import java.util.ArrayList;

/**
 * Created by DK on 2017/8/3.
 */

public class TradDetail {
    ArrayList<String> tidArray = new ArrayList<String>();
    ArrayList<Integer> moneyArray = new ArrayList<Integer>();
    public TradDetail() {

    }

    public void addTid(String tid) {
        tidArray.add(tid);
    }
    public ArrayList<String> getTidArray(){
        return this.tidArray;
    }

    public void addMoney(int money) {
        moneyArray.add(money);
    }
    public ArrayList<Integer> getMoneyArray(){
        return this.moneyArray;
    }
}
