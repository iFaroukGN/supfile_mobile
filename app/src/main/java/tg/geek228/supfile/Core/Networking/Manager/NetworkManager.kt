package tg.geek228.supfile.Core.Networking.Manager

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import tg.geek228.supfile.Core.Networking.Service.UserService
import tg.geek228.supfile.Core.Networking.WebserviceConstant.APIConstant

class NetworkManager {

    val retrofit = Retrofit.Builder()
            .baseUrl(APIConstant.API_ENDPOINT)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    val service = retrofit.create(UserService::class.java);

}