package tg.geek228.supfile.Model

open class Ressource {

    val id:Long? = null

    var name:String

    var path:String


    init {
        this.name = ""
        this.path = ""
    }

    constructor(){

    }

    constructor(id:Long, name: String, path: String){
        this.name = name
        this.path = path
    }

    constructor(name: String, second: String) {
        this.name = name
        this.path = path
    }
}