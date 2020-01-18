package de.htwg.se.kingoftokyo.model.playersComp.playersBaseComponent

import com.google.inject.Inject
import de.htwg.se.kingoftokyo.model.playersComp.PlayersInterface
import de.htwg.se.kingoftokyo.model.rollResultComp.RollResultInterface
import org.graalvm.compiler.lir.alloc.trace.TraceGlobalMoveResolutionPhase

case class Players (players: Vector[Player]) extends PlayersInterface {

  def this() = this(Vector.empty)

  override def getEmptyPlayers: Players = Players(Vector.empty)

  def addPlayer(player: Player): Players = Players(players :+ player)

  override def addNewPlayer(name:String, energy: Int, heart: Int,stars: Int): PlayersInterface = {
    Players(players :+ Player(name, energy, heart, stars))
  }

  override def toStringVector: Vector[String] = for (p <- players) yield p.name




  override def getGood(rollResult: RollResultInterface, playerIndex: Int): PlayersInterface = {
    val tmpPlayer = this.players(playerIndex)
      .gainEnergy(rollResult.evaluateEnergy())
      .gainHeart(rollResult.evaluateHeart())
      .gainStar(rollResult.evaluateStars())
    copy(this.players.updated(playerIndex, tmpPlayer))
  }

  override def getAttacks(rollResult: RollResultInterface, inside: Boolean, kotIndex: Int, lapNr: Int):
  (PlayersInterface, Int, Int, Boolean) = {

    val attacks = rollResult.evaluateAttacks()
    if (attacks == 0) {
      (this, kotIndex, lapNr, false)
    }
    else {
      var tmp = this.players
      var tmpKOT = kotIndex
      var tmpLapNr = lapNr % tmp.length
      var tmpCut: (Vector[Player], Int, Boolean) = (tmp, tmpKOT, false)
      var tmpCutKOT: (Vector[Player], Int, Int, Boolean) = (tmp, tmpKOT, tmpLapNr, false)

      inside match {
        case true => {
          for (index <- 0 until tmp.length if index != tmpKOT) {
            tmp = looseHeart(tmp, index, attacks)
          }
          tmpCut = cutPlayerR(tmp, 0, tmpKOT, tmp.length - 1, false)
          tmp = tmpCut._1
          tmpKOT = tmpCut._2
          tmpLapNr = tmpKOT
          (Players(tmp), tmpKOT, tmpLapNr, false)
        }
        case false => {
          tmp = looseHeart(tmp, tmpKOT, attacks)
          tmpCutKOT = cutKOT(tmp, tmpKOT, tmpLapNr)
          if (tmpCutKOT._4) {
            tmp = tmpCutKOT._1
            tmpKOT = tmpCutKOT._2
            tmpLapNr = tmpCutKOT._3
            (Players(tmp), tmpKOT, tmpLapNr, false)
          } else {
            (Players(tmp), tmpKOT, tmpLapNr, true)
          }
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
    var tmp = playerVector
    var kotTmp = kotIndex
    var lapTmp = lapNr
    var tmpPlayer = tmp(kotTmp)
    var changed = false

    if (tmpPlayer.heart == 0) {
      tmp = tmp.filter(_ != tmpPlayer)
      if (lapTmp > kotTmp) {
        lapTmp -= 1
      }
      kotTmp = lapTmp
      changed = true
    }
    (tmp, kotTmp, lapTmp, changed)
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
