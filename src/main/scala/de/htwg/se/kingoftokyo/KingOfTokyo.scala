package de.htwg.se.kingoftokyo

import de.htwg.se.kingoftokyo.model.Player
import de.htwg.se.kingoftokyo.model.GitHubTest

object KingOfTokyo{
  def main(args: Array[String]): Unit = {
    val student = Player("Simon")
    println("Hello, " + student.name)

    val message = GitHubTest()
    println(message.message)
  }
}
