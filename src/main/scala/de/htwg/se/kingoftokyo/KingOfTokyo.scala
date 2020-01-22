package de.htwg.se.kingoftokyo


import com.google.inject.Guice
import net.codingwell.scalaguice.InjectorExtensions._
import de.htwg.se.kingoftokyo.aview._
import de.htwg.se.kingoftokyo.controller.controllerComponent._

import scala.io.StdIn._

object KingOfTokyo {

  val injector = Guice.createInjector(new KingOfTokyoModule)
  val controller = injector.instance[ControllerInterface]/*(Names.named("initCont"))*/
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

