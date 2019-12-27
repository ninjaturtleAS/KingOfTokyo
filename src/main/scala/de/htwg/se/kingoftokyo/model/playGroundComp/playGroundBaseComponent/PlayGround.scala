package de.htwg.se.kingoftokyo.model.playGroundComp.playGroundBaseComponent

import com.google.inject.Inject
import com.google.inject.name.Named
import de.htwg.se.kingoftokyo.model.playGroundComp.PlayGroundInterface
import de.htwg.se.kingoftokyo.model.playersComp.PlayersInterface
import de.htwg.se.kingoftokyo.model.playersComp.playersBaseComponent.{Players, PlayersCreator}
import de.htwg.se.kingoftokyo.model.rollResultComp.RollResultInterface
import de.htwg.se.kingoftokyo.model.rollResultComp.rollResultBaseComponent.{RollResult, Throw}


case class PlayGround @Inject() (@Named("initPG") players: PlayersInterface, lapNr: Integer,
                      rollResult: RollResultInterface, kingOfTokyo: Int) extends PlayGroundInterface {

  val yes = true
  val no = false

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

  override def attack(rollResult: RollResultInterface):PlayGroundInterface = {
    if (this.lapNr % this.players.getLength() == this.kingOfTokyo) {
      val tmpPlayers = this.players.getAttacks(rollResult, yes, this.kingOfTokyo)
      copy(tmpPlayers, this.lapNr, this.rollResult, this.kingOfTokyo)
    } else {
      val tmpPlayers = this.players.getAttacks(rollResult,no, this.kingOfTokyo)
      copy(tmpPlayers, this.lapNr, this.rollResult, this.kingOfTokyo)
    }
  }

  override def throwDies():PlayGroundInterface = {
    val tmpRollResult = RollResult(List.concat(this.rollResult.toIntVector,Throw(6-this.rollResult.length).throwDies()).toVector)
    copy(this.players,this.lapNr, tmpRollResult, this.kingOfTokyo)
  }

  override def createPlayerInRandomOrder(playerNames: String): PlayGroundInterface = {
    val tmpPlayersCreator = PlayersCreator(playerNames)
    val tmpPlayers = tmpPlayersCreator.getRandomPlayers(tmpPlayersCreator.toStringList, new Players())
    copy(tmpPlayers,this.lapNr, this.rollResult, this.kingOfTokyo)
  }

  override def switchKingOfTokyo(newKotIndex: Int): PlayGroundInterface = {
    copy(this.players,this.lapNr, this.rollResult, newKotIndex)
  }

  override def filterThrowResult(filter: String): PlayGroundInterface = {
    if (filter == "") {
      val empty = Vector[Int]()
      copy(this.players,this.lapNr, RollResult(empty), this.kingOfTokyo)
    } else {
      val filteredThrowResult = this.rollResult.filterThrowResult(filter)
      copy(this.players,this.lapNr, filteredThrowResult, this.kingOfTokyo)
    }
  }

  override def getKOT: Int = this.kingOfTokyo

  override def getPlayers: PlayersInterface = this.players

  override def getRollResult: RollResultInterface = this.rollResult

  override def getLapNr: Int = this.lapNr

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
