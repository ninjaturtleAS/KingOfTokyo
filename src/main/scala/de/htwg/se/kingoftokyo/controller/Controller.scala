package de.htwg.se.kingoftokyo.controller

import de.htwg.se.kingoftokyo.model.{Player, Players, PlayersCreator}
import de.htwg.se.kingoftokyo.util.Observable

class Controller extends Observable {
  var turnNumber = 0
  def askForPlayerNames = ???

  def createPlayers = ???

  def nextTurn: Unit = {
    //playTurn()
    turnNumber += 1
  }

  def playTurn (player: Player) = ???

}
