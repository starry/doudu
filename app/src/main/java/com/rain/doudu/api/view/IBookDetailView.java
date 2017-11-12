package com.rain.doudu.api.view;

/**
 * Created by rain on 2017/4/25.
 */

public interface IBookDetailView {
 void showMessage(String msg);

 void showProgress();

 void hideProgress();

 void updateView(Object result);
}
