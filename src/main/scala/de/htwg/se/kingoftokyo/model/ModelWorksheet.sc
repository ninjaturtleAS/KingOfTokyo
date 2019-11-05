
case class Player(name:String, heart:Int = 10, stars:Int = 0, energy:Int = 0) {

  def gainHeart(gain: Int): Player = copy(name, heart + gain, stars, energy)
  def looseHeart(lost: Int): Player = copy(name, heart - lost, stars, energy)

  def gainStar(gain: Int): Player = copy(name, heart, stars + gain, energy)
  def looseStar(lost: Int): Player = copy(name, heart, stars - lost, energy)

  def gainEnergy(gain: Int): Player =  copy(name, heart, stars, energy + gain)
  def spendEnergy(spend: Int): Player = copy(name, heart, stars, energy - spend)

  def info = f"$name%s, Heart:  $heart%d, Stars:  $stars%d, Energy: $energy%d"

  def evaluateDies(result: IndexedSeq[Int], tokyo: Player): Player = {
    var help1 = 0
    var help2 = 0
    var help3 = 0
    for (x <- result) {
      x match {
        case 1 => help1 += 1
        case 2 => help2 += 1
        case 3 => help3 += 1
        case 4 => this.gainEnergy(1)
        case 5 => this.gainHeart(1)
        case 6 => tokyo.looseHeart(1)
      }
      if (help1 >= 3) this.gainStar(help1 - 2)
      if (help2 >= 3) this.gainStar(help2 - 1)
      if (help3 >= 3) this.gainStar(help3)
    }
    this
  }

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

val die = Die(6)

val roll = die.roll




def firstthrow(): IndexedSeq[Int] = {
  val faces = IndexedSeq(die.roll, die.roll, die.roll, die.roll, die.roll, die.roll)
  faces
}

def keep(choice: IndexedSeq[Int], faces: IndexedSeq[Int]): IndexedSeq[Int] = {
  var chosen = new ListBuffer[Int]()
  for (x <- choice) {
    chosen += faces(x - 1)
  }
  val kept = chosen.toIndexedSeq
  val newFacesBuffer = new ListBuffer[Int]()
  kept.foreach(newFacesBuffer += _)
  for (x <- 0 to 5 - kept.size) { newFacesBuffer += die.roll }
  val newFaces = newFacesBuffer.toIndexedSeq
  newFaces
}

//Unnoetig
def rollIt(kept: IndexedSeq[Int]): IndexedSeq[Int] = {
  var facesBuffer = new ListBuffer[Int]()
  kept.foreach(facesBuffer += _)
  for (x <- 0 to 5 - kept.size) { facesBuffer += die.roll }
  val newFaces = facesBuffer.toIndexedSeq
  newFaces
}

val faces = firstthrow()
val choice = IndexedSeq(2, 4)

val kept = keep(choice, faces)

val newfaces = rollIt(choice)




cyberKitty.evaluateDies(kept, king)

val king1 = cyberKitty.evaluateDies(kept, king)


king

