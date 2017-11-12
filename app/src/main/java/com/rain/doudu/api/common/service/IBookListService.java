package com.rain.doudu.api.common.service;


import com.rain.doudu.bean.http.douban.BookListResponse;

import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;



public interface IBookListService {
    @GET("book/search")
    Observable<Response<BookListResponse>> getBookList(@Query("q") String q, @Query("tag") String tag, @Query("start") int start, @Query("count") int count, @Query("fields") String fields);



}
