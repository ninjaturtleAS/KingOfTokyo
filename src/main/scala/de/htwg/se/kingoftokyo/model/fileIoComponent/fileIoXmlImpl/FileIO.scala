package de.htwg.se.kingoftokyo.model.fileIoComponent.fileIoXmlImpl


import com.google.inject.Guice
import com.google.inject.name.Names
import net.codingwell.scalaguice.InjectorExtensions._
import de.htwg.se.kingoftokyo.KingOfTokyoModule
import de.htwg.se.kingoftokyo.model.fileIoComponent.FileIoInterface
import de.htwg.se.kingoftokyo.model.playGroundComp.PlayGroundInterface
import de.htwg.se.kingoftokyo.model.playersComp.PlayersInterface
import de.htwg.se.kingoftokyo.model.playersComp.playersBaseComponent.Player
import de.htwg.se.kingoftokyo.model.playersComp.playersBaseComponent.Player

import scala.xml.{Elem, NodeSeq, PrettyPrinter}


class FileIO  extends FileIoInterface {

  override def load: PlayGroundInterface = {
    var playGround: PlayGroundInterface = null
    val file = scala.xml.XML.loadFile("playground.xml")
    //val sizeAttr = (file \\ "playground" \ "@size")
    //val size = sizeAttr.text.toInt
    val injector = Guice.createInjector(new KingOfTokyoModule)
    /*size match {
      case 1 => grid = injector.instance[GridInterface](Names.named("tiny"))
    }*/
    playGround = injector.instance[PlayGroundInterface]
    val players = (file \\ "players").text
    /*val cellNodes = (file \\ "cell")
    for (cell <- cellNodes) {
      val row: Int = (cell \ "@row").text.toInt
      val col: Int = (cell \ "@col").text.toInt
      val value: Int = cell.text.trim.toInt
      grid = grid.set(row, col, value)
      val given = (cell \ "@given").text.toBoolean
      val showCandidates = (cell \ "@showCandidates").text.toBoolean
      if (given) grid = grid.setGiven(row, col, value)
      if (showCandidates) grid = grid.setShowCandidates(row, col)
    }*/
    playGround
  }

  def save(playground: PlayGroundInterface): Unit = ??? //saveString(playground)

  def saveXML(playGround: PlayGroundInterface): Unit = {
    scala.xml.XML.save("grid.xml", playgroundToXml(playGround))
  }

//  def saveString(pgStr: String): Unit = {
    /*import java.io._
    val pw = new PrintWriter(new File("playground.xml"))
    val prettyPrinter = new PrettyPrinter(120, 4)
    val xml = prettyPrinter.format(playgroundToXml(playGround))
    pw.write(pgStr)
    pw.close
  }*/
  def playgroundToXml(playground: PlayGroundInterface): Elem = {
    <playground players={playground.getPlayers.playersXML()} lapNr={playground.getLapNr.toString} rollResult={playground.getRollResult.rollResultXML} kot={playground.getKOT.toString}>
    </playground>
  }

  def playersToXml(players: PlayersInterface): Elem = {
    <players info={players.playersXML()}>
    </players>
  }
}
