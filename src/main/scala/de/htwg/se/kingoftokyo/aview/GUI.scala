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
      val text = new Label(controller.getPlayground().getRollResult.toString)
      text.foreground = resultTextColor
      text
    }
    val button: Button = Button("Choice?") { choice() }
    button.foreground = Color.BLACK
    button.background = resultTextColor
    contents += button
  }

  def kotChoicePanel: BorderPanel = new BorderPanel() {
    val playground = controller.getPlayground()
    val players = playground.getPlayers.getPlayers()

    background = Color.ORANGE.brighter()
    val text = new Label("Möchte " + players(playground.getKOT).name + " King of Tokyo bleiben?")
    text.foreground = Color.BLACK
    val yesButton: Button = Button("Ja") { controller.kotStay() }
    val noButton: Button = Button("Nein") { controller.kotLeave() }
    add(text, BorderPanel.Position.Center)
    add(yesButton, BorderPanel.Position.West)
    add(noButton, BorderPanel.Position.East)
  }

  def buyPanel: BorderPanel = new BorderPanel() {
    background = Color.BLUE.brighter()
    val text = new Label("Möchten Sie für 5 Energy 1 Heart oder Star kaufen?")
    text.foreground = Color.ORANGE
    val heartButton: Button = Button("Heart") { controller.buy(0) }
    val starButton: Button = Button("Star") { controller.buy(1) }
    val noButton: Button = Button("Nein") { controller.buy(2) }
    add(text, BorderPanel.Position.Center)
    add(heartButton, BorderPanel.Position.West)
    add(starButton, BorderPanel.Position.East)
    add(noButton, BorderPanel.Position.South)
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

  def resultPanel: FlowPanel = new FlowPanel() {
    background = Color.MAGENTA
    contents += {
      val text = new Label(controller.getPlayground().getRollResult.toString)
      text.foreground = Color.BLACK
      text
    }
  }

  def evaluatePanel: BorderPanel = new BorderPanel() {
    background = Color.MAGENTA
    val button: Button = Button("Evaluate!") { evaluate() }
    add(button, BorderPanel.Position.Center)
    add(resultPanel, BorderPanel.Position.North)
  }

  def evaluate(): Unit = {
    controller.evaluateThrow()
  }

  def endPanel: BorderPanel = new BorderPanel() {
    background = Color.RED.darker().darker().darker()
    val text = new Label(controller.getWinnerString())
    text.foreground = Color.WHITE
    add(text, BorderPanel.Position.Center)
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
      contents += new MenuItem(Action("Save") {
        controller.save
      })
      contents += new MenuItem(Action("Load") {
        controller.load
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
          add(evaluatePanel,BorderPanel.Position.Center)
        }

      case WaitForKotDecision =>
        new BorderPanel() {
          background = backColor
          minimumSize_=(preferredSize)
          add(kotChoicePanel, BorderPanel.Position.Center)
      }

      case WaitForBuy =>
        new BorderPanel() {
          background = backColor
          minimumSize_=(preferredSize)
          add(buyPanel, BorderPanel.Position.Center)
        }
      case End =>
        new BorderPanel() {
          background = backColor
          minimumSize_=(preferredSize)
          add(endPanel, BorderPanel.Position.Center)
        }
    }
  }
}