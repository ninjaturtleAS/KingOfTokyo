package de.htwg.se.yourgame

import de.htwg.se.yourgame.model.Player

object KingOfTokyo{
  def main(args: Array[String]): Unit = {
    val student = Player("Simon")
    println("Hello, " + student.name)
  }
}
