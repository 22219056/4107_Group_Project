class EventManager {
    var listeners: MutableList<Listener> = mutableListOf();

    fun addListener(listener: Listener) {
        listeners.add(listener);
    }

    fun removeListener(listener: Listener) {
        listeners.remove(listener);
    }

    fun notifyListener(eventType: String, target: Hero, card: Card, continuable: Boolean = false) {
        for (listener in listeners) {

            if (listener.hero == target) //Avoid using effects that affect the hero themselves
                continue;

            if (eventType == "Attack") {
                if (listener.beAttack(target, card)) {
                    break
                }
            }
        }
    }
}


class Listener(val hero: Hero) {

    //if the affect successfully return true otherwise return false
    fun beAttack(target: Hero, cardByAttacker: Card): Boolean {
        var state: Boolean = false;
        //Check whether the player has a dodge card.
        println("${hero.name} is under attack now");
        for (card in hero.cards) {
            if (state)
                break;

            if (card is DodgeCard) {
                println("Do you want to place a dodge card?(yes/no)");
                while (true) {
                    var decision = readLine();
                    when (decision) {
                        "yes" -> {
                            state = true;
                            println("${hero.name} dodged attack")
                            hero.cards.remove(card);
                            break;
                        }

                        "no" -> break;
                        else -> {
                            println("invalid input, please input again");
                            continue;
                        }
                    }
                }
            }

        }
        if (state == false) {
            ANSIColorConsole.printDanger("${hero.name} can't dodge attack.");
            (cardByAttacker as AttackCard).active(hero);
        }

        return state;
    }
}