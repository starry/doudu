package com.rain.doudu.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.rain.doudu.R;
import com.rain.doudu.ui.activity.MainActivity;

import butterknife.BindView;

/**
 * Created by rain on 2017/4/27.
 */

public class MyReviewFragment  extends BaseFragment {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;



    private MyReviewListFragment fragment;

    public static MyReviewFragment newInstance() {

        Bundle args = new Bundle();
        MyReviewFragment fragment = new MyReviewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initRootView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_diary, container, false);
    }

    @Override
    protected void initEvents() {

    }

    @Override
    protected void initData(boolean isSavedNull) {
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
        ((MainActivity) getActivity()).setToolbar(mToolbar);

    }

    private void init() {
       fragment = new MyReviewListFragment();
        FragmentTransaction fragmentTransaction =
                getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container,fragment).commit();

    }
}
