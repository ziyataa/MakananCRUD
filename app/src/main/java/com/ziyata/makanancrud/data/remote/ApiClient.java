package com.ziyata.makanancrud.data.remote;

import com.ziyata.makanancrud.utils.Constant;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private  static Retrofit retrofit = null;
    public static Retrofit getClient(){
        retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }
}
