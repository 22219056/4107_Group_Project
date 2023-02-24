package People.Shu

import Card
import People.Handler
import People.Gender
import People.Hero
import Role

abstract class ShuHero(role: Role): Hero(role),Handler{
    var nation = "Shu"
    var otherHero: Handler? = null


    override fun setNext(h: Handler){
        otherHero?.setNext(h)
    }

    override fun handle(): Boolean {
        TODO("Not yet implemented")
    }
}

class LiuBei(role: Role) : ShuHero(role) {
    override var name = "Liu Bei";
    override var HP = 4;
    override var maxHP = 4;
    override var gender = Gender.Male;
    override var cards = mutableListOf<Card>();

}

class GuanYu(role: Role): ShuHero(role){
    override var name = "Guan Yu";
    override var HP = 4;
    override var maxHP = 4;
    override var gender = Gender.Male;
    override var cards = mutableListOf<Card>();

}

class ZhangFei(role: Role): ShuHero(role){
    override var name = "Zhang Fei";
    override var HP = 4;
    override var maxHP = 4;
    override var gender = Gender.Male;
    override var cards = mutableListOf<Card>();

}

class ZhugeLiang(role: Role): ShuHero(role){
    override var name = "Zhuge Liang";
    override var HP = 3;
    override var maxHP = 3;
    override var gender = Gender.Male;
    override var cards = mutableListOf<Card>();

}

class MaChao(role: Role): ShuHero(role){
    override var name = "Ma Chao";
    override var HP = 4;
    override var maxHP = 4;
    override var gender = Gender.Male;
    override var cards = mutableListOf<Card>();

}

class HuangYueYing(role: Role): ShuHero(role){
    override var name = "Huang Yue Ying";
    override var HP = 3;
    override var maxHP = 3;
    override var gender = Gender.Female;
    override var cards = mutableListOf<Card>();

}

