package de.htwg.se.kingoftokyo.aview

import scala.swing._
import scala.swing.Swing.LineBorder
import scala.swing.event._
import de.htwg.se.kingoftokyo.controller._
import  de.htwg.se.kingoftokyo.model._

import scala.io.Source._

class GUI(controller: Controller) extends Frame {

  listenTo(controller)

  title = "King of Tokyo"

  def initialPanel = new FlowPanel() {
    val button = Button("Players?") { inputPlayers() }
    contents += button
    listenTo(button)
  }

  def inputPlayers(): Unit = {
    val help = ""
    val names = Dialog.showInput(contents.head, "Players?", initial = help)
    controller.createPlayers(names)
  }

  def playgroundPanel = new FlowPanel(){
    contents += new Label("Tokyo: ")

    listenTo(initialPanel)
    for (p <- controller.playGround.players.players) {
      val player = new TextField(p.info)
      player.preferredSize = (new Dimension(20,20))
      contents += player
    }
  }



  preferredSize = new Dimension(600, 500)

  contents = new BorderPanel {
    minimumSize_=(preferredSize)
    add(initialPanel, BorderPanel.Position.North)
    add(playgroundPanel, BorderPanel.Position.Center)
  }

  menuBar = new MenuBar {
    contents += new Menu("File") {
      mnemonic = Key.F
      contents += new MenuItem(Action("New") {
        //controller.createPlayers()
      })
      contents += new MenuItem(Action("Quit") {
        System.exit(0)
      })
    }
    contents += new Menu("Edit") {
      mnemonic = Key.E
      contents += new MenuItem(Action("Undo") {
        controller.undo
      })
      contents += new MenuItem(Action("Redo") {
        controller.redo
      })
    }
  }


  visible = true
  //redraw

  reactions += {
    case event: PlaygroundChanged => repaint
  }

}
