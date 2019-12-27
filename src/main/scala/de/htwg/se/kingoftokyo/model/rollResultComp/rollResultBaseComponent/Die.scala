package de.htwg.se.kingoftokyo.model.rollResultComp.rollResultBaseComponent

import java.util.Random

case class Die (sides:Int)(implicit random:Random) {
  def roll:Int = random.nextInt(sides) + 1
}
