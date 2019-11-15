package de.htwg.se.kingoftokyo

import de.htwg.se.kingoftokyo.controller.Controller
import de.htwg.se.kingoftokyo.model._
import de.htwg.se.kingoftokyo.aview.Tui
import de.htwg.se.kingoftokyo.model.State._

import scala.io.StdIn._

object KingOfTokyo {

//  var input: String = ""
//  var output: String = ""
//  var player1 = Player("")
//
//  def main(args: Array[String]): Unit = {
//    val tui = new Tui
//    tui.startTui
//  }
  val controller = new Controller(PlayGround(new Players(),0, new StatusMessageOld("kann weg"), WaitForPlayerNames, RollResult(Vector.empty), 0))
  val tui = new Tui(controller)
  controller.notifyObservers

  def main(args: Array[String]): Unit = {
    var input: String = ""

    do {
      input = readLine()
      tui.processInputLine(input)
    } while (input != "q")
  }
}

