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

  override def getAttacks(rollResult: RollResultInterface, inside: Boolean, kotIndex : Int): (PlayersInterface, Boolean) = {
    val attacks = rollResult.evaluateAttacks()
    var tmp = this.players
    inside match {
      case true => {
        for (index <- 0 to (tmp.length - 1)) {
          if (index != kotIndex) {
            tmp = cutPlayers(tmp, index, attacks)
          }
        }
        (Players(tmp), false)
      }
      case false => {
        tmp = cutPlayers(tmp, kotIndex, attacks)
        if (attacks != 0) {
          (Players(tmp), true)
        }
        else {
          (Players(tmp), false)
        }
      }
    }
  }

  def cutPlayers(playerVector: Vector[Player], index: Int, attacks: Int): Vector[Player] = {
    var tmp = playerVector
    val tmpPlayer = tmp(index).looseHeart(attacks)
    tmp = tmp.updated(index, tmpPlayer)
    if (tmp(index).heart == 0) {
      tmp = tmp.filter(_ != tmpPlayer)
    }
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
  override def toPlayerVector: Vector[Player] = {players}

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
