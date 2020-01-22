
package de.htwg.se.kingoftokyo.model.playGroundComp.playGroundBaseComponent

import de.htwg.se.kingoftokyo.model.playersComp.playersBaseComponent.{Player, Players}
import de.htwg.se.kingoftokyo.model.rollResultComp.rollResultBaseComponent.RollResult
import org.scalatest._


class PlayersSpec extends WordSpec with Matchers {

  "Players at start" when {
    "new" should {
      val pl = Players(Vector(Player("Simon"), Player("Alex")))
      val plXML = Players(Vector(Player("Simon"), Player("Alex")))
      val plAdded = Players(Vector(Player("Simon"), Player("Alex"), Player("Marco")))
      val vecPlayers = pl.toStringVector
      val testResult = RollResult(Vector(6, 6, 6, 6, 6, 6))
      val noAttack = RollResult(Vector(1,2,3,1,2,3))
      val testPlayer = Vector(Player("Alex", 5, 0, 10), Player("Simon", 5, 0, 10), Player("Klaus", 5, 5, 10), Player("Marco", 5, 0, 10))
      val plTest = Players(testPlayer)

      val testPlayer2 = Vector(Player("Alex", 5, 0, 10), Player("Simon", 5, 0, 10), Player("Klaus", 5, 5, 10), Player("Marco", 5, 10, 10))
      val plTest2 = Players(testPlayer2)

      val testPlayer3 = Vector(Player("Alex", 5, 0, 10), Player("Simon", 5, 5, 10), Player("Klaus", 5, 0, 10), Player("Marco", 5, 10, 10))
      val plTest3 = Players(testPlayer3)

      "buy one Star" in {
        val testPlayer = plTest.buyStar(0).getPlayers()(0)
        testPlayer.stars should be(11)
        testPlayer.energy should be(0)
      }

      "buy one Heart" in {
        val testPlayer = plTest.buyHeart(0).getPlayers()(0)
        testPlayer.heart should be(1)
        testPlayer.energy should be(0)
      }

      "get Attacks" in {
        pl.getAttacks(testResult, true, 0, 0)._1.getPlayers()(1).heart should be(4)
        pl.getAttacks(noAttack, true, 0, 0)._1.getPlayers()(1).heart should be(10)
        pl.getAttacks(testResult, true, 0, 0)._1.getPlayers()(0).heart should be(10)
        pl.getAttacks(testResult, false, 0, 1)._1.getPlayers()(0).heart should be(4)

        plTest.getAttacks(testResult, false, 0, 1)._4 should be (false)
      }

      "cut KoT" in {
        val tupel = plTest.cutKOT(testPlayer, 1, 3)
        tupel._1.length should be(3)
        tupel._2 should be(2)
        tupel._3 should be(2)
        tupel._4 should be(true)

        val tupel2 = plTest.cutKOT(testPlayer, 1, 0)
        tupel2._1.length should be(3)
        tupel2._2 should be(0)
        tupel2._3 should be(0)
        tupel2._4 should be(true)
      }

      "cutPlayerR" in {
        val tupel = plTest.cutPlayerR(testPlayer, 0, 1, 3, false)
        tupel._1.length should be(2)
        tupel._2 should be (0)
        tupel._3 should be (true)

        val tupel2 = plTest.cutPlayerR(testPlayer, 0, 3, 3, false)
        tupel2._1.length should be(2)
        tupel2._2 should be (1)
        tupel2._3 should be (true)

        val tupel3 = plTest2.cutPlayerR(testPlayer, 0, 1, 3, false)
        tupel3._1.length should be(2)
        tupel3._2 should be (0)
        tupel3._3 should be (true)

        val tupel4 = plTest3.cutPlayerR(testPlayer, 0, 1, 2, false)
        tupel4._1.length should be(3)
        tupel4._2 should be (0)
        tupel4._3 should be (true)
      }

      "have a String Vector representation" in {
        vecPlayers should be(Vector("Simon", "Alex"))
      }
      "have add a Player" in {
        pl.addPlayer(Player("Paul")) should be (Players(Vector(Player("Simon"), Player("Alex"), Player("Paul"))))
      }
      "have a length" in {
        pl.getLength() should be(2)
      }
      "be a Vector of Player" in {
        pl.toPlayerVector should be (Vector(Player("Simon"), Player("Alex")))
      }
      "be able to getGood" in {
        val pl2 = Players(Vector(Player("Simon", 0,10,1), Player("Alex")))
        pl.getGood(RollResult(Vector(1,1,1,5,5,5)), 0, 0) should be (pl2)
      }
      "be able to add a new Player" in {
        val pl2 = pl
        pl2.addNewPlayer("Marco", 0, 10, 0) should be (plAdded)
      }
      "a String representation for XML" in {
        plXML.playersXML() should be ("Simon,0,10,0;Alex,0,10,0;")
      }
      "return a Vector[Player] from a String of players" in {
        plXML.playersStrToPlayers("Simon,0,10,0;Alex,0,10,0;", Player("")) should be (Vector(Player("Simon"), Player("Alex")))
      }
      "be able to get copied" in {
        plXML.set(Vector(Player("Simon"), Player("Alex"))) should be (plXML)
      }
    }
  }
}
