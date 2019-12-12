package de.htwg.se.kingoftokyo.model


case class PlayGround(players: Players, lapNr: Integer, rollResult: RollResult, kingOfTokyo: Int) {

  val yes = true
  val no = false

  def incLapNr(): PlayGround = {
    if (this.lapNr == players.players.length - 1) {
      copy(this.players, 0, this.rollResult, this.kingOfTokyo)
    } else {
      copy(this.players, this.lapNr + 1, this.rollResult, this.kingOfTokyo)
    }
  }

  def getGood(rollResult: RollResult): PlayGround = {
    val playerIndex = this.lapNr % this.players.getLength()
    val tmpPlayers = this.players.getGood(rollResult, playerIndex)
    copy(tmpPlayers, this.lapNr, this.rollResult, this.kingOfTokyo)
  }

  def completeThrow(): PlayGround = {
    copy(this.players, this.lapNr, this.rollResult, this.kingOfTokyo)
  }

  def attack(rollResult: RollResult):PlayGround = {
    if (this.lapNr % this.players.getLength() == this.kingOfTokyo) {
      val tmpPlayers = this.players.getAttacks(rollResult, yes, this.kingOfTokyo)
      copy(tmpPlayers, this.lapNr, this.rollResult, this.kingOfTokyo)
    } else {
      val tmpPlayers = this.players.getAttacks(rollResult,no, this.kingOfTokyo)
      copy(tmpPlayers, this.lapNr, this.rollResult, this.kingOfTokyo)
    }
  }

  def throwDies():PlayGround = {
    val tmpRollResult = RollResult(List.concat(this.rollResult.toIntVector,Throw(6-this.rollResult.length).throwDies()).toVector)
    copy(this.players,this.lapNr, tmpRollResult, this.kingOfTokyo)
  }

  def createPlayerInRandomOrder(playerNames: String): PlayGround = {
    val tmpPlayersCreator = PlayersCreator(playerNames)
    val tmpPlayers = tmpPlayersCreator.getRandomPlayers(tmpPlayersCreator.toStringList, new Players())
    copy(tmpPlayers,this.lapNr, this.rollResult, this.kingOfTokyo)
  }

  def switchKingOfTokyo(newKotIndex: Int): PlayGround = {
    copy(this.players,this.lapNr, this.rollResult, newKotIndex)
  }

  def filterThrowResult(filter: String): PlayGround = {
    if (filter == "") {
      val empty = Vector[Int]()
      copy(this.players,this.lapNr, RollResult(empty), this.kingOfTokyo)
    } else {
      val filteredThrowResult = this.rollResult.filterThrowResult(filter)
      copy(this.players,this.lapNr, filteredThrowResult, this.kingOfTokyo)
    }
  }

  override def toString: String = {
    val kotName = if (this.players.toPlayerVector.isEmpty) {""} else { this.players.toStringVector(this.kingOfTokyo)}
    val retString = f"\n\n\n\n\n\n\n\n\nKing of Tokyo: $kotName%s\n\n"
      .concat(players.toPlayerVector.map(x => x.info.concat("\n")).mkString)
      .concat(if (this.players.getLength()>0) { "\nSpieler am Zug: ".concat(this.players.toPlayerVector(this.lapNr % this.players.getLength()).name)} else {""})
      .concat("\n\nAktueller Wurf: ")
      .concat(this.rollResult.toString())
      .concat("\n\n")

    retString
  }
}
