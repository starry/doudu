package com.rain.doudu.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.rain.doudu.R;
import com.rain.doudu.ui.adapter.CategoryAdapter;
import com.rain.doudu.ui.widget.RecyclerViewDecoration.SpacesItemDecoration;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryFragment extends BaseFragment {


    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    private GridLayoutManager mLayoutManager;
    private CategoryAdapter mCategoryAdapter;

    public static CategoryFragment newInstance() {

        Bundle args = new Bundle();

        CategoryFragment fragment = new CategoryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initRootView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_category, null, false);
    }

    @Override
    protected void initEvents() {
        //添加装饰器
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(20));
        //设置布局管理器
        mLayoutManager = new GridLayoutManager(getActivity(), 3);
        mRecyclerView.setLayoutManager(mLayoutManager);
        //设置adapter
        mCategoryAdapter = new CategoryAdapter();
        mRecyclerView.setAdapter(mCategoryAdapter);
        //设置Item增加、移除动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    protected void initData(boolean isSavedNull) {

    }

}
