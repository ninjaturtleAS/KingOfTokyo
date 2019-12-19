package de.htwg.se.kingoftokyo.model.rollResultComp.rollResultComp1

import java.security.SecureRandom

case class Throw(number: Int) {
  val six = 6

  def throwDies() : Vector[Int] = {
    val die = Die(six)(new SecureRandom())
    for {
      _ <- 0 until number
    } yield die.roll
  }.toVector

}
