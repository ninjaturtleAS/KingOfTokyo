package de.htwg.se.kingoftokyo.model.playersComp

import de.htwg.se.kingoftokyo.model.playersComp.playersComp1.{Player, Players}
import de.htwg.se.kingoftokyo.model.rollResultComp.rollResultComp1.RollResult

trait PlayersInterface {

  def addPlayer(player: Player): Players
  def toStringVector: Vector[String]
  def getGood(rollResult: RollResult, playerIndex: Int): Players
  def getAttacks(rollResult: RollResult, inside: Boolean, kot : Int): Players
  def getLength(): Int
  def toPlayerVector: Vector[Player]
}
