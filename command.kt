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
            receiver.canAttack = false;
            return true
        }
        return false

    }
}



