package People.Wu

import Card
import People.Gender
import People.Handler
import People.Hero
import Role

abstract class WuHero(role: Role) : Hero(role), Handler {
    var nation = "Wu"
    var otherHero: Handler? = null


    override fun setNext(h: Handler) {
        otherHero?.setNext(h)
    }

    override fun handle(): Boolean {
        TODO("Not yet implemented")
    }


}

class SunQuan(role: Role) : WuHero(role) {
    override var name = "Sun Quan";
    override var HP = 4;
    override var maxHP = 4;
    override var gender = Gender.Male;
    override var cards = mutableListOf<Card>();

}

class GanNing(role: Role) : WuHero(role) {
    override var name = "Gan Ning";
    override var HP = 4;
    override var maxHP = 4;
    override var gender = Gender.Male;
    override var cards = mutableListOf<Card>();

}

class LuMeng(role: Role) : WuHero(role) {
    override var name = "Lu Meng";
    override var HP = 4;
    override var maxHP = 4;
    override var gender = Gender.Male;
    override var cards = mutableListOf<Card>();

}

class HuangGai(role: Role) : WuHero(role) {
    override var name = "Huang Gai";
    override var HP = 4;
    override var maxHP = 4;
    override var gender = Gender.Male;
    override var cards = mutableListOf<Card>();

}

class ZhouYU(role: Role) : WuHero(role) {
    override var name = "Zhou YU";
    override var HP = 3;
    override var maxHP = 3;
    override var gender = Gender.Male;
    override var cards = mutableListOf<Card>();

    override fun drawPhase(hero: Hero) {
        super.drawPhase(hero)
        println("I am handsome boy, one more draw")
        hero.getCard(Deck.getRadomCard())
    }


}

class LuXun(role: Role) : WuHero(role) {
    override var name = "Lu Xun";
    override var HP = 3;
    override var maxHP = 3;
    override var gender = Gender.Male;
    override var cards = mutableListOf<Card>();

}


class DaQiao(role: Role) : WuHero(role) {
    override var name = "Da Qiao";
    override var HP = 3;
    override var maxHP = 3;
    override var gender = Gender.Female;
    override var cards = mutableListOf<Card>();


}

class SunShangXiang(role: Role) : WuHero(role) {
    override var name = "Sun Shang Xiang";
    override var HP = 3;
    override var maxHP = 3;
    override var gender = Gender.Female;
    override var cards = mutableListOf<Card>();
}