package de.htwg.se.kingoftokyo.model.playersComp

import de.htwg.se.kingoftokyo.model.playersComp.playersBaseComponent.{Player, Players, PlayersCreator}
import de.htwg.se.kingoftokyo.model.rollResultComp.RollResultInterface

trait PlayersInterface {

  //def addPlayer(player: Player): PlayersInterface
  def addNewPlayer(name:String, energy: Int, heart: Int,stars: Int): PlayersInterface
  def toStringVector: Vector[String]
  def getGood(rollResult: RollResultInterface, playerIndex: Int): PlayersInterface
  def getAttacks(rollResult: RollResultInterface, inside: Boolean, kot : Int): (PlayersInterface, Boolean)
  def getLength(): Int
  def toPlayerVector: Vector[Player]
  def getPlayers(): Vector[Player]
  def playersXML(): String
  def playersStrToPlayers(playersStr: String, player: Player): Vector[Player]
  def set(players: Vector[Player]): PlayersInterface
  def getPlayersCreator(playerNames: String): PlayersCreator
  def getEmptyPlayers: Players
}
