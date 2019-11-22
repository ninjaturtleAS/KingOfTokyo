
package de.htwg.se.kingoftokyo.model
import org.scalatest._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import Matchers._

import de.htwg.se.kingoftokyo.aview.Tui
import de.htwg.se.kingoftokyo.controller.Controller
import de.htwg.se.kingoftokyo.model._

@RunWith(classOf[JUnitRunner])
class ControllerSpec extends WordSpec with Matchers {

  //initialize a test playground
  val lapNr = 1
  val kot = 1
  val alex = Player("Alex")
  val simon = Player("Simon")
  val marco = Player("Marco")
  val testString = "Alex, Simon, Marco"
  val players = Players(Vector())
  val testResult =  RollResult(Vector(1, 2, 3, 4, 5, 6))

  val complete = State.ThrowComplete
  val waitfirst = State.WaitFor1stThrow
  val waitsecond = State.WaitFor2ndThrow
  val waitAttack =  State.WaitForAttack
  val kotDecision = State.WaitForKotDecision
  val waitplayers = State.WaitForPlayerNames

  var playGroundWaitPlayers = PlayGround(players, lapNr, waitplayers, testResult, kot)
  var playGroundWaitFirst = PlayGround(players, lapNr, waitfirst, testResult, kot)
  var playGroundWaitSecond = PlayGround(players, lapNr, waitsecond, testResult, kot)
  var playGroundWaitAttack = PlayGround(players, lapNr, waitAttack, testResult, kot)
  var playGroundKOTDecision = PlayGround(players, lapNr, kotDecision, testResult, kot)
  var playGroundComplete = PlayGround(players, lapNr, complete, testResult, kot)


  "Controller " when {
    "new" should {
      val controller = new Controller(playGroundWaitPlayers)
      val controller1 =  new Controller(playGroundWaitFirst)
      val controller2 = new Controller(playGroundWaitSecond)
      val controllerComp = new Controller(playGroundComplete)
      val controllerKOT =  new Controller(playGroundKOTDecision)
      val controllerAttack = new Controller(playGroundWaitAttack)

      "ask for PlayerNames" in {
        controller.askForPlayerNames()  should be ()
      }


      "with given 3 names" in {
        controller.createPlayers((testString)).players.players.length should be (3)
      }

      "incrementing Lap Number" in {
        controller.incLapNr.lapNr should be (2)
      }

      "throw dies" in {
        controller1.throwDies().status should be (State.WaitFor2ndThrow)
        controller2.throwDies().status should be (State.ThrowComplete)
      }

      "complete throw" in {
        controller2.completeThrow().status should be (State.ThrowComplete)
      }

      "attack" in {
        controller.attack("test").status should be (State.WaitFor1stThrow)
      }

      "filter throw results" in {
        controller1.filterThrowResult("0 1 2 3 4 5").rollResult.toString() should be ("1 2 3 Energy Heart Attack ")
      }

      var playGroundGood = PlayGround(Players(Vector(alex, simon, marco)), lapNr, complete, testResult, kot)
      val controllerGood = new Controller(playGroundGood)
      "get Good" in {
        controllerGood.evaluateThrow().rollResult should be(testResult)
      }
      "have a nice String" in {
        controllerGood.playGroundToString() should be ("King of Tokyo: Simon\n\n" +
          "Alex (Energy: 0, Heart: 10, Stars: 0)\n" +
          "Simon (Energy: 1, Heart: 10, Stars: 0)\n" +
          "Marco (Energy: 0, Heart: 10, Stars: 0)\n" +
          "\n" +
          "Spieler am Zug: Simon\n\n" +
          "Aktueller Wurf: 1 2 3 Energy Heart Attack \n\n" +
          "Wurf wird ausgewertet")
      }


    }
  }


}
