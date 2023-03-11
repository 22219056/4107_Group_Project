import People.Hero

class Game {
    lateinit var currentHero: Hero;
    lateinit var heros: List<Hero>;

    fun startPhase() {
        println("start phase");
    }

    fun judgmentPhase() {}


    fun mainPhase(name: String) {

//        ANSIColorConsole.red(toString(currentHero.showCurrentHP()));

        currentHero.canAttack = true;
        currentHero.drawPhase(currentHero)
//  dont del      val numbersIterator = heros.iterator().next()
//  dont del      print(numbersIterator.name +" hello world ")
        while (currentHero.cards.size > 0) {

            if (currentHero.getJudgmentZone()) {
                currentHero.judgmentZone.pop().active(currentHero, currentHero.getJudgement())
                if (currentHero.abandonRound) {
                    println("hero abandoned")
                    discardPhase()
                    break;
                }
            }

            println("${name}'s turn:");
            currentHero.showCurrentHP();

            println("EquipmentCard Weapons: ${currentHero.weapons?.name} | Armor: ${currentHero.armor?.name}")
            currentHero.displayCards();
            print("0.[End of turn]\n");
            print("Please select a card: ");
            var playerInput = readLine();
            if (playerInput == "0") {
                println()
                discardPhase()
                break;
            } else {
                //player placed a card
                var cardPlaced = currentHero.cards[playerInput!!.toInt() - 1];

                println("you use [${cardPlaced.getCardString()}]");
                if (cardPlaced.name == "Attack") {
                    if (!currentHero.canAttack && !currentHero.weapons?.name.equals("Zhuge Crossbow")) {
                        println("You can not attack again\n")
                        continue
                    }
                    //currentHero.attackEventHandle(cardPlaced);
                    currentHero.attackEvent(cardPlaced);
                    currentHero.canAttack = false;

                } else if (cardPlaced.name == "Peach") {
                    if (currentHero.HP == currentHero.maxHP) {
                        println("You HP is max, please you again select other card")
                        continue
                    } else {
                        currentHero.HP += 1
                        currentHero.removeCard(cardPlaced)
                        currentHero.showCurrentHP()
                    }
                } else if (cardPlaced is Weapons) {
                    currentHero.weapons = cardPlaced

                } else if (cardPlaced is Armor) {
                    currentHero.armor = cardPlaced

                } else if (cardPlaced is AcediaCard) {
                    currentHero.acediaEventHandle(cardPlaced)
                } else if (cardPlaced is BarbariansAssault) {
                    currentHero.barbariansAssaultHandle(cardPlaced)
                } else if (cardPlaced is HailofArrows) {
                    currentHero.HailofArrowsHandle(cardPlaced)
                } else if (cardPlaced is SleightofHand) {
                    for (i in 1..2) {
                        currentHero.getCard(Deck.getRadomCard())
                    }
                }else if(cardPlaced is Duel){
                    currentHero.duelHandle(cardPlaced)
                }else if(cardPlaced is OathOfPeachGarden){
                    cardPlaced.active(currentHero = currentHero);
                }else if(cardPlaced is Plifer){
                    currentHero.PliferHandle(cardPlaced)
                }

                currentHero.removeCard(cardPlaced);
            }
            println()
        }
    }

    fun discardPhase() {
        while (currentHero.cards.size > currentHero.HP) {
            println("You need to discard ${currentHero.cards.size - currentHero.HP}")
            var selectedCard = currentHero.askHeroPlaceACard();
            currentHero.cards.remove(selectedCard);
            println("You discard [${selectedCard.getCardString()}]");

//            var playerInput = readLine();
//            var cardPlaced = currentHero.cards[playerInput!!.toInt() - 1];
//            println("You discard [${cardPlaced.getCardString()}]")
//            currentHero.removeCard(cardPlaced);
//            currentHero.displayCards();
        }

    }

    fun endPhase() {
        //diao chan can draw one card
        println("end of ${currentHero.name}'s turn");
    }

    fun start() {
        while (true) {
            for (hero in heros) {
                currentHero = hero;
                startPhase();
                mainPhase("${hero.name}");
                endPhase();
                println();

            }
        }

    }

}


//--global variables
var mainEventManager = EventManager();
var heros: List<Hero> = listOf();

//--------

fun main() {
    var heroFactory = HeroFactory();
    heroFactory.initHeros();
    heros = heroFactory.listOfHero;
    var cardFactory = CardFactory(heros);
    cardFactory.dealingCard();

    var game = Game();
    game.heros = heros;

    game.start();

}