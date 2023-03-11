import People.Hero

interface Command{
    fun execute(cardPlaced: Card? = null): Boolean;
}

class Abandon(var receiver: Hero): Command{
    override fun execute(cardPlaced: Card?): Boolean {
        receiver.abandonRound = true;
        return true;
    }

}
class Attack(var receiver: Hero): Command{
    override fun execute(cardPlaced: Card?): Boolean {
        var enemies = receiver.role.getEnemies();

        if(enemies.size > 0 && cardPlaced != null && receiver.canAttack){
            var enemy = enemies.random();
            mainEventManager.notifySpecificListener("Attack",receiver,enemy,cardPlaced);
            return true
        }else if(cardPlaced != null){
            for(hero in heros){
                if(hero.name != receiver.name){
                    mainEventManager.notifySpecificListener("Attack",receiver,hero,cardPlaced);
                    return true
                }
            }
        }

        receiver.canAttack = false;
        return false

    }
}



