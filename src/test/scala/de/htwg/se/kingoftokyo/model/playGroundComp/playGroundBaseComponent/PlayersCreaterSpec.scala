package de.htwg.se.kingoftokyo.model.playGroundComp.playGroundBaseComponent

import de.htwg.se.kingoftokyo.model.playersComp.playersBaseComponent.{Players, PlayersCreator}
import org.junit.runner.RunWith
import org.scalatest._
import org.scalatest.junit.JUnitRunner


@RunWith(classOf[JUnitRunner])
class PlayersCreaterSpec extends WordSpec with Matchers  {
  "A PlayersCreater at start" when {
    "new" should {
      val pc = PlayersCreator("Alex,Simon")
      val strList = pc.toStringList
      "have a String List representation" in {
        strList should be(List("Alex", "Simon"))
      }
      "have a String of Names" in {
        pc.stringOfNames should be ("Alex,Simon")
      }
      "have a length" in {
        pc.numberOfPlayers should be(2)
      }
      "give random Players" in {
        pc.getRandomPlayers(strList).toStringVector should contain allElementsOf Vector("Alex", "Simon")
      }
      "give also random Players" in {
        pc.getRandomPlayers(strList, new Players()).toStringVector should contain allElementsOf Vector("Alex", "Simon")
      }
    }
  }
}
