package com.rain.doudu.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.rain.doudu.ui.fragment.BaseFragment;

import java.util.List;

/**
 * Created by rain on 2017/4/25.
 */

public class CategoryDetailAdapter extends FragmentStatePagerAdapter {
    private final String[] titles;
    private final List<BaseFragment> fragments;

    public CategoryDetailAdapter(FragmentManager fm, List<BaseFragment> fragments, String[] mCategory) {
        super(fm);
        titles = mCategory;
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
