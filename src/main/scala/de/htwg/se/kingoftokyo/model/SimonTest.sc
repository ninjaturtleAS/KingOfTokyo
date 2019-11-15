import java.security.SecureRandom

import de.htwg.se.kingoftokyo.KingOfTokyo
import de.htwg.se.kingoftokyo.model._

import scala.collection.mutable.ListBuffer





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


case class PlayersCreator(stringOfNames: String) {
  //val aNames: ArrayBuffer[String] = new ArrayBuffer[String]()
  val vNames: List[String] = stringOfNames.split(",").toList
  val numberOfPlayers: Integer = vNames.length

  def toStringList: List[String] = {
    vNames
  }

  def getRandomPlayers(names: List[String], players: Players): Players = {
    //val rPlayerNames: Vector[String] = names -- players.toStringVector
    val rPlayerNames: List[String] = names.diff(players.toStringVector)
    if (rPlayerNames.isEmpty)
    {
      players
    }
    else
    {
      // use Die Class to randomize players order
      val random = Die(rPlayerNames.length)(new SecureRandom()).roll - 1
      val player = Player(rPlayerNames(random))
      getRandomPlayers(vNames, players.addPlayer(player))
    }
  }
  def getRandomPlayers(names: List[String]): Players = {
    getRandomPlayers(names,new Players())
  }
}

val plc = PlayersCreator("Simon,Alex,Paul")
plc.getRandomPlayers(plc.vNames).toStringVector



