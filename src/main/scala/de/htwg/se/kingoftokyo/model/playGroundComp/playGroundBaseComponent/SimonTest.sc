import com.google.inject.Guice
import de.htwg.se.kingoftokyo.KingOfTokyoModule
import de.htwg.se.kingoftokyo.model.playGroundComp.PlayGroundInterface
import de.htwg.se.kingoftokyo.model.playGroundComp.playGroundBaseComponent.PlayGround
import de.htwg.se.kingoftokyo.model.playersComp.PlayersInterface
import de.htwg.se.kingoftokyo.model.playersComp.playersBaseComponent.{Player, Players}
import de.htwg.se.kingoftokyo.model.rollResultComp.rollResultBaseComponent.RollResult
import net.codingwell.scalaguice.InjectorExtensions._
//
//import scala.xml.Elem
//
//val lapNr = 1
//val kot = 1
//val a = Player
//val alex = de.htwg.se.kingoftokyo.model.playersComp.playersBaseComponent.Player("Alex")
//val simon = de.htwg.se.kingoftokyo.model.playersComp.playersBaseComponent.Player("Simon")
//val marco = de.htwg.se.kingoftokyo.model.playersComp.playersBaseComponent.Player("Marco")
//val testString = "Alex, Simon, Marco"
//val players = Players(Vector(alex, simon, marco))
//val testResult =  RollResult(Vector(1, 2, 3, 4, 5, 6))
//var playGroundWaitPlayers = PlayGround(players, lapNr, testResult, kot)
//
//
//val x: String = playGroundWaitPlayers.getRollResult.rollResultXML
//println(x)
//
//def playgroundToXml(playground: PlayGroundInterface): Elem = {
//  <playground players={playground.getPlayers.playersXML()} lapNr={playground.getLapNr.toString} rollResult={playground.getRollResult.rollResultXML} kot={playground.getKOT.toString}>
//  </playground>
//}
//
//def playersToXml(players: PlayersInterface): Elem = {
//  <players info={players.playersXML()}>
//  </players>
//}
//
//def load: PlayGroundInterface = {
//  var playGround: PlayGroundInterface = null
//  val file = scala.xml.XML.loadFile("playground.xml")
//  val injector = Guice.createInjector(new KingOfTokyoModule)
//  playGround = injector.instance[PlayGroundInterface]
//  val playersStr = (file \\ "players").text
//  val lapNr = (file \\ "lapNr").text.toInt
//  val rollResultStr = (file \\ "rollResult").text
//  val kot = (file \\ "kot").text.toInt
//  val players = playGround.getPlayers.set(playGround.getPlayers.playersStrToPlayers(playersStr, de.htwg.se.kingoftokyo.model.playersComp.playersBaseComponent.Player("")))
//  val rollResult = playGround.getRollResult.set(playGround.getRollResult.toIntVector)
//  playGround = playGround.set(players, lapNr, rollResult, kot)
//  playGround
//}
//
//val safe = playgroundToXml(playGroundWaitPlayers)
//println(safe)
//val pg = load
//println(pg)
val v = Vector(de.htwg.se.kingoftokyo.model.playersComp.playersBaseComponent.Player("a"), de.htwg.se.kingoftokyo.model.playersComp.playersBaseComponent.Player("b"))
val a = v(0)
val x = v.filter(_ != a)
