package com.rain.doudu.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.baidu.api.AsyncBaiduRunner;
import com.baidu.api.Baidu;
import com.baidu.api.BaiduDialog;
import com.baidu.api.BaiduDialogError;
import com.baidu.api.BaiduException;
import com.baidu.api.Util;
import com.google.gson.Gson;
import com.rain.doudu.R;
import com.rain.doudu.api.common.Dao.UserDao;
import com.rain.doudu.bean.http.baidu.Person;
import com.rain.doudu.bean.http.jiangjianyu.User;
import com.rain.doudu.common.URL;
import com.rain.doudu.utils.common.ToastUtils;

import java.io.IOException;

import okhttp3.MediaType;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private static final String EXTRA_USER_NAME = "com.rain.username";

    private EditText mAccount;
    private EditText mPassword;
    private Button mLogin;
    private Button mRegister;
    private ImageButton mImageButton;

    private User mUser;
    private Person mPerson;

    private UserDao mUserDao;
    private Baidu baidu = null;
    final String url = URL.USER_INOF_URL;
    private Handler mHandler = new Handler();
    private String json;

    //是否每次授权都强制登陆
    private boolean isForceLogin = true;
    private boolean isConfirmLogin = true;

    public static final MediaType MEDIA_TYPE_MARKDOWN = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        mLogin.setOnClickListener(new LoginOnClick());
        mRegister.setOnClickListener(new RegOnClick());
        mImageButton.setOnClickListener(new BaiDuOnClick());
    }

    private void init() {
        mAccount = (EditText) findViewById(R.id.account);
        mPassword = (EditText) findViewById(R.id.password);
        mLogin = (Button) findViewById(R.id.login);
        mRegister = (Button) findViewById(R.id.register);
        mImageButton = (ImageButton) findViewById(R.id.baidu_login_btn);
        mHandler = new Handler();
    }

    private class LoginOnClick implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            mUserDao = new UserDao(getApplicationContext());
            mUser = new User();
            mUser.setMyName(mAccount.getText().toString());
            mUser.setMyPassword(mPassword.getText().toString());
            mUser = mUserDao.queryUser(mUser);
            if (mUser.getMyId() == null) {
                ToastUtils.showShort("用户名或密码输入错误");
            } else {
                Intent intent = MainActivity.newIntent(getApplicationContext(), mUser);
                startActivity(intent);

            }
        }
    }

    private class RegOnClick implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);

        }
    }

    private class BaiDuOnClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            //获取token
            baidu = new Baidu(URL.BAIDU_API_KEY, LoginActivity.this);
            baidu.authorize(LoginActivity.this, isForceLogin,isConfirmLogin,new BaiduDialog.BaiduDialogListener() {

                @Override
                public void onComplete(Bundle values) {

                    showTokenInfo();
                }

                @Override
                public void onBaiduException(BaiduException e) {

                }

                @Override
                public void onError(BaiduDialogError e) {

                }

                @Override
                public void onCancel() {
                    Util.logd("cancle","I am back");
                }
            });



        }
    }
    private void showTokenInfo() {
        AsyncBaiduRunner runner = new AsyncBaiduRunner(baidu);
        runner.request(url, null, "POST", new DefaultRequstListener());

    }
    public class DefaultRequstListener implements AsyncBaiduRunner.RequestListener {

        @Override
        public void onBaiduException(BaiduException arg0) {

        }

        @Override
        public void onComplete(final String value) {
            mHandler.post(new Runnable() {

                @Override
                public void run() {
                    json = value;
                    Gson gson = new Gson();
                    mPerson = new Person();
                    mPerson = gson.fromJson(json, Person.class);
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    intent.putExtra("BAIDUER", mPerson);
                   // baidu.clearAccessToken();
                    startActivity(intent);
                    finish();
                }
            });

        }

        @Override
        public void onIOException(IOException arg0) {

        }

    }
}
