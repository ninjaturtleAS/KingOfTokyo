import java.security.SecureRandom

import de.htwg.se.kingoftokyo.KingOfTokyo
import de.htwg.se.kingoftokyo.controller.controllerComponent.controllerComponent
import de.htwg.se.kingoftokyo.model._
import de.htwg.se.kingoftokyo.model.playGroundComp.playGroundBaseComponent.PlayGround
import de.htwg.se.kingoftokyo.model.playGroundComp.PlayGroundInterface
import de.htwg.se.kingoftokyo.model.playersComp.playersBaseComponent.Players
import de.htwg.se.kingoftokyo.model.rollResultComp.RollResultInterface
import de.htwg.se.kingoftokyo.model.rollResultComp.rollResultBaseComponent.RollResult
import javax.swing.text.DocumentFilter.FilterBypass
import javax.swing.text.{AbstractDocument, AttributeSet, DocumentFilter}

import scala.collection.mutable.ListBuffer





def strHelper (i: Int): String = {
  i match {
    case 1 | 2 | 3 => return i.toString() + " "
    case 4 => return "Energy "
    case 5 => return "Heart "
    case 6 => return "Attack "
  }
}

//case class RollResult(result: Vector[Int]) {
//
//  def keepThrow(selection: Vector[Int]): RollResult = {
//    RollResult( for {x <- selection} yield this.result(x))
//  }
//
//  def keepThrow(selectionStr: String): RollResult = {
//    val selection = selectionStr.split(" ").toVector
//    RollResult( for {x <- selection} yield this.result(x.toInt))
//  }
//}

val x = RollResult(Vector(1,2,3,4,5))
//val y = x.keepThrow("2 4")
val s = strHelper(1).concat(strHelper(5))


case class PlayersCreator(stringOfNames: String) {
  //val aNames: ArrayBuffer[String] = new ArrayBuffer[String]()
  val vNames: List[String] = stringOfNames.split(",").toList
  val numberOfPlayers: Integer = vNames.length

  def toStringList: List[String] = {
    vNames
  }
}
val r = RollResult(Vector.empty)
val initPG = PlayGround(new Players(),0 , RollResult(Vector.empty), 0)
val initCont = new controllerComponent.Controller(initPG)
