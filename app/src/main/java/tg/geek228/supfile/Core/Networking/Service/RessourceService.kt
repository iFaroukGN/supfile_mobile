package tg.geek228.supfile.Core.Networking.Service

import okhttp3.MultipartBody
import retrofit2.Call
import tg.geek228.supfile.Core.Networking.WebserviceConstant.APIConstant
import tg.geek228.supfile.Model.Ressource
import tg.geek228.supfile.Model.User
import okhttp3.RequestBody
import retrofit2.http.*
import retrofit2.http.POST
import retrofit2.http.Multipart




interface RessourceService{


    @POST(APIConstant.SEND_FILE_ENDPOINT)
    @Multipart
    //fun sendFile(@Header("Authorization") authorization: String, @Part filePart: MultipartBody.Part, @Part("name") name: RequestBody ) : Call<String>
    fun sendFile(@Header("Authorization") authorization: String, @Part("file\"; filename=\"pp.png\" ") file: RequestBody,@Query("parentFolderId")  parentFolderId:String): Call<String>

}