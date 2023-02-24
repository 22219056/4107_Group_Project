import People.Hero
import People.Shu.*

class HeroFactory{
    var roles = listOf<Role>(Emperor(),Rebels(),Loyalist(),Traitor());
    lateinit var listOfHero: List<Hero>;

    fun initHeros(){
        listOfHero = listOf<Hero>(
            LiuBei(roles.random()),
            GuanYu(roles.random())
        );

        //add event listener
        for(hero in listOfHero){
            mainEventManager.addListener(Listener(hero));
        }
    }
}


class CardFactory(var heros: List<Hero>){

    fun dealingCard(){
        for(hero in heros){
            for(i in 1..4)
                hero.getCard(Deck.getRadomBasicCard());

        }
    }

}

class Deck{
    companion object {
        fun getRandomSuit(): Suit{
            return Suit.values().random();
        }

        fun getRandomRank(start: Int = 1, end: Int = 13): Int{
            var number = (start..end).toList()
            return number.random();
        }

        fun getRandomColor(): Color{
            return Color.values().random();
        }
        
        fun getRadomBasicCard(): BasicCard{
            var color = getRandomColor();
            var suit = getRandomSuit();
            var rank = getRandomRank();

            var basicCards = listOf(
                AttackCard(color,suit,rank),
                DodgeCard(color,suit,rank),
                PeachCard(color,suit,rank)
            )

            return basicCards.random();
        }
    }
}