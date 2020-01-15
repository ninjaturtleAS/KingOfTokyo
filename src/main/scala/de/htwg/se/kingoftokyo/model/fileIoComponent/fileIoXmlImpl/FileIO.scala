package de.htwg.se.kingoftokyo.model.fileIoComponent.fileIoXmlImpl


import com.google.inject.Guice
import com.google.inject.name.Names
import net.codingwell.scalaguice.InjectorExtensions._
import de.htwg.se.kingoftokyo.KingOfTokyoModule
import de.htwg.se.kingoftokyo.model.fileIoComponent.FileIoInterface
import de.htwg.se.kingoftokyo.controller.controllerComponent.ControllerInterface
import de.htwg.se.kingoftokyo.controller.controllerComponent.State
import de.htwg.se.kingoftokyo.model.playGroundComp.PlayGroundInterface
import de.htwg.se.kingoftokyo.model.playersComp.PlayersInterface

import scala.xml.{Elem, NodeSeq, PrettyPrinter}


class FileIO  extends FileIoInterface {

  override def load: PlayGroundInterface = {
    var playGround: PlayGroundInterface = null
    val file = scala.xml.XML.loadFile("playground.xml")
    val injector = Guice.createInjector(new KingOfTokyoModule)
    playGround = injector.instance[PlayGroundInterface]
    val playersStr = (file \\ "playground" \ "@players").text
    val lapNr = (file \\ "playground" \ "@lapNr").text.toInt
    val rollResultStr = (file \\ "playground" \ "@rollResult").text
    val kot = (file \\ "playground" \ "@kot").text.toInt
    val stateStr = (file \\ "playground" \ "@state").text
    val state = State.mapStringtoState(stateStr)
    val players = playGround.getPlayers.set(playGround.getPlayers.playersStrToPlayers(playersStr, de.htwg.se.kingoftokyo.model.playersComp.playersBaseComponent.Player("")))
    val rollResult = playGround.getRollResult.set(playGround.getRollResult.stringToIntVector(rollResultStr))
    playGround = playGround.set(players, lapNr, rollResult, kot, state)
    playGround
  }

  def save(playground: PlayGroundInterface): Unit = saveString(playground)

  def saveXML(playGround: PlayGroundInterface): Unit = {
    scala.xml.XML.save("playground.xml", playgroundToXml(playGround))
  }

  def saveString(pg: PlayGroundInterface): Unit = {
    import java.io._
    val pw = new PrintWriter(new File("playground.xml"))
    val prettyPrinter = new PrettyPrinter(120, 4)
    val xml = prettyPrinter.format(playgroundToXml(pg))
    pw.write(xml)
    pw.close()
  }
  def playgroundToXml(playground: PlayGroundInterface): Elem = {
    <playground players={playground.getPlayers.playersXML()} lapNr={playground.getLapNr.toString} rollResult={playground.getRollResult.rollResultXML}
                kot={playground.getKOT.toString} state={playground.getState.toString}>
    </playground>
  }

  def playersToXml(players: PlayersInterface): Elem = {
    <players info={players.playersXML()}>
    </players>
  }
}
