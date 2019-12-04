package de.htwg.se.kingoftokyo.controller

import de.htwg.se.kingoftokyio.util.Command

class CreatePlayersCommand(names: String, controller: Controller) extends Command {
  override def doStep: Unit =   controller.playGround = controller.playGround.createPlayerInRandomOrder(names)

  override def undoStep: Unit = controller.playGround = controller.playGround.createPlayerInRandomOrder("")

  override def redoStep: Unit = controller.playGround = controller.playGround.createPlayerInRandomOrder(names)
}
