package com.rain.doudu.api.model.impl;

import com.rain.doudu.api.ApiCompleteListener;
import com.rain.doudu.api.common.ServiceFactory;
import com.rain.doudu.api.common.service.UserService;
import com.rain.doudu.api.model.UserModel;
import com.rain.doudu.bean.http.douban.BaseResponse;
import com.rain.doudu.bean.http.douban.BookListResponse;
import com.rain.doudu.bean.http.jiangjianyu.User;
import com.rain.doudu.common.URL;
import com.rain.doudu.utils.common.ToastUtils;

import java.net.UnknownHostException;

import retrofit2.Response;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by rain on 2017/5/30.
 */

public class UserModelImpl implements UserModel {
    @Override
    public void loadUser(User user) {
        UserService userService = ServiceFactory.createService(URL.HOST_URL_DOUDU, UserService.class);
        userService.addUser(user)
                .subscribeOn(Schedulers.io())    //请求在io线程中执行
                .observeOn(AndroidSchedulers.mainThread())//最后在主线程中执行
                .subscribe(new Subscriber<Response<User>>() {


                    @Override
                    public void onCompleted() {


                    }

                    @Override
                    public void onError(Throwable throwable) {

                    }

                    @Override
                    public void onNext(Response<User> userResponse) {

                    }
                });
    }
}

