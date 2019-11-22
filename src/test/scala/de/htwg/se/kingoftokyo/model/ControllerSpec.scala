
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
  val lapNr = 1;
  val kot = 1;
  val alex = Player("Alex");
  val simon = Player("Simon");
  val marco = Player("Marco");
  val testString = "Alex, Simon, Marco";
  val players = Players(Vector(alex, simon, marco));
  val statusMessage = new StatusMessageOld("Hallo");
  val rollResult =  RollResult(Vector(1, 2, 3, 4, 5, 6));

  val complete = State.ThrowComplete;
  val waitfirst = State.WaitFor1stThrow;
  val waitsecond = State.WaitFor2ndThrow;
  val waitAttack =  State.WaitForAttack;
  val kotDecision = State.WaitForKotDecision;
  val waitplayers = State.WaitForPlayerNames;

  var playGroundWaitPlayers = PlayGround(players, lapNr, statusMessage, waitplayers, rollResult, kot);


  "Controller " when {
    "new" should {
      val controller = new Controller(playGroundWaitPlayers);

      "ask for PlayerNames" in {
        controller.askForPlayerNames()  should be ();
      }

      "with given 3 names" in {
        controller.createPlayers((testString)).players.players.length should be (3);
      }


    }
  }


}
