package de.htwg.se.kingoftokyo.model.rollResultComp.rollResultBaseComponent
import com.google.inject.{Guice, Inject}
import net.codingwell.scalaguice.InjectorExtensions._
import de.htwg.se.kingoftokyo.model.rollResultComp.RollResultInterface
import de.htwg.se.kingoftokyo.KingOfTokyoModule

case class RollResult (result: Vector[Int]) extends RollResultInterface {

  def this() = this(Vector.empty)

  override def throwOne(): Vector[Int] = {
    Throw(6).throwDies()
  }

  override def filterThrowResult(selectionStr: String): RollResultInterface = {
    val selection = selectionStr.split(",").toVector
    RollResult(for {x <- selection} yield this.result(x.toInt - 1))
  }

  override def mapFacesToString(i: Int): String = {
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

  override def evaluateHeart(): Int = {
    result.count(x => x == 5)
  }

  override def evaluateEnergy(): Int = {
    result.count(x => x == 4)
  }

  override def evaluateAttacks(): Int = {
    result.count(x => x == 6)
  }

  override def evaluateStars(): Int = {
    val c1 = result.count(x => x == 1)
    val c2 = result.count(x => x == 2)
    val c3 = result.count(x => x == 3)
    val s1 = if (c1 < 3) 0 else c1 - 2
    val s2 = if (c2 < 3) 0 else c2 - 1
    val s3 = if (c3 < 3) 0 else c3
    s1 + s2 + s3
  }

  override def length: Int = {
    result.length
  }

  override def toIntVector: Vector[Int] = {
    this.result
  }

  override def rollResultXML: String = {
    var ret = ""
    for (i <- result.indices) {
      ret += result(i).toString
      if (i != result.length - 1) ret = ret.concat(",")
    }
    ret
  }

  override def set(result: Vector[Int]): RollResultInterface = copy(result)

  override def stringToIntVector(string: String): Vector[Int] = {
    val ret = string.split(",").map(s => s.toInt).toVector
    ret
  }
}
