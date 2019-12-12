package de.htwg.se.kingoftokyo.aview

import scala.swing._
import scala.swing.Swing.LineBorder
import scala.swing.event._
import de.htwg.se.kingoftokyo.controller._
import de.htwg.se.kingoftokyo.controller.State._
import  de.htwg.se.kingoftokyo.model._

import scala.io.Source._

class GUI(controller: Controller) extends Frame {

  listenTo(controller)

  title = "King of Tokyo"

  def initialPanel = new FlowPanel() {
    val button = Button("Players?") { inputPlayers() }
    contents += button
    //listenTo(button)
  }

  def inputPlayers(): Unit = {
    val init = ""
    val names = Dialog.showInput(contents.head, "Players?", initial = init)
    controller.createPlayers(names)
  }

  def playersPanel = new BoxPanel(Orientation.Vertical){
    listenTo(initialPanel)

    for (i <- 0 until controller.playGround.players.players.length) {
      val player = new FlowPanel() {
        val kot = if (i == controller.playGround.kingOfTokyo) {"KoT, "} else {""}
        val turn = if (i == controller.playGround.lapNr) {"turn, "} else {""}
        contents += new Label(turn + kot + controller.playGround.players.players(i).info)
      }
      contents += player
    }
    //listenTo(playgroundPanel)
  }

  def playgroundPanel =  new FlowPanel() {
    contents += new TextArea(controller.playGround.rollResult.toString())
    val button = Button("Choice?") { choice() }
    contents += button
    //listenTo(button)
  }

  def choice(): Unit = {
    val init = ""
    val choice = Dialog.showInput(contents.head, "Choice, All or None?", initial = init)
    choice match {
      case None =>
      case Some("") => controller.filterThrowResult("")
      case Some("all") => controller.completeThrow()
      case Some(choice) => controller.filterThrowResult(choice)
    }
  }

  def nextPanel = new FlowPanel() {
    val button  = Button("Next Turn?") { nextTurn() }
    contents += button
    //listenTo(button)
  }

  def nextTurn(): Unit = {
    controller.evaluateThrow()
  }



  preferredSize = new Dimension(450, 300)

  contents = new BorderPanel {
    minimumSize_=(preferredSize)
    add(initialPanel, BorderPanel.Position.North)
  }

  menuBar = new MenuBar {
    contents += new Menu("Game") {
      mnemonic = Key.F
      contents += new MenuItem(Action("New") {
        controller.newGame
      })
      contents += new MenuItem(Action("Quit") {
        System.exit(0)
      })
    }
    contents += new Menu("Edit") {
      mnemonic = Key.E
      contents += new MenuItem(Action("Undo") {
        if (controller.state == WaitFor1stThrow) {
          controller.undo
        }
      })
      contents += new MenuItem(Action("Redo") {
        if (controller.state == WaitForPlayerNames) {
          controller.redo
        }
      })
    }
  }


  visible = true
  redraw

  reactions += {
    case event: PlaygroundChanged => redraw
  }

  def redraw = {
    contents = controller.state match {
      case WaitForPlayerNames => {
        new BorderPanel() {
          minimumSize_=(preferredSize)
          add(initialPanel, BorderPanel.Position.North)
        }
      }
      case WaitFor1stThrow | WaitFor2ndThrow => {
        new BorderPanel() {
          minimumSize_=(preferredSize)
          add(playersPanel, BorderPanel.Position.Center)
          add(playgroundPanel, BorderPanel.Position.South)
        }
      }
      case ThrowComplete => {
        new BorderPanel() {
          minimumSize = (preferredSize)
          add(nextPanel,BorderPanel.Position.Center)
        }
      }
    }
  }
}