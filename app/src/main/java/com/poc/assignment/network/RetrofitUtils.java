package com.poc.assignment.network;

import android.text.TextUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.poc.assignment.model.AppConstants;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitUtils {
    private static final String TAG = "RetrofitUtils";
    private static RetrofitUtils sInstance;
    public static final long LOW_PRIORITY_TIMEOUT = 30 * 1000; // 30 Seconds
    public static final long MEDIUM_PRIORITY_TIMEOUT = 60 * 1000; // 60 Seconds
    public static final long HIGH_PRIORITY_TIMEOUT = 120 * 1000; // 120 Seconds
    private long mRequestTimeOut = LOW_PRIORITY_TIMEOUT;


    public RetrofitUtils() {

    }

    public static RetrofitUtils getInstance() {
        if (sInstance == null) {
            sInstance = new RetrofitUtils();
        }
        return sInstance;
    }

//    public void setRequestTimeOut(long mConnectTimeOut) {
//        this.mRequestTimeOut = mConnectTimeOut;
//    }



    public APIInterface getService() {
        String baseUrl = AppConstants.baseURL;
        OkHttpClient okHttpClient = getOkHttpClient();
        if (okHttpClient == null || TextUtils.isEmpty(baseUrl)) {
            return null;
        }

        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(baseUrl);
//        builder.addCallAdapterFactory(RxJava2CallAdapterFactory.create());

        GsonBuilder bulder= new GsonBuilder() ;
        bulder.serializeNulls();
        bulder.setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        Gson gson = bulder.create();
        builder.addConverterFactory(GsonConverterFactory.create(gson));

        builder.client(okHttpClient);

        return builder.build().create(APIInterface.class);
    }

    private OkHttpClient getOkHttpClient() {
        OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder();
        httpBuilder.connectTimeout(mRequestTimeOut, TimeUnit.MILLISECONDS);
        httpBuilder.readTimeout(mRequestTimeOut, TimeUnit.MILLISECONDS);
        httpBuilder.writeTimeout(mRequestTimeOut, TimeUnit.MILLISECONDS);
        return httpBuilder.build();
    }




}