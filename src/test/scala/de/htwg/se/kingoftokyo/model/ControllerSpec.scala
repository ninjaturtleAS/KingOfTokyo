
package de.htwg.se.kingoftokyo.model
import org.scalatest._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import Matchers._

import de.htwg.se.kingoftokyo.controller._

@RunWith(classOf[JUnitRunner])
class ControllerSpec extends WordSpec with Matchers {

  //initialize a test playground
  val lapNr = 0
  val kot = 1
  val alex = Player("Alex")
  val simon = Player("Simon")
  val marco = Player("Marco")
  val testString = "Alex, Simon, Marco"
  val players = Players(Vector())
  val testResult =  RollResult(Vector(1, 2, 3, 4, 5, 6))

  var playGroundWaitPlayers = PlayGround(players, lapNr, testResult, kot)
  var playGroundWaitFirst = PlayGround(players, lapNr, testResult, kot)
  var playGroundWaitSecond = PlayGround(players, lapNr, testResult, kot)
  var playGroundKOTDecision = PlayGround(players, lapNr, testResult, kot)
  var playGroundComplete = PlayGround(players, lapNr, testResult, kot)


  "Controller " when {
    "new" should {
      val controller = new Controller(playGroundWaitPlayers)
      val controllerStr = new Controller(playGroundWaitPlayers)
      val controller1 =  new Controller(playGroundWaitFirst)
      val controller2 = new Controller(playGroundWaitSecond)
      val controller3 = new Controller(playGroundWaitSecond)
      val controllerComp = new Controller(playGroundComplete)
      val controllerKOT =  new Controller(playGroundKOTDecision)


      "with given 3 names" in {
        controller.createPlayers((testString)).players.players.length should be (3)
      }

      "throw dies" in {
        controller1.throwDies().players should be (players)
        controller2.throwDies().players should be (players)
      }

      "complete throw" in {
        controller2.completeThrow().players should be (players)
      }

      "increase lap number" in {
        controller3.incLapNr.lapNr should be (1)
      }

      "filter throw results" in {
        controller1.filterThrowResult("0 1 2 3 4 5").rollResult.toString() should be ("1 2 3 Energy Heart Attack ")
        controller1.state = State.WaitFor1stThrow
        controller1.filterThrowResult("0 1 2 3 4 5").rollResult.toString() should be ("1 2 3 Energy Heart Attack ")
      }

      "get Good" in {
        var playGroundGood = PlayGround(Players(Vector(alex, simon)), lapNr, testResult, kot)
        val controllerGood = new Controller(playGroundGood)
        controllerGood.evaluateThrow().players.players(lapNr).energy should be(1)
        controllerGood.evaluateThrow().players.players(kot).heart should be(9)
      }

      "Have an nice String representaion" in {
        controllerStr.playGroundToString() should be (playGroundWaitPlayers.toString)
      }

      "increase LapNr for next turn" in {
        controllerComp.nextTurn().lapNr should be(lapNr + 1)
      }
    }
  }


}
