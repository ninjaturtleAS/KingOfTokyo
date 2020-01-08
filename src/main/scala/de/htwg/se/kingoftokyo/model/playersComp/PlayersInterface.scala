package de.htwg.se.kingoftokyo.model.playersComp

import de.htwg.se.kingoftokyo.model.playersComp.playersBaseComponent.{Player, Players}
import de.htwg.se.kingoftokyo.model.rollResultComp.RollResultInterface

trait PlayersInterface {

  //def addPlayer(player: Player): PlayersInterface
  def toStringVector: Vector[String]
  def getGood(rollResult: RollResultInterface, playerIndex: Int): PlayersInterface
  def getAttacks(rollResult: RollResultInterface, inside: Boolean, kot : Int): PlayersInterface
  def getLength(): Int
  def getPlayers(): Vector[Player]
}
