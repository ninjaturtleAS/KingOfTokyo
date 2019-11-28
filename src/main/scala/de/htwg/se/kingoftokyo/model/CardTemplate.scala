package de.htwg.se.kingoftokyo.model

trait CardTemplate {
  def switch(playGround: PlayGround): PlayGround
}

case class CardGainExtraEnergy() extends CardTemplate {
  override def switch(playGround: PlayGround): PlayGround = ???

  def gainEnergy(rollResult: RollResult, players: Players, index:Int): Players = {
    players.getGood(rollResult, index)
  }
}
