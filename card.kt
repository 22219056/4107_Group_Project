import People.Hero
import kotlin.random.Random

enum class Suit {
    Spade, Heart, Diamond, Club;
}

enum class Color {
    Black, Red;
}


interface Card {
    val name: String
    val suit: Suit
    var rank: Int
    val color: Color
    val used: Boolean

    fun getCardString(): String {
        var suitString = when (suit) {
            Suit.Spade -> "♠"
            Suit.Heart -> "♥"
            Suit.Club -> "♣"
            Suit.Diamond -> "♦"
        }
        if (color == Color.Red)
            suitString = ANSIColorConsole.red(suitString);
        else
            suitString = ANSIColorConsole.black(suitString);

        return "$color $suitString $rank $name";
    }

    fun active(currentHero: Hero, judgement: Card? = null, targetHero: Hero? = null)
}

abstract class BasicCard : Card {
    override val used: Boolean = false;

    override fun active(currentHero: Hero, judgement: Card?, targetHero: Hero?) {};
}

abstract class Mounts : Card {
    override val used: Boolean = false;

    override fun active(currentHero: Hero, judgement: Card?, targetHero: Hero?) {};
}

abstract class TacticsCard : Card {
    override val used: Boolean = false;

    override fun active(currentHero: Hero, judgement: Card?, targetHero: Hero?) {};
}

class AcediaCard(color: Color, suit: Suit, rank: Int) : TacticsCard() {
    override var name = "Acedia";
    override var color = color;
    override var suit = suit;
    override var rank = rank;

    override fun active(currentHero: Hero, judgement: Card?, targetHero: Hero?) {
        if(judgement != null){
            println("judenment card: "+this.name+"\njudgement card is" + judgement.getCardString())
            if (judgement.suit.equals(Suit.Heart)) {
                currentHero.abandonRound = false
            } else {
//                println("judgement card is" + judgement.getCardString())
                currentHero.abandonRound = true
            }
        }
    }
}

class lightningBolt(color: Color, suit: Suit, rank: Int) : TacticsCard() {
    override var name = "Lightning Bolt";
    override var color = color;
    override var suit = suit;
    override var rank = rank;

    override fun active(currentHero: Hero, judgement: Card?, targetHero: Hero?) {
        if(judgement != null){
            println("judenment card: "+this.name+"\njudgement card is" + judgement.getCardString())

            if (judgement.suit.equals(Suit.Spade) && (judgement.rank <= 9 && judgement.rank >= 2)) {
                println("bingo")
                currentHero.HP -= 3
                if(currentHero.HP<=0){
                    currentHero.alive = false
                    println("${currentHero.name} is dead by lightningBolt")
//                    heros.remove(currentHero)
                }

            } else {
                if(targetHero?.getJudgmentZone()?.equals(this is lightningBolt) == true)  {
                    println("Next hero already have a lightningBolt keep in your judgement zone\n")
                }else{
                    targetHero?.judgmentZone?.push(this)
                }
            }

//                currentHero.commandMap["abandon"]?.execute();

        }

    }
}

class RedHare(color: Color, suit: Suit, rank: Int) : Mounts() {
    override var name = "Red Hare";
    override var color = color;
    override var suit = suit;
    override var rank = rank;

}
class DaYuan(color: Color, suit: Suit, rank: Int) : Mounts() {
    override var name = "Da Yuan";
    override var color = color;
    override var suit = suit;
    override var rank = rank;

}
class HuaLiu (color: Color, suit: Suit, rank: Int) : Mounts() {
    override var name = "Hua Liu";
    override var color = color;
    override var suit = suit;
    override var rank = rank;

}

class BurnBridges(color: Color, suit: Suit, rank: Int) : TacticsCard() {
    override var name = "Burn Bridges";
    override var color = color;
    override var suit = suit;
    override var rank = rank;


}

class Plifer(color: Color, suit: Suit, rank: Int) : TacticsCard() {
    override var name = "Plifer";
    override var color = color;
    override var suit = suit;
    override var rank = rank;

}

class OathOfPeachGarden(color: Color, suit: Suit, rank: Int) : TacticsCard() {
    override var name = "Oath Of Peach Garden";
    override var color = color;
    override var suit = suit;
    override var rank = rank;

    override fun active(currentHero: Hero, judgement: Card?, targetHero: Hero?) {
        mainEventManager.notifyAllHero("oathOfPeachGarden",this);
    }
}


class BarbariansAssault(color: Color, suit: Suit, rank: Int) : TacticsCard() {
    override var name = "Barbarians Assault";
    override var color = color;
    override var suit = suit;
    override var rank = rank;


}

class HailofArrows(color: Color, suit: Suit, rank: Int) : TacticsCard() {
    override var name = "Hail of Arrows";
    override var color = color;
    override var suit = suit;
    override var rank = rank;


}

class SleightofHand(color: Color, suit: Suit, rank: Int) : TacticsCard() {
    override var name = "Sleight of Hand";
    override var color = color;
    override var suit = suit;
    override var rank = rank;


}

class Duel(color: Color, suit: Suit, rank: Int) : TacticsCard() {
    override var name = "Duress";
    override var color = color;
    override var suit = suit;
    override var rank = rank;

}

class AttackCard(color: Color, suit: Suit, rank: Int) : BasicCard() {
    override var name = "Attack";
    override var color = color;
    override var suit = suit;
    override var rank = rank;

    override fun active(currentHero: Hero, judgement: Card?, targetHero: Hero?) {
        if (currentHero.HP > 0) {
            currentHero.HP -= 1;
        }
    }
}

class DodgeCard(color: Color, suit: Suit, rank: Int) : BasicCard() {
    override var name = "Dodge";
    override var color = color;
    override var suit = suit;
    override var rank = rank;
}

class PeachCard(color: Color, suit: Suit, rank: Int) : BasicCard() {
    override var name = "Peach";
    override var color = color;
    override var suit = suit;
    override var rank = rank;
}


abstract class Weapons : Card {
    override val used: Boolean = false;

    override fun active(currentHero: Hero, judgement: Card?, targetHero: Hero?) {};
}


class ZhugeCrossbow(color: Color, suit: Suit, rank: Int) : Weapons() {
    override var name = "Zhuge Crossbow";
    override var color = color;
    override var suit = suit;
    override var rank = rank;
}


class KirinBow(color: Color, suit: Suit, rank: Int) : Weapons() {
    override var name = "Kirin Bow";
    override var color = color;
    override var suit = suit;
    override var rank = rank;
}


class HeavenHalberd(color: Color, suit: Suit, rank: Int) : Weapons() {
    override var name = "Heaven Halberd";
    override var color = color;
    override var suit = suit;
    override var rank = rank;
}

class AzureDragonCrescentBlade(color: Color, suit: Suit, rank: Int):Weapons(){
    override var name = "Azure Dragon Crescent Blade";
    override var color = color;
    override var suit = suit;
    override var rank = rank;

    override fun active(currentHero: Hero, judgement: Card?, targetHero: Hero?) {
        if(currentHero.hasAttackTypeCard() && targetHero != null){
            println("Active effect of Azure Dragon Crescent Blade weapon, can attack again");
            mainEventManager.notifySpecificListener("Attack",currentHero,targetHero,this);
        }
    }
}

class TwinSwords(color: Color, suit: Suit, rank: Int):Weapons(){
    override var name = "Twin Swords";
    override var color = color;
    override var suit = suit;
    override var rank = rank;

    override fun active(currentHero: Hero, judgement: Card?, targetHero: Hero?) {
        if(targetHero != null){
            if(currentHero.gender != targetHero.gender){
                println("Active Twin Swords Weapon effect")
                var choice = (0..1).random();
                if(choice == 0){
                    //discard a card
                    println("${targetHero.name} choose discard a card");
                    targetHero.randomRemoveCard(1);
                }else{
                    // let current hero draw a card
                    println("${currentHero.name} draw a card");
                    currentHero.getCard(Deck.getRadomCard());
                }
            }
        }
    }
}

class RockCleavingAxe(color: Color, suit: Suit, rank: Int):Weapons(){
    override var name = "Rock Cleaving Axe"
    override var color = color;
    override var suit = suit;
    override var rank = rank;
}

class FrostBlade(color: Color, suit: Suit, rank: Int):Weapons(){
    override var name = "Frost Blade";
    override var color = color;
    override var suit = suit;
    override var rank = rank;

    override fun active(currentHero: Hero, judgement: Card?, targetHero: Hero?){
        //choose 2 cards in target hero’s hand or equipment zone and discard them.
        println("Actived Frost Blade's effect, discard 2 cards in target hero's hand");
        targetHero?.randomRemoveCard(2);
    }
}
abstract class Armor : Card {
    override val used: Boolean = false;

    override fun active(currentHero: Hero, judgement: Card?, targetHero: Hero?) {};
}

class EightTrigrams(color: Color, suit: Suit, rank: Int) : Armor() {
    override var name = "Eight Trigrams";
    override var color = color;
    override var suit = suit;
    override var rank = rank;
}




