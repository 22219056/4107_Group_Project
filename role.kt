import People.Hero

interface Role{
    var name: String;
    fun getEnemies(): List<Hero>;
}
class Emperor: Role{
    override var name: String = "Emperor";
    override fun getEnemies():List<Hero> {
        var enemies = listOf<Hero>();
        for(hero in heros){
            if(hero.role is Rebel || hero.role is Traitor)
                enemies += hero;
        }
        return enemies;
    }
}
class Rebel: Role{
    override var name: String = "Rebel";
    override fun getEnemies():List<Hero> {
        var enemies = listOf<Hero>();
        for(hero in heros){
            if(hero.role is Emperor || hero.role is Minister)
                enemies += hero;
        }
        return enemies;
    }
}
class Minister: Role{
    override var name: String = "Minister";
    override fun getEnemies():List<Hero> {
        var enemies = listOf<Hero>();
        for(hero in heros){
            if(hero.role is Rebel || hero.role is Traitor)
                enemies += hero;
        }
        return enemies;
    }
}



class Traitor: Role{
    override var name: String = "Traitor";
    override fun getEnemies():List<Hero> {
        var enemies = listOf<Hero>();
        for(hero in heros){
            if(hero.role is Emperor || hero.role is Minister)
                enemies += hero;
        }
        return enemies;
    }
}