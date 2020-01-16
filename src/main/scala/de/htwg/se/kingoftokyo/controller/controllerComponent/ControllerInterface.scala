package de.htwg.se.kingoftokyo.controller.controllerComponent

import scala.swing.Publisher
import scala.swing.event.Event
import de.htwg.se.kingoftokyo.model.playGroundComp.PlayGroundInterface
import de.htwg.se.kingoftokyo.controller.controllerComponent.State.GameState

trait ControllerInterface extends Publisher{

  def newGame: PlayGroundInterface
  def createPlayers(playerNames: Option[String]): PlayGroundInterface
  def evaluateThrow(): PlayGroundInterface
  def completeThrow(): PlayGroundInterface
  def incLapNr: PlayGroundInterface
  def throwDies():PlayGroundInterface
  def filterThrowResult(filter: String):PlayGroundInterface
  def playGroundToString(): String
  def nextTurn():PlayGroundInterface
  def getState(): GameState
  def getPlayground(): PlayGroundInterface
  def kotStay(): PlayGroundInterface
  def kotLeave(): PlayGroundInterface
  def contStay(): ControllerInterface
  def getWinnerString(): String
  def undo: Unit
  def redo: Unit
  def save: Unit
  def load: Unit

}
class PlaygroundChanged extends Event