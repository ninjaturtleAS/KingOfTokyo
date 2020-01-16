package de.htwg.se.kingoftokyo.model.playersComp.playersBaseComponent

import com.google.inject.Inject
import de.htwg.se.kingoftokyo.model.playersComp.PlayersInterface
import de.htwg.se.kingoftokyo.model.rollResultComp.RollResultInterface

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

  override def getAttacks(rollResult: RollResultInterface, inside: Boolean, kot : Int): (PlayersInterface, Boolean) = {
    val attacks = rollResult.evaluateAttacks()
    inside match {
      case true => {
        var tmp = this.players
        for (p <- 0 to (players.length - 1) if p != kot) {
          val tmpPlayer = this.players(p).looseHeart(attacks)
          if (tmpPlayer.heart == 0)
          tmp = tmp.updated(p, tmpPlayer)
        }
        (Players(tmp), false)
      }
      case false => {
        val tmpPlayer = this.players(kot)
          .looseHeart(attacks)
        if (attacks != 0) {
          (copy(this.players.updated(kot, tmpPlayer)), true)
        }
        else {
          (copy(this.players.updated(kot, tmpPlayer)), false)
        }
      }
    }
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
