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
          if (index != kotIndex) player.looseHeart(attacks) else player
        }
        val (cutPlayers, cutKotIndex, _) = cutPlayerR(newPlayers, 0, kotIndex, newPlayers.length - 1, false)
        // kot wasn't attacked
        val kotWasAttackedAndCanDecide = false
        (Players(cutPlayers), tmpLapNr, cutKotIndex, kotWasAttackedAndCanDecide)
      } else {
        val newPlayers = looseHeart(this.players, kotIndex, attacks)
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

  def cutPlayerR(players: Vector[Player], currentIndex: Int, kotIndex: Int, lengthPlayers: Int, prevChange: Boolean)
  : (Vector[Player], Int, Boolean) = {
    var tmp = players
    var tmpCurrent = currentIndex
    var tmpKOT = kotIndex
    var tmpLength = lengthPlayers
    var tmpChanged = prevChange
    var zero = false
    var returnTuple = (players, kotIndex, prevChange)

    if (tmpCurrent == tmpKOT) {
      if (tmpCurrent < tmpLength) {
        tmpCurrent += 1
        returnTuple = cutPlayerR(tmp, tmpCurrent, tmpKOT, tmpLength, tmpChanged)
      } else {
        returnTuple  = (tmp, tmpKOT, tmpChanged)
      }
    } else {
      if (tmp(tmpCurrent).heart ==  0) {
        tmp = tmp.filter(_ != tmp(tmpCurrent))
        tmpChanged = true
        zero = true
        tmpLength -= 1
      }
      if (zero) {
        if (tmpCurrent < tmpKOT) {
          tmpKOT -= 1
          returnTuple = cutPlayerR(tmp, tmpCurrent, tmpKOT, tmpLength, tmpChanged)
        } else {
          if (tmpCurrent <= tmpLength) {
            returnTuple = cutPlayerR(tmp, tmpCurrent, tmpKOT, tmpLength, tmpChanged)
          } else {
            returnTuple = (tmp, tmpKOT, tmpChanged)
          }
        }
      } else {
        if (tmpCurrent < tmpLength) {
          tmpCurrent += 1
          returnTuple = cutPlayerR(tmp, tmpCurrent, tmpKOT, tmpLength, tmpChanged)
        } else {
          returnTuple = (tmp, tmpKOT, tmpChanged)
        }
      }
    }
    returnTuple
  }

  def cutKOT(playerVector: Vector[Player], kotIndex: Int, lapNr: Int): (Vector[Player], Int, Int, Boolean) = {
    val kot = playerVector(kotIndex)

    val newPlayerVector = if (kot.heart == 0) playerVector.filter(_ != kot) else playerVector
    val newLapNr = if (kot.heart == 0 && lapNr > kotIndex) lapNr - 1 else lapNr
    val newKotIndex = if (kot.heart == 0) newLapNr else kotIndex
    val changed = if (kot.heart == 0) true else false
    (newPlayerVector, newKotIndex, newLapNr, changed)
  }

  def looseHeart(playerVector: Vector[Player], index: Int, attacks: Int): Vector[Player] = {
    var tmp = playerVector
    val tmpPlayer = tmp(index).looseHeart(attacks)
    tmp = tmp.updated(index, tmpPlayer)
    tmp
  }

  override def getLength(): Int = {players.length}

  override def getPlayers(): Vector[Player] = this.players

  override def playersXML(): String = {
    var playersStr = ""
    for (p <- getPlayers()) {
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
