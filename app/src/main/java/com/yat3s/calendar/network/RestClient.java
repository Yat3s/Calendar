package com.yat3s.calendar.network;

import com.yat3s.calendar.network.component.ConverterFactory;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

/**
 * Created by Yat3s on 18/05/2017.
 * Email: hawkoyates@gmail.com
 * GitHub: https://github.com/yat3s
 */
public class RestClient {
    private static RestClient sInstance;
    private APIService mAPIService;

    /**
     * The singleton of RestClient.
     * It will be used frequently.
     * @return
     */
    public static RestClient getInstance() {
        if (null == sInstance) {
            sInstance = new RestClient(APIConfig.BASE_URL);
        }
        return sInstance;
    }

    private RestClient(String baseUrl) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(ConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        mAPIService = retrofit.create(APIService.class);
    }

    public APIService getAPIService() {
        return mAPIService;
    }

}
