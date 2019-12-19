package de.htwg.se.kingoftokyo.controller.controllerComponent

import de.htwg.se.kingoftokyo.model.PlayGround

import scala.swing.Publisher
import scala.swing.event.Event

trait ControllerInterface extends Publisher{

  def newGame: PlayGround
  def createPlayers(playerNames: Option[String]): PlayGround
  def evaluateThrow(): PlayGround
  def completeThrow(): PlayGround
  def incLapNr: PlayGround
  def throwDies():PlayGround
  def filterThrowResult(filter: String):PlayGround
  def playGroundToString(): String
  def nextTurn():PlayGround
  def undo: Unit
  def redo: Unit


  class PlaygroundChanged extends Event
}
