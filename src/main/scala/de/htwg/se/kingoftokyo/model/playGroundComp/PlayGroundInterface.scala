package de.htwg.se.kingoftokyo.model.playGroundComp

import de.htwg.se.kingoftokyo.controller.controllerComponent.State.GameState
import de.htwg.se.kingoftokyo.model.playersComp.PlayersInterface
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
  def getRollResult: RollResultInterface
  def getPlayers: PlayersInterface
  def getKOT: Int
  def getLapNr: Int
  def set(playersInterface: PlayersInterface, lapNr: Int, rollResultInterface: RollResultInterface, kot: Int, state: GameState): PlayGroundInterface
  def setState(gameState: GameState)
  def getState: GameState
  def nextTurn: PlayGroundInterface
}
