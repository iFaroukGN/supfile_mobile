package tg.geek228.supfile.Model

import android.provider.ContactsContract

//val is a final like in java only get can't set

class User {
    var id: Long? = null
    var username: String
    var email: String
    var googleEmail: String
    var password: String
    var token: String

    init {
        this.id = 0
        this.email = ""
        this.username = ""
        this.googleEmail = ""
        this.password = ""
        this.token = ""
    }

    constructor(userId: Long,userName: String, userEmail: String, userPassword: String) {
        this.id = userId
        this.username = userName
        this.email = userEmail
        this.password = userPassword
    }

    constructor(userName: String, userEmail: String ,userPassword: String) {
        this.username = userName
        this.email = userEmail
        this.password = userPassword
    }

    constructor(userName: String, userEmail: String, facebookEmail:String, googleEmail:String ,userPassword: String) {
        this.username = userName
        this.email = userEmail
        this.googleEmail = googleEmail
        this.password = userPassword
    }

    constructor(userEmail:String, userPassword: String){
        this.email = userEmail;
        this.password = userPassword;
    }

    override fun toString(): String {
        return "User(id=$id, username='$username', email='$email', googleEmail='$googleEmail', password='$password', token='$token')"
    }


}