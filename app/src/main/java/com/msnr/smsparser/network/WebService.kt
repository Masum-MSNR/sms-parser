package com.msnr.smsparser.network

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object WebService {
    private var webApiInterface: WebServiceInterface? = null
    val client: WebServiceInterface?
        get() {
            if (webApiInterface == null) {
                val logging = HttpLoggingInterceptor()
                logging.setLevel(HttpLoggingInterceptor.Level.BODY)
                val okClient: OkHttpClient = OkHttpClient.Builder()
                    .addInterceptor(logging)
                    .connectTimeout(10, TimeUnit.MINUTES)
                    .readTimeout(10, TimeUnit.MINUTES)
                    .writeTimeout(10, TimeUnit.MINUTES)
                    .build()
                val gson = GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                    .setLenient()
                    .create()
                val client: Retrofit = Retrofit.Builder()
                    .baseUrl("https://callfoxcrm.helperfox.in/apis/")
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(okClient)
                    .build()
                webApiInterface = client.create(WebServiceInterface::class.java)
            }
            return webApiInterface
        }

    interface WebServiceInterface {
//        @GET
//        fun getAllMembers(@Url url: String?): Call<MemberListPojo?>?

    }
}