package com.hitaxi.object;

/**
 * Created by DK on 2017/8/3.
 */

public class LoginDetail {


    //code 代碼
    //-1 => "帳號或密碼錯誤。",
    //-2 => "標章驗證錯誤。",
    //-99 => "未知錯誤。"
    protected String code;

    //errorMsg 錯誤訊息
    protected String errorMsg;

    //token 驗證用token，後面與PHP溝通會用到
    protected String token;

    //使用者ID
    protected String userId;

    //使用者型態
    // 1=>乘客
    //2=>司機
    protected String userType;

    public void setDetail(String code, String errorMsg, String token, String userId, String userType) {
        this.code = code;
        this.errorMsg = errorMsg;
        this.token = token;
        this.userId = userId;
        this.userType = userType;
    }

    public int getUserType() {
        return Integer.parseInt(this.userType);
    }

    public int getCode() {
        return Integer.parseInt(this.code);
    }

    public String getUserToken() {
        return this.token;
    }

    public String getUserID() {
        return this.userId;
    }


}
