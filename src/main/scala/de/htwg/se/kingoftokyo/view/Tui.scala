package de.htwg.se.kingoftokyo.view

import de.htwg.se.kingoftokyo.controller.Controller
import de.htwg.se.kingoftokyo.util.Observer

class Tui (controller: Controller) extends Observer{

  def processInputLine(input: String) = ???

  override def update: Unit = ???
}
