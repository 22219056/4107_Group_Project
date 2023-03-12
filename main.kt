import People.Hero
import People.KingLess.DiaoChan
import People.Wei.WeiHero
import kotlin.contracts.contract
import kotlin.random.Random
import kotlin.system.exitProcess

class Game {
    lateinit var currentHero: Hero;
    lateinit var heros: MutableList<Hero>;

    fun startPhase() {
        println("start phase");

    }

    fun checkEndGame(): String {
        var rebelDie = 0
        var traitorDie = 0
        var ministerDie = 0
        var heroName = ""

        for ((index, hero) in heros.withIndex()) {
            if (hero.HP <= 0) {
                println("${hero.name} has died")
                if (hero.role is Rebel) {
                    rebelDie += 1
                    heroName = hero.name

                    if (rebelDie == 2) {
                        var asd = readln()
                        println("Monarch and Minister Win The Game")
                        exitProcess(0)
                    }
                } else if (hero.role is Emperor) {
                    if (rebelDie == 2  && ministerDie == 1) {      // Traitor win the game
                        println("Traitor Win The Game")
                        exitProcess(0)
                    }
                    println("Rebel Win The Game")
                    exitProcess(0)
                } else if (hero.role is Minister) {
                    ministerDie +=1
                    heroName = hero.name
                } else if (hero.role is Traitor) {
                    traitorDie +=1
                    heroName = hero.name

                }

            }
        }

        return heroName
    }


    fun removeHero() {
        var diedHerosIndex = listOf<Int>();

        for ((index, hero) in heros.withIndex()) {
            if (hero.HP <= 0) {
                diedHerosIndex += index
            }
        }
        for (diedHeroindex in diedHerosIndex) {
            try{
                heros.removeAt(diedHeroindex)
                mainEventManager.listeners.removeAt(diedHeroindex)
            }catch (e: Exception){
                continue;
            }
//            mainEventManager.listeners.removeAt(diedHeroindex)
        }
    }


    fun judgmentPhase() {
        var nextHero: Hero

        if (heros.indexOf(currentHero) + 1 == heros.size) { //last man
            nextHero = heros.get(0)
        } else {
            nextHero = heros.get(heros.indexOf(currentHero) + 1)
        }

        if (currentHero.judgmentZone.isEmpty()) println("JudmentZone : ${currentHero.judgmentZone?.peek()?.name}")

        for (Cards in currentHero.judgmentZone) {
            currentHero.judgmentZone.pop().active(currentHero, currentHero.getJudgement(), nextHero)
            if (currentHero.HP <= 0) {
                break
            }
        }
    }


    fun mainPhase(name: String) {

//        ANSIColorConsole.red(toString(currentHero.showCurrentHP()));

        currentHero.canAttack = true;
        currentHero.drawPhase(currentHero)
        if (currentHero.abandonRound){
            return
        }
        var flash = 1

        while (currentHero.cards.size > 0) {

            if (!currentHero.judgmentZone.isEmpty()) println("JudmentZone : ${currentHero.judgmentZone?.peek()?.name}")
            println("${name}'s turn: ");
            currentHero.showCurrentHP();

            println("EquipmentCard Weapons: ${currentHero.weapons?.name} | Armor: ${currentHero.armor?.name}")
            currentHero.displayCards();
            print("0.[End of turn]\n");
            print("Please select a card: \n");

            var placeNum=Random.nextInt(0,currentHero.cards.size)
            var cardPlaced = currentHero.cards[placeNum]

//            var cardPlaced = Random.nextInt(currentHero.cards.size)

//var asd = readln()


            if (placeNum == 0 ||currentHero.checkOnlyDodge_Attack_Peach(currentHero) == 0 || flash == 0) {

                println()
                discardPhase()
                break;
            } else {
                //player placed a card
//                var cardPlaced = currentHero.cards[playerInput!!.toInt() - 1];

                println("you use [${cardPlaced.getCardString()}]\n");
                if (cardPlaced.name == "Attack") {
                    if (!currentHero.canAttack && !currentHero.weapons?.name.equals("Zhuge Crossbow")) {
                        println("You can not attack again\n")
                        flash = currentHero.flashRepeat(0)
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
                        flash = currentHero.flashRepeat(0)
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
                        flash = currentHero.flashRepeat(0)
                        continue
                    } else {
                        currentHero.judgmentZone.push(cardPlaced)
                    }
                } else if (cardPlaced is DodgeCard) {
                    println("You can not use Dodge card")
                    flash = currentHero.flashRepeat(0)
                    continue
                }

                currentHero.removeCard(cardPlaced);
                checkEndGameState();
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

        }

    }

    fun endPhase() {
        //diao chan can draw one card
        if (currentHero is DiaoChan) {
            (currentHero as DiaoChan).Eclipse();
        }

        println("end of ${currentHero.name}'s turn");
    }

    fun start() {
        while (true) {

            for (hero in heros) {
                if (hero.name == checkEndGame()) {
                    continue
                }
                currentHero = hero;
                startPhase();
                judgmentPhase()
                if (hero.HP > 0) {

                    mainPhase("${hero.name}");
                    endPhase();
                    println();
                }
            }
            removeHero()
        }

    }

    fun isEmperorDeath():Boolean{
        for(hero in heros){
            if(hero.role is Emperor && hero.HP == 0){
                return true;
            }
        }
        return false;
    }
    fun allTraitorDeath():Boolean{
        var numOfTraitor = 0;
        var death = 0;
        for(hero in heros){
            if(hero.role is Traitor){
                numOfTraitor += 1
                if(hero.HP == 0)
                    death += 1
            }
        }
        if(numOfTraitor != 0 && death != 0){
            return numOfTraitor == death;
        }
        return false;
    }
    fun allRebelDeath():Boolean{
        var numOfRebel = 0;
        var death = 0;
        for(hero in heros){
            if(hero.role is Rebel){
                numOfRebel += 1
                if(hero.HP == 0)
                    death += 1
            }
        }
        if(numOfRebel != 0 && death != 0){
            return numOfRebel == death;
        }
        return false;
    }
    fun checkEndGameState(){
        if(isEmperorDeath()){
            println("Rebel and Traitor Win The Game")
            exitProcess(0)
        }else if(allRebelDeath()){
            println("Monarch and Minister Win The Game")
            exitProcess(0)
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