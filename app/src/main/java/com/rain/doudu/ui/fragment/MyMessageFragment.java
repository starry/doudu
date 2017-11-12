package com.rain.doudu.ui.fragment;

import android.content.DialogInterface;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.rain.doudu.R;
import com.rain.doudu.api.common.Dao.UserDao;
import com.rain.doudu.bean.http.jiangjianyu.User;
import com.rain.doudu.ui.activity.MainActivity;
import com.rain.doudu.utils.common.ToastUtils;

import butterknife.BindView;

/**
 * Created by rain on 2017/4/27.
 */

public class MyMessageFragment extends BaseFragment {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.person_name)
    EditText mNameEditText;
    @BindView(R.id.person_birth)
    EditText mBirthEditText;
    @BindView(R.id.person_city)
    EditText mCityEditText;
    @BindView(R.id.person_desc)
    EditText mDescEditText;
    @BindView(R.id.person_modify)
    Button mMButton;
    @BindView(R.id.person_del)
    Button mDButton;
    @BindView(R.id.person_update)
    Button mUButton;
    private static final String LOGIN_USER ="LOGIN_USER" ;

    private User mUser;
    private UserDao mUserDao;



    @Override
    protected void initRootView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mRootView = inflater.inflate(R.layout.fragment_my_information, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((MainActivity) getActivity()).setToolbar(mToolbar);
    }
    @Override
    protected void initEvents() {

    }

    @Override
    protected void initData(boolean isSavedNull) {

        mUButton.setVisibility(View.GONE);

        mUser = (User) getActivity().getIntent().getSerializableExtra(LOGIN_USER);
        mNameEditText.setText(mUser.getMyName());
        mNameEditText.setEnabled(false);
        mBirthEditText.setText(mUser.getMyBirth());
        mBirthEditText.setEnabled(false);
        mCityEditText.setText(mUser.getMyCity());
        mCityEditText.setEnabled(false);
        mDescEditText.setText(mUser.getMyDesc());
        mDescEditText.setEnabled(false);


        mMButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modify();
            }
        });

        mUButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update();
            }
        });

        mDButton.setOnClickListener(v -> exit());

    }

    private void modifyImage() {

    }

    private void update() {
        mUser.setMyName( mNameEditText.getText().toString().trim());
        mUser.setMyCity(mCityEditText.getText().toString().trim());
        mUser.setMyDesc(mDescEditText.getText().toString().trim());
        mUserDao = new UserDao(getContext());
        mUserDao.updateUser(mUser);
        ToastUtils.showShort("修改成功");
        mUButton.setVisibility(View.GONE);
        mMButton.setVisibility(View.VISIBLE);
        mNameEditText.setEnabled(false);
        mCityEditText.setEnabled(false);
        mDescEditText.setEnabled(false);
        mBirthEditText.setEnabled(false);

    }

    private void modify() {
        mMButton.setVisibility(View.GONE);
        mUButton.setVisibility(View.VISIBLE);
        mNameEditText.setEnabled(true);
        mCityEditText.setEnabled(true);
        mDescEditText.setEnabled(true);
        mBirthEditText.setEnabled(true);

    }

    private void exit() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("确认注销并退出吗？");
        builder.setTitle("提示");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mUserDao = new UserDao(getContext());
                mUserDao.deleteUser(mUser);
                getActivity().finish();
                dialog.dismiss();

            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    public static MyMessageFragment newInstance() {
        return new MyMessageFragment();
    }
}

