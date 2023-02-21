class Factory{
    var roles = listOf<Role>(Emperor(),Rebels(),Loyalist(),Traitor());
    lateinit var listOfHero: List<Hero>;

    fun createHero(){
        listOfHero = listOf<Hero>(
            LiuBei(roles.random()),
            GuanYu(roles.random())
        );
    }
}

class CardFactory{
    
}