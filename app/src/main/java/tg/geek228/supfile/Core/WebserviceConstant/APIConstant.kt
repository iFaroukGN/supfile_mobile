package tg.geek228.supfile.Core.Networking.WebserviceConstant

class APIConstant {

    companion object{

        /* to create a public constant that will be use in other class .
        if it's create with val it can only be use in the current class */

        const val API_ENDPOINT:String  = "http://10.0.2.2:8081"
        const val LOGIN_ENDPOINT:String = "/auth/login/default"
        const val USERS_ENDPOINT:String = "/users"
        const  val LOGOUT_ENDPOINT:String = " /auth/logout"
    }

}