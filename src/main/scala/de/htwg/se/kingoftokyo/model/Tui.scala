package de.htwg.se.kingoftokyo.model
import util.control.Breaks._

class Tui {
  def startTui {
    var player1 = new Player("");
    var rightChoice = true
    while (rightChoice) {
      println("Select your Monster!")
      println("Type in 1 for King, 2 for Cyber Kitty, 3 for Ailionoid or 4 for Gigasaurus")
      val scanner = new java.util.Scanner(System.in)
      if (scanner.hasNextInt) {
        val choice = scanner.nextInt()
        choice match {
          case 1 => player1 = new Player("King"); rightChoice = false
          case 2 => player1 = new Player("Cyber Kitty"); rightChoice = false
          case 3 => player1 = new Player("Ailionoid"); rightChoice = false
          case 4 => player1 = new Player("Gigasaurus"); rightChoice = false
          case _ => println("wrong Number, try again")
        }
      } else {
        println("wrong input")
        scanner.reset()
      }
    }
    println("Welcome " + player1.name + " die to die")
  }
}
