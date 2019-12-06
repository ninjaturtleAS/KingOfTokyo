package de.htwg.se.kingoftokyo.controller

import de.htwg.se.kingoftokyo.controller.State._
import de.htwg.se.kingoftokyo.model._
import de.htwg.se.kingoftokyo.util._

import scala.util.Try

class Controller (var playGround: PlayGround) extends Observable {
  var state: GameState = WaitForPlayerNames
  private val undoManager = new UndoManager

  def createPlayers(playerNames: String):PlayGround = {


    undoManager.doStep(new CreatePlayersCommand(playerNames, this))
    playGround = playGround.createPlayerInRandomOrder(playerNames)
      .throwDies()
    state = WaitFor1stThrow
    notifyObservers
    playGround
  }

  def evaluateThrow(): PlayGround = {
    playGround = playGround.getGood(playGround.rollResult)
    playGround = playGround.attack(playGround.rollResult)
    notifyObservers
    playGround = nextTurn()
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
    val selection = filter.split(" ").toVector
    val list = Try(selection.map(x => x.toInt))
    if (list.isSuccess) {
      playGround = playGround.filterThrowResult(filter)
        .throwDies()
      state = if (state==WaitFor1stThrow) {WaitFor2ndThrow}
      else  {ThrowComplete}
      notifyObservers
      playGround
    }
    else {
      notifyObservers
      this.playGround
    }

  }

  def playGroundToString(): String = {
    playGround.toString()
  }

  def nextTurn():PlayGround = {
    playGround = playGround.copy(playGround.players, playGround.lapNr + 1,
                                    RollResult(playGround.rollResult.throwOne()), playGround.kingOfTokyo)
    state = WaitFor1stThrow
    playGround
  }

  def undo: Unit = {
    undoManager.undoStep
    notifyObservers
  }

  def redo: Unit = {
    undoManager.redoStep
    notifyObservers
  }


}
