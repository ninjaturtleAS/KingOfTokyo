package de.htwg.se.kingoftokyo.model

import org.scalatest._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import Matchers._

@RunWith(classOf[JUnitRunner])
class ThrwoSpec extends WordSpec with Matchers {

  //test throw  dice

  val initDice = 6

  "A throw" when {
    "initialized wtih 6" should {
      val testThrow = Throw(6)
      "throw six dices" in {
        testThrow.throwDies().length should be(6)
      }
    }
    "initialized with 0" should {
      val testThrow2 = Throw(0)
      "throw zero dieces" in {
        testThrow2.throwDies().length should be(0)
      }
    }
  }

}
