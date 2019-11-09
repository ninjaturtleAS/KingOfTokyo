package de.htwg.se.kingoftokyo.model

case class Players(players: IndexedSeq[Player]) {
  var kingOfTokyo: Option[Player] = None
}
