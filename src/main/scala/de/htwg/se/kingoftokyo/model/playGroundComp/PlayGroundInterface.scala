package de.htwg.se.kingoftokyo.model.playGroundComp

import de.htwg.se.kingoftokyo.model.rollResultComp.RollResultInterface

trait PlayGroundInterface {
  def incLapNr(): PlayGroundInterface
  def getGood(rollResult: RollResultInterface): PlayGroundInterface
  def completeThrow(): PlayGroundInterface
  def attack(rollResult: RollResultInterface): PlayGroundInterface
  def throwDies(): PlayGroundInterface
  def createPlayerInRandomOrder(playerNames: String): PlayGroundInterface
  def switchKingOfTokyo(newKotIndex: Int): PlayGroundInterface
  def filterThrowResult(filter: String): PlayGroundInterface
  def toString: String
}

