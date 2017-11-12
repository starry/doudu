package com.rain.doudu.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.rain.doudu.R;
import com.rain.doudu.bean.http.baidu.Person;
import com.rain.doudu.common.URL;
import com.rain.doudu.ui.activity.LoginActivity;
import com.rain.doudu.ui.activity.MainActivity;
import com.rain.doudu.utils.customtabs.GlideCircleTransform;

import java.text.MessageFormat;

import butterknife.BindView;


public class BaiduMessageFragment extends BaseFragment {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.portrait)
    ImageView portrait;
    @BindView(R.id.realname)
    TextView realname;
    @BindView(R.id.username)
    TextView username;
    @BindView(R.id.sex)
    TextView sex;
    @BindView(R.id.birthday)
    TextView birthday;
    @BindView(R.id.marriage)
    TextView marriage;
    @BindView(R.id.figure)
    TextView figure;
    @BindView(R.id.blood)
    TextView blood;
    @BindView(R.id.constellation)
    TextView constellation;
    @BindView(R.id.education)
    TextView education;
    @BindView(R.id.job)
    TextView job;
    @BindView(R.id.trade)
    TextView trade;
    @BindView(R.id.userdetail)
    TextView userdetail;
    @BindView(R.id.exit)
    Button exit;

    public static BaiduMessageFragment newInstance() {
        BaiduMessageFragment fragment = new BaiduMessageFragment();
        return fragment;
    }

    @Override
    protected void initRootView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_baidu_message, container, false);
    }

    @Override
    protected void initEvents() {

    }

    @Override
    protected void initData(boolean isSavedNull) {

        Person mPerson = (Person) getActivity().getIntent().getSerializableExtra("BAIDUER");
        if (mPerson != null) {
            Glide.with(this)
                    .load(URL.BAIDU_LARGE_IMAGE + mPerson.getPortrait())
                    .placeholder(R.drawable.baidu_logo)
                    .transform(new GlideCircleTransform(getContext()))
                    .into(portrait);
            if (mPerson.getUsername() != null) {
                username.setText(MessageFormat.format("{0}{1}", getString(R.string.username), mPerson.getUsername()));
            }
            if (mPerson.getRealname() != null) {
                realname.setText(MessageFormat.format("{0}{1}", getString(R.string.realname), mPerson.getRealname()));
            }
            if (mPerson.getBrithday() != null) {
                birthday.setText(getString(R.string.birthday)+mPerson.getBrithday().toString());
            }
            if (!mPerson.getMarriage().equals("0")) {
                marriage.setText(MessageFormat.format("{0}{1}", getString(R.string.marriage), mPerson.getMarriage()));
            }
            if (!mPerson.getBlood().equals("0")) {
                blood.setText(MessageFormat.format("{0}{1}", getString(R.string.blood), mPerson.getBlood()));
            }
            if (!mPerson.getConstellation().equals("0")) {
                constellation.setText(MessageFormat.format("{0}{1}", getString(R.string.constellation), mPerson.getConstellation()));
            }
            if (!mPerson.getEducation().equals("0")) {
                education.setText(MessageFormat.format("{0}{1}", getString(R.string.education), mPerson.getEducation()));
            }
            if (!mPerson.getJob().equals("0")) {
                job.setText(MessageFormat.format("{0}{1}", getString(R.string.job), mPerson.getJob()));
            }
            if (mPerson.getUserdetail() != null) {
                userdetail.setText(MessageFormat.format("{0}{1}", getString(R.string.userdetail), mPerson.getUserdetail()));
            }
            if (!mPerson.getFigure().equals("0")) {
                figure.setText(MessageFormat.format("{0}{1}", getString(R.string.figure), mPerson.getFigure()));
            }
            if (!mPerson.getTrade().equals("0")) {
                trade.setText(MessageFormat.format("{0}{1}", getString(R.string.trade), mPerson.getTrade()));
            }
            if (mPerson.getSex().equals("1")) {
                sex.setText(MessageFormat.format("{0}男", getString(R.string.sex)));
            }
            if (mPerson.getSex().equals("0")) {
                sex.setText(MessageFormat.format("{0}女", getString(R.string.sex)));
            }

            exit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                    getActivity().finish();

                }
            });
        }

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((MainActivity) getActivity()).setToolbar(mToolbar);
    }
}
