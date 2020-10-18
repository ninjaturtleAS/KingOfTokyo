package de.htwg.se.kingoftokyo.model.playersComp.playersBaseComponent

import com.google.inject.Inject
import de.htwg.se.kingoftokyo.model.playersComp.PlayersInterface
import de.htwg.se.kingoftokyo.model.rollResultComp.RollResultInterface
import org.graalvm.compiler.lir.alloc.trace.TraceGlobalMoveResolutionPhase

case class Players (players: Vector[Player]) extends PlayersInterface {

  val five = 5

  def this() = this(Vector.empty)

  override def getEmptyPlayers: Players = Players(Vector.empty)

  def addPlayer(player: Player): Players = Players(players :+ player)

  override def addNewPlayer(name:String, energy: Int, heart: Int,stars: Int): PlayersInterface = {
    Players(players :+ Player(name, energy, heart, stars))
  }

  override def toStringVector: Vector[String] = for (p <- players) yield p.name


  override def buyHeart(index: Int): PlayersInterface = {
    val tmpPlayer = this.players(index)
      .gainHeart(1)
        .spendEnergy(five)
    copy(this.players.updated(index, tmpPlayer))
  }

  override def buyStar(index: Int): PlayersInterface = {
    val tmpPlayer = this.players(index)
      .gainStar(1)
        .spendEnergy(five)
    copy(this.players.updated(index, tmpPlayer))
  }


  override def getGood(rollResult: RollResultInterface, playerIndex: Int, kot: Int): PlayersInterface = {
    val tmpPlayer = if (playerIndex != kot) {
      this.players(playerIndex)
        .gainEnergy(rollResult.evaluateEnergy())
        .gainHeart(rollResult.evaluateHeart())
        .gainStar(rollResult.evaluateStars())
    } else {
      this.players(playerIndex)
        .gainEnergy(rollResult.evaluateEnergy())
        .gainStar(rollResult.evaluateStars())
    }
      copy(this.players.updated(playerIndex, tmpPlayer))
  }

  override def getAttacks(rollResult: RollResultInterface, inside: Boolean, kotIndex: Int, lapNr: Int):
  (PlayersInterface, Int, Int, Boolean) = {
    /** evaluates the attack of the attacking Player and makes the attacked Player/Players loose Hearts
     *  @return The Players with their new State, the new lapNr, the new KOT Index,
     *          True/False if the attacking Player was the KOT or not
     */
    val attacks = rollResult.evaluateAttacks()
    if (attacks == 0) {
      (this, lapNr, kotIndex, false)
    }
    else {
      // lapNr is always incremented
      val tmpLapNr = lapNr % this.players.length

      if (inside) {
        // zipWithIndex: [Player0, Player1] => [(Player0, 0), (Player1, 1), ...]
        // case for pattern matching (player, index)
        val newPlayers = this.players.zipWithIndex.map { case (player, index) =>
          if (index != kotIndex) player.looseHeart(attacks) else player}

        val (cutPlayersVector, newKotIndexAndLapNr) = cutPlayers(newPlayers, kotIndex)
        // kot attacked, so the newLapNr is the newKotIndex because it was KOTs turn
        val kotWasAttackedAndCanDecide = false
        (Players(cutPlayersVector), newKotIndexAndLapNr, newKotIndexAndLapNr, kotWasAttackedAndCanDecide)
      } else {
        val newPlayers = this.players.zipWithIndex.map { case (player, index) =>
          if (index == kotIndex) player.looseHeart(attacks) else player}

        val (cutPlayers, cutLapNr, cutKotIndex, kotChanged) = cutKOT(newPlayers, kotIndex, tmpLapNr)
        // kot changes if he has 0 hearts after the attack
        if (kotChanged) {
          // kot was attacked, but can't decide to leave or stay, because he was removed from the game
          val kotWasAttackedAndCanDecide = false
          (Players(cutPlayers), cutLapNr, cutKotIndex, kotWasAttackedAndCanDecide)
        } else {
          // kot was attacked and can decide to leave or stay
          val kotWasAttackedAndCanDecide = true
          (Players(cutPlayers), cutLapNr, cutKotIndex, kotWasAttackedAndCanDecide)
        }
      }
    }
  }

  def cutPlayers(players: Vector[Player], kotIndexAndLapNr: Int): (Vector[Player], Int) = {
    /** Removes players with 0 hearts after the attack of KOT. kotIndex = lapNr because it was KOTs turn
     * if the index of a player, that has to be removed is smaller than the kotIndex=lapNr, than
     * the kotIndex=lapNr has to be decremented
     * @return the cutted Players and the newKotIndex
     */
    val indicesOfPlayersToRemove = players.zipWithIndex.map { case (player, index) =>
      if (player.heart == 0) index else 0}.filterNot(index => index == 0)
    // new kotIndex has to be decrementKotIndex times lower than current KotIndex
    val decrementKotIndex = indicesOfPlayersToRemove.count(playerToRemoveIndex => playerToRemoveIndex < kotIndexAndLapNr)
    val newKotIndexAndLapNr = kotIndexAndLapNr - decrementKotIndex
    val newPlayers = players.filterNot(player => player.heart == 0)
    (newPlayers, newKotIndexAndLapNr)
  }


  def cutKOT(playerVector: Vector[Player], kotIndex: Int, lapNr: Int): (Vector[Player], Int, Int, Boolean) = {
    val kot = playerVector(kotIndex)

    val newPlayerVector = if (kot.heart == 0) playerVector.filter(_ != kot) else playerVector
    val newLapNr = if (kot.heart == 0 && lapNr > kotIndex) lapNr - 1 else lapNr
    val newKotIndex = if (kot.heart == 0) newLapNr else kotIndex
    val changed = if (kot.heart == 0) true else false
    (newPlayerVector, newLapNr, newKotIndex, changed)
  }

  override def getLength: Int = {players.length}

  override def getPlayers: Vector[Player] = this.players

  override def playersXML(): String = {
    var playersStr = ""
    for (p <- getPlayers) {
      playersStr = playersStr.concat(p.name).concat("," + p.energy).concat("," + p.heart).concat("," + p.stars).concat(";")
    }
    playersStr
  }
  override def toPlayerVector: Vector[Player] = players

  override def playersStrToPlayers(playersStr: String, player: Player): Vector[Player] = {
    val playerStrVec: Vector[String] = playersStr.split(";").toVector
    val ret: Vector[Player] = for {p <- playerStrVec} yield player.strToPlayer(p)
    ret
  }

  override def set(players: Vector[Player]): Players = {
    copy(players)
  }

  override def getPlayersCreator(playerNames: String): PlayersCreator = PlayersCreator(playerNames)
}
