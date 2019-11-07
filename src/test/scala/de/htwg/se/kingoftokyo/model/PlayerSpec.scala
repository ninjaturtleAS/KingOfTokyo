package de.htwg.se.kingoftokyo.model

import org.scalatest._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import Matchers._


@RunWith(classOf[JUnitRunner])
class PlayerSpec extends WordSpec with Matchers {
  val initHeat = 10
  val initRest =  0
  val one = 1

  "A Player" when {
    "new" should {
      val player = Player("Alex")
      "have a name" in {
        player.name should be("Alex")
      }
      "have heart" in {
        player.heart should be(initHeat)
      }
      "have energy" in {
        player.energy should be(initRest)
      }
      "have stars" in {
        player.stars should be(initRest)
      }
      "have a nice String representation" in {
        player.toString should be("Player(Alex,10,0,0)")
      }

      "loose Heart to 9" in {
        player.looseHeart(one) should be(Player("Alex", initHeat -1, initRest, initRest))
      }
      "gain Heart to 10" in {
        player.gainHeart(one) should be(Player("Alex", initHeat + 1, initRest, initRest))
      }
      "gain Star to 1" in {
        player.gainStar(one) should be(Player("Alex", initHeat, initRest + 1, initRest))
      }
      "loose Star to 0" in {
        player.looseStar(one) should be(Player("Alex", initHeat, initRest - 1, initRest))
      }
      "gain Energy to 1" in {
        player.gainEnergy(one) should be(Player("Alex", initHeat, initRest, initRest + 1))
      }
      "spend Energyto 0" in {
        player.spendEnergy(one) should be(Player("Alex", initHeat, initRest, initRest - 1))
      }
      "have a nice Info String" in {
        player.info should be("Alex, Heart: 10, Stars: 0, Energy: 0")
      }
    }
  }
}
