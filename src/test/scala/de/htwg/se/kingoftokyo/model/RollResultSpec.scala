package de.htwg.se.kingoftokyo.model

import org.scalatest._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import Matchers._

@RunWith(classOf[JUnitRunner])
class RollResultSpec extends WordSpec with Matchers {

  val initVector = Vector(1, 2, 3, 4, 5, 6)
  val testSelect = Vector(1, 2)
  val testString = "1 2"

  "A throw" when {
    "initialized wtih 6" should {
      val testThrow = RollResult(initVector)
      //firstThrow
      "throw six dices" in {
        testThrow.throwOne().length should be(6)
      }

      //keep throw
      "select 1, 2" in {
        testThrow.keepThrow(testSelect) should be(Vector(1, 2))
      }

      //filterThrowResult
      "select 1, 2" in {
        testThrow.filterThrowResult(testString) should be(Vector(1, 2))
      }

      //mapFacesToString
      "print out 1 " in {
        testThrow.mapFacesToString(1) should be("1 ")
      }
      "print out 2 " in {
        testThrow.mapFacesToString(2) should be("2 ")
      }
      "print out 3 " in {
        testThrow.mapFacesToString(3) should be("3 ")
      }
      "print out Energy " in {
        testThrow.mapFacesToString(4) should be("Energy ")
      }
      "print out Heart " in {
        testThrow.mapFacesToString(5) should be("Heart ")
      }
      "print out Attack " in {
        testThrow.mapFacesToString(6) should be("Attack ")
      }

    //evaluateHeart
      "be 1" in {
        testThrow.evaluateHeart() should be(1)
      }
      //eveluateEnergy
      "be 1" in {
        testThrow.evaluateEnergy() should be(1)
      }
      //evaluateAttack
      "be 1" in {
        testThrow.evaluateAttacs() should be(1)
      }

      //length
      "be 6" in {
        testThrow.length should be(6)
      }

      //to intVector
      "form an Int Vector" in {
        testThrow.toIntVector should be(Vector(1, 2, 3, 4, 5, 6))
      }

      //evaluateStars
      "should be 3" in {
        val starThrow = RollResult(Vector(1, 2, 3, 3, 3, 4))
        starThrow.evaluateStars() should be(3)
      }
      "should be 0" in  {
        testThrow.evaluateStars() should be(0)
      }
    }
  }

}
