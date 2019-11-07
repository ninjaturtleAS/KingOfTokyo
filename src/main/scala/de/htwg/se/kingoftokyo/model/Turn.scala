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

class Turn {
  implicit val random = new SecureRandom
  val initDie = 6
  val die = Die(initDie)
  def throwOne(): IndexedSeq[Int] = {
    IndexedSeq(die.roll, die.roll, die.roll, die.roll, die.roll, die.roll)
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

  def evaluateDies(result: IndexedSeq[Int]): IndexedSeq[Int] = {
    var help1 = 0
    var help2 = 0
    var help3 = 0
    val one = 1
    val res =  ArrayBuffer[Int](0,0,0,0)

    for (x <- result) {
      x match {
        case 1 => help1 += one
        case 2 => help2 += one
        case 3 => help3 += one
        case 4 => res(0) = res(0) + one
        case 5 => res(1) = res(1) + one
        case 6 => res(2) += one
      }
    }
    if (help1 >=3) res(3) = res(3) + (help1 - 2)
    if (help2 >= 3) res(3) = res(3) + (help1 - 1)
    if (help3 >= 3) res(3) = res(3) + (help1)
    res.toIndexedSeq
  }

}
