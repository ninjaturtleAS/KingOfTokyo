package de.htwg.se.kingoftokyio.util

trait Command {

  def doStep:Unit
  def undoStep:Unit
  def redoStep:Unit

}