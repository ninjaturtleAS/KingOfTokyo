package de.htwg.se.kingoftokyo.controller

import org.scalatest._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import Matchers._

@RunWith(classOf[JUnitRunner])
class SateSpec extends WordSpec with Matchers {

  "A State" when {
    "initialized wtih ThrowComplete" should {
      val complete = State.ThrowComplete
      "map to wird ausgewertet" in {
        State.map(complete) should be("Wurf wird ausgewertet")
      }
    }
  }

}
