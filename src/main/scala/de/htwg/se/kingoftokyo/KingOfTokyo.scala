package de.htwg.se.kingoftokyo

import de.htwg.se.kingoftokyo.model.{Player, Rectangle}

object KingOfTokyo{
  def main(args: Array[String]): Unit = {
    val student = Player("Simon")
    println("Hello, " + student.name)

    val rect = Rectangle(3,5)
    println(rect.area)
    val rect2 = Rectangle()
    println(rect2.area)

  }
}
