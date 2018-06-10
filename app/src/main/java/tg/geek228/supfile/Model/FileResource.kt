package tg.geek228.supfile.Model

class FileResource : Ressource {


    var size:String = ""

    constructor(id: Long, name: String, path: String, size: String) : super(id, name, path) {
        this.size = size
    }

    constructor(name: String, path: String, size: String) : super(name, path) {
        this.size = size
    }

}