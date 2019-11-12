import java.security.SecureRandom

import de.htwg.se.kingoftokyo.KingOfTokyo
import de.htwg.se.kingoftokyo.model.{Die, Player, Players}

import scala.collection.mutable.ListBuffer

case class Player(name:String, var heart: Int = 10, var stars: Int = 0, var energy: Int = 0) {

  val maxHeart = 10
  val zero = 0
  val maxStar = 20

  def gainHeart(gain: Int): Player = {
    if (maxHeart < (gain + this.heart)) {
      copy(name, maxHeart, stars, energy)
    } else {
      copy(name, heart + gain, stars, energy)
    }
  }
  def looseHeart(lost: Int): Player = {
    if (zero > (heart - lost)) {
      copy(name, zero, stars, energy)
    } else {
      copy(name, heart - lost, stars, energy)
    }
  }

  def gainStar(gain: Int): Player = {
    if (maxStar < (stars + gain)) {
      copy(name, heart, maxStar, energy)
    } else {
      copy(name, heart, stars + gain, energy)
    }
  }
  def looseStar(lost: Int): Player = {
    if (zero > (stars - lost)) {
      copy(name, heart, zero, energy)
    } else {
      copy(name, heart, stars - lost, energy)
    }
  }

  //Gain as much as you want
  def gainEnergy(gain: Int): Player =  copy(name, heart, stars, energy + gain)
  //Buying cards has to check if you cn afford them but if else to make sure
  def spendEnergy(spend: Int): Player = {
    if (zero >= energy -spend) {
      copy(name, heart, stars, zero)
    } else {
      copy(name, heart, stars, energy - spend)
    }
  }

  def info : String = f"$name%s, Heart: $heart%d, Stars: $stars%d, Energy: $energy%d"

}


case class Players(players: Vector[Player]) {
  def this () = this(Vector.empty)

  def addPlayer(player: Player): Players = new Players(players :+ player)

  def toStringVector: Vector[String] = for (p <- players) yield p.name

}


case class PlayersCreator(stringOfNames: String) {
  //val aNames: ArrayBuffer[String] = new ArrayBuffer[String]()
  val vNames: List[String] = stringOfNames.split(",").toList
  val numberOfPlayers: Integer = vNames.length
  //stringOfNames.split(",").copyToBuffer(aNames)

  //  def getPlayers: IndexedSeq[Player] = {
  //    for {
  //      i <- 0 until numberOfPlayers
  //    } yield getRandomPlayers
  //
  //  }
  def toStringList: List[String] = {
    vNames
  }
}
//  def getRandomPlayers(names: List[String], players: Players): Players = {
//    //val rPlayerNames: Vector[String] = names -- players.toStringVector
//    val rPlayerNames: List[String] = names.diff(players.toStringVector)
//    println(rPlayerNames)
//    if (rPlayerNames.length == 0)
//    {
//      players
//    }
//    else
//    {
//      val random = Die(rPlayerNames.length)(new SecureRandom()).roll - 1
//      println(random)
//      val player = Player(rPlayerNames(random))
//      getRandomPlayers(vNames, players.addPlayer(player))
//    }
//  }
//}
def strHelper (i: Int): String = {
  i match {
    case 1 | 2 | 3 => return i.toString() + " "
    case 4 => return "Energy "
    case 5 => return "Heart "
    case 6 => return "Attack "
  }
}

case class RollResult(result: Vector[Int]) {

  def keepThrow(selection: Vector[Int]): RollResult = {
    RollResult( for {x <- selection} yield this.result(x))
  }

  def keepThrow(selectionStr: String): RollResult = {
    val selection = selectionStr.split(" ").toVector
    RollResult( for {x <- selection} yield this.result(x.toInt))
  }
}

val x = RollResult(Vector(1,2,3,4,5))
val y = x.keepThrow("2 4")
val s = strHelper(1).concat(strHelper(5))
