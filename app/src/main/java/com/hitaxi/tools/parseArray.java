package com.hitaxi.tools;

import com.hitaxi.object.PersonalDetail;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by DK on 2017/8/4.
 */

public class parseArray {
    public static PersonalDetail parseLoginJson(String mJSONText) throws JSONException {
        JSONObject jObject = new JSONObject(mJSONText);
        String code = jObject.getString("Code");
        String errorMsg = jObject.getString("ErrorMsg");
        String token = jObject.getString("Token");
        String userId = jObject.getString("UserId");
        String userType = jObject.getString("UserType");
        PersonalDetail pd = new PersonalDetail();
        pd.setLoginDetail(code, errorMsg, token, userId, userType);
        return pd;
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
