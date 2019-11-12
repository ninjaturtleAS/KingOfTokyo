package de.htwg.se.kingoftokyo.model

import de.htwg.se.kingoftokyo.model.State._

case class PlayGround(players: Players, lapNr: Integer, statusMessage: StatusMessage, status: State, rollResult: RollResult, kingOfTokyo: Int) {

  def setStatusMessage(message:String):PlayGround = new PlayGround(this.players, this.lapNr, new StatusMessage(message), this.status, this.rollResult, this.kingOfTokyo)

  def incLapNr(): PlayGround = {
    copy(this.players, this.lapNr + 1, this.statusMessage, this.status, this.rollResult, this.kingOfTokyo)
  }

  def getGood(rollResult: RollResult): PlayGround = {
    val playerIndex = this.lapNr % this.players.getLength()
    val tmpPlayers = this.players.getGood(rollResult, playerIndex)
    copy(tmpPlayers,this.lapNr, this.statusMessage, this.status, this.rollResult, this.kingOfTokyo)
  }

  def throwDies():PlayGround = {
    val tmpRollResult = new RollResult(Throw(6).throwDies())
    copy(this.players,this.lapNr, this.statusMessage, this.status, tmpRollResult, this.kingOfTokyo)
  }

  def createPlayerInRandomOrder(playerNames: String): PlayGround = {
    val tmpPlayersCreator = PlayersCreator(playerNames)
    val tmpPlayers = tmpPlayersCreator.getRandomPlayers(tmpPlayersCreator.toStringList, new Players())
    copy(tmpPlayers,this.lapNr, this.statusMessage, WaitFor1stThrow, this.rollResult, this.kingOfTokyo)
  }

  def switchKingOfTokyo(newKotIndex: Int): PlayGround = {
    copy(this.players,this.lapNr, this.statusMessage, this.status, this.rollResult, newKotIndex)
  }

  override def toString(): String = {
    val kotInfo = this.players.toPlayerVector(this.kingOfTokyo).info
    val kotName = this.players.toStringVector(this.kingOfTokyo)
    val retString = (f"King of Tokyo: $kotName%s\n\n")
      .concat(players.toPlayerVector.map(x => x.info.concat("\n")).toString())
      .concat("\nAktueller Wurf: ")
      .concat(this.rollResult.toString())
      .concat("\n\n")
      .concat(this.statusMessage.toString())
    retString
  }

}
