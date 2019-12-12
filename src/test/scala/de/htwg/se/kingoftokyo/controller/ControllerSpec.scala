
package de.htwg.se.kingoftokyo.controller

import de.htwg.se.kingoftokyo.model.{PlayGround, Player, Players, RollResult}
import org.junit.runner.RunWith
import org.scalatest._
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class ControllerSpec extends WordSpec with Matchers {

  //initialize a test playground
  val lapNr = 0
  val kot = 1
  val initHeart =  10
  val alex = Player("Alex")
  val simon = Player("Simon")
  val marco = Player("Marco")
  val testString = "Alex, Simon, Marco"
  val players = Players(Vector())
  val testResult =  RollResult(Vector(1, 2, 3, 4, 5, 6))
  val badResult = RollResult(Vector(6, 6, 6, 6, 6, 6))

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
      val controllerUndoRedu =  new Controller(playGroundWaitFirst)


      "new Game" in {
        controllerComp.newGame.players.players.length should be (0)
      }

      "with given 3 names" in {
        controller.createPlayers((Some(testString))).players.players.length should be (3)
      }


      "with no names" in {
        controller.createPlayers(None) should be (controller.playGround)
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
        controller1.filterThrowResult("String") should be (controller1.playGround)
        controller1.filterThrowResult("0 1 2 3 4 5").rollResult.toString() should be ("1 2 3 Energy Heart Attack ")
        controller1.state = State.WaitFor1stThrow
        controller1.filterThrowResult("0 1 2 3 4 5").rollResult.toString() should be ("1 2 3 Energy Heart Attack ")
      }

      "evaluate Results" in {
        var playGroundGood = PlayGround(Players(Vector(alex, simon)), lapNr, testResult, kot)
        var playGroundBad = playGroundGood.copy(Players(Vector(alex, simon)),  0, badResult , 1)
        val controllerGood = new Controller(playGroundGood)
        val controllerBad = new Controller(playGroundBad)
        controllerGood.evaluateThrow().players.players(lapNr).energy should be(1)
        controllerBad.evaluateThrow().players.players(kot).heart should be(initHeart - 6)
      }

      "Have an nice String representaion" in {
        controllerStr.playGroundToString() should be (playGroundWaitPlayers.toString)
      }

      "increase LapNr for next turn" in {
        controllerComp.nextTurn().lapNr should be(lapNr + 1)

      }
      "undo redo" in {
        controllerUndoRedu.undo should be(controllerUndoRedu.redo)
      }
    }
  }


}
