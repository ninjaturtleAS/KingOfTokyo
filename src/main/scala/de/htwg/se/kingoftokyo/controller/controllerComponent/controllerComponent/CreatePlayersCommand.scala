package de.htwg.se.kingoftokyo.controller.controllerComponent.controllerComponent

import de.htwg.se.kingoftokyio.util.Command
import de.htwg.se.kingoftokyo.controller.controllerComponent.State

class CreatePlayersCommand(names: String, controller: Controller) extends Command {
  override def doStep: Unit =   {
    controller.playGround = controller.playGround.createPlayerInRandomOrder(names)
    controller.state = State.WaitFor1stThrow
  }

  override def undoStep: Unit = {
    controller.playGround = controller.playGround.createPlayerInRandomOrder("")
    controller.state = State.WaitForPlayerNames
  }

  override def redoStep: Unit = {
    controller.playGround = controller.playGround.createPlayerInRandomOrder(names)
    controller.state = State.WaitFor1stThrow

  }
}
