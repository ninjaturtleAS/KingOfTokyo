package de.htwg.se.kingoftokyo.model

import java.util.Scanner
import scala.io.StdIn._

class Tui {
  def startTui {
    var player1 = Player("")
    var rightChoice = true
    while (rightChoice) {
      printf("Select your Monster!\n")
      printf("Type in 1 for King, 2 for Cyber Kitty, 3 for Ailionoid or 4 for Gigasaurus\n")
      val scanner = new Scanner(System.in)
      if (scanner.hasNextInt) {
        val choice = scanner.nextInt()
        choice match {
          case 1 => rightChoice = false; player1 = Player("King")
          case 2 => rightChoice = false; player1 = Player("Cyber Kitty")
          case 3 => rightChoice = false; player1 = Player("Ailionoid")
          case 4 => rightChoice = false; player1 = Player("Gigasaurus")
          case _ => printf("wrong Number, try again\n")
        }
      } else {
        printf("wrong input\n")
      }
    }
    printf("You've chosen " + player1.info + "\n")
    printf("Welcome " + player1.name + " type anything to die\n")
    val scanner = new Scanner(System.in)
    scanner.next()
    val firstTurn = new Turn(player1)
    val firstThrow = firstTurn.throwOne()
    printf(firstTurn.printThrow(firstThrow) + "\n")
    printf("make choice: e.g. 1 3 4 or type all\n")
    val line = readLine()
    val finalTrow = if (line.equals("all")) {
      firstThrow
    } else {
      val choice = line.split(" ")
      val intChoice = choice.map(_.toInt)
      //println(intChoice.toList)
      val secondThrow = firstTurn.keepThrow(intChoice, firstThrow)
      printf(firstTurn.printThrow(secondThrow) + "\n")
      secondThrow
    }
    println(finalTrow.toList)
  }
}
