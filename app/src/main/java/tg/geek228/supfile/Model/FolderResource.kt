package tg.geek228.supfile.Model

class FolderResource :Ressource{

    constructor(id: Long, name: String, path: String) : super(id, name, path)

    constructor(name: String, path: String) : super(name, path)

}