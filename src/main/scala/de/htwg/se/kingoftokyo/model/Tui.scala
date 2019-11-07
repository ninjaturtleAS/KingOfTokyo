package de.htwg.se.kingoftokyo.model
import util.control.Breaks._

class Tui {
  def startTui {
    var rightChoice = true
    while (rightChoice) {
      println("Select your Monster!")
      println("Type in 1 for King, 2 for Cyber Kitty, 3 for Ailionoid or 4 for Gigasaurus")
      val scanner = new java.util.Scanner(System.in)
      if (scanner.hasNextInt) {
        val choice = scanner.nextInt()
        var player1 = new Player("");
        choice match {
          case 1 => player1 = new Player("King"); rightChoice = false
          case 2 => player1 = new Player("Cyber Kitty"); rightChoice = false
          case 3 => player1 = new Player("Ailionoid"); rightChoice = false
          case 4 => player1 = new Player("Gigasaurus"); rightChoice = false
          case _ => println("wrong Number, try again")
        }
      } else {
        println("wrong input")
      }
    }
    println("Type die to die")
  }
}
