fun main(){
    var fac = Factory();
    fac.createHero();

    for(hero in fac.listOfHero){
        println("${hero.name} Role = ${hero.role.name}");
       
    }
}