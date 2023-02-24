package People.KingLess

import Card
import Handler
import Hero
import Role

abstract class KinglessHero(role: Role):Hero(role),Handler{
    var nation = "Shu"
    var otherHero: Handler? = null


    override fun setNext(h: Handler){
        otherHero?.setNext(h)
    }

    override fun handle(): Boolean {
        TODO("Not yet implemented")
    }
}

class HuaTuo(role: Role): KinglessHero(role){
    override var name = "Hua Tuo";
    override var HP = 3;
    override var maxHP = 3;
    override var gender = Gender.Male;
    override var cards = mutableListOf<Card>();

}

class LuBu(role: Role): KinglessHero(role){
    override var name = "Lu Bu";
    override var HP = 4;
    override var maxHP = 4;
    override var gender = Gender.Male;
    override var cards = mutableListOf<Card>();

}

class DiaoChan(role: Role): KinglessHero(role){
    override var name = "Diao Chan";
    override var HP = 3;
    override var maxHP = 3;
    override var gender = Gender.Female;
    override var cards = mutableListOf<Card>();

}