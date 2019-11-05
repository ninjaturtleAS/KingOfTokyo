
case class Player(name:String, heart:Int = 10, stars:Int = 0, energy:Int = 0) {

  def gainHeart(gain: Int): Player = copy(name, heart + gain, stars, energy)
  def looseHeart(lost: Int): Player = copy(name, heart - lost, stars, energy)

  def gainStar(gain: Int): Player = copy(name, heart, stars + gain, energy)
  def looseStar(lost: Int): Player = copy(name, heart, stars - lost, energy)

  def gainEnergy(gain: Int): Player =  copy(name, heart, stars, energy + gain)
  def spendEnergy(spend: Int): Player = copy(name, heart, stars, energy - spend)

  def info = f"$name%s, Heart:  $heart%d, Stars:  $stars%d, Energy: $energy%d"

}

val king = Player("King")
val cyberKitty = Player("Cyber Kitty")
val ailionoid = Player("Ailionoid")
val gigasaurus = Player("Gigasaurus")
val info = gigasaurus.info

import java.util.Random
import java.security.SecureRandom

import scala.collection.mutable.ListBuffer

case class Die (sides:Int)(implicit random:Random) {
  def roll:Int = random.nextInt(sides) + 1
}

object SecureDie {
  implicit val random = new SecureRandom
}

import SecureDie.random

val die1 = Die(6)
val die2 = Die(6)
val die3 = Die(6)
val die4 = Die(6)
val die5 = Die(6)
val die6 = Die(6)

val roll1 = die1.roll

case class Turn (player: Player) {

  val die = Die(6)

  def firstroll() : Array[Int] = {
    val faces = Array(die.roll, die.roll, die.roll, die.roll, die.roll, die.roll)
    return faces
  }

  def keep(chosen: Array[Int], faces: Array[Int]): Array[Int] = {
    var kept = new ListBuffer[Int]()
    for (x <- chosen) {
      kept += faces(x - 1)
    }
    val returnkept = kept.toArray
    return returnkept
  }




}

val firstroll = Turn(king).firstroll()

val choice = Array(1,2)

val kept =  Turn(king).keep(choice, firstroll)



