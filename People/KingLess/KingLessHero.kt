package People.KingLess

import Card
import People.Handler
import People.Gender
import People.Hero
import Role

abstract class KinglessHero(role: Role): Hero(role),Handler{
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

    fun Eclipse(){
        println("At the end of turn, I may draw a card")
        var card1 = Deck.getRadomCard();
        var card2 = Deck.getRadomCard();
        if(cards.size == 0){
            println("As i have no cards in my hand so i can draw two cards");
            getCard(card1);
            getCard(card2);
        }else{
            getCard(card1);
        }

    }
}