
case class Player(name:String,var heart:Int, var stars:Int ,var energy:Int) {

  def gainHeart(gain: Int): Player = copy(name, heart + gain, stars, energy)
  def looseHeart(lost: Int): Player = copy(name, heart - lost, stars, energy)

  def gainStar(gain: Int): Player = copy(name, heart, stars + gain, energy)
  def looseStar(lost: Int): Player = copy(name, heart, stars - lost, energy)

  def gainEnergy(gain: Int): Player =  copy(name, heart, stars, energy + gain)
  def spendEnergy(spend: Int): Player = copy(name, heart, stars, energy - spend)

  def info = f"$name%s, Heart:  $heart%d, Stars:  $stars%d, Energy: $energy%d"

}

var king = Player("King", 10, 0, 0)
var cyberKitty = Player("Cyber Kitty", 10, 0, 0)
var ailionoid = Player("Ailionoid", 10, 0, 0)
var gigasaurus = Player("Gigasaurus", 10, 0, 0)
var info = gigasaurus.info

king = king.looseHeart(1)
king.info
import java.util.Random
import java.security.SecureRandom

import scala.collection.mutable.{ArrayBuffer, ListBuffer}

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

//val newfaces = rollIt(choice)



def evaluateDies(result: IndexedSeq[Int]): IndexedSeq[Int] = {
  var help1 = 0
  var help2 = 0
  var help3 = 0
  var res =  ArrayBuffer[Int](0,0,0,0)

    for (x <- result) {
      x match {
        case 1 => help1 += 1
        case 2 => help2 += 1
        case 3 => help3 += 1
        case 4 => res(0) = res(0) + 1
        case 5 => res(1) = res(1) + 1
        case 6 => res(2) += 1
      }
    }
    if (help1 >= 3) res(3) = res(3) + (help1 - 2)
    if (help2 >= 3) res(3) = res(3) + (help1 - 1)
    if (help3 >= 3) res(3) = res(3) + (help1)
  val resOut = res.toIndexedSeq
  resOut
}

def evaluateAttack(faces: IndexedSeq[Int]): Int = {
  var help = 0
  for (x <- faces) {
    if(x == 6)
      help += 1
  }
  help
}

val test = IndexedSeq(1,1,1,5,4,6)

val result = evaluateDies(test)


king

case class Turn(var player: Player, var tokyo: Player) {
  def getGood(result: IndexedSeq[Int]): Player = {
    player = player.gainEnergy(result(0))
    player = player.gainHeart(result(1))
    player = player.gainStar(result(3))
    player
  }
  def getAttack(int: Int): Player = {
    tokyo = tokyo.looseHeart(int)
    tokyo
  }
}

cyberKitty = Turn(cyberKitty, king).getGood(result)

king = Turn(cyberKitty, king).getAttack(result(2))