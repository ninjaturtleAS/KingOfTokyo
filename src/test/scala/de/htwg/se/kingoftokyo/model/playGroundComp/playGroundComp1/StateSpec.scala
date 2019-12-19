package de.htwg.se.kingoftokyo.model.playGroundComp.playGroundComp1

import de.htwg.se.kingoftokyo.controller.State
import org.junit.runner.RunWith
import org.scalatest._
import org.scalatest.junit.JUnitRunner
import Matchers._
import de.htwg.se.kingoftokyo.controller.controllerComponent.State

@RunWith(classOf[JUnitRunner])
class StateSpec extends WordSpec with Matchers {

  "A State" when {
    "initialized wtih ThrowComplete" should {
      val complete = State.ThrowComplete
      "map to wird ausgewertet" in {
        State.message(complete) should be("Wurf wird ausgewertet")
      }
    }
  }
}