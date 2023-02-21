enum class Suit{
    Spade, Heart, Diamond, Club;
}

interface Card{
    var name: String;
    var suit: Suit;
    var rank: Int;
}

abstract class BasicCard: Card{ }

abstract class EquipmentCard: Card{

}


