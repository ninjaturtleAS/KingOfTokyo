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

      "get Attacks" in {
        pl.getAttacks(testResult, true, 0).getPlayers()(1).heart should be(4)
        pl.getAttacks(testResult, true, 0).getPlayers()(0).heart should be(10)
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
        pl.getGood(RollResult(Vector(1,1,1,5,5,5)), 0) should be (pl2)
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
