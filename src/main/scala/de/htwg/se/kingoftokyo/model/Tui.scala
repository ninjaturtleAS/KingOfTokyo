package de.htwg.se.kingoftokyo.model
import util.control.Breaks._

class Tui {
  def startTui {
    var player1 = Player("")
    var rightChoice = true
    while (rightChoice) {
      println("Select your Monster!")
      println("Type in 1 for King, 2 for Cyber Kitty, 3 for Ailionoid or 4 for Gigasaurus")
      val scanner = new java.util.Scanner(System.in)
      if (scanner.hasNextInt) {
        val choice = scanner.nextInt()
        choice match {
          case 1 => rightChoice = false; player1 = Player("King")
          case 2 => rightChoice = false; player1 = Player("Cyber Kitty")
          case 3 => rightChoice = false; player1 = Player("Ailionoid")
          case 4 => rightChoice = false; player1 = Player("Gigasaurus")
          case _ => println("wrong Number, try again")
        }
      } else {
        println("wrong input")
      }
    }
    println("You've chosen " + player1.info)
    println("Welcome " + player1.name + " die to die")
  }
}
