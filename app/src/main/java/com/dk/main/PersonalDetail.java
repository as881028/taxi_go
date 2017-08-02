package com.dk.main;

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


    void setDetail(String code, String UserArray) {
        this.code = code;
        this.userArray = UserArray;
    }

    String getUserArray() {
        return this.userArray;
    }

   public void setName(String name) {
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

