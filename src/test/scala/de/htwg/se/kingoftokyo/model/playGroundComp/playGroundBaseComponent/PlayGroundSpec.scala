
package de.htwg.se.kingoftokyo.model.playGroundComp.playGroundBaseComponent

import de.htwg.se.kingoftokyo.model.playersComp.playersBaseComponent.{Player, Players}
import de.htwg.se.kingoftokyo.model.rollResultComp.rollResultBaseComponent.RollResult
import de.htwg.se.kingoftokyo.controller.controllerComponent.State._
import org.junit.runner.RunWith
import org.scalatest._
import org.scalatest.junit.JUnitRunner

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
  val playersFinished = Players(Vector(Player("Alex", 0, 10, 20), simon, marco))
  val testResult =  RollResult(Vector(1, 2, 3, 4, 5, 6))
  val noAttackResult =  RollResult(Vector(1, 2, 3, 4, 5, 5))

  var playGroundWaitPlayers = PlayGround(players, lapNr, testResult, kot)
  var playGroundWaitFirst = PlayGround(players, lapNr, testResult, kot)
  var playGroundWaitSecond = PlayGround(players, lapNr, testResult, kot)
  var playGroundWaitAttack = PlayGround(players, lapNr, testResult, kot)
  var playGroundKOTDecision = PlayGround(players, lapNr, testResult, kot)
  var playGroundComplete = PlayGround(players, lapNr, testResult, kot)
  var pgNoAttacks = PlayGround(players, lapNr, noAttackResult, 2)
  var playGroundFinishedStars = PlayGround(playersFinished, lapNr, testResult, kot)

  "Playground" when {
    "tested" should {
      "have a KoT Index of 2" in {
        playGroundComplete.switchKingOfTokyo(2).getKOT should be (2)
      }

      "attack player out of tokyo" in {
        playGroundComplete.attack(testResult)._1.getPlayers.getPlayers(0).heart should be (9)
        playGroundComplete.attack(testResult)._1.getPlayers.getPlayers(2).heart should be (9)
        pgNoAttacks.attack(noAttackResult)._2 should be (false)
      }

      "get Good" in {
        playGroundComplete.getGood(testResult).getRollResult should be(testResult)
      }

      "filter empy choice" in {
         playGroundComplete.filterThrowResult("").getPlayers should be(players)
      }
      "filter non empty choice" in {
        playGroundComplete.filterThrowResult("1").getPlayers should be(players)
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
        playGroundComplete.incLapNr().getLapNr should be(lapNr + 1)
      }
      "return a state" in {
        playGroundComplete.getState should be (WaitForPlayerNames)
      }
      "have a factory method, to create a pg with a given state" in {
        playGroundWaitAttack.set(players, lapNr, testResult, kot, WaitForPlayerNames) should be (playGroundComplete)
      }
      "be able to check if game is finished" in {
        playGroundFinishedStars.checkFinish._1 should be (true)
      }
    }
  }
}