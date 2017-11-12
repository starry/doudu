package com.rain.doudu.api.view;

/**
 * Created by rain on 2017/4/25.
 */

public interface IBookListView {
    void showMessage(String msg);

    void showProgress();

    void hideProgress();

    void refreshData(Object result);

    void addData(Object result);
}
