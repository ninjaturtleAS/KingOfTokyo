package de.htwg.se.yourgame

import de.htwg.se.yourgame.model.Player
import de.htwg.se.yourgame.model.GitHubTest
import de.htwg.se.yourgame.model.Test1

object KingOfTokyo{
  def main(args: Array[String]): Unit = {
    val student = Player("Simon")
    println("Hello, " + student.name)

    val message = GitHubTest()
    println(message.message)
    val t = Test1()
    println(t.mes)
  }
}
