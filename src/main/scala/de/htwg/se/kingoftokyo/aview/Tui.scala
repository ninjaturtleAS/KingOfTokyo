package de.htwg.se.kingoftokyo.aview

import de.htwg.se.kingoftokyo.controller.Controller
import de.htwg.se.kingoftokyo.util.Observer
//import de.htwg.se.kingoftokyo.model.State._
import de.htwg.se.kingoftokyo.controller.State._
import de.htwg.se.kingoftokyo.controller._

class Tui (controller: Controller) extends Observer {

  controller.add(this)

  def processInputLine(input: String): Unit = {
    controller.state match {
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
          case "all" => controller.completeThrow()
          case _ => controller.filterThrowResult(input)
        }

      case ThrowComplete =>
        input match {
          case "q" =>
          case _ => controller.evaluateThrow()
        }

      case WaitForAttack =>
        input match {
          case "q" =>
          case _ => controller.attack(input)
        }
    }
  }

  override def update: Boolean = {
    println(controller.playGroundToString())
    println(State.message(controller.state) + "\n")
    true
  }
}
