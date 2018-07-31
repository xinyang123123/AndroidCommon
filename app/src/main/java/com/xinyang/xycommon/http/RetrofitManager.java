package com.xinyang.xycommon.http;

import com.xinyang.xycommon.App;
import com.xinyang.xycommon.BuildConfig;
import com.xinyang.xycommon.constant.Constant;
import com.xinyang.xycommon.http.cookies.CookiesManager;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import io.rx_cache2.internal.RxCache;
import io.victoralbertos.jolyglot.GsonSpeaker;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManager {

    private static long CONNECT_TIMEOUT = 60L;
    private static long READ_TIMEOUT = 60L;
    private static long WRITE_TIMEOUT = 60L;

    private static OkHttpClient mOkHttpClient;

    /**
     * 获取OkHttpClient实例
     *
     * @return
     */
    private static OkHttpClient getOkHttpClient() {
        if (mOkHttpClient == null) {
            synchronized (RetrofitManager.class) {
                if (mOkHttpClient == null) {
                    mOkHttpClient = new OkHttpClient.Builder()
                            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                            .addInterceptor(getLogInterceptor())
                            .cookieJar(new CookiesManager())
                            .build();
                }
            }
        }
        return mOkHttpClient;
    }

    /**
     * 获取日志拦截器
     *
     * @return okHttp自带的日志拦截器
     */
    private static HttpLoggingInterceptor getLogInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        if (BuildConfig.DEBUG) {
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        } else {
            interceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
        }
        return interceptor;
    }

    private static Interceptor getHeaderInterceptor(final HashMap<String, String> headerMap) {
        Interceptor interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request.Builder request = chain.request().newBuilder();
                for (String key : headerMap.keySet()) {
                    request.addHeader(key, headerMap.get(key));
                }
                return chain.proceed(request.build());
            }
        };
        return interceptor;
    }


    /**
     * 获取Service
     *
     * @param clazz
     * @return
     */
    public static <T> T create(Class<T> clazz) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.REQUEST_URL)
                .client(getOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit.create(clazz);
    }

    /**
     * 创建缓存代理对象
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T createCache(Class<T> clazz) {
        //获取缓存目录
        File cacheFile = new File(App.getInstance().getCacheDir(), "HttpCache");
        if (!cacheFile.exists()) {
            cacheFile.mkdir();
        }
        cacheFile.setReadable(true);
        cacheFile.setWritable(true);
        return new RxCache.Builder()
                // 缓存目录,转换方式
                .persistence(cacheFile, new GsonSpeaker())
                .using(clazz);
    }


}
