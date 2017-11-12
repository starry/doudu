package com.rain.doudu.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.rain.doudu.R;
import com.rain.doudu.ui.activity.MainActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends BaseFragment {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    @BindView(R.id.tabLayout)
    TabLayout mTabLayout;

    private List<BaseFragment> fragments;

    public static HomeFragment newInstance() {

        Bundle args = new Bundle();

        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initRootView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_home, container, false);
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
        fragments = new ArrayList<>();
        fragments.add(BookListFragment.newInstance("新书"));
        fragments.add(BookListFragment.newInstance("推荐"));
        fragments.add(BookListFragment.newInstance("热门"));
        fragments.add(CategoryFragment.newInstance());
      

        mViewPager.setAdapter(new MainAdapter(getChildFragmentManager(), fragments));
        mViewPager.setOffscreenPageLimit(5);
        mViewPager.setCurrentItem(2);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setSelectedTabIndicatorColor(getContext().getResources().getColor(R.color.white));
    }

    class MainAdapter extends FragmentStatePagerAdapter {
        private List<BaseFragment> mFragments;
        private final String[] titles;

        public MainAdapter(FragmentManager fm, List<BaseFragment> fragments) {
            super(fm);
            this.titles = getResources().getStringArray(R.array.main_tab_type);
            mFragments = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }

}
