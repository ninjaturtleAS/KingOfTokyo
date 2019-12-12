package de.htwg.se.kingoftokyo.model

import org.scalatest._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import Matchers._

@RunWith(classOf[JUnitRunner])
class PlayGroundSpec extends WordSpec with Matchers {

  //initialize a test playground
  val lapNr = 1
  val kot = 1
  val alex = Player("Alex")
  val simon = Player("Simon")
  val marco = Player("Marco")
  val testString = "Alex, Simon, Marco"
  val players = Players(Vector(alex, simon, marco))
  val testResult =  RollResult(Vector(1, 2, 3, 4, 5, 6))

  var playGroundWaitPlayers = PlayGround(players, lapNr, testResult, kot)
  var playGroundWaitFirst = PlayGround(players, lapNr, testResult, kot)
  var playGroundWaitSecond = PlayGround(players, lapNr, testResult, kot)
  var playGroundWaitAttack = PlayGround(players, lapNr, testResult, kot)
  var playGroundKOTDecision = PlayGround(players, lapNr, testResult, kot)
  var playGroundComplete = PlayGround(players, lapNr, testResult, kot)

  "Playground" when {
    "tested" should {
      "have a KoT Index of 2" in {
        playGroundComplete.switchKingOfTokyo(2).kingOfTokyo should be (2)
      }

      "attack player out of tokyo" in {
        playGroundComplete.attack(testResult).players.players(0).heart should be (9)
        playGroundComplete.attack(testResult).players.players(2).heart should be (9)
      }

      "get Good" in {
        playGroundComplete.getGood(testResult).rollResult should be(testResult)
      }

      "filter empy choice" in {
         playGroundComplete.filterThrowResult("").players should be(players)
      }
      "filter non empty choice" in {
        playGroundComplete.filterThrowResult("1").players should be(players)
      }

      "Have a nice String" in {
        playGroundComplete.toString should be (
          "\n" +
          "\n" +
          "\n" +
          "\n" +
          "\n" +
          "\n" +
          "\n" +
          "\n" +
          "\nKing of Tokyo: Simon" +
          "\n" +
          "\nAlex (Energy: 0, Heart: 10, Stars: 0)" +
          "\nSimon (Energy: 0, Heart: 10, Stars: 0)" +
          "\nMarco (Energy: 0, Heart: 10, Stars: 0)" +
          "\n" +
          "\nSpieler am Zug: Simon" +
          "\n" +
          "\nAktueller Wurf: 1 2 3 Energy Heart Attack " +
          "\n" +
          "\n");
      }

      "increase LapNr" in {
        playGroundComplete.incLapNr().lapNr should be(lapNr + 1)
      }
    }
  }
}