package com.hitaxi.object;



import com.hitaxi.tools.parseMethod;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

/**
 * Created by DK on 2017/8/3.
 */

public class TradDetail {
    ArrayList<String> tidArrayList = new ArrayList<String>();
    ArrayList<Integer> moneyArrayList = new ArrayList<Integer>();
    ArrayList<String> dateArrayList = new ArrayList<String>();

    public TradDetail() {

    }

    public void addTid(String tid) {
        tidArrayList.add(tid);
    }

    public ArrayList<String> getTidArrayList() {
        return this.tidArrayList;
    }

    public String[] getTidArray() {
        return parseMethod.parseStringArray(tidArrayList);
    }

    public void addMoney(int money) {
        moneyArrayList.add(money);
    }

    public ArrayList<Integer> getMoneyArrayList() {
        return this.moneyArrayList;
    }

    public Integer[] getMoneyArray() {
        return parseMethod.parseIntArray(moneyArrayList);
    }

    public void addDate(String month) {
        dateArrayList.add(month);
    }

    public ArrayList<String> getDateArrayList() {
        return this.dateArrayList;
    }

    public String[] getDateArray() {
        return parseMethod.parseStringArray(dateArrayList);
    }

}
