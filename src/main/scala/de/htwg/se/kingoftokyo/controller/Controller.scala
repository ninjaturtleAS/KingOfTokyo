package de.htwg.se.kingoftokyo.controller

import de.htwg.se.kingoftokyo.controller.State._
import de.htwg.se.kingoftokyo.model._
import de.htwg.se.kingoftokyo.util.Observable

class Controller (var playGround: PlayGround) extends Observable {
  var state: GameState = WaitForPlayerNames

  def createPlayers(playerNames: String):PlayGround = {
    playGround = playGround.createPlayerInRandomOrder(playerNames)
      .throwDies()
    state = WaitFor1stThrow
    notifyObservers
    playGround
  }

  def evaluateThrow(): PlayGround = {
    playGround = playGround.getGood(playGround.rollResult)
    playGround = playGround.attack(playGround.rollResult)
    state = ThrowComplete
    notifyObservers
    playGround
  }

  def completeThrow(): PlayGround = {
    playGround = playGround.completeThrow()
    state = ThrowComplete
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
    state = if (state==WaitFor1stThrow) {WaitFor2ndThrow}
    else  {ThrowComplete}
    notifyObservers
    playGround
  }

  def playGroundToString(): String = {
    playGround.toString()
  }


}
