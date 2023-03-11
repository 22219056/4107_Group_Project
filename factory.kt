import People.Hero
import People.Shu.*
import People.Wu.*
import People.Wei.*

class HeroFactory {
    var roles = listOf<Role>(Emperor(), Rebels(), Loyalist(), Traitor());
    lateinit var listOfHero: List<Hero>;

    fun initHeros() {
        listOfHero = listOf<Hero>(
                ZhouYU(roles.random()),
                GuanYu(roles.random())
        );

        //add event listener
        for (hero in listOfHero) {
            mainEventManager.addListener(Listener(hero));
        }
    }
}


class CardFactory(var heros: List<Hero>) {

    fun dealingCard() {
        for (hero in heros) {
            for (i in 1..4)
                hero.getCard(Deck.getRadomCard());

        }
    }

}

class Deck {
    companion object {
        fun getRandomSuit(): Suit {
            return Suit.values().random();
        }

        fun getRandomRank(start: Int = 1, end: Int = 13): Int {
            var number = (start..end).toList()
            return number.random();
        }

        fun getRandomColor(): Color {
            return Color.values().random();
        }

        fun getRadomCard(): Card {
            var color = getRandomColor();
            var suit = getRandomSuit();
            var rank = getRandomRank();

            var cardList = listOf(
                    AttackCard(color, suit, rank),
                    DodgeCard(color, suit, rank),
//                    PeachCard(color, suit, rank),
//                    ZhugeCrossbow(Color.Black, Suit.Spade, 1),
//                    EightTrigrams(Color.Black, Suit.Spade, 2),
//                    AcediaCard(Color.Red, Suit.Heart,6),
//                    BarbariansAssault(Color.Black,Suit.Club,7),
//                    HailofArrows(Color.Red,Suit.Heart,1),
//                    SleightofHand(Color.Red,Suit.Heart,7),
//                    Duel(Color.Black,Suit.Spade,1),
//                    lightningBolt(Color.Black, Suit.Spade,1)
//                    OathOfPeachGarden(Color.Red, Suit.Heart, 1),
////                Plifer(Color.Red, Suit.Spade, 4),
//                    AzureDragonCrescentBlade(Color.Black, Suit.Spade, 5),
//                    TwinSwords(Color.Black, Suit.Spade, 2),
//                    RockCleavingAxe(Color.Red, Suit.Diamond, 5),
                    HeavenHalberd(Color.Red,Suit.Diamond,12),
                FrostBlade(Color.Black, Suit.Spade, 2)
            )

            return cardList.random();
        }

//        fun getRadomCard():List<Card>{
//            var color = getRandomColor();
//            var suit = getRandomSuit();
//            var rank = getRandomRank();
//
//            var cards = listOf(
//                    AttackCard(color, suit, rank),
//                    DodgeCard(color, suit, rank),
//                    PeachCard(color, suit, rank),
//                    ZhugeCrossbow(color, suit, rank)
//
//                    )
//            return listOf(cards.random());
//        }

    }
}