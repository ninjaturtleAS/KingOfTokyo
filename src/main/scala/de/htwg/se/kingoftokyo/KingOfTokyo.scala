package de.htwg.se.kingoftokyo

import de.htwg.se.kingoftokyo.model.{Player, Rectangle, Tui}

object KingOfTokyo{
  def main(args: Array[String]): Unit = {
    val student = Player("Simon")

    println("Hello Student , " + student.name)

    val rect = Rectangle(3,5)
    println(rect.area)
    val rect2 = Rectangle()
    println(rect2.area)

    val tui = new Tui
    tui.startTui

  }
}

