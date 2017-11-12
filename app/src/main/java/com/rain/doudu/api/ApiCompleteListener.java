package com.rain.doudu.api;

import com.rain.doudu.bean.http.douban.BaseResponse;

/**
 * Created by rain on 2017/4/25.
 */

public interface ApiCompleteListener {
    void onComplected(Object result);

    void onFailed(BaseResponse msg);
}
