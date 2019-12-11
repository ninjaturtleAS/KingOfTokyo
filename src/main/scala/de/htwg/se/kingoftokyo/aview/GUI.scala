package de.htwg.se.kingoftokyo.aview

import scala.swing._
import scala.swing.Swing.LineBorder
import scala.swing.event._
import de.htwg.se.kingoftokyo.controller._

import scala.io.Source._

class GUI(controller: Controller) extends Frame {

  listenTo(controller)

  title = "King of Tokyo"

  def playgroundPanel = new FlowPanel(){
    border = LineBorder(java.awt.Color.BLACK, 2)
    background = java.awt.Color.BLACK
    for {
      outerRow <- 0 until controller.blockSize
      outerColumn <- 0 until controller.blockSize
    } {
      contents += new GridPanel(controller.blockSize, controller.blockSize) {
        border = LineBorder(java.awt.Color.BLACK, 2)
        for {
          innerRow <- 0 until controller.blockSize
          innerColumn <- 0 until controller.blockSize
        } {
          val x = outerRow * controller.blockSize + innerRow
          val y = outerColumn * controller.blockSize + innerColumn
          val cellPanel = new CellPanel(x, y, controller)
          cells(x)(y) = cellPanel
          contents += cellPanel
          listenTo(cellPanel)
        }
      }
    }
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
}
