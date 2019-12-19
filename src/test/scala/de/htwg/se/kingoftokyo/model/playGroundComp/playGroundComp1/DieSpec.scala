package de.htwg.se.kingoftokyo.model.playGroundComp.playGroundComp1

import java.security.SecureRandom

import de.htwg.se.kingoftokyo.model.rollResultComp.rollResultComp1.Die
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{Matchers, WordSpec}


@RunWith(classOf[JUnitRunner])
class DieSpec extends WordSpec with Matchers {
  implicit val random = new SecureRandom
  val initDie = 6
  "A Die" when {
    "initialized with 6" should {
      val die = Die(initDie)
      "have a rolling Value" in {
        die.roll should be < 7
        die.roll should be > 0
      }
      "have 6 sides" in {
        die.sides should be(6)
      }
    }
  }
}
