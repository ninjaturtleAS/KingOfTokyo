package de.htwg.se.kingoftokyo.model
import org.scalatest._

class CardGainExtraHeartSpec extends WordSpec with Matchers {
  val alex = Player("Alex")
  val simon = Player("Simon")
  val marco = Player("Marco")
  val players = Players(Vector(alex, simon, marco))
  val newPlayers = Players(Vector(Player("Alex", 0, 10, 4), simon, marco))
  val testResult =  RollResult(Vector(1, 1, 1, 1, 1, 1))
  val cTS = CardGainExtraEnergy()
  "A CardGainExtraHeart" should  {
      "gain heart" in {
         cTS.gainEnergy(testResult, players, 0) should be (newPlayers)
    }
  }
}

