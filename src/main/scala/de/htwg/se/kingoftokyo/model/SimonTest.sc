import java.security.SecureRandom

import de.htwg.se.kingoftokyo.KingOfTokyo
import de.htwg.se.kingoftokyo.model._
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

case class RollResult(result: Vector[Int]) {

  def keepThrow(selection: Vector[Int]): RollResult = {
    RollResult( for {x <- selection} yield this.result(x))
  }

  def keepThrow(selectionStr: String): RollResult = {
    val selection = selectionStr.split(" ").toVector
    RollResult( for {x <- selection} yield this.result(x.toInt))
  }
}

val x = RollResult(Vector(1,2,3,4,5))
val y = x.keepThrow("2 4")
val s = strHelper(1).concat(strHelper(5))


case class PlayersCreator(stringOfNames: String) {
  //val aNames: ArrayBuffer[String] = new ArrayBuffer[String]()
  val vNames: List[String] = stringOfNames.split(",").toList
  val numberOfPlayers: Integer = vNames.length

  def toStringList: List[String] = {
    vNames
  }

}
import swing._
import swing.Dialog._
import event._

//object TempConverter extends SimpleSwingApplication {
//  def top = new MainFrame {
//    title = "Celsius/Fahrenheit Converter"
//    object celsius extends TextField { columns = 5 }
//    object fahrenheit extends TextField { columns = 5 }
//    contents = new FlowPanel {
//      contents += celsius
//      contents += new Label(" Celsius  =  ")
//      contents += fahrenheit
//      contents += new Label(" Fahrenheit")
//      border = Swing.EmptyBorder(15, 10, 10, 10)
//    }
//    listenTo(celsius, fahrenheit)
//    reactions += {
//      case EditDone(`fahrenheit`) =>
//        val f = fahrenheit.text.toInt
//        val c = (f - 32) * 5 / 9
//        celsius.text = c.toString
//      case EditDone(`celsius`) =>
//        val c = celsius.text.toInt
//        val f = c * 9 / 5 + 32
//        fahrenheit.text = f.toString
//    }
//  }
//}
//TempConverter.top.visible = true
object ProcessInputString extends Frame {
  def getPlayerNames = new MainFrame {
    title = "Spielernamen kommagetrennt eingeben"
    object namen extends TextField {columns = 30}
    //object getNames extends Button
    val button = new Button("Process")
    contents = new FlowPanel {
      contents += new Label("Namen: ")
      contents += namen
      border = Swing.EmptyBorder(15, 10, 10, 10)
    }
  }
}
ProcessInputString.getPlayerNames