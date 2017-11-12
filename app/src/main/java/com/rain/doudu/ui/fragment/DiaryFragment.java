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

public class DiaryFragment extends BaseFragment {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    private DiaryListFragment fragment;

    public static DiaryFragment newInstance() {

        Bundle args = new Bundle();

        DiaryFragment fragment = new DiaryFragment();
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
        fragment = new DiaryListFragment();
        FragmentTransaction fragmentTransaction =
                getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment).commit();
    }

}
