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
        player.toString should be("Player(Alex,0,10,0)")
      }

      "loose Heart to 9" in {
        player.looseHeart(one) should be(Player("Alex", initRest, initHeat -1, initRest))
      }
      "not loose Heart below Zero" in {
        player.looseHeart(11) should be (Player("Alex", initRest, 0, initRest));
      }
      "gain Heart not to 11" in {
        player.gainHeart(one) should be(Player("Alex", initRest, initHeat, initRest))
      }
      "gain Star to 1" in {
        player.gainStar(one) should be(Player("Alex", initRest, initHeat, initRest + 1))
      }
      "loose Star not to -1" in {
        player.looseStar(one) should be(Player("Alex",  initRest, initHeat, initRest))
      }
      "gain Energy to 1" in {
        player.gainEnergy(one) should be(Player("Alex", initRest + 1, initHeat, initRest))
      }
      "spend Energy not to -1" in {
        player.spendEnergy(one) should be(Player("Alex", initRest, initHeat, initRest))
      }
      "have a nice Info String" in {
        player.info should be("Alex (Energy: 0, Heart: 10, Stars: 0)")
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
        player.looseHeart(one) should be(Player("Alex", testInit, testInit - 1, testInit))
      }
      "gain Heart to 6" in {
        player.gainHeart(one) should be(Player("Alex", testInit, testInit + 1, testInit))
      }
      "gain Star to 6" in {
        player.gainStar(one) should be(Player("Alex", testInit, testInit, testInit + 1))
      }
      "gain Star to max 20" in {
        player.gainStar(testStar) should be(Player("Alex", testInit, testInit, testStar))
      }
      "loose Star to 4" in {
        player.looseStar(one) should be(Player("Alex", testInit, testInit , testInit - 1))
      }
      "gain Energy to 6" in {
        player.gainEnergy(one) should be(Player("Alex", testInit + 1, testInit, testInit))
      }
      "spend Energy to 4" in {
        player.spendEnergy(one) should be(Player("Alex", testInit - 1, testInit, testInit))
      }
      "have a nice Info String" in {
        player.info should be("Alex (Energy: 5, Heart: 5, Stars: 5)")
      }
    }
  }
}
