package de.htwg.se.kingoftokyo.model

import java.util.Scanner

import scala.io.StdIn.readLine

class Tui1 {
  def startTui(player1: Player, input: String): Player = {
    val returnPlayer = input match {
      case "1" => Player("King")
      case "2" => Player("Cyber Kitty")
      case "3" => Player("Ailionoid")
      case "4" => Player("Gigasaurus")
      case _ => {
        startTui(player1, readLine())
      }
    }
    returnPlayer
  }
}

