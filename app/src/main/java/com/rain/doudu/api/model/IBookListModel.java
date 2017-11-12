package com.rain.doudu.api.model;


import com.rain.doudu.api.ApiCompleteListener;


public interface IBookListModel {
    /**
     * 获取图书接口
     */
    void loadBookList(String q, String tag, int start, int count, String fields, ApiCompleteListener listener);

    /**
     * 取消加载数据
     */
    void cancelLoading();
}
