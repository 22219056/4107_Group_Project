import People.Hero
import kotlin.random.Random

enum class EventType {
    Attack
}

class EventManager {
    var listeners: MutableList<Listener> = mutableListOf();

    fun addListener(listener: Listener) {
        listeners.add(listener);
    }

    fun removeListener(listener: Listener) {
        listeners.remove(listener);
    }

    fun notifyListener(eventType: String, target: Hero, card: Card) {
        for (listener in listeners) {
            if (listener.hero == target) //Avoid using effects that affect the hero themselves
                continue;
            if (eventType == "Attack") {
                if (listener.beAttack(target, card)) {
                    break;
                }
            } else if (eventType == "AskSaveMe") {
                if (listener.askSaveMe(target, card)) {
                    break;
                };
            } else if (eventType == "barbariansAssault") {
                listener.blockBarbariansAssault(listener.hero, card)
            } else if (eventType == "hailofArrowsAssault") {
                listener.blockHailofArrows(listener.hero, card)
            }
        }
    }

    fun notifyAllHero(eventType: String, card: Card) {
        for (listener in listeners) {
            if (eventType == "oathOfPeachGarden") {
                listener.allheroGiveHealth(listener.hero, card)
            }
        }
    }

    fun notifySpecificListener(eventType: String, target: Hero, specificHero: Hero, card: Card) {
        for (listener in listeners) {
            if (listener.hero == specificHero) {
                if (eventType == "Attack") {
                    listener.beAttack(target, card)
                } else if (eventType == "Duel") {
                    listener.toDuel(target, card)
                } else if (eventType == "Plifer") {
                    listener.toPlifer(target)
                } else if (eventType == "BurnBridges") {
                    listener.toBridges(target)
                }
                break;
            }
        }
    }


}


class Listener(val hero: Hero) {

    //if the affect successfully return true otherwise return false
    fun beAttack(target: Hero, cardByAttacker: Card): Boolean {
        //var state: Boolean = false;
        //Check whether the player has a dodge card.
        println()
        println("${hero.name} is under attack now");


        //active weapon effect
        if (target.weapons is TwinSwords) {
            (target.weapons as TwinSwords).active(currentHero = target, targetHero = hero);
        } else if (target.weapons is FrostBlade) {
            var choose = (0..1).random();
            // 0 means active FrostBlade's effect
            if (choose == 0) {
                (target.weapons as FrostBlade).active(currentHero = target, targetHero = hero);
                return true;
            }

        }

        //player has not a dodge card
        if (hero.armor?.name != null) {
            if (Deck.getRadomCard().color.equals(Color.Red)) {
                println("${hero.name} use  Eight Trigrams to judgment get red card, therefore, hero can dodge the attack\n")

                // use Rock Cleaving Axe effect
                if (target.weapons is RockCleavingAxe) {

                    var num = Random.nextInt(0, 1)

                    if (num == 1) {
                        for (c in 1..2) {
                            target.removeCard(target.cards[Random.nextInt(target.cards.size)])
                        }
                        println("${target.name} use Rock Cleaving Axe effect")

                        if (hero.HP > 0) {
                            hero.HP -= 1;
                        }
                        println("${hero.name} get hurt hp -1");
                        println("${hero.name} ${ANSIColorConsole.red("♥")} HP = ${hero.HP}");

                        if (hero.HP == 0) {
                            println("Asking other heros to save ${hero.name} life by using peach card.");
                            mainEventManager.notifyListener("AskSaveMe", hero, cardByAttacker);
                        }
                        return false;
                    } else {
                        println("${target.name} not use Rock Cleaving Axe effect")
                    }
                }

                return true
            } else {
                println("${ANSIColorConsole.red("${hero.name} use  Eight Trigrams to judgment get black card, therefore, hero can not use it to dodge the attack")}")
            }

        }

        if (!hero.hasDodgeTypeCard()) {
            if (hero.HP > 0) {
                hero.HP -= 1;
            }

            ANSIColorConsole.printDanger("${ANSIColorConsole.red("${hero.name} can't dodge this attack as ${hero.name} don't have a dodge card.")}");
            println("${hero.name} get hurt hp -1");
            println("${hero.name} ${ANSIColorConsole.red("♥")} HP = ${hero.HP}");

            return false;
        }

        while (true) {
            var selectedDodgeCard = hero.askHeroPlaceACardOrNot(listOf("Dodge"));
            if (selectedDodgeCard is DodgeCard) {
                hero.removeCard(selectedDodgeCard);
                println("${hero.name} dodged the attack");

                //compulsory use attack card when attacker equipped AzureDragonCrescentBlade weapon
                if (target.weapons is AzureDragonCrescentBlade) {
                    (target.weapons as AzureDragonCrescentBlade).active(currentHero = target, targetHero = hero);
                } else if (target.weapons is RockCleavingAxe) {
//                    var num = Random.nextInt(0,1)
                    var num = 1
                    if (num == 1) {
                        for (c in 1..2) {
                            target.removeCard(target.cards[Random.nextInt(target.cards.size)])
                        }
                        println("${target.name} use Rock Cleaving Axe effect")

                        if (hero.HP > 0) {
                            hero.HP -= 1;
                        }
                        println("${hero.name} get hurt hp -1");
                        println("${hero.name} ${ANSIColorConsole.red("♥")} HP = ${hero.HP}");

                        if (hero.HP == 0) {
                            println("Asking other heros to save ${hero.name} life by using peach card.");
                            mainEventManager.notifyListener("AskSaveMe", hero, cardByAttacker);
                        }
                        return false;
                    } else {
                        println("${target.name} not use Rock Cleaving Axe effect")
                    }
                }

                return true;
            } else {
                if (hero.HP > 0) {
                    hero.HP -= 1;
                }
                println("${hero.name} get hurt hp -1");
                println("${hero.name} ${ANSIColorConsole.red("♥")} HP = ${hero.HP}");

                if (hero.HP == 0) {
                    println("Asking other heros to save ${hero.name} life by using peach card.");
                    mainEventManager.notifyListener("AskSaveMe", hero, cardByAttacker);
                }
                return false;
            }
        }


    }

    fun allheroGiveHealth(target: Hero, card: Card): Boolean {
        if (hero.HP < hero.maxHP) {
            hero.HP += 1
            println("\n${ANSIColorConsole.red("${hero.name} hp increase one")}");
            println("${hero.name} ${ANSIColorConsole.red("♥")} HP = ${hero.HP}");
            return true
        } else {
            println("\n${ANSIColorConsole.red("${hero.name} can not than max hp")}");
            println("${hero.name} ${ANSIColorConsole.red("♥")} HP = ${hero.HP}");
            return false
        }
    }


    fun toBridges(target: Hero): Boolean {
        if(hero.cards.size > 0){
            println("${target.name} place a card to demolition target card");
            hero.randomRemoveCard(1);
            return true;
        }
        return false

//        while (true) {
//            println("${hero.name}")
//            println("Please place a card to demolition target card");
//            hero.displayCardFromList(hero.cards)
//            var index = readLine()?.toInt();
//            if (index != 0 && hero.cards.size >= index!!) {
//
//
//                hero.removeCard(hero.cards[index - 1])
//
//                return true
//            }
//            println("Not valid input, Please input again.");
//            continue;
//        }
//        return true
    }

    fun toPlifer(target: Hero): Boolean {
        if(hero.cards.size > 0){
            var pliferCard = hero.cards.random();
            target.getCard(pliferCard);
            hero.removeCard(pliferCard);
            return true;
        }
        return false;
//        while (true) {
//            println("${hero.name}")
//            println("Please place a card to take");
//            hero.displayCardFromList(hero.cards)
//            var index = readLine()?.toInt();
//            if (index != 0 && hero.cards.size >= index!!) {
//
//                //take target hero card
//                target.getCard(hero.cards[index - 1])
//
//                hero.removeCard(hero.cards[index - 1])
//
//                return true
//            }
//            println("Not valid input, Please input again.");
//            continue;
//        }
//        return true
    }

    fun toDuel(target: Hero, cardByAttacker: Card): Boolean {
        while (true) {
            if (hero.hasAttackTypeCard()) {
                println(hero.name)
                var selectedAttackCard = hero.askHeroPlaceACardOrNot(listOf("Attack"));
                if (selectedAttackCard is AttackCard) {
                    hero.removeCard(selectedAttackCard);
                    println("${hero.name} use attack card\n");

                } else {
                    if (hero.HP > 0) {
                        hero.HP -= 1;
                    }
                    println("${hero.name} get hurt hp -1");
                    println("${hero.name} ${ANSIColorConsole.red("♥")} HP = ${hero.HP}");

                    if (hero.HP == 0) {
                        println("Asking other heros to save ${hero.name} life by using peach card.");
                        mainEventManager.notifyListener("AskSaveMe", hero, cardByAttacker);
                    }
                    return false;
                }
            } else {
                hero.HP -= 1;
                println("${hero.name} without attack card")
                println("${hero.name} get hurt hp -1");
                println("${hero.name} ${ANSIColorConsole.red("♥")} HP = ${hero.HP}");

                if (hero.HP == 0) {
                    println("Asking other heros to save ${hero.name} life by using peach card.");
                    mainEventManager.notifyListener("AskSaveMe", hero, cardByAttacker);
                }
                return false;
            }

            if (target.hasAttackTypeCard()) {
                println(target.name)
                var selectedAttackCard = target.askHeroPlaceACardOrNot(listOf("Attack"));
                if (selectedAttackCard is AttackCard) {
                    target.removeCard(selectedAttackCard);
                    println("${target.name} use attack card\n");

                } else {
                    if (target.HP > 0) {
                        target.HP -= 1;
                    }
                    println("${target.name} get hurt hp -1");
                    println("${target.name} ${ANSIColorConsole.red("♥")} HP = ${hero.HP}");

                    if (target.HP == 0) {
                        println("Asking other heros to save ${target.name} life by using peach card.");
                        mainEventManager.notifyListener("AskSaveMe", target, cardByAttacker);
                    }
                    return false;
                }

            } else {
                target.HP -= 1;
                println("${target.name} without attack card")
                println("${target.name} get hurt hp -1");
                println("${target.name} ${ANSIColorConsole.red("♥")} HP = ${hero.HP}");

                if (target.HP == 0) {
                    println("Asking other heros to save ${target.name} life by using peach card.");
                    mainEventManager.notifyListener("AskSaveMe", target, cardByAttacker);
                }
                return false;
            }
        }
    }


    fun blockBarbariansAssault(target: Hero, cardByAttacker: Card): Boolean {

        if (hero.hasAttackTypeCard()) {
            while (true) {
                println("${hero.name}")
                var selectedAttackCard = hero.askHeroPlaceACardOrNot(listOf("Attack"));
                if (selectedAttackCard is AttackCard) {
                    hero.removeCard(selectedAttackCard);
                    println("${hero.name} dodged the Barbarians Assault of hurt");
                    return true;
                } else {
                    if (hero.HP > 0) {
                        hero.HP -= 1;
                    }
                    println("${ANSIColorConsole.red("${hero.name} give up / without [attack] card, ${hero.name} can not dodged the Barbarians Assault of hurt")}")
                    println("${hero.name} get hurt hp -1");
                    println("${hero.name} ${ANSIColorConsole.red("♥")} HP = ${hero.HP}");

                    if (hero.HP == 0) {
                        println("Asking other heros to save ${hero.name} life by using peach card.");
                        mainEventManager.notifyListener("AskSaveMe", hero, cardByAttacker);
                    }
                    return false;
                }
            }
        } else {
            if (hero.HP > 0) {
                hero.HP -= 1;
            }
            println("${ANSIColorConsole.red("${hero.name} without [attack] card, ${hero.name} can not dodged the Barbarians Assault of hurt")}")
            println("${hero.name} get hurt hp -1");
            println("${hero.name} ${ANSIColorConsole.red("♥")} HP = ${hero.HP}");

            if (hero.HP == 0) {
                println("Asking other heros to save ${hero.name} life by using peach card.");
                mainEventManager.notifyListener("AskSaveMe", hero, cardByAttacker);
            }
        }
        return false
    }

    fun blockHailofArrows(target: Hero, cardByAttacker: Card): Boolean {

        if (hero.armor?.name != null) {
            if (Deck.getRadomCard().color.equals(Color.Red)) {
                println("${hero.name} use  Eight Trigrams to judgment get red card, therefore, hero can dodge the Hailof Arrows hurt \n")
                return true
            } else {
                println("${hero.name} use  Eight Trigrams to judgment get black card, therefore, hero can not use it to dodge the Hailof Arrows hurt")
            }

        }

        if (hero.hasDodgeTypeCard()) {
            while (true) {
                println("${hero.name}")
                var selectedDodgeCard = hero.askHeroPlaceACardOrNot(listOf("Dodge"));
                if (selectedDodgeCard is DodgeCard) {
                    hero.removeCard(selectedDodgeCard);
                    println("${hero.name} dodged the Hailof Arrows of hurt");
                    return true;
                } else {
                    if (hero.HP > 0) {
                        hero.HP -= 1;
                    }
                    println("${ANSIColorConsole.red("${hero.name} give up / without [dodge] card, ${hero.name} can not dodged the Hailof Arrows of hurt")}")
                    println("${hero.name} get hurt hp -1");
                    println("${hero.name} ${ANSIColorConsole.red("♥")} HP = ${hero.HP}");

                    if (hero.HP == 0) {
                        println("Asking other heros to save ${hero.name} life by using peach card.");
                        mainEventManager.notifyListener("AskSaveMe", hero, cardByAttacker);
                    }
                    return false;
                }
            }
        } else {
            if (hero.HP > 0) {
                hero.HP -= 1;
            }
            println("${ANSIColorConsole.red("${hero.name} without [dodge] card, ${hero.name} can not dodged the  Hailof Arrows of hurt")}")
            println("${hero.name} get hurt hp -1");
            println("${hero.name} ${ANSIColorConsole.red("♥")} HP = ${hero.HP}");

            if (hero.HP == 0) {
                println("Asking other heros to save ${hero.name} life by using peach card.");
                mainEventManager.notifyListener("AskSaveMe", hero, cardByAttacker);
            }
        }
        return false
    }

    fun askSaveMe(target: Hero, cardByTarget: Card): Boolean {
        if(hero.hasPeachTypeCard()){
            var choiceOfSaveLife = (0..1).random()
            if(choiceOfSaveLife == 0) {
                var peachCard = hero.askHeroPlaceACard(kotlin.collections.listOf("Peach"));
                hero.removeCard(peachCard);
                target.HP += 1;
                println("${hero.name} save ${target.name} life, ${ANSIColorConsole.red("♥")} HP = ${target.HP}");
                return true;
            }
        }
        return false
    }
}