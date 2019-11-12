package de.htwg.se.kingoftokyo.controller

import de.htwg.se.kingoftokyo.model._
import de.htwg.se.kingoftokyo.util.Observable

class Controller (var playGround: PlayGround) extends Observable {
  def askForPlayerNames():Unit = {
    notifyObservers
  }

  def createPlayers(playerNames: String):PlayGround = {
    val pg = playGround.createPlayerInRandomOrder(playerNames)
    notifyObservers
    pg
  }

  def incLapNr: PlayGround = {
    val pg = playGround.incLapNr()
    notifyObservers
    pg
  }

  def startGame:PlayGround = {
    val pg = playGround.setStatusMessage("Bitte Namen der Spieler kommagetrennt eingeben")
    notifyObservers
    pg
  }

  def throwDies():PlayGround = {
    val pg = playGround.throwDies()
    notifyObservers
    pg
  }

  def playGroundToString(): String = {
    playGround.toString()
  }


}
