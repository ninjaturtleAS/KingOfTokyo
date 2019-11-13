package de.htwg.se.kingoftokyo.view

import de.htwg.se.kingoftokyo.controller.Controller
import de.htwg.se.kingoftokyo.util.Observer
import de.htwg.se.kingoftokyo.model.State._

class Tui (controller: Controller) extends Observer{

  controller.add(this)

  def processInputLine(input: String):Unit = {
    controller.playGround.status match {
      case Starting => controller.startGame
      case WaitForPlayerNames =>
        input match {
          case "q" =>
          case _ => {
            controller.createPlayers(input)
            controller.throwDies()
          }

        }

      case WaitFor1stThrow =>
        input match {
          case "q" =>
          case _ =>
        }
    }
  }
  override def update: Unit =  println(controller.playGroundToString())
}
