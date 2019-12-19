package de.htwg.se.kingoftokyo.model.playGroundComp

import de.htwg.se.kingoftokyo.model.playGroundComp.playGroundComp1.{PlayGround, RollResult}

trait PlayGroundInterface {
  def incLapNr(): PlayGround
  def getGood(rollResult: RollResult): PlayGround
  def completeThrow(): PlayGround
  def attack(rollResult: RollResult): PlayGround
  def throwDies(): PlayGround
  def createPlayerInRandomOrder(playerNames: String): PlayGround
  def switchKingOfTokyo(newKotIndex: Int): PlayGround
  def filterThrowResult(filter: String): PlayGround
  def toString: String
}

