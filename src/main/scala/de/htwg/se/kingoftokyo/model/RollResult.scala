package de.htwg.se.kingoftokyo.model

import scala.collection.mutable.{ArrayBuffer, ListBuffer}

case class RollResult(result: Vector[Int]) {

  def throwOne(): Vector[Int] = {
    //IndexedSeq(die.roll, die.roll, die.roll, die.roll, die.roll, die.roll)
    Throw(6).throwDies()
  }


  //selct what you wanna keep out of result
  def keepThrow(selection: Vector[Int]): RollResult = {
    RollResult(for {x <- selection} yield this.result(x))
  }


  def filterThrowResult(selectionStr: String): RollResult = {
    val selection = selectionStr.split(" ").toVector
    RollResult(for {x <- selection} yield this.result(x.toInt))
  }

  def mapFacesToString(i: Int): String = {
    i match {
      case 1 | 2 | 3 => i.toString() + " "
      case 4 => "Energy "
      case 5 => "Heart "
      case 6 => "Attack "
      case _ => ""
    }
  }

  override def toString(): String = {
    result.map(x => mapFacesToString(x)).mkString
  }

  def evaluateHeart(): Int = {
    result.count(x => x == 5)
  }

  def evaluateEnergy(): Int = {
    result.count(x => x == 4)
  }

  def evaluateAttacks(): Int = {
    result.count(x => x == 6)
  }

  def evaluateStars(): Int = {
    val c1 = result.count(x => x == 1)
    val c2 = result.count(x => x == 2)
    val c3 = result.count(x => x == 3)
    val s1 = if (c1 < 3) 0 else c1 - 2
    val s2 = if (c2 < 3) 0 else c2 - 1
    val s3 = if (c3 < 3) 0 else c3
    s1 + s2 + s3
  }

  def length: Int = {
    result.length
  }

  def toIntVector: Vector[Int] = {
    this.result
  }

}
