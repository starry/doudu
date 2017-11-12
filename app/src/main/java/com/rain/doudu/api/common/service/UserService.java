package com.rain.doudu.api.common.service;

import com.rain.doudu.bean.http.jiangjianyu.User;



import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by rain on 2017/5/30.
 */

public interface UserService {
    @POST("add")
    Observable<Response<User>> addUser(@Body User user);

}
