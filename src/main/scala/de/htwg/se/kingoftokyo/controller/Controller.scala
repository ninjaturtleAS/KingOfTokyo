package de.htwg.se.kingoftokyo.controller

import de.htwg.se.kingoftokyo.model._
import de.htwg.se.kingoftokyo.util.Observable

class Controller (var playGround: PlayGround) extends Observable {
  def askForPlayerNames():Unit = {
    notifyObservers
  }

  def createPlayers(playerNames: String):PlayGround = {
    playGround = playGround.createPlayerInRandomOrder(playerNames)
      .throwDies()
    notifyObservers
    playGround
  }

  def evaluateThrow(): PlayGround = {
    playGround = playGround.getGood(playGround.rollResult)
    notifyObservers
    playGround
  }

  def completeThrow(): PlayGround = {
    playGround = playGround.completeThrow()
    notifyObservers
    playGround
  }

  def attack(attackedPlayers: String) = {
    playGround = playGround.attack(attackedPlayers)
    notifyObservers
    playGround
  }

  def incLapNr: PlayGround = {
    playGround = playGround.incLapNr()
    notifyObservers
    playGround
  }

  def throwDies():PlayGround = {
    playGround = playGround.throwDies()
    notifyObservers
    playGround
  }

  def filterThrowResult(filter: String):PlayGround = {
    playGround = playGround.filterThrowResult(filter)
      .throwDies()
    notifyObservers
    playGround
  }

  def playGroundToString(): String = {
    playGround.toString()
  }


}
