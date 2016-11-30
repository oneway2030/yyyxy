package com.yyyxt.www.nohttp;

import com.yolanda.nohttp.rest.Response;

/**
 * <p>接受回调结果.</p>
 * Created in Nov 4, 2015 12:54:50 PM.
 *
 * @author Yan Zhenjie.
 */
public interface HttpListener<T> {

    void onSucceed(int what, Response<T> response);

    void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis);

}
