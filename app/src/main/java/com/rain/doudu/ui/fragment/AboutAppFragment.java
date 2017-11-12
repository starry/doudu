package com.rain.doudu.ui.fragment;


import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rain.doudu.R;
import com.rain.doudu.ui.activity.MainActivity;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class AboutAppFragment extends BaseFragment {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.version)
    TextView mTextView;

    @Override
    protected void initRootView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       mRootView = inflater.inflate(R.layout.fragment_about_app, container, false);
    }

    @Override
    protected void initEvents() {

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((MainActivity) getActivity()).setToolbar(mToolbar);
    }

    @Override
    protected void initData(boolean isSavedNull) {
        mTextView.setText("版本号：" + getVersion());
    }

    public String getVersion() {
        try {
            PackageManager manager = getContext().getPackageManager();
            PackageInfo info = manager.getPackageInfo(getContext().getPackageName(), 0);
            String version = info.versionName;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static AboutAppFragment newInstance() {
        return new AboutAppFragment();
    }
}
