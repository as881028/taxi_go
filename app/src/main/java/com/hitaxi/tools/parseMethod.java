package com.hitaxi.tools;

import android.util.Log;

import com.hitaxi.object.PersonalDetail;
import com.hitaxi.object.TradDetail;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by DK on 2017/8/4.
 */

public class parseMethod {
    public static void parseLoginJson(String mJSONText, PersonalDetail pd) throws JSONException {
        JSONObject jObject = new JSONObject(mJSONText);
        String code = jObject.getString("Code");
        String errorMsg = jObject.getString("ErrorMsg");
        String token = jObject.getString("Token");
        String userId = jObject.getString("UserId");
        String userType = jObject.getString("UserType");
        pd.setLoginDetail(code, errorMsg, token, userId, userType);

    }


    public static void parseTradJson(String mJSONText, TradDetail td) throws JSONException {
        JSONObject jObject = new JSONObject(mJSONText);
        JSONArray jArray = jObject.getJSONArray("TradArray");

        for (int i = 0; i < jArray.length(); i++) {

            String tid = jArray.getJSONObject(i).getString("Tid");
            td.addTid(tid);

            int money = jArray.getJSONObject(i).getInt("Income");
            td.addMoney(money);

            String date = jArray.getJSONObject(i).getString("StartTime");
            String[] dateArray = date.split(" ");
            dateArray = dateArray[0].split("-");
            td.addDate(dateArray[0] + "年" + dateArray[1] + "月" + dateArray[2] + "日");
        }

        //
//        String tid = jArray.getJSONObject(0).getString("Tid");
//        String startTime = jArray.getJSONObject(0).getString("StartTime");
//        String endTime = jArray.getJSONObject(0).getString("EndTime");
//        String StartLatitude = jArray.getJSONObject(0).getString("StartLatitude");
//        String StartLongitude = jArray.getJSONObject(0).getString("StartLongitude");
//        String EndLatitude = jArray.getJSONObject(0).getString("EndLatitude");
//        String EndLongitude = jArray.getJSONObject(0).getString("EndLongitude");
//        String EstimetePrice = jArray.getJSONObject(0).getString("EstimetePrice");
//        String ActualPrice = jArray.getJSONObject(0).getString("ActualPrice");
//        String TravelTime = jArray.getJSONObject(0).getString("TravelTime");
//        String Income = jArray.getJSONObject(0).getString("Income");
//        String Evaluation = jArray.getJSONObject(0).getString("Evaluation");
//        String Opinion = jArray.getJSONObject(0).getString("Opinion");
        //


    }

    public static void parsePersonalJson(String mJSONText, PersonalDetail pd) throws JSONException {
        JSONObject jObject = new JSONObject(mJSONText);
        String code = jObject.getString("Code");
        String userArray = jObject.getString("UserArray");
        String name = new JSONObject(new JSONObject(mJSONText).getString("UserArray")).getString("Name");
        String team = new JSONObject(new JSONObject(mJSONText).getString("UserArray")).getString("Team");
        String callNum = new JSONObject(new JSONObject(mJSONText).getString("UserArray")).getString("CallNum");
        String picture = new JSONObject(new JSONObject(mJSONText).getString("UserArray")).getString("Picture");

        pd.setDetail(code, userArray);
        pd.setName(name);
        pd.setTeam(team);
        pd.setCallNum(callNum);


    }

    public static String[] parseStringArray(ArrayList<String> al) {
        String[] array = new String[al.size()];
        for (int i = 0; i < al.size(); i++) {
            array[i] = al.get(0);
        }
        return array;
    }

    public static Integer[] parseIntArray(ArrayList<Integer> al) {
        Integer[] array = new Integer[al.size()];
        for (int i = 0; i < al.size(); i++) {
            array[i] = al.get(0);
        }
        return array;
    }
}
