package com.dk.main;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import static android.Manifest.permission.READ_CONTACTS;

// DB columns
import static android.provider.BaseColumns._ID;
import static com.dk.main.DBConstants.ACCOUNT;
import static com.dk.main.DBConstants.PASSWORD;
import static com.dk.main.DBConstants.TABLE_NAME;
//

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {
    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "0909288870:123456", "bar@example.com:world"
    };
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;

    private GlobalVar var;

    protected static final String TAG = "LoginActivity";
    // UI references.
    private EditText mPhoneView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private DBHelper userDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        openDatabase();
        initView();
        var = ((GlobalVar) getApplicationContext());
        // set last user account and password (SQLite)
        setViewData();
    }

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

    private void openDatabase() {
        Log.i(TAG,"openDatabase");
        userDBHelper = new DBHelper(this);
    }

    private void closeDatabase() {
        userDBHelper.close();
    }

    private Cursor getCursor() {

        SQLiteDatabase db = userDBHelper.getReadableDatabase();
        String[] columns = {_ID, ACCOUNT, PASSWORD};

        Cursor cursor = db.query(TABLE_NAME, columns, null, null, null, null, null);
        if(cursor!=null)
        {
            startManagingCursor(cursor);
            Log.i("test","not null");
        }


        return cursor;
    }

    private void setViewData() {
        Cursor cursor = getCursor();
        while (cursor.moveToNext()) {
            String account = cursor.getString(1);
            Log.i(TAG,account);
            String password = cursor.getString(2);
            Log.i(TAG,password);
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


            mAuthTask = new UserLoginTask(phone, password);
            mAuthTask.execute((Void) null);
        }
    }

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

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mPhone;
        private final String mPassword;

        UserLoginTask(String phone, String password) {
            mPhone = phone;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {
                // Simulate network access.
                ArrayList<String> key = new ArrayList<>();
                ArrayList<String> value = new ArrayList<>();

                key.add("account");
                value.add(mPhone);
                key.add("password");
                value.add(mPassword);


                String result = phpConnection.createConnection(var.queryUser, key, value);

                if (result.equals("user")) {
                    Log.i(TAG, "登入成功");

                    SQLiteDatabase db = userDBHelper.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    values.put(ACCOUNT, value.get(0));
                    values.put(PASSWORD, value.get(1));
                    db.insert(TABLE_NAME, null, values);

                    //
                    Intent intent = new Intent();
                    intent.setClass(LoginActivity.this,MapsActivity.class);
                    startActivity(intent);
                    LoginActivity.this.finish();
                    //


                } else {
                    Log.i(TAG, "登入失敗");
                }
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                return false;
            }
//
//            for (String credential : DUMMY_CREDENTIALS) {
//                String[] pieces = credential.split(":");
//
//                if (pieces[0].equals(mPhone)) {
//                    // Account exists, return true if the password matches.
//
//                    return pieces[1].equals(mPassword);
//                }
//            }

            // TODO: register the new account here.
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {
              finish();
            } else {
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }


//    private void getHttpReuslt(String account, String password) {
//
//        String responseString = null;
//        String urlString = "http://140.128.98.46/taxi_go/query_user.php";
//        HttpURLConnection connection = null;
//
//        try {
//            // 初始化 URL
//            URL url = new URL(urlString);
//            // 取得連線物件
//            connection = (HttpURLConnection) url.openConnection();
//            // 設定 request timeout
//            connection.setReadTimeout(1500);
//            connection.setConnectTimeout(1500);
//            connection.setDoOutput(true);// 設置此方法,允許向伺服器輸出內容
//
//            // post請求的參數
//            String data = String.format("account=%s&password=%s", account, password);
//
//            OutputStream out = connection.getOutputStream();// 產生一個OutputStream，用來向伺服器傳數據
//            out.write(data.getBytes());
//            out.flush();
//            out.close();
//            // 模擬 Chrome 的 user agent, 因為手機的網頁內容較不完整
////            connection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.71 Safari/537.36");
//            // 設定開啟自動轉址
//            connection.setInstanceFollowRedirects(true);
//
//            // 若要求回傳 200 OK 表示成功取得網頁內容
//            if (connection.getResponseCode() == HttpsURLConnection.HTTP_OK) {
//                // 讀取網頁內容
//                InputStream inputStream = connection.getInputStream();
//                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
//
//                String tempStr;
//                StringBuffer stringBuffer = new StringBuffer();
//
//                while ((tempStr = bufferedReader.readLine()) != null) {
//                    stringBuffer.append(tempStr);
//                }
//
//                bufferedReader.close();
//                inputStream.close();
//
//                /*
//                // 取得網頁內容類型
//                String mime = connection.getContentType();
//                boolean isMediaStream = false;
//
//                // 判斷是否為串流檔案
//                if (mime.indexOf("audio") == 0 || mime.indexOf("video") == 0) {
//                    isMediaStream = true;
//                }
//                 */
//                // 網頁內容字串
//                responseString = stringBuffer.toString();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            // 中斷連線
//            if (connection != null) {
//                connection.disconnect();
//            }
//        }
//    }
}

