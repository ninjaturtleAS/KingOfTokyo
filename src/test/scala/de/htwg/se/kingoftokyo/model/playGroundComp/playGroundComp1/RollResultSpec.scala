package de.htwg.se.kingoftokyo.model.playGroundComp.playGroundComp1

import org.junit.runner.RunWith
import org.scalatest._
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class RollResultSpec extends WordSpec with Matchers {

  val initVector = Vector(1, 2, 3, 4, 5, 6)
  val testStar = Vector(2, 2, 2, 1, 1, 1)
  val testAttack = Vector(6, 6, 6, 6, 6, 6)
  val testString = "1,2"

  "A throw" when {
    "initialized wtih 6" should {
      val testThrow = RollResult(initVector)
      val testAttacks = RollResult(testAttack)
      val testStars = RollResult(testStar)
      //firstThrow
      "throw six dices" in {
        testThrow.throwOne().length should be(6)
      }

      "give 3 stars when" in {
        testStars.evaluateStars() should be(3)
      }


      //filterThrowResult
      "select also 1, 2" in {
        testThrow.filterThrowResult(testString) should be(RollResult(Vector(1, 2)))
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
      "print out nothing " in {
        testThrow.mapFacesToString(7) should be("")
      }

    //evaluateHeart
      "be 1" in {
        testThrow.evaluateHeart() should be(1)
      }
      //eveluateEnergy
      "be 1 also" in {
        testThrow.evaluateEnergy() should be(1)
      }
      //evaluateAttack
      "be 1 as well" in {
        testThrow.evaluateAttacks() should be(1)
      }

      "be 3" in {
        testAttacks.evaluateAttacks() should be(6)
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
