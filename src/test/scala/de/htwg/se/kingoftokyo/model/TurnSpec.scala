package de.htwg.se.kingoftokyo.model

import org.scalatest._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import Matchers._


@RunWith(classOf[JUnitRunner])
class TurnSpec extends WordSpec with Matchers {

  val testSeq = IndexedSeq(1, 2, 3, 4, 5, 6)
  val sixStars = IndexedSeq(3, 3, 3, 3, 3, 3)
  var alex = Player("Alex")
  val turn = new Turn(alex)

  "printThrow at call" when {
    "given List" should {
      "every Value" in {
        turn.printThrow(testSeq) should be("1 2 3 Energy Heart Attack ")
      }
    }
  }

  "evaluateDies" when {
    "every Value" should {
      "every Value" in {
        turn.evaluateDies(testSeq) should be(IndexedSeq(1, 1, 1, 0))
      }
      "six 3" in {
        turn.evaluateDies(sixStars) should be(IndexedSeq(0, 0, 0, 6))
      }
    }
  }

  val testGood = IndexedSeq(1, 1, 1, 1)

  "getGood" when {
    "tested" should {
      "gets everything" in {
        turn.getGood(testGood) should be (Player("Alex", 10, 1, 1))
      }
    }
  }
 }
