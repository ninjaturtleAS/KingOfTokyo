package de.htwg.se.kingoftokyo.model

import scala.collection.JavaConverters._

case class Players(players: Vector[Player]) {
  //def this () = this(Vector.tabulate(1) { (x) => new Player("")})
  def this() = this(Vector.empty)
  def addPlayer(player: Player): Players = Players(players :+ player)

  def toStringVector: Vector[String] = for (p <- players) yield p.name

  def getGood(rollResult: RollResult, playerIndex: Int): Players = {
    val tmpPlayer = this.players(playerIndex)
      .gainEnergy(rollResult.evaluateEnergy())
      .gainHeart(rollResult.evaluateHeart())
      .gainStar(rollResult.evaluateStars())
    copy(this.players.updated(playerIndex, tmpPlayer))
  }

  def getAttacks(rollResult: RollResult, inside: Boolean, kot : Int): Players = {
    inside match {
      case true => {
        for (p <- 0 to (players.length - 1) if p != kot) {
          val tmpPlayer = this.players(p)
            .looseHeart(rollResult.evaluateAttacks())
          this.players.updated(p, tmpPlayer)
        }
        Players(this.players)
      }
      case false => {
        val tmpPlayer = this.players(kot)
          .looseHeart(rollResult.evaluateAttacks())
        copy(this.players.updated(kot, tmpPlayer))
      }
    }
  }


  def getLength(): Int = {players.length}

  def toPlayerVector: Vector[Player] = {players}

}
