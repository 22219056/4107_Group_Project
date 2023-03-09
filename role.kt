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
            if(hero.role is Rebels || hero.role is Traitor)
                enemies += hero;
        }
        return enemies;
    }
}
class Rebels: Role{
    override var name: String = "Rebels";
    override fun getEnemies():List<Hero> {
        var enemies = listOf<Hero>();
        for(hero in heros){
            if(hero.role is Emperor || hero.role is Loyalist)
                enemies += hero;
        }
        return enemies;
    }
}
class Loyalist: Role{
    override var name: String = "Loyalist";
    override fun getEnemies():List<Hero> {
        var enemies = listOf<Hero>();
        for(hero in heros){
            if(hero.role is Rebels || hero.role is Traitor)
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
            if(hero.role is Emperor || hero.role is Loyalist)
                enemies += hero;
        }
        return enemies;
    }
}