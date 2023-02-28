import People.Hero
import kotlin.random.Random

enum class Suit{
    Spade, Heart, Diamond, Club;
}

enum class Color{
   Black, Red;
}


interface Card {
    val name: String
    val suit: Suit
    val rank: Int
    val color: Color
    val used: Boolean
    
    fun getCardString(): String {
        var suitString = when (suit) {
            Suit.Spade -> "♠"
            Suit.Heart -> "♥"
            Suit.Club -> "♣"
            Suit.Diamond -> "♦"
        }
        if(color == Color.Red)
            suitString = ANSIColorConsole.red(suitString);
        else
            suitString = ANSIColorConsole.black(suitString);

        return "$color $suitString $rank $name";
    }
    fun active(currentHero: Hero, judgement: Card)
}

abstract class BasicCard : Card{
    override val used: Boolean = false;

    override fun active(currentHero: Hero, judgement: Card) {};
}

abstract class TacticsCard : Card{
    override val used: Boolean = false;

    override fun active(currentHero: Hero, judgement: Card) {};
}

class AcediaCard(color: Color, suit: Suit, rank: Int):TacticsCard(){
    override var name = "Acedia";
    override var color = color;
    override var suit = suit;
    override var rank = rank;

    override fun active(targetHero: Hero, judgement: Card){
        if (judgement.suit.equals("♥")){
            targetHero.abandonRound = false
        }else{
            println("judgement card is"+judgement.getCardString())
            targetHero.abandonRound = true
        }

    }
}

class AttackCard(color: Color, suit: Suit, rank: Int): BasicCard (){
    override var name = "Attack";
    override var color = color;
    override var suit = suit;
    override var rank = rank;

     override fun active(targetHero: Hero, judgement: Card){
        if(targetHero.HP > 0){
            targetHero.HP -= 1;
        }
    }
}

class DodgeCard(color: Color, suit: Suit, rank: Int): BasicCard (){
    override var name = "Dodge";
    override var color = color;
    override var suit = suit;
    override var rank = rank;  
}

class PeachCard(color: Color, suit: Suit, rank: Int): BasicCard (){
    override var name = "Peach";
    override var color = color;
    override var suit = suit;
    override var rank = rank;  
}


abstract class Weapons: Card{
    override val used: Boolean = false;

    override fun active(currentHero: Hero, judgement: Card) {};
}


class ZhugeCrossbow(color: Color, suit: Suit, rank: Int): Weapons(){
    override var name = "Zhuge Crossbow";
    override var color = color;
    override var suit = suit;
    override var rank = rank;
}

abstract class Armor: Card{
    override val used: Boolean = false;

    override fun active(currentHero: Hero, judgement: Card) {};
}

class EightTrigrams(color: Color, suit: Suit, rank: Int): Armor(){
    override var name = "Eight Trigrams";
    override var color = color;
    override var suit = suit;
    override var rank = rank;
}




// abstract class TacticsCard:Card{
// }

// abstract class DelayTacticsCard:Card{}

// abstract class EquipmentCard: Card{   
// }

// abstract class ArmorCard:Card{}

// abstract class MountsCard:Card{}

