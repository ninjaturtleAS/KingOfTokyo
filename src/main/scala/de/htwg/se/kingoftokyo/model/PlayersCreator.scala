package de.htwg.se.kingoftokyo.model

import java.security.SecureRandom

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
