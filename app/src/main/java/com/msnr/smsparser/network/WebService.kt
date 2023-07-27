package com.msnr.smsparser.network

import android.text.Editable
import com.google.gson.GsonBuilder
import com.msnr.smsparser.network.pojo.ResponsePojo
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import java.util.concurrent.TimeUnit

class WebService {
    companion object {
        private var webApiInterface: WebServiceInterface? = null
        val client: WebServiceInterface?
            get() {
                if (webApiInterface == null) {
                    val logging = HttpLoggingInterceptor()
                    logging.setLevel(HttpLoggingInterceptor.Level.BODY)
                    val okClient: OkHttpClient = OkHttpClient.Builder().addInterceptor(logging)
                        .connectTimeout(10, TimeUnit.SECONDS).readTimeout(10, TimeUnit.SECONDS)
                        .writeTimeout(10, TimeUnit.SECONDS).build()
                    val gson =
                        GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").setLenient().create()
                    val client: Retrofit =
                        Retrofit.Builder().baseUrl("https://checkurl.phishtank.com/")
                            .addConverterFactory(GsonConverterFactory.create(gson)).client(okClient)
                            .build()
                    webApiInterface = client.create(WebServiceInterface::class.java)
                }
                return webApiInterface
            }
    }

    interface WebServiceInterface {
        @POST("checkurl/")
        @Headers("User-Agent: phishtank")
        @FormUrlEncoded
        fun checkUrl(
            @Field("url") url: String, @Field("format") format: String
        ): Call<ResponsePojo>
    }
}