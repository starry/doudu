package com.rain.doudu.api.presenter.impl;

import com.rain.doudu.BaseApplication;
import com.rain.doudu.R;
import com.rain.doudu.api.ApiCompleteListener;
import com.rain.doudu.api.model.IBookListModel;
import com.rain.doudu.api.model.impl.BookListModelImpl;
import com.rain.doudu.api.presenter.IBookListPresenter;
import com.rain.doudu.api.view.IBookListView;
import com.rain.doudu.bean.http.douban.BaseResponse;
import com.rain.doudu.bean.http.douban.BookListResponse;
import com.rain.doudu.utils.common.NetworkUtils;


public class BookListPresenterImpl implements IBookListPresenter, ApiCompleteListener {
    private IBookListView mBookListView;
    private IBookListModel mBookListModel;

    public BookListPresenterImpl(IBookListView view) {
        mBookListView = view;
        mBookListModel = new BookListModelImpl();
    }

    /**
     * 加载数据
     */
    @Override
    public void loadBooks(String q, String tag, int start, int count, String fields) {
        if (!NetworkUtils.isConnected(BaseApplication.getApplication())) {
            mBookListView.showMessage(BaseApplication.getApplication().getString(R.string.poor_network));
            mBookListView.hideProgress();
//            return;
        }
        mBookListView.showProgress();
        mBookListModel.loadBookList(q, tag, start, count, fields, this);
    }

    @Override
    public void cancelLoading() {
        mBookListModel.cancelLoading();
    }

    /**
     * 访问接口成功
     *
     * @param result 返回结果
     */
    @Override
    public void onComplected(Object result) {
        if (result instanceof BookListResponse) {
            int index = ((BookListResponse) result).getStart();
            if (index == 0) {
                mBookListView.refreshData(result);
            } else {
                mBookListView.addData(result);
            }
            mBookListView.hideProgress();
        }
    }

    /**
     * 请求失败
     *
     * @param msg 错误信息
     */
    @Override
    public void onFailed(BaseResponse msg) {
        mBookListView.hideProgress();
        if (msg == null) {
            return;
        }
        mBookListView.showMessage(msg.getMsg());
    }
}
