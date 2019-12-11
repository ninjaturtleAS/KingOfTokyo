package de.htwg.se.kingoftokyo.controller

import de.htwg.se.kingoftokyo.controller.State._
import de.htwg.se.kingoftokyo.model._
import de.htwg.se.kingoftokyo.util._

import scala.swing.Publisher
import scala.util.Try

class Controller (var playGround: PlayGround) extends Publisher {
  var state: GameState = WaitForPlayerNames
  private val undoManager = new UndoManager

  def newGame: PlayGround = {
    playGround = new PlayGround(new Players(),0 , RollResult(Vector.empty), 0)
    state = WaitForPlayerNames
    publish(new PlaygroundChanged)
    playGround
  }

  def createPlayers(playerNames: Option[String]): PlayGround = {
    playerNames match {
      case None =>
        publish(new PlaygroundChanged)
        playGround
      case Some(playerNames) =>
        undoManager.doStep (new CreatePlayersCommand (playerNames, this) )
        playGround = playGround.createPlayerInRandomOrder (playerNames)
        .throwDies ()
        state = WaitFor1stThrow
        publish(new PlaygroundChanged)
        playGround
    }
  }

  def evaluateThrow(): PlayGround = {
    playGround = playGround.getGood(playGround.rollResult)
    playGround = playGround.attack(playGround.rollResult)
    publish(new PlaygroundChanged)
    playGround = nextTurn()
    publish(new PlaygroundChanged)
    playGround
  }

  def completeThrow(): PlayGround = {
    playGround = playGround.completeThrow()
    state = ThrowComplete
    publish(new PlaygroundChanged)
    playGround
  }

  def incLapNr: PlayGround = {
    playGround = playGround.incLapNr()
    publish(new PlaygroundChanged)
    playGround
  }

  def throwDies():PlayGround = {
    playGround = playGround.throwDies()
    publish(new PlaygroundChanged)
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
      publish(new PlaygroundChanged)
      playGround
    }
    else {
      publish(new PlaygroundChanged)
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
    publish(new PlaygroundChanged)
  }

  def redo: Unit = {
    undoManager.redoStep
    publish(new PlaygroundChanged)
  }


}
