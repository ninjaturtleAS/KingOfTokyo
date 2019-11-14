package de.htwg.se.kingoftokyo.model

import java.security.SecureRandom

import org.junit.runner.RunWith
import org.scalatest.{Matchers, WordSpec}
import org.scalatest.junit.JUnitRunner


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