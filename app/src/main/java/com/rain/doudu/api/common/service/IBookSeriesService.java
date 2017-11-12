package com.rain.doudu.api.common.service;



import com.rain.doudu.bean.http.douban.BookSeriesListResponse;

import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;


public interface IBookSeriesService {
    @GET("book/series/{seriesId}/books")
    Observable<Response<BookSeriesListResponse>> getBookSeries(@Path("seriesId") String seriesId, @Query("start") int start, @Query("count") int count, @Query("fields") String fields);

}
