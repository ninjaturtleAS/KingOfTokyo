package de.htwg.se.kingoftokyo.model

case class Rectangle(height: Double = 1, width: Double = 1) {

  def area: Double = height * width
}
