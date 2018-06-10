package tg.geek228.supfile.Core.Networking.Service


import retrofit2.Call;
import retrofit2.http.*
import tg.geek228.supfile.Core.Networking.WebserviceConstant.APIConstant
import tg.geek228.supfile.Model.User

interface UserService {


    @POST(APIConstant.LOGIN_ENDPOINT)
    @Headers(
            "Accept: application/json",
            "Content-type:application/json; charset=utf-8"
    )
    fun login( @Body user:User) : Call<User>


    //log the user out
    @GET(APIConstant.LOGOUT_ENDPOINT)
    @Headers(
            "Accept: application/json",
            "Content-type:application/json"
    )
    fun logout(): Call<String>



    //create users
    @POST(APIConstant.USERS_ENDPOINT)
    @Headers(
            "Accept: application/json",
            "Content-type:application/json"
    )
    fun registrationWithEmail( @Body user: User) : Call<User>

}