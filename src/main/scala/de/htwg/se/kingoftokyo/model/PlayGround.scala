package de.htwg.se.kingoftokyo.model

import de.htwg.se.kingoftokyo.model.State._

case class PlayGround(players: Players, lapNr: Integer, statusMessage: StatusMessageOld, status: State, rollResult: RollResult, kingOfTokyo: Int) {

//  def setStatusMessage(message:String):PlayGround = PlayGround(this.players, this.lapNr, new StatusMessageOld(message), this.status,
//        this.rollResult, this.kingOfTokyo)

  def incLapNr(): PlayGround = {
    copy(this.players, this.lapNr + 1, this.statusMessage, this.status, this.rollResult, this.kingOfTokyo)
  }

  def getGood(rollResult: RollResult): PlayGround = {
    val playerIndex = this.lapNr % this.players.getLength()
    val tmpPlayers = this.players.getGood(rollResult, playerIndex)
    copy(tmpPlayers, this.lapNr, this.statusMessage, ThrowComplete, this.rollResult, this.kingOfTokyo)
  }

  def completeThrow(): PlayGround = {
    copy(this.players, this.lapNr, this.statusMessage, ThrowComplete, this.rollResult, this.kingOfTokyo)
  }

  def attack(attackedPlayers: String):PlayGround = {
    //val attackes = attackedPlayers.split(",")
    //???
    copy(this.players, this.lapNr, this.statusMessage, this.status, this.rollResult, this.kingOfTokyo);
  }

  def throwDies():PlayGround = {
    val tmpRollResult = RollResult(List.concat(this.rollResult.toIntVector,Throw(6-this.rollResult.length).throwDies()).toVector)
    val tmpStatus = if (status<WaitFor1stThrow || status>WaitFor2ndThrow) { WaitFor1stThrow }
    else if (status==WaitFor1stThrow) {WaitFor2ndThrow}
    else  {ThrowComplete}

    copy(this.players,this.lapNr, this.statusMessage, tmpStatus, tmpRollResult, this.kingOfTokyo)
  }

  def createPlayerInRandomOrder(playerNames: String): PlayGround = {
    val tmpPlayersCreator = PlayersCreator(playerNames)
    val tmpPlayers = tmpPlayersCreator.getRandomPlayers(tmpPlayersCreator.toStringList, new Players())
    copy(tmpPlayers,this.lapNr, this.statusMessage, this.status, this.rollResult, this.kingOfTokyo)
  }

  def switchKingOfTokyo(newKotIndex: Int): PlayGround = {
    copy(this.players,this.lapNr, this.statusMessage, this.status, this.rollResult, newKotIndex)
  }

  def filterThrowResult(filter: String): PlayGround = {
    val filteredThrowResult = this.rollResult.filterThrowResult(filter)
    copy(this.players,this.lapNr, this.statusMessage, this.status, filteredThrowResult, this.kingOfTokyo)
  }

  override def toString: String = {
    val kotName = if (this.players.toPlayerVector.isEmpty) {""} else { this.players.toStringVector(this.kingOfTokyo)}
    val retString = f"King of Tokyo: $kotName%s\n\n"
      .concat(players.toPlayerVector.map(x => x.info.concat("\n")).mkString)
      .concat(if (this.players.getLength()>0) { "\nSpieler am Zug: ".concat(this.players.toPlayerVector(this.lapNr % this.players.getLength()).name)} else {""})
      .concat("\n\nAktueller Wurf: ")
      .concat(this.rollResult.toString())
      .concat("\n\n")
      .concat(this.status.toString()).concat("\n")  // only for debugging
      .concat(this.getMessageFromStatus)
      //.concat(this.statusMessage.toString())

    retString
  }

  def getMessageFromStatus: String = {
    this.status match {
      case WaitForPlayerNames => "Bitte Spielernamen kommagetrennt eingeben"
      case WaitFor1stThrow|WaitFor2ndThrow => "Ihr Auswahl"
      case ThrowComplete => "Wurf wird ausgewertet"
      case WaitForAttack => "Wen wollen Sie angreifen?"
      case WaitForKotDecision => "Wollen Sie King of Tokyo bleiben (J/N)"
      case _ => "not implemeted yet"
    }
  }

}
