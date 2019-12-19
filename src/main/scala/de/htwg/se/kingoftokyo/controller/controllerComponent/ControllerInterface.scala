package de.htwg.se.kingoftokyo.controller.controllerComponent

import scala.swing.Publisher
import scala.swing.event.Event
import de.htwg.se.kingoftokyo.model.playGroundComp.PlayGroundInterface

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
  def undo: Unit
  def redo: Unit

}
class PlaygroundChanged extends Event