package de.htwg.se.kingoftokyo.model

import java.security.SecureRandom

case class Throw(number: Int) {
  def throwDies() : Vector[Int] = {
    val die = Die(6)(new SecureRandom())
    for {
      i <- 0 until number
    } yield die.roll
  }.toVector

}
