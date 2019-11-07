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

  "A Player at start" when {
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
      "gain Heart not to 11" in {
        player.gainHeart(one) should be(Player("Alex", initHeat, initRest, initRest))
      }
      "gain Star to 1" in {
        player.gainStar(one) should be(Player("Alex", initHeat, initRest + 1, initRest))
      }
      "loose Star not to -1" in {
        player.looseStar(one) should be(Player("Alex", initHeat, initRest, initRest))
      }
      "gain Energy to 1" in {
        player.gainEnergy(one) should be(Player("Alex", initHeat, initRest, initRest + 1))
      }
      "spend Energy not to -1" in {
        player.spendEnergy(one) should be(Player("Alex", initHeat, initRest, initRest))
      }
      "have a nice Info String" in {
        player.info should be("Alex, Heart: 10, Stars: 0, Energy: 0")
      }
    }
  }

  val testInit = 5
  val testStar = 20

  "A Player mid. Game" when {
    "not new" should {
      val player = Player("Alex", testInit, testInit, testInit)
      "have a name" in {
        player.name should be("Alex")
      }
      "have heart" in {
        player.heart should be(testInit)
      }
      "have energy" in {
        player.energy should be(testInit)
      }
      "have stars" in {
        player.stars should be(testInit)
      }
      "have a nice String representation" in {
        player.toString should be("Player(Alex,5,5,5)")
      }

      "loose Heart to 4" in {
        player.looseHeart(one) should be(Player("Alex", testInit -1, testInit, testInit))
      }
      "gain Heart to 6" in {
        player.gainHeart(one) should be(Player("Alex", testInit + 1, testInit, testInit))
      }
      "gain Star to 6" in {
        player.gainStar(one) should be(Player("Alex", testInit, testInit + 1, testInit))
      }
      "gain Star to max 20" in {
        player.gainStar(testStar) should be(Player("Alex", testInit, testStar, testInit))
      }
      "loose Star to 4" in {
        player.looseStar(one) should be(Player("Alex", testInit, testInit -1 , testInit))
      }
      "gain Energy to 6" in {
        player.gainEnergy(one) should be(Player("Alex", testInit, testInit, testInit + 1))
      }
      "spend Energy to 4" in {
        player.spendEnergy(one) should be(Player("Alex", testInit, testInit, testInit - 1))
      }
      "have a nice Info String" in {
        player.info should be("Alex, Heart: 5, Stars: 5, Energy: 5")
      }
    }
  }
}
