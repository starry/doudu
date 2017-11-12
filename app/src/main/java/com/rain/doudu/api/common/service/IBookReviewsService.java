package com.rain.doudu.api.common.service;


import com.rain.doudu.bean.http.douban.BookReviewsListResponse;

import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;


public interface IBookReviewsService {
    @GET("book/{bookId}/reviews")
    Observable<Response<BookReviewsListResponse>> getBookReviews(@Path("bookId") String bookId, @Query("start") int start, @Query("count") int count, @Query("fields") String fields);

}
