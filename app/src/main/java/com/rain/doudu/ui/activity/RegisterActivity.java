package com.rain.doudu.ui.activity;

import android.app.DatePickerDialog;
import android.content.Intent;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.rain.doudu.R;
import com.rain.doudu.api.common.Dao.UserDao;
import com.rain.doudu.api.model.impl.UserModelImpl;
import com.rain.doudu.bean.http.jiangjianyu.User;
import com.rain.doudu.utils.common.ToastUtils;

import java.util.Calendar;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";
    private EditText mName;
    private EditText mPassword;
    private EditText mPassword2;
    private EditText mCity;
    private EditText mDesc;
    private EditText mBirth;
    private Button mOk;

    private Calendar calendar;
    private int year,month,day;

    private User mUser;
    private UserDao mUserDao;
    private UserModelImpl mUserModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getDate();
        init();
        mOk.setOnClickListener(new RegisterActivity.RegisterOnclick());
    }

    private void  getDate(){
        calendar = Calendar.getInstance();
        year=calendar.get(Calendar.YEAR);
        month=calendar.get(Calendar.MONTH);
        day=calendar.get(Calendar.DAY_OF_MONTH);
    }

    private void init() {
        mName = (EditText) findViewById(R.id.user_name);
        mPassword = (EditText) findViewById(R.id.user_pwd);
        mPassword2 = (EditText) findViewById(R.id.user_pwd2);
        mCity = (EditText) findViewById(R.id.user_city);
        mDesc = (EditText) findViewById(R.id.user_desc);
        mOk = (Button) findViewById(R.id.user_ok);
        mBirth = (EditText) findViewById(R.id.user_birth);
        mBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        mBirth.setText(year+"-"+(++month)+"-"+day);
                    }
                };
                DatePickerDialog dialog=new DatePickerDialog(RegisterActivity.this, 0,listener,year,month,day);
                dialog.show();
            }
        });
    }

    private void initData() {

        String myName = mName.getText().toString().trim();
        String myPassword = mPassword.getText().toString().trim();
        String myPassword2 = mPassword2.getText().toString().trim();
        String myCity = mCity.getText().toString().trim();
        String myDesc = mDesc.getText().toString().trim();
        String myBirth = mBirth.getText().toString().trim();

        if (myPassword.length() < 6) {
            ToastUtils.showShort("密码必须大于6位！");
        } else if (!(myPassword.equals(myPassword2))) {
            ToastUtils.showShort("两次密码不一致！");
        } else {
            mUser = new User();
            mUser.setMyName(myName);
            mUser.setMyPassword(myPassword);
            mUser.setMyBirth(myBirth);
            mUser.setMyCity(myCity);
            mUser.setMyDesc(myDesc);

            mUserDao = new UserDao(getApplicationContext());
            if (mUserDao.insertUser(mUser) != (-1)) {
                mUserModel = new UserModelImpl();
                mUserModel.loadUser(mUser);
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                intent.putExtra("user", mUser);
                startActivity(intent);
                ToastUtils.showShort("注册成功！");
                finish();
            } else {
                ToastUtils.showShort("该用户名已注册！");
            }

        }
    }



    private class RegisterOnclick implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            initData();
        }
    }
}


