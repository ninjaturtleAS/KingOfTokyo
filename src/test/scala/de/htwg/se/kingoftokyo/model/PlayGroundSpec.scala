package de.htwg.se.kingoftokyo.model

import org.scalatest._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import Matchers._

@RunWith(classOf[JUnitRunner])
class PlayGroundSpec extends WordSpec with Matchers {

//  //initialize a test playground
//  val lapNr = 1;
//  val kot = 1;
//  val alex = Player("Alex");
//  val simon = Player("Simon");
//  val marco = Player("Marco");
//  val testString = "Alex, Simon, Marco";
//  val players = Players(Vector(alex, simon, marco));
//  val testResult =  RollResult(Vector(1, 2, 3, 4, 5, 6));
//
//  val complete = State.ThrowComplete;
//  val waitfirst = State.WaitFor1stThrow;
//  val waitsecond = State.WaitFor2ndThrow;
//  val waitAttack =  State.WaitForAttack;
//  val kotDecision = State.WaitForKotDecision;
//  val waitplayers = State.WaitForPlayerNames;
//
//  var playGroundWaitPlayers = PlayGround(players, lapNr, waitplayers, testResult, kot);
//  var playGroundWaitFirst = PlayGround(players, lapNr, waitfirst, testResult, kot);
//  var playGroundWaitSecond = PlayGround(players, lapNr, waitsecond, testResult, kot);
//  var playGroundWaitAttack = PlayGround(players, lapNr, waitAttack, testResult, kot);
//  var playGroundKOTDecision = PlayGround(players, lapNr, kotDecision, testResult, kot);
//  var playGroundComplete = PlayGround(players, lapNr, complete, testResult, kot);
//
//  "Playground" when {
//    "tested" should {
//      "have a KoT Index of 2" in {
//        playGroundComplete.switchKingOfTokyo(2).kingOfTokyo should be (2);
//      }
//
//      "get Good" in {
//        playGroundComplete.getGood(testResult).rollResult should be(testResult);
//      }
//
//      "return status" in {
//        playGroundWaitPlayers.getMessageFromStatus should be("Bitte Spielernamen kommagetrennt eingeben");
//        playGroundWaitFirst.getMessageFromStatus should be("Ihre Auswahl");
//        playGroundWaitSecond.getMessageFromStatus should be ("Ihre Auswahl");
//        playGroundComplete.getMessageFromStatus should be("Wurf wird ausgewertet");
//        playGroundWaitAttack.getMessageFromStatus should be("Wen wollen Sie angreifen?");
//        playGroundKOTDecision.getMessageFromStatus should be("Wollen Sie King of Tokyo bleiben (J/N)");
//
//      }
//
//      "Have a nice String" in {
//        playGroundComplete.toString should be ("King of Tokyo: Simon\n\n" +
//          "Alex (Energy: 0, Heart: 10, Stars: 0)\n" +
//          "Simon (Energy: 0, Heart: 10, Stars: 0)\n" +
//          "Marco (Energy: 0, Heart: 10, Stars: 0)\n" +
//          "\n" +
//          "Spieler am Zug: Simon\n\n" +
//          "Aktueller Wurf: 1 2 3 Energy Heart Attack \n\n" +
//          "Wurf wird ausgewertet");
//      }
//    }
//  }

}
