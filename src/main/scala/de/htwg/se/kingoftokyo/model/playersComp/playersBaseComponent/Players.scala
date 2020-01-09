package de.htwg.se.kingoftokyo.model.playersComp.playersBaseComponent

import com.google.inject.Inject
import de.htwg.se.kingoftokyo.model.playersComp.PlayersInterface
import de.htwg.se.kingoftokyo.model.rollResultComp.RollResultInterface

case class Players (players: Vector[Player]) extends PlayersInterface {

  def this() = this(Vector.empty)
  def addPlayer(player: Player): Players = Players(players :+ player)

  override def toStringVector: Vector[String] = for (p <- players) yield p.name




  override def getGood(rollResult: RollResultInterface, playerIndex: Int): PlayersInterface = {
    val tmpPlayer = this.players(playerIndex)
      .gainEnergy(rollResult.evaluateEnergy())
      .gainHeart(rollResult.evaluateHeart())
      .gainStar(rollResult.evaluateStars())
    copy(this.players.updated(playerIndex, tmpPlayer))
  }

  override def getAttacks(rollResult: RollResultInterface, inside: Boolean, kot : Int): PlayersInterface = {
    inside match {
      case true => {
        var tmp = this.players
        for (p <- 0 to (players.length - 1) if p != kot) {
          val tmpPlayer = this.players(p)
            .looseHeart(rollResult.evaluateAttacks())
          tmp = tmp.updated(p, tmpPlayer)
        }
        Players(tmp)
      }
      case false => {
        val tmpPlayer = this.players(kot)
          .looseHeart(rollResult.evaluateAttacks())
        copy(this.players.updated(kot, tmpPlayer))
      }
    }
  }


  override def getLength(): Int = {players.length}

  override def getPlayers(): Vector[Player] = this.players

  override def playersXML(): String = {
    var playersStr = ""
    for (p <- getPlayers()) {
      playersStr = playersStr.concat(p.name).concat("," + p.energy).concat("," + p.heart).concat("," + p.stars) //f"$p.name%s ,$p.energy%d ,$p.heart%d ,$p.stars%d \n")
    }
    playersStr
  }
  override def toPlayerVector: Vector[Player] = {players}

  override def playersStrToPlayers(playersStr: String, player: Player): Vector[Player] = {
    val playerStrVec: Vector[String] = playersStr.split("\n").toVector
    val ret: Vector[Player] = for {p <- playerStrVec} yield player.strToPlayer(p)
    ret
  }

  override def set(players: Vector[Player]): Players = {
    copy(players)
  }
}
