package de.htwg.se.kingoftokyo

import de.htwg.se.kingoftokyo.controller.PlaygroundChanged
import de.htwg.se.kingoftokyo.model._
import de.htwg.se.kingoftokyo.aview._
import de.htwg.se.kingoftokyo.controller.controllerComponent.{Controller, PlaygroundChanged}

import scala.io.StdIn._

object KingOfTokyo {

  val controller = new Controller(PlayGround(new Players(),0 , RollResult(Vector.empty), 0))
  val tui = new Tui(controller)
  val gui = new GUI(controller)
  controller.publish(new PlaygroundChanged)

  def main(args: Array[String]): Unit = {
    var input: String = ""

    do {
      input = readLine()
      tui.processInputLine(input)
    } while (input != "q")
  }
}

