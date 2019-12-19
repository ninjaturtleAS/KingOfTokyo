package de.htwg.se.kingoftokyo.model.playGroundComp.playGroundComp1
import java.security.SecureRandom
import java.util.Random

case class Die (sides:Int)(implicit random:Random) {
  def roll:Int = random.nextInt(sides) + 1
}
