package de.htwg.se.kingoftokyo

import de.htwg.se.kingoftokyo.controller.Controller
import de.htwg.se.kingoftokyo.model.{Player,Tui}
//import de.htwg.se.kingoftokyo.view.Tui


import scala.io.StdIn._

object KingOfTokyo {

  val initHeart = 10
  var input: String = ""
  var output: String = ""
  var player1 = Player("")

  def main(args: Array[String]): Unit = {
    val tui = new Tui
    tui.startTui
  }
//val controller = new Controller()
//  val tui = new Tui(controller)
//  controller.notifyObservers
//
//  def main(args: Array[String]): Unit = {
//    var input: String = ""
//
//    do {
//      input = readLine()
//      tui.processInputLine(input)
//    } while (input != "q")
//  }
}

