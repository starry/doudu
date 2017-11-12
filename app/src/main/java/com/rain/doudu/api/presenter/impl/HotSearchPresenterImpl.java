package com.rain.doudu.api.presenter.impl;

import com.rain.doudu.api.ApiCompleteListener;
import com.rain.doudu.api.model.IHotSearchModel;
import com.rain.doudu.api.model.impl.HotSearchModelImpl;
import com.rain.doudu.api.presenter.IHotSearchPresenter;
import com.rain.doudu.api.view.IHotSearchView;
import com.rain.doudu.bean.http.douban.BaseResponse;


public class HotSearchPresenterImpl implements IHotSearchPresenter, ApiCompleteListener {
    private IHotSearchView mHotSearchView;
    private IHotSearchModel mHotSearchModel;

    public HotSearchPresenterImpl(IHotSearchView view) {
        mHotSearchView = view;
        mHotSearchModel = new HotSearchModelImpl();
    }

    @Override
    public void loadHotSearch(int page) {

    }

    @Override
    public void cancelLoading() {

    }

    @Override
    public void onComplected(Object result) {

    }

    @Override
    public void onFailed(BaseResponse msg) {

    }
}
