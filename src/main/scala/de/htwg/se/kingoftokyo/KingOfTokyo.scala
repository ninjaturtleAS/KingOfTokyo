package de.htwg.se.kingoftokyo


import com.google.inject.Guice
import net.codingwell.scalaguice.InjectorExtensions._
import de.htwg.se.kingoftokyo.model._
import com.google.inject.name.Names
import de.htwg.se.kingoftokyo.aview._
import de.htwg.se.kingoftokyo.controller.controllerComponent._
import de.htwg.se.kingoftokyo.controller.controllerComponent.controllerComponent.Controller
import de.htwg.se.kingoftokyo.model.playGroundComp.playGroundBaseComponent.PlayGround
import de.htwg.se.kingoftokyo.model.playersComp.playersBaseComponent.Players
import de.htwg.se.kingoftokyo.model.rollResultComp.rollResultBaseComponent.RollResult

import scala.io.StdIn._

object KingOfTokyo {

  val injector = Guice.createInjector(new KingOfTokyoModule)
  val controller = injector.instance[ControllerInterface](Names.named("initCont"))
  //val controller = new Controller(PlayGround(new Players(),0 , RollResult(Vector.empty), 0))
  val tui = new Tui(controller)
  val gui = new GUI(controller)
  controller.publish(new PlaygroundChanged)

  def main (args: Array[String]): Unit = {
    var input: String = ""

    do {
      input = readLine()
      tui.processInputLine(input)
    } while (input != "q")
  }
}

