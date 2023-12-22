package com.capstone.fresheats.network

import com.capstone.fresheats.BuildConfig
import com.capstone.fresheats.Preferences
import com.capstone.fresheats.utils.Helpers
import com.chuckerteam.chucker.api.ChuckerInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiConfig {

    private var client: Retrofit? = null
    private var endpoint: ApiService? = null

    companion object {
        private var mInstance: ApiConfig = ApiConfig()

        @Synchronized
        fun getInstance(): ApiConfig {
            return mInstance
        }
    }

    init {
        buildRetrofitClient()
    }

    fun getApi(): ApiService? {
        if (endpoint == null) {
            endpoint = client!!.create(ApiService::class.java)
        }
        return endpoint
    }

    private fun buildRetrofitClient() {
        val token = Preferences.getApp().getToken()
        buildRetrofitClient(token)
    }

    fun buildRetrofitClient(token: String?) {
        val builder = OkHttpClient.Builder()
        builder.connectTimeout(2, TimeUnit.MINUTES)
        builder.readTimeout(2, TimeUnit.MINUTES)

        if (BuildConfig.DEBUG) {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(interceptor)
            builder.addInterceptor(ChuckerInterceptor(Preferences.getApp()))
        }

        if (token != null) {
            builder.addInterceptor(getInterceptorWithHeader("Authorization", "Bearer $token"))
        }

        val okHttpClient = builder.build()

        client = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL + "api/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(Helpers.getDefaultGson()))
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()

        endpoint = null
    }

    private fun getInterceptorWithHeader(headerName: String, headerValue: String): Interceptor {
        val header = HashMap<String, String>()
        header[headerName] = headerValue
        return getInterceptorWithHeader(header)
    }

    private fun getInterceptorWithHeader(headers: Map<String, String>): Interceptor {
        return Interceptor {
            val original = it.request()
            val builder = original.newBuilder()
            for ((key, value) in headers) {
                builder.addHeader(key, value)
            }
            builder.method(original.method, original.body)
            it.proceed(builder.build())
        }
    }
}
