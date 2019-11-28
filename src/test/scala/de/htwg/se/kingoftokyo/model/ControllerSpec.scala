
package de.htwg.se.kingoftokyo.model
import org.scalatest._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import Matchers._

import de.htwg.se.kingoftokyo.aview.Tui
import de.htwg.se.kingoftokyo.controller._
import de.htwg.se.kingoftokyo.model._

@RunWith(classOf[JUnitRunner])
class ControllerSpec extends WordSpec with Matchers {

  //initialize a test playground
  val lapNr = 1;
  val kot = 1;
  val alex = Player("Alex");
  val simon = Player("Simon");
  val marco = Player("Marco");
  val testString = "Alex, Simon, Marco";
  val players = Players(Vector());
  val testResult =  RollResult(Vector(1, 2, 3, 4, 5, 6));

  var playGroundWaitPlayers = PlayGround(players, lapNr, testResult, kot);
  var playGroundWaitFirst = PlayGround(players, lapNr, testResult, kot);
  var playGroundWaitSecond = PlayGround(players, lapNr, testResult, kot);
  var playGroundKOTDecision = PlayGround(players, lapNr, testResult, kot);
  var playGroundComplete = PlayGround(players, lapNr, testResult, kot);


  "Controller " when {
    "new" should {
      val controller = new Controller(playGroundWaitPlayers);
      val controller1 =  new Controller(playGroundWaitFirst);
      val controller2 = new Controller(playGroundWaitSecond);
      val controllerComp = new Controller(playGroundComplete);
      val controllerKOT =  new Controller(playGroundKOTDecision);


      "with given 3 names" in {
        controller.createPlayers((testString)).players.players.length should be (3);
      }

      "incrementing Lap Number" in {
        controller.incLapNr.lapNr should be (2);
      }

      "throw dies" in {
        controller1.throwDies().players should be (players);
        controller2.throwDies().players should be (players);
      }

      "complete throw" in {
        controller2.completeThrow().players should be (players);
      }


      "filter throw results" in {
        controller1.filterThrowResult("0 1 2 3 4 5").rollResult.toString() should be ("1 2 3 Energy Heart Attack ");
      }

      "get Good" in {
        var playGroundGood = PlayGround(Players(Vector(alex, simon)), lapNr, testResult, kot);
        val controllerGood = new Controller(playGroundGood);
        controllerGood.evaluateThrow().players.players(lapNr).energy should be(1);
      }

      "restet increase LapNr for next turn" in {
        controllerComp.nextTurn().lapNr should be(lapNr + 1)
      }
    }
  }


}
