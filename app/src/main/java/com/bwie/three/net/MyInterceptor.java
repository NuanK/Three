package com.bwie.three.net;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


public class MyInterceptor  implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        String s = request.url().url().toString();
        String url =  s+"&source=android";
        Request request1 = request.newBuilder().url(url).build();
        Response response = chain.proceed(request1);
        return response;
    }
}
