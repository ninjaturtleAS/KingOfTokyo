package de.htwg.se.kingoftokyo.aview

import java.awt.Color

import scala.swing._
import scala.swing.event._
import de.htwg.se.kingoftokyo.controller.controllerComponent.{ControllerInterface, PlaygroundChanged}
import de.htwg.se.kingoftokyo.controller.controllerComponent.State._



class GUI(controller: ControllerInterface) extends Frame {

  listenTo(controller)

  title = "King of Tokyo"

  val backColor: Color = Color.DARK_GRAY
  val playerTextColor: Color = Color.MAGENTA.brighter()
  val resultTextColor: Color = Color.YELLOW.brighter()

  val width = 450
  val height = 300
  preferredSize = new Dimension(width, height)

  def initialPanel: FlowPanel = new FlowPanel() {
    background = backColor
    val button: Button = Button("Players?") { inputPlayers() }
    button.foreground = Color.BLACK
    button.background = playerTextColor
    contents += button
  }

  def inputPlayers(): Unit = {
    val init = ""
    val names = Dialog.showInput(contents.head, "Players?", initial = init)
    controller.createPlayers(names)
  }

  def playersPanel: BoxPanel = new BoxPanel(Orientation.Vertical){
    for (i <- controller.getPlayground().getPlayers.getPlayers().indices) {
      val player: FlowPanel = new FlowPanel() {
        background = backColor
        val kot: String = if (i == controller.getPlayground().getKOT) {"KoT, "} else {""}
        val turn: String = if (i == controller.getPlayground().getLapNr % controller.getPlayground().getPlayers.getLength()) {"turn, "} else {""}
        contents += {
          val label: Label = new Label(turn + kot + controller.getPlayground().getPlayers.getPlayers()(i).info)
          label.foreground = playerTextColor
          label
        }
      }
      contents += player
    }
  }

  def playgroundPanel: FlowPanel =  new FlowPanel() {
    background = backColor
    contents += {
      val text = new Label(controller.getPlayground().getRollResult.toString())
      text.foreground = resultTextColor
      text
    }
    val button: Button = Button("Choice?") { choice() }
    button.foreground = Color.BLACK
    button.background = resultTextColor
    contents += button
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

  def nextPanel: FlowPanel = new FlowPanel() {
    background = Color.MAGENTA
    val button: Button = Button("Next Turn?") { nextTurn() }
    contents += button
  }

  def nextTurn(): Unit = {
    controller.evaluateThrow()
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
        if (controller.getState() == WaitFor1stThrow) {
          controller.undo
        }
      })
      contents += new MenuItem(Action("Redo") {
        if (controller.getState() == WaitForPlayerNames) {
          controller.redo
        }
      })
    }
  }


  visible = true
  redraw()

  reactions += {
    case _: PlaygroundChanged => redraw()
  }

  def redraw(): Unit = {
    contents = controller.getState() match {
      case WaitForPlayerNames =>
        new BorderPanel() {
          background = backColor
          minimumSize_=(preferredSize)
          add(initialPanel, BorderPanel.Position.North)
        }
      case WaitFor1stThrow | WaitFor2ndThrow =>
        new BorderPanel() {
          background = backColor
          minimumSize_=(preferredSize)
          add(playersPanel, BorderPanel.Position.Center)
          add(playgroundPanel, BorderPanel.Position.South)
        }
      case ThrowComplete =>
        new BorderPanel() {
          background = backColor
          minimumSize = preferredSize
          add(nextPanel,BorderPanel.Position.Center)
        }
    }
  }
}