package com.rain.doudu.api.model.impl;

import com.rain.doudu.api.ApiCompleteListener;
import com.rain.doudu.api.common.ServiceFactory;
import com.rain.doudu.api.common.service.IBookReviewsService;
import com.rain.doudu.api.common.service.IBookSeriesService;
import com.rain.doudu.api.model.IBookDetailModel;
import com.rain.doudu.bean.http.douban.BaseResponse;
import com.rain.doudu.bean.http.douban.BookReviewsListResponse;
import com.rain.doudu.bean.http.douban.BookSeriesListResponse;
import com.rain.doudu.common.URL;

import java.net.UnknownHostException;

import retrofit2.Response;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class BookDetailModelImpl implements IBookDetailModel {

    @Override
    public void loadReviewsList(String bookId, int start, int count, String fields, final ApiCompleteListener listener) {
        IBookReviewsService iBookReviewsService = ServiceFactory.createService(URL.HOST_URL_DOUBAN, IBookReviewsService.class);
        iBookReviewsService.getBookReviews(bookId, start, count, fields)
                .subscribeOn(Schedulers.io())    //请求在io线程中执行
                .observeOn(AndroidSchedulers.mainThread())//最后在主线程中执行
                .subscribe(new Subscriber<Response<BookReviewsListResponse>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof UnknownHostException) {
                            listener.onFailed(null);
                            return;
                        }
                        listener.onFailed(new BaseResponse(404, e.getMessage()));
                    }

                    @Override
                    public void onNext(Response<BookReviewsListResponse> bookReviewsResponse) {
                        if (bookReviewsResponse.isSuccessful()) {
                            listener.onComplected(bookReviewsResponse.body());
                        } else {
                            listener.onFailed(new BaseResponse(bookReviewsResponse.code(), bookReviewsResponse.message()));
                        }

                    }
                });
    }

    @Override
    public void loadSeriesList(String SeriesId, int start, int count, String fields, final ApiCompleteListener listener) {
        IBookSeriesService iBookSeriesService = ServiceFactory.createService(URL.HOST_URL_DOUBAN, IBookSeriesService.class);
        iBookSeriesService.getBookSeries(SeriesId, start, count, fields)
                .subscribeOn(Schedulers.newThread())    //请求在新的线程中执行
                .observeOn(AndroidSchedulers.mainThread())//最后在主线程中执行
                .subscribe(new Subscriber<Response<BookSeriesListResponse>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof UnknownHostException) {
                            listener.onFailed(null);
                            return;
                        }
                        listener.onFailed(new BaseResponse(404, e.getMessage()));
                    }

                    @Override
                    public void onNext(Response<BookSeriesListResponse> bookSeriesResponse) {
                        if (bookSeriesResponse.isSuccessful()) {
                            listener.onComplected(bookSeriesResponse.body());
                        } else {
                            listener.onFailed(new BaseResponse(bookSeriesResponse.code(), bookSeriesResponse.message()));
                        }

                    }
                });
    }

    @Override
    public void cancelLoading() {

    }
}
