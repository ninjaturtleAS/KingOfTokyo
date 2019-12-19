package de.htwg.se.kingoftokyo.model.playGroundComp.playGroundComp1

import java.security.SecureRandom

import de.htwg.se.kingoftokyo.model.Die

case class Throw(number: Int) {
  val six = 6

  def throwDies() : Vector[Int] = {
    val die = Die(six)(new SecureRandom())
    for {
      _ <- 0 until number
    } yield die.roll
  }.toVector

}
