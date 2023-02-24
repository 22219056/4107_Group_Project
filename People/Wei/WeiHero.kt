package People.Wei

import Card
import Handler
import Hero
import People.Wu.WuHero
import Role

abstract class WeiHero(role: Role):Hero(role),Handler{
    var nation = "Wei"
    var otherHero: Handler? = null


    override fun setNext(h: Handler){
        otherHero?.setNext(h)
    }

    override fun handle(): Boolean {
        TODO("Not yet implemented")
    }

//    override fun handle():Boolean{
//
//        if(alive){
//            if (roleTitle != "Rebel" && numofCards >= 0) { // Successful to use
//                println("${name} spent 1 card to help his/her lord to dodge.")
//                numofCards--
//                return true
//
//            } else if (numofCards == 0) { // No cards to use
//                println("${name} does not have cards.")
//                return otherHero!!.handle()
//
//            } else if (roleTitle == "Rebel") { // rebel not help
//                println("${name} does not want to help.")
//                return otherHero!!.handle()
//            }else {
//                println("No one can help lord to dodge.")
//                return false
//            }
//        } else {
//            if(otherHero == null){
//                println("No one can help lord to dodge.")
//                return false
//            }
//            return otherHero!!.handle()
//        }
//
//    }

}

class CaoCao(role: Role): WuHero(role){
    override var name = "Cao Cao";
    override var HP = 4;
    override var maxHP = 4;
    override var gender = Gender.Male;
    override var cards = mutableListOf<Card>();

}

class SimaYi(role: Role): WuHero(role){
    override var name = "Sima Yi";
    override var HP = 3;
    override var maxHP = 3;
    override var gender = Gender.Male;
    override var cards = mutableListOf<Card>();

}

class GuoJia(role: Role): WuHero(role){
    override var name = "Guo Jia";
    override var HP = 3;
    override var maxHP = 3;
    override var gender = Gender.Male;
    override var cards = mutableListOf<Card>();

}

class ZhenJi(role: Role): WuHero(role){
    override var name = "Zhen Ji";
    override var HP = 3;
    override var maxHP = 3;
    override var gender = Gender.Female;
    override var cards = mutableListOf<Card>();

}

class XiahouDun(role: Role): WuHero(role){
    override var name = "Xia Hou Dun";
    override var HP = 4;
    override var maxHP = 4;
    override var gender = Gender.Male;
    override var cards = mutableListOf<Card>();

}

class ZhangLiao(role: Role): WuHero(role){
    override var name = "Zhang Liao";
    override var HP = 4;
    override var maxHP = 4;
    override var gender = Gender.Male;
    override var cards = mutableListOf<Card>();

}

class XuChu(role: Role): WuHero(role){
    override var name = "Xu Chu";
    override var HP = 4;
    override var maxHP = 4;
    override var gender = Gender.Male;
    override var cards = mutableListOf<Card>();

}