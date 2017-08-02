package com.hitaxi.object;

/**
 * Created by DK on 2017/8/2.
 */
//個人資料物件
public class PersonalDetail {

    protected String code;
    protected String userArray;
    protected String name;
    protected String team;
    protected String callNum;
    protected String setPicture;


    public void setDetail(String code, String UserArray) {
        this.code = code;
        this.userArray = UserArray;
    }

    String getUserArray() {
        return this.userArray;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getTeam() {
        return this.team;
    }

    public void setCallNum(String callNum) {
        this.callNum = callNum;
    }

    public String getCallNum() {
        return this.callNum;
    }
}

