enum class Gender{
    Male, Female;
}

abstract class Hero(role: Role){
    abstract var name: String;
    abstract var HP: Int;
    abstract var maxHP: Int;
    abstract var gender: Gender;
    abstract var cards: MutableList<Card>;
    var role: Role = role;

    fun startPhase(){}
}

class LiuBei(role: Role) : Hero(role) {
    override var name = "Liu Bei";
    override var HP = 4;
    override var maxHP = 4;
    override var gender = Gender.Male;
    override var cards = mutableListOf<Card>();
}

class GuanYu(role: Role): Hero(role){
    override var name = "Guan Yu";
    override var HP = 4;
    override var maxHP = 4;
    override var gender = Gender.Male;
    override var cards = mutableListOf<Card>();
}