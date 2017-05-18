package com.yat3s.calendar.network.component;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yat3s.calendar.BuildConfig;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by Yat3s on 18/05/2017.
 * Email: hawkoyates@gmail.com
 * GitHub: https://github.com/yat3s
 */
public class ResponseConverter<T> implements Converter<ResponseBody, T> {
    private static final String TAG = "Network";

    private final Gson mGson;
    private final Type mType;

    public ResponseConverter(Gson gson, Type type) {
        mGson = gson;
        mType = type;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        String responseString = value.string();
        if (BuildConfig.DEBUG) {
            Log.d(TAG, "Response --> " + responseString);
        }
        return convertResponseToEntity(responseString);
    }

    public T convertResponseToEntity(String response) {
        TypeToken typeToken = TypeToken.get(mType);
        if (typeToken.getRawType() == String.class || TextUtils.isEmpty(response)) {
            return (T) response;
        } else {
            return mGson.fromJson(response, mType);
        }
    }
}
