package de.htwg.se.kingoftokyo.model

import java.security.SecureRandom

import scala.collection.mutable.{ArrayBuffer, ListBuffer}

case class PlayersCreator(stringOfNames: String) {
  val aNames: ArrayBuffer[String] = new ArrayBuffer[String]()
  val numberOfPlayers = aNames.length
  stringOfNames.split(",").copyToBuffer(aNames)
  def getPlayers: IndexedSeq[Player] = {
    for {
      i <- 0 until numberOfPlayers
    } yield getRandomPlayer

  }

  def getRandomPlayer: Player = {
    val random = Die(aNames.length)(new SecureRandom()).roll - 1
    val player = Player(aNames(random))
    aNames.remove(random)
    player
  }
}
