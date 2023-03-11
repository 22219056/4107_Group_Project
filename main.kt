import People.Hero
import kotlin.random.Random
import kotlin.system.exitProcess

class Game {
    lateinit var currentHero: Hero;
    lateinit var heros: MutableList<Hero>;

    fun startPhase() {
        if(heros.size==1){
            println("win")
            exitProcess(0)
        }
        println("start phase");

        var nextHero: Hero

        if (heros.indexOf(currentHero) + 1 == heros.size) { //last man
            nextHero = heros.get(0)
        } else {
            nextHero = heros.get(heros.indexOf(currentHero) + 1)
        }

        if (currentHero.judgmentZone.isEmpty()) println("JudmentZone : ${currentHero.judgmentZone?.peek()?.name}")

        for (Cards in currentHero.judgmentZone) {
            currentHero.judgmentZone.pop().active(currentHero, currentHero.getJudgement(), nextHero)
            if(currentHero.HP<=0){
                break
            }
        }
    }

    fun judgmentPhase() {}


    fun mainPhase(name: String) {

//        ANSIColorConsole.red(toString(currentHero.showCurrentHP()));




        currentHero.canAttack = true;
        currentHero.drawPhase(currentHero)



        while (currentHero.cards.size > 0) {

            if (!currentHero.judgmentZone.isEmpty()) println("JudmentZone : ${currentHero.judgmentZone?.peek()?.name}")
            println("${name}'s turn:");
            currentHero.showCurrentHP();

            println("EquipmentCard Weapons: ${currentHero.weapons?.name} | Armor: ${currentHero.armor?.name}")
            currentHero.displayCards();
            print("0.[End of turn]\n");
            print("Please select a card: \n");
            var cardPlaced = currentHero.cards[Random.nextInt(0, currentHero.cards.size)]
           
            if (currentHero.canAttack == false) {
                cardPlaced.rank = 0
            }

            if (cardPlaced.rank == 0) {
                println()
                discardPhase()
                break;
            } else {
                //player placed a card
//                var cardPlaced = currentHero.cards[playerInput!!.toInt() - 1];

                println("you use [${cardPlaced.getCardString()}]");
                if (cardPlaced.name == "Attack") {
                    if (!currentHero.canAttack && !currentHero.weapons?.name.equals("Zhuge Crossbow")) {
                        println("You can not attack again\n")
                        continue
                    }
                    //currentHero.attackEventHandle(cardPlaced);
                    if (currentHero.cards.size == 1 && currentHero.weapons is HeavenHalberd) {
                        var heroList = Random.nextInt(1, heros.size)
                        while (heroList > 3 || heroList > heros.size - 1) {
                            heroList = Random.nextInt(1, heros.size)
                        }

                        for (a in 1..heroList) {
                            currentHero.attackEvent(cardPlaced);
                        }
                    } else {
                        currentHero.attackEvent(cardPlaced);
                    }

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
                } else if (cardPlaced is Duel) {
                    currentHero.duelHandle(cardPlaced)
                } else if (cardPlaced is OathOfPeachGarden) {
                    cardPlaced.active(currentHero = currentHero);
                } else if (cardPlaced is Plifer) {
                    currentHero.PliferHandle(cardPlaced)
                } else if (cardPlaced is BurnBridges) {
                    currentHero.BurnBridgesHandle(cardPlaced)
                } else if (cardPlaced is Mounts) {
                    currentHero.mounts = cardPlaced
                } else if (cardPlaced is lightningBolt) {
                    if (currentHero.getJudgmentZone().equals(cardPlaced is lightningBolt)) {
                        println("You already have a lightningBolt\n")
                        continue
                    } else {
                        currentHero.judgmentZone.push(cardPlaced)
                    }
                } else if (cardPlaced is DodgeCard) {
                    println("You can not use Dodge card")
                    continue
                }

                currentHero.removeCard(cardPlaced);
            }
            println()
        }
    }

    fun discardPhase() {
        while (currentHero.cards.size > currentHero.HP) {
            println("You need to discard ${currentHero.cards.size - currentHero.HP}")
//            var selectedCard = currentHero.askHeroPlaceACard();
            var selectedCard = currentHero.cards[Random.nextInt(0, currentHero.cards.size)];
            println()
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
                if(hero.HP>0){
                    mainPhase("${hero.name}");
                    endPhase();
                    println();
                }


            }
        }

    }

}


//--global variables
var mainEventManager = EventManager();
var heros: MutableList<Hero> = mutableListOf();

//--------

fun main() {
    var heroFactory = HeroFactory();
    heroFactory.initHeros();
    heros = heroFactory.listOfHero;
    var cardFactory = CardFactory(heros);
    cardFactory.dealingCard();

    var game = Game();
    game.heros = heros as MutableList<Hero>;

    game.start();

}