package de.htwg.se.kingoftokyo.model

import org.scalatest._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import Matchers._


@RunWith(classOf[JUnitRunner])
class PlayerSpec extends WordSpec with Matchers {
  "A Player" when { "new" should {
    val player = Player("Alex")
    "have a name"  in {
      player.name should be("Alex")
    }
    "have a nice String representation" in {
      player.toString should be("Alex")
    }
  }}


}
