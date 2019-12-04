package de.htwg.se.kingoftokyo.aview

import de.htwg.se.kingoftokyo.controller.Controller
import de.htwg.se.kingoftokyo.util.Observer
import de.htwg.se.kingoftokyo.controller.State._
import de.htwg.se.kingoftokyo.controller._

class Tui (controller: Controller) extends Observer {

  controller.add(this)

  def processInputLine(input: String): Unit = {
    controller.state match {
      //case Starting => controller.startGame
      case WaitForPlayerNames =>
        input match {
          case "q" =>
          case _ => {
            controller.createPlayers(input)
          }

        }

      case WaitFor1stThrow | WaitFor2ndThrow =>
        input match {
          case "q" =>
          case "z" => controller.undo
          case "y" => controller.redo
          case "all" => controller.completeThrow()
          case _ => controller.filterThrowResult(input)
        }

      case ThrowComplete =>
        input match {
          case "q" =>
          case _ => controller.evaluateThrow()
        }

    }
  }

  override def update: Boolean = {
    println(controller.playGroundToString())
    println(State.message(controller.state) + "\n")
    println(controller.state)
    true
  }
}
