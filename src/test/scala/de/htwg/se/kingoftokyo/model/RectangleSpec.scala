package de.htwg.se.kingoftokyo.model

import org.scalatest._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import Matchers._


@RunWith(classOf[JUnitRunner])
class RectangleSpec extends WordSpec with Matchers {
  "A Rectangle" when {
    "not built with no Arguments " should {
      val defaultRectangle = Rectangle()
      "have a heigth and width 1" in {
        defaultRectangle.width should be(1)
        defaultRectangle.height should be(1)
      }
    }
    "built with a heigth and width of 2" should {
      val anotherRect = Rectangle(2, 2)
      "have the area" in {
        anotherRect.area should be(4)
      }
    }
  }
}