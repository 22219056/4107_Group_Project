interface Role{
    var name: String;
}
class Emperor: Role{
    override var name: String = "Emperor";
}
class Rebels: Role{
    override var name: String = "Rebels";
}
class Loyalist: Role{
    override var name: String = "Loyalist";
}
class Traitor: Role{
    override var name: String = "Traitor";
}