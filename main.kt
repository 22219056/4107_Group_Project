class Game {
    lateinit var currentHero: Hero;
    lateinit var heros: List<Hero>;

    fun startPhase() {
        println("start phase");
    }

    fun judgmentPhase() {}
    fun drawPhase(hero: Hero) {
        for (i in 1..2) {
            hero.getCard(Deck.getRadomBasicCard())

        }

    }

    fun mainPhase(name: String) {


//        ANSIColorConsole.red(toString(currentHero.showCurrentHP()));
        currentHero.showCurrentHP();
        drawPhase(currentHero)
        while (currentHero.cards.size > 0) {
            println("${name}'s turn:");
            currentHero.displayCards();
            print("0.[End of turn]\n");
            print("Please select a card: ");
            var playerInput = readLine();
            if (playerInput == "0") {
                // discardPhase()
                break;
            } else {
                //player placed a card
                var cardPlaced = currentHero.cards[playerInput!!.toInt() - 1];

                println("you use [${cardPlaced.getCardString()}]");
                if (cardPlaced.name == "Attack") {

                    currentHero.attackEvent(cardPlaced,heros,currentHero.name);

                }
                currentHero.removeCard(cardPlaced);

            }
            println()

        }
    }

    fun discardPhase() {


        while (currentHero.cards.size > currentHero.HP) {
            println("You need to discard ${currentHero.cards.size - currentHero.HP}")
            var playerInput = readLine();

            var cardPlaced = currentHero.cards[playerInput!!.toInt() - 1];
            println("You discard [${cardPlaced.getCardString()}]")
            currentHero.removeCard(cardPlaced);
            currentHero.displayCards();
        }

    }

    fun endPhase() {
        //diao chan can draw one card
        println("end of ${currentHero.name}'s turn");
    }

    fun start() {

        for (hero in heros) {
            currentHero = hero;
            startPhase();
            mainPhase("${hero.name}");
            endPhase();
            println();

        }
    }

}


//--global variables
var mainEventManager = EventManager();


//--------

fun main() {

    var heroFactory = HeroFactory();
    heroFactory.initHeros();
    var heros = heroFactory.listOfHero;
    var cardFactory = CardFactory(heros);
    cardFactory.dealingCard();


    var game = Game();
    game.heros = heros;

    game.start();

}