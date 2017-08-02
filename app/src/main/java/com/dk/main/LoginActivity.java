package com.dk.main;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import javax.net.ssl.HttpsURLConnection;

import static android.Manifest.permission.*;

import static android.Manifest.permission.*;
import static android.Manifest.permission.READ_CONTACTS;

// DB columns
import static android.provider.BaseColumns._ID;
import static com.dk.main.DBConstants.ACCOUNT;
import static com.dk.main.DBConstants.PASSWORD;
import static com.dk.main.DBConstants.TABLE_NAME;
import static com.dk.main.DBConstants.TOKEN;
import static com.dk.main.DBConstants.USERID;
//

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends BaseActivity {


    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private HttpPostLoginTask mAuthTask = null;
    private String mPersonalResult = null;
    private GlobalVar var;

    protected static final String TAG = "LoginActivity";
    // UI references.
    private EditText mPhoneView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    //    private DBHelper userDBHelper;
    LoginDetail LoginDetail = new LoginDetail();

    class LoginDetail {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        var = (GlobalVar) getApplicationContext();
        openDatabase();
        initView();
        var.PersonalDetail = new PersonalDetail();

        // set last user account and password (SQLite)
        setViewData();
    }

    //宣告相關物件
    private void initView() {
        mPhoneView = (EditText) findViewById(R.id.phone);
        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });
        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });
        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }


    private void setViewData() {
        Cursor cursor = getCursor();
        while (cursor.moveToNext()) {
            String account = cursor.getString(1);
            String password = cursor.getString(2);
            if (var.debug) {
                Log.i(TAG, account);
                Log.i(TAG, password);
            }
            mPhoneView.setText(account);
            mPasswordView.setText(password);

        }

    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        closeDatabase();
    }

    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mPhoneView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String phone = mPhoneView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }


        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);

            Map<String, String> map = new HashMap<String, String>();
            map.put("LoginAccount", phone);
            map.put("LoginPassword", password);
            mAuthTask = new HttpPostLoginTask(map);
            mAuthTask.execute((Void) null);

        }
    }

    //驗證密碼格式
    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4 && password.length() < 16;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    //登入用的TASK
    public class HttpPostLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final Map<String, String> mMap;


        HttpPostLoginTask(Map<String, String> map) {
            mMap = map;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.
            Handler handler = new Handler(getApplicationContext().getMainLooper());
            try {
                // Simulate network access.

                //取得PHP回傳值
//                String result = phpConnection.createConnection(var.validation, key, value);

                String result = phpConnection.createConnection(var.validation, mMap);
                Log.i(TAG, result);
                //解析JSON
                parseJson(result);
                //判斷是否為司機端
                Intent intent = new Intent();

                if (LoginDetail.getUserType() == 2 && LoginDetail.getCode() == 1) {
                    if (var.debug) {
                        Log.i(TAG, "司機 登入成功");
                    }

                    //data insert to database
                    SQLiteDatabase db = userDBHelper.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    values.put(ACCOUNT, mMap.get("LoginAccount"));
                    values.put(PASSWORD, mMap.get("LoginPassword"));
                    values.put(TOKEN, LoginDetail.getUserToken());
                    values.put(USERID, LoginDetail.getUserID());
                    db.insert(TABLE_NAME, null, values);


                    // 換頁 to MapsActivity
                    intent.setClass(LoginActivity.this, MapsActivity.class);
                    startActivity(intent);
                    LoginActivity.this.finish();
                    //


                } else if (LoginDetail.getUserType() == 1 && LoginDetail.getCode() == 1) {
                    handler.post(new Runnable() {
                        public void run() {
                            Toast.makeText(getApplicationContext(), "此帳號為乘客端帳號，請改用乘客端登入。", Toast.LENGTH_LONG).show();
                        }
                    });
                    return false;

                } else if (LoginDetail.getCode() == -1) {
                    if (var.debug) {
                        Log.i(TAG, "帳號密碼錯誤");
                    }
                    handler.post(new Runnable() {
                        public void run() {
                            Toast.makeText(getApplicationContext(), "帳號密碼錯誤。", Toast.LENGTH_LONG).show();
                        }
                    });
                    return false;
                } else {
                    if (var.debug) {
                        Log.i(TAG, "登入失敗,DEBUG MODE ON,直接登入");
                        //跳過登入
                        intent.setClass(LoginActivity.this, MapsActivity.class);
                        startActivity(intent);
                        LoginActivity.this.finish();
                        return true;
                    }
                    return false;
                }
                Thread.sleep(3000);
            } catch (ConnectException e) {
                if (var.debug) {
                    Log.i(TAG, "Connect Exception");
                    Log.i(TAG, e.toString());
                }
                return false;
            } catch (InterruptedException e) {
                if (var.debug)
                    Log.i(TAG, "Interrupted Exception");

                return false;
            } catch (NullPointerException e) {
                handler.post(new Runnable() {
                    public void run() {
                        Toast.makeText(getApplicationContext(), "請確認網路連線。", Toast.LENGTH_LONG).show();
                    }
                });
                if (var.debug) {
                    Log.i(TAG, "NullPoint Exception");
                    Log.i(TAG, e.toString());
                }

                return false;


            } catch (Exception e) {
                if (var.debug)
                    Log.i(TAG, e.toString());
            }

            // TODO: register the new account here.
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);
            //登入完成後
            if (success) {
                getPersonalData();
                finish();
            } else {
//                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }

    private void parseJson(String mJSONText) throws JSONException {
        JSONObject jObject = new JSONObject(mJSONText);
        String code = jObject.getString("Code");
        String errorMsg = jObject.getString("ErrorMsg");
        String token = jObject.getString("Token");
        String userId = jObject.getString("UserId");
        String userType = jObject.getString("UserType");
        LoginDetail.setDetail(code, errorMsg, token, userId, userType);

    }

    //讀取個人資料
    public class HttpPostPersonalTask extends AsyncTask<Void, Void, String> {

        private final Map<String, String> mMap;
        private final String mUrl;

        HttpPostPersonalTask(String url, Map<String, String> map) {
            mMap = map;
            mUrl = url;
        }

        @Override
        protected String doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.
            String result = "";
            try {
                //取得PHP回傳值
                result = phpConnection.createConnection(mUrl, mMap);
                parsePersonalJson(result);

                Log.i(TAG, result);

            } catch (NullPointerException e) {
                if (var.debug) {
                    Log.i(TAG, "Null Point");
                    return null;
                }
            } catch (Exception e) {
                if (var.debug)
                    Log.i(TAG, e.toString());
            }
            // TODO: register the new account here.
            return result;
        }


        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }

    }

    //
    private void parsePersonalJson(String mJSONText) throws JSONException {
        JSONObject jObject = new JSONObject(mJSONText);
        String code = jObject.getString("Code");
        String userArray = jObject.getString("UserArray");
        String name = new JSONObject(new JSONObject(mJSONText).getString("UserArray")).getString("Name");
        String team = new JSONObject(new JSONObject(mJSONText).getString("UserArray")).getString("Team");
        String callNum = new JSONObject(new JSONObject(mJSONText).getString("UserArray")).getString("CallNum");
        String picture = new JSONObject(new JSONObject(mJSONText).getString("UserArray")).getString("Picture");

        var.PersonalDetail.setDetail(code, userArray);
        var.PersonalDetail.setName(name);
        var.PersonalDetail.setTeam(team);
        var.PersonalDetail.setCallNum(callNum);


    }

    private void getPersonalData() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("Token", LoginDetail.getUserToken());
        map.put("UserID", LoginDetail.getUserID());
        String url = var.queryDriver;

        try {
            mPersonalResult = new HttpPostPersonalTask(url, map).execute().get();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


    }
}

