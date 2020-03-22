package de.htwg.se.kingoftokyo.aview

import de.htwg.se.kingoftokyo.controller.controllerComponent.State._
import de.htwg.se.kingoftokyo.controller.controllerComponent.{ControllerInterface, PlaygroundChanged, State}

import scala.swing.Publisher

class Tui (controller: ControllerInterface) extends Publisher {

  listenTo(controller)
  def processInputLine(input: String): Unit = {
    controller.getState() match {

      //case Starting => controller.startGame
      case WaitForPlayerNames =>
        input match {
          case "q" =>
          case "y" => controller.redo
          case _ => controller.createPlayers(if(input.isEmpty){None} else {Some(input)})
        }

      case WaitFor1stThrow =>
        input match {
          case "q" =>
          case "z" => controller.undo
          case "all" => controller.completeThrow()
          case _ => controller.filterThrowResult(input)
        }

      case WaitFor2ndThrow =>
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

      case WaitForBuy =>
        input match {
          case "q" =>
          case "heart" => controller.buy(0)
          case "star" => controller.buy(1)
          case _  =>
        }


      case WaitForKotDecision =>
        input match {
          case "q" =>
          case "yes" => controller.kotStay()
          case "no" => controller.kotLeave()
          case _ => controller.contStay()
        }

      case End =>
        input match {
          case "q" =>
          case _ => controller.contStay()
        }
    }
  }

  reactions+= {
    case event: PlaygroundChanged => printTui()
  }

  def printTui(): Unit = {
    if (controller.getState() == End) {
      println(controller.getWinnerString())
    }
    else {
      println(controller.playGroundToString())
      println(State.message(controller.getState()) + "\n")
      println(controller.getState())
    }
  }
  /*
  override def update: Boolean = {
    println(controller.playGroundToString())
    println(State.message(controller.state) + "\n")
    println(controller.state)
    true
  }
   */
}
