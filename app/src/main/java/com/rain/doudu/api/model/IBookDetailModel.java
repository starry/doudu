package com.rain.doudu.api.model;


import com.rain.doudu.api.ApiCompleteListener;


public interface IBookDetailModel {
    /**
     * 获取图书评论
     */
    void loadReviewsList(String bookId, int start, int count, String fields, ApiCompleteListener listener);

    /**
     * 获取推荐丛书
     */
    void loadSeriesList(String SeriesId, int start, int count, String fields, ApiCompleteListener listener);

    /**
     * 取消加载数据
     */
    void cancelLoading();
}
