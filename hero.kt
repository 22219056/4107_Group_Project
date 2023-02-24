enum class Gender {
    Male, Female;
}

abstract class Hero(role: Role) {
    abstract var name: String;
    abstract var HP: Int;
    abstract var maxHP: Int;
    abstract var gender: Gender;
    abstract var cards: MutableList<Card>;
    var role: Role = role;

    fun displayCards() {
        println("Card List: ");
        for ((index, card) in cards.withIndex()) {
            println("${index + 1}.[${card.getCardString()}] ");
        }
    }

    fun showCurrentHP() {
        println("${ANSIColorConsole.red("♥")} HP = ${HP}");
    }

    fun getCard(card: Card) {
        cards.add(card);
    }

    fun removeCard(card: Card) {
        cards.remove(card);
    }

    fun showRoleList(heros: List<Hero>, currentHero: String) {
        for ((index, hero) in heros.withIndex()) {
            if (!hero.name.equals(currentHero))
                println("${index + 1}. ${hero.name}")


        }
    }

    fun attackEvent(placedCard: Card, heros: List<Hero>, currentHero: String) {
        println("Select you want to attack role")

        showRoleList(heros, currentHero)
        var commandInput = readLine();

        for ((index, hero) in heros.withIndex()) {
            if (commandInput.equals("${(index + 1).toString()}")) {
                println("You select attack ${hero.name}\n")

//                println("${hero.name} need to make a decision, please select a card:")
                dodgeEvent(heros, hero.name)
//                hero.displayCards()
//                commandInput = readLine();
            }
        }

//        mainEventManager.notifyListener("Attack", this, placedCard);
    }

    fun dodgeEvent(heros: List<Hero>, beAttackedHero: String) {

        for ((index, hero) in heros.withIndex()) {

            if (hero.name.equals("${beAttackedHero}") && hero.cards.size > 0) {

                for ((index, card) in hero.cards.withIndex()) {

//                    if (card.name.equals("Dodge")) {
                        println("${hero.name}")
                        hero.displayCards()
                        print("0.[give up dodge]\n");

                        while (true) {
                            var commandInput = readLine();
                            if (commandInput == "0") {
                                println("${hero.name} get hurt hp -1")
                                hero.HP -= 1
                                println("${hero.name} ${ANSIColorConsole.red("♥")} HP = ${hero.HP}")
                                break
                            } else {
                                if (hero.cards[commandInput!!.toInt() - 1].name.equals("Dodge")) {
                                    println(hero.cards[commandInput!!.toInt() - 1].name)
                                    var cardPlaced = hero.cards[commandInput!!.toInt() - 1];
                                    hero.removeCard(cardPlaced);
                                    println("${hero.name} use dodge card to dodge attack")
                                    break
                                } else {
                                    println("You can not use this card ,please input command again")
                                    continue
                                }

                            }
                        }
//                    }

                }


            } else if (hero.name.equals("${beAttackedHero}")) {
                println("${hero.name} no card dodge that get hurt hp -1")
                hero.HP -= 1
                println("${hero.name} ${ANSIColorConsole.red("♥")} HP = ${hero.HP}")
                break
            }
        }
    }


}

class LiuBei(role: Role) : Hero(role) {
    override var name = "Liu Bei";
    override var HP = 4;
    override var maxHP = 4;
    override var gender = Gender.Male;
    override var cards = mutableListOf<Card>();
}

class GuanYu(role: Role) : Hero(role) {
    override var name = "Guan Yu";
    override var HP = 4;
    override var maxHP = 4;
    override var gender = Gender.Male;
    override var cards = mutableListOf<Card>();
}