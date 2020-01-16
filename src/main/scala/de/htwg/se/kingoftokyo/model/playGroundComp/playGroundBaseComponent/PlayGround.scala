package de.htwg.se.kingoftokyo.model.playGroundComp.playGroundBaseComponent

import com.google.inject.{Guice, Inject}
import com.google.inject.name.Named
import de.htwg.se.kingoftokyo.controller.controllerComponent.State._
import de.htwg.se.kingoftokyo.model.playGroundComp.PlayGroundInterface
import de.htwg.se.kingoftokyo.model.playersComp.PlayersInterface
import de.htwg.se.kingoftokyo.model.rollResultComp.RollResultInterface


case class PlayGround @Inject() (players: PlayersInterface, @Named("Zero") lapNr: Int,
                      rollResult: RollResultInterface, @Named("Zero") kingOfTokyo: Int) extends PlayGroundInterface {
  var state: GameState = WaitForPlayerNames
  val yes = true
  val no = false

  override def setState(gameState: GameState): Unit = {
    this.state = gameState
  }
  override def getState: GameState = {
    this.state
  }
  override def incLapNr(): PlayGroundInterface = {
    copy(this.players, this.lapNr + 1, this.rollResult, this.kingOfTokyo)
  }

  override def getGood(rollResult: RollResultInterface): PlayGroundInterface = {
    val playerIndex = this.lapNr % this.players.getLength()
    val tmpPlayers = this.players.getGood(rollResult, playerIndex)
    copy(tmpPlayers, this.lapNr, this.rollResult, this.kingOfTokyo)
  }

  override def completeThrow(): PlayGroundInterface = {
    copy(this.players, this.lapNr, this.rollResult, this.kingOfTokyo)
  }

  override def attack(rollResult: RollResultInterface):(PlayGroundInterface, Boolean) = {
    if (this.lapNr % this.players.getLength() == this.kingOfTokyo) {
      val tmpPlayers = this.players.getAttacks(rollResult, yes, this.kingOfTokyo)
      (copy(tmpPlayers._1, this.lapNr, this.rollResult, this.kingOfTokyo), false)
    } else {
      val tmpPlayers = this.players.getAttacks(rollResult,no, this.kingOfTokyo)
      if (tmpPlayers._2) {
        (copy(tmpPlayers._1, this.lapNr, this.rollResult, this.kingOfTokyo), true)
      }
      else {
        (copy(tmpPlayers._1, this.lapNr, this.rollResult, this.kingOfTokyo), false)
      }
    }
  }

  override def throwDies():PlayGroundInterface = {
    val tmpRollResult = this.rollResult.throwAgain(this.rollResult)
    copy(this.players,this.lapNr, tmpRollResult, this.kingOfTokyo)
  }

  override def createPlayerInRandomOrder(playerNames: String): PlayGroundInterface = {
    val tmpPlayersCreator = this.players.getPlayersCreator(playerNames)
    val tmpPlayers = tmpPlayersCreator.getRandomPlayers(tmpPlayersCreator.toStringList, this.players.getEmptyPlayers)
    copy(tmpPlayers,this.lapNr, this.rollResult, this.kingOfTokyo)
  }

  override def switchKingOfTokyo(newKotIndex: Int): PlayGroundInterface = {
    copy(this.players,this.lapNr, this.rollResult, newKotIndex)
  }

  override def filterThrowResult(filter: String): PlayGroundInterface = {
    // if unnötig, hier kommt man nie hin
    if (filter == "") {
      copy(this.players,this.lapNr, this.rollResult.getEmptyResult, this.kingOfTokyo)
    } else {
      val filteredThrowResult = this.rollResult.filterThrowResult(filter)
      copy(this.players,this.lapNr, filteredThrowResult, this.kingOfTokyo)
    }
  }

  override def getKOT: Int = this.kingOfTokyo

  override def getPlayers: PlayersInterface = this.players

  override def getRollResult: RollResultInterface = this.rollResult

  override def getLapNr: Int = this.lapNr

  override def nextTurn: PlayGroundInterface = {
    PlayGround(this.players, this.lapNr + 1, this.rollResult.throwAllGetRR(), this.kingOfTokyo)
  }

  override def set(playersInterface: PlayersInterface, lapNr: Int, rollResultInterface: RollResultInterface,
                   kot: Int, state: GameState): PlayGroundInterface = {
    val pg = PlayGround(playersInterface, lapNr, rollResultInterface, kot)
    pg.setState(state)
    pg
  }

  override def setKOT(newKOT: Int): PlayGroundInterface = {
    copy(this.players,this.lapNr, this.rollResult, newKOT)
  }

  override def checkFinish: (Boolean, String) = {
    var finish: Boolean = false
    var wstring: String = ""
    val playersTmp = players.getPlayers()
    if (playersTmp.length == 1) {
      finish = true
      wstring = "Player: " + playersTmp(0).name + " won by killing everybody"
    }
    else {
      for (p <- playersTmp) {
        if (p.stars == 20) {
          finish = true
          wstring = "Player: " + p.name + " won by reaching 20 Stars"
        }
      }
    }
    (finish, wstring)
  }

  override def toString: String = {
    val kotName = if (this.players.toPlayerVector.isEmpty) {""} else { this.players.toStringVector(this.kingOfTokyo)}
    val retString = f"\n\n\n\n\n\n\n\n\nKing of Tokyo: $kotName%s\n\n"
      .concat(players.toPlayerVector.map(x => x.info.concat("\n")).mkString)
      .concat(if (this.players.getLength()>0) { "\nSpieler am Zug: ".concat(this.players.toPlayerVector(this.lapNr % this.players.getLength()).name)} else {""})
      .concat("\n\nAktueller Wurf: ")
      .concat(this.rollResult.toString)
      .concat("\n\n")

    retString
  }
}