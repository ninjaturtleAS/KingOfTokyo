package de.htwg.se.kingoftokyo.controller

import de.htwg.se.kingoftokyo.controller.State._
import de.htwg.se.kingoftokyo.model._
import de.htwg.se.kingoftokyo.util.Observable

class Controller (var playGround: PlayGround) extends Observable {
  var state: GameState = WaitForPlayerNames

  def askForPlayerNames():Unit = {
    notifyObservers
  }

  def createPlayers(playerNames: String):PlayGround = {
    playGround = playGround.createPlayerInRandomOrder(playerNames)
      .throwDies()
    state = if (state<WaitFor1stThrow || state>WaitFor2ndThrow) { WaitFor1stThrow }
    else if (state==WaitFor1stThrow) {WaitFor2ndThrow}
    else  {ThrowComplete}
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
    state = if (state<WaitFor1stThrow || state>WaitFor2ndThrow) { WaitFor1stThrow }
    else if (state==WaitFor1stThrow) {WaitFor2ndThrow}
    else  {ThrowComplete}
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
