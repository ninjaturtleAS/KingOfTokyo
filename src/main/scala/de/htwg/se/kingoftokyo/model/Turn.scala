package de.htwg.se.kingoftokyo.model

import java.security.SecureRandom

import scala.collection.mutable
import scala.collection.mutable.{ArrayBuffer, ListBuffer}

/*
1 = 1
2 = 2
3 = 3
4 = Energy
5 = Heart
6 = Attack
 */

class Turn (var player: Player) {
  //implicit val random = new SecureRandom
  val initDie = 6
  val die = Die(initDie) (new SecureRandom())
  def throwOne(): IndexedSeq[Int] = {
    //IndexedSeq(die.roll, die.roll, die.roll, die.roll, die.roll, die.roll)
    Throw(6).throwDies()
  }

  def keepThrow(choice: IndexedSeq[Int], faces: IndexedSeq[Int]): IndexedSeq[Int] = {
    var chosen = new ListBuffer[Int]()
    for (x <- choice) {
      chosen += faces(x - 1)
    }
    val kept = chosen.toIndexedSeq
    val newFacesBuffer = new ListBuffer[Int]()
    kept.foreach(newFacesBuffer += _)
    for (x <- 0 to 5 - kept.size) { newFacesBuffer += die.roll }
    newFacesBuffer.toIndexedSeq
  }

  def printThrow(faces: IndexedSeq[Int]): String = {
    var string = ""
    for (x <- faces) {
      x match {
        case 1 | 2 | 3 => string += f"$x%d "
        case 4 => string += "Energy "
        case 5 => string += "Heart "
        case 6 => string += "Attack "
      }
    }
    string
  }

  def evaluateDies(result: IndexedSeq[Int]): IndexedSeq[Int] = {
    var help1 = 0
    var help2 = 0
    var help3 = 0
    val res =  ArrayBuffer[Int](0,0,0,0)

    for (x <- result) {
      x match {
        case 1 => help1 += 1
        case 2 => help2 += 1
        case 3 => help3 += 1
        case 4 => res(0) += 1
        case 5 => res(1) += 1
        case 6 => res(2) += 1
      }
    }
    if (help1 >= 3) res(3) += (help1 - 2)
    if (help2 >= 3) res(3) += (help2 - 1)
    if (help3 >= 3) res(3) += help3
    res.toIndexedSeq
  }


  def getGood(result: IndexedSeq[Int]): Player = {
    player = player.gainEnergy(result(0))
    player = player.gainHeart(result(1))
    player = player.gainStar(result(3))
    player
  }
  /*
  def getAttack(int: Int): Player = {
    tokyo = tokyo.looseHeart(int)
    tokyo
  }
  */

}
