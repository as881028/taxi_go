package com.hitaxi.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;

import android.database.Cursor;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dk.main.R;
import com.hitaxi.base.BaseActivity;
import com.hitaxi.db.DBHelper;
import com.hitaxi.object.LoginDetail;
import com.hitaxi.object.PersonalDetail;

import com.hitaxi.tools.phpConnection;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.ConnectException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

// DB columns
import static com.hitaxi.db.DBConstants.ACCOUNT;
import static com.hitaxi.db.DBConstants.PASSWORD;
import static com.hitaxi.db.DBConstants.TABLE_NAME;
import static com.hitaxi.db.DBConstants.TOKEN;
import static com.hitaxi.db.DBConstants.USERID;
//

/**
 * A login screen that offers login via email/password.
 */
public class LoginAty extends BaseActivity {


    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private HttpPostLoginTask mAuthTask = null;
    private String mPersonalResult = null;
//    private com.hitaxi.base.GlobalVar var;

    protected static final String TAG = "LoginAty";
    // UI references.
    private EditText mPhoneView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    //    private DBHelper userDBHelper;
    LoginDetail LoginDetail = new LoginDetail();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//        var = var;
        openDatabase();
        initView();
        getGlobal();
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
                    insertSQLite();


                    // 換頁 to MapsActivity
                    intent.setClass(LoginAty.this, MapsAty.class);
                    startActivity(intent);
                    LoginAty.this.finish();
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
                        intent.setClass(LoginAty.this, MapsAty.class);
                        startActivity(intent);
                        LoginAty.this.finish();
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

        private void insertSQLite() {
            String account = "";
            String password = "";
            SQLiteDatabase db = userDBHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            Cursor cursor = getCursor();
            while (cursor.moveToNext()) {
                account = cursor.getString(1);
                password = cursor.getString(2);
            }


            if (!account.equals(mMap.get("LoginAccount")) && !password.equals(mMap.get("LoginPassword"))) {
                //data insert to database

                values.put(ACCOUNT, mMap.get("LoginAccount"));
                values.put(PASSWORD, mMap.get("LoginPassword"));
                values.put(TOKEN, LoginDetail.getUserToken());
                values.put(USERID, LoginDetail.getUserID());
                db.insert(TABLE_NAME, null, values);
            } else if (account.equals(mMap.get("LoginAccount")) && !password.equals(mMap.get("LoginPassword"))) {
                values.put(PASSWORD, mMap.get("LoginPassword"));
                db.update(TABLE_NAME, values, ACCOUNT + "=" + mMap.get("LoginAccount"), null);
            }

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

