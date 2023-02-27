import People.Hero

enum class EventType{
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

    fun notifyListener(eventType: String, target: Hero, card: Card, continuable: Boolean = false) {
        for (listener in listeners) {
            if (listener.hero == target) //Avoid using effects that affect the hero themselves
                continue;
            if (eventType == "Attack") {
                if (listener.beAttack(target, card)) {
                    break;
                }
            }else if(eventType == "AskSaveMe"){
                if(listener.askSaveMe(target, card)){
                    break;
                };
            }
        }
    }

    fun notifySpecificListener(eventType: String, target: Hero, specificHero: Hero, card: Card){
        for (listener in listeners) {
            if(listener.hero == specificHero){
                if(eventType == "Attack"){
                    listener.beAttack(target, card);
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

        println("${hero.name} is under attack now");

        //player has not a dodge card
        if(!hero.hasDodgeTypeCard()){
            if(hero.HP > 0){
                hero.HP -= 1;
            }
            ANSIColorConsole.printDanger("${hero.name} can't dodge this attack as ${hero.name} don't have a dodge card.");
            println("${hero.name} get hurt hp -1");
            println("${hero.name} ${ANSIColorConsole.red("♥")} HP = ${hero.HP}");
            return false;
        }

        while(true){
            var selectedDodgeCard = hero.askHeroPlaceACardOrNot(listOf("Dodge"));
            if(selectedDodgeCard is DodgeCard){
                hero.removeCard(selectedDodgeCard);
                println("${hero.name} dodged the attack");
                return true;
            }else{
                if(hero.HP > 0){
                    hero.HP -= 1;
                }
                println("${hero.name} get hurt hp -1");
                println("${hero.name} ${ANSIColorConsole.red("♥")} HP = ${hero.HP}");

                if(hero.HP == 0){
                    println("Asking other heros to save ${hero.name} life by using peach card.");
                    mainEventManager.notifyListener("AskSaveMe", hero, cardByAttacker);
                }
                return false;
            }
        }

    }

    fun askSaveMe(target: Hero, cardByTarget: Card):Boolean{
        if(hero.hasPeachTypeCard()){
            while(true){
                println("${hero.name}, you want to save ${target.name} life?(yes/no)");
                var question = readLine();
                if(question == "yes"){
                    var peachCard = hero.askHeroPlaceACard(listOf("Peach"));
                    hero.removeCard(peachCard);
                    target.HP += 1;
                    println("you save ${target.name} life, ${ANSIColorConsole.red("♥")} HP = ${target.HP}");
                    return true;
                }else if(question == "no"){
                    return false;
                }else{
                    println("invalid input, please input again!");
                    continue;
                }
            }
            return true;
        }
        return false
    }
}