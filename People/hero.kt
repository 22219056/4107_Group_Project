package People

import ANSIColorConsole
import Abandon
import Attack
import AttackCard
import Card
import Deck
import DodgeCard
import PeachCard
import Role
import heros
import mainEventManager
import java.util.ArrayDeque
import javax.swing.text.StyledEditorKit.BoldAction

enum class Gender {
    Male, Female;
}

interface Handler {
    fun setNext(h: Handler)
    fun handle(): Boolean
}

abstract class Hero(role: Role) : Handler {
    abstract var name: String;
    abstract var HP: Int;
    abstract var maxHP: Int;
    abstract var gender: Gender;
    abstract var cards: MutableList<Card>;
    var role: Role = role;
    open var weapons: Card? = null
    open var armor: Card? = null
    var judgmentZone = ArrayDeque<Card>()
    var judgmentFlag:Boolean = false
    var judmentPass:Boolean = true
    var abandonRound: Boolean = false

    var canAttack: Boolean = true;

    //status
    var abandon: Boolean = false;

    //command map
    var commandMap = mutableMapOf(
        "abandon" to Abandon(this),
        "attack" to Attack(this)
    )

    open fun setJudgmentZone(card:Card){
        judgmentZone.push(card)
    }

    open fun getJudgmentZone():Boolean{
        if(!judgmentZone.isEmpty()) {
            return true // need to have judgment
        }

        return this.judgmentFlag
    }

    open fun drawPhase(hero: Hero) {
        hero.getCard(Deck.getRadomCard())
        hero.getCard(Deck.getRadomCard())
    }
    open fun getJudgement():Card{
        return  Deck.getRadomCard()
    }
    //Compulsory
    open fun askHeroPlaceACard(filterList: List<String>? = null): Card {
        var cardList = mutableListOf<Card>();
        cardList = if (filterList != null) {
            filterCardFromCards(filterList);
        } else {
            cards;
        }

        while (true) {
            println("Please place a card");
            this.displayCardFromList(cardList);
            var index = readLine()?.toInt(); //card of index
            if (index !== null && cards.size >= index && index!! > 0) {
                return cards[index - 1];
            }
            println("Not valid input, Please input again.");
            continue;
        }
    }

    //Selection
    open fun askHeroPlaceACardOrNot(filterList: List<String>? = null): Card? {
        var cardList = mutableListOf<Card>();
        cardList = if (filterList != null) {
            filterCardFromCards(filterList);
        } else {
            cards;
        }

        while (true) {
            println("Please place a card");
            this.displayCardFromList(cardList);
            println("0.[cancel place a card]");
            var index = readLine()?.toInt();

            if (index != 0 && cardList.size >= index!!) {
                return cardList[index - 1];
            } else if (index == 0) {
                break;
            }
            println("Not valid input, Please input again.");
            continue;
        }
        return null;
    }

    open fun hasDodgeTypeCard(): Boolean {
        for (card in cards) {
            if (card is DodgeCard || card.name == "Dodge") {
                return true;
            }
        }
        return false;
    }

    open fun hasAttackTypeCard(): Boolean {
        for (card in cards) {
            if (card is AttackCard || card.name == "Attack") {
                return true;
            }
        }
        return false;
    }

    open fun hasPeachTypeCard(): Boolean {
        for (card in cards) {
            if (card is PeachCard || card.name == "Peach") {
                return true;
            }
        }
        return false;
    }

    open fun displayCardFromList(cardList: List<Card>) {
        println("Card List: ");
        for ((index, card) in cardList.withIndex()) {
            println("${index + 1}.[${card.getCardString()}] ");
        }
    }

    open fun filterCardFromCards(filterList: List<String>? = null): MutableList<Card> {
        val filteredCardList = mutableListOf<Card>();
        if (filterList != null) {
            for (card in cards) {

                if (card.name in filterList) {
                    filteredCardList.add(card);
                }
            }
        }
        return filteredCardList;
    }

    open fun displayCards() {
        println("Card List: ");
        for ((index, card) in cards.withIndex()) {
            println("${index + 1}.[${card.getCardString()}] ");
        }
    }

    open fun showCurrentHP() {
        println("$name ${ANSIColorConsole.red("♥")} HP = ${HP}");
    }

    open fun getCard(card: Card) {
        cards.add(card);
    }

    open fun removeCard(card: Card) {
        cards.remove(card);
    }

    open fun showRoleList(heros: List<Hero>, currentHero: String) {
        for ((index, hero) in heros.withIndex()) {
            if (!hero.name.equals(currentHero))
                println("${index + 1}. ${hero.name}")
        }
    }

    open fun PliferHandle(placedCard: Card){
        println("Please select a hero you want to take her/his card");

        //show list of hero
        var availableHeroes = listOf<Hero>();
        for ((index, hero) in heros.withIndex()) {
            if (hero != this) {
                println("${availableHeroes.size}. ${hero.name}");
                availableHeroes += hero;
            }
        }

        //selected by attacker
        var index = readlnOrNull()?.toInt();
        if (index != null) {
            mainEventManager.notifySpecificListener("Plifer", this, availableHeroes[index], placedCard);
        }
    }

    open fun attackEventHandle(placedCard: Card) {
        println("Please select a hero you want to attack");

        //show list of hero
        var availableHeroes = listOf<Hero>();
        for ((index, hero) in heros.withIndex()) {
            if (hero != this) {
                println("${availableHeroes.size}. ${hero.name}");
                availableHeroes += hero;
            }
        }

        //selected by attacker
        var index = readlnOrNull()?.toInt();
        if (index != null) {
            mainEventManager.notifySpecificListener("Attack", this, availableHeroes[index], placedCard);
        }
    }

    open fun attackEvent(placedCard: Card){
        commandMap["attack"]?.execute(cardPlaced = placedCard);
    }
    open fun randomRemoveCard(numOfCard: Int){
        for(i in 0..numOfCard){
            if(cards.size > 0){
                removeCard(cards.random());
            }
        }
    }

    open fun duelHandle(placedCard: Card) {
        println("Please select a hero you want to duel");

        //show list of hero
        var availableHeroes = listOf<Hero>();
        for ((index, hero) in heros.withIndex()) {
            if (hero != this) {
                println("${availableHeroes.size}. ${hero.name}");
                availableHeroes += hero;
            }
        }

        //selected by attacker
        var index = readlnOrNull()?.toInt();
        if (index != null) {
            mainEventManager.notifySpecificListener("Duel", this, availableHeroes[index], placedCard);
        }
    }

    open fun barbariansAssaultHandle(placedCard: Card){
        println("${this.name} use ${placedCard.name}, all hero need to use [Attack] card to dodge the hurt\n")

        var availableHeroes = listOf<Hero>();
        for ((index, hero) in heros.withIndex()) {
            if (hero != this) {
//                println("${availableHeroes.size}. ${hero.name}");
                availableHeroes += hero;

            }
        }
        mainEventManager.notifyListener("barbariansAssault", this,placedCard);


    }

    open fun HailofArrowsHandle(placedCard: Card){
        println("${this.name} use ${placedCard.name}, all hero need to use [Dodge] card to dodge the hurt\n")

        var availableHeroes = listOf<Hero>();
        for ((index, hero) in heros.withIndex()) {
            if (hero != this) {
                availableHeroes += hero;

            }
        }
        mainEventManager.notifyListener("hailofArrowsAssault", this,placedCard);


    }

    open fun acediaEventHandle(placedCard: Card) {
        println("Please select a hero you want to place the Acedia");

        //show list of hero
        var availableHeroes = listOf<Hero>();
        for ((index, hero) in heros.withIndex()) {
            if (hero != this) {
                println("${availableHeroes.size}. ${hero.name}");
                availableHeroes += hero;
            }
        }

        //selected by attacker
        var index = readlnOrNull()?.toInt();
        if (index != null) {
           availableHeroes[index].setJudgmentZone(placedCard)
        }
    }


}

