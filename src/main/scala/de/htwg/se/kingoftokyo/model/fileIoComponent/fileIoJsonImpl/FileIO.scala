package de.htwg.se.kingoftokyo.model.fileIoComponent.fileIoJsonImpl

import com.google.inject.Guice
import net.codingwell.scalaguice.InjectorExtensions._
import de.htwg.se.kingoftokyo.KingOfTokyoModule
import de.htwg.se.kingoftokyo.controller.controllerComponent.State
import de.htwg.se.kingoftokyo.model.fileIoComponent.FileIoInterface
import de.htwg.se.kingoftokyo.model.playGroundComp.PlayGroundInterface
import de.htwg.se.kingoftokyo.model.playersComp.PlayersInterface
import de.htwg.se.kingoftokyo.model.rollResultComp.RollResultInterface
import play.api.libs.json._

import scala.io.Source

class FileIO extends FileIoInterface {
  override def load: PlayGroundInterface = {
    val injector = Guice.createInjector(new KingOfTokyoModule)
    var playground: PlayGroundInterface =null
    playground  = injector.instance[PlayGroundInterface]
    val source: String = Source.fromFile("playground.json").getLines.mkString
    val json: JsValue = Json.parse(source)
    var players = injector.instance[PlayersInterface]
    val playersSize = (json \ "playground" \ "playersSize").as[Int]
    for (index <- 0 until playersSize) {
      val playerJ = (json \\ "player")(index)
      val name = (playerJ \ "name").as[String]
      val energy = (playerJ \ "energy").as[Int]
      val heart = (playerJ \ "heart").as[Int]
      val stars = (playerJ \ "stars").as[Int]
      players.addNewPlayer(name, energy, heart, stars)
    }
    val lapNr = (json \ "playground" \ "lapNr").as[Int]
    val rollResultVector = (json \ "playground" \ "rollResult").as[Vector[Int]]
    val rollResult = playground.getRollResult.set(rollResultVector)
    val kot = (json \ "playground" \ "kot").as[Int]
    val state = (json \ "playground" \ "state").as[String]
    playground.set(players, lapNr, rollResult, kot, State.mapStringtoState(state))
    playground
  }

  override def save(playground: PlayGroundInterface): Unit = {
    import java.io._
    val pw = new PrintWriter(new File("playground.json"))
    pw.write(Json.prettyPrint(playgroundToJson(playground)))
    pw.close
  }

  def playgroundToJson(playground: PlayGroundInterface): JsObject = {
    val players = playground.getPlayers.getPlayers()
    val rr = playground.getRollResult.toIntVector
    Json.obj(
      "playground" -> Json.obj(
        "playersSize" -> JsNumber(players.size),
        "players" -> Json.toJson(
          for {i <- 0 until players.size} yield {
            Json.obj(
              "player" -> Json.obj(
                "name" -> Json.toJson(players(i).name),
                "energy" -> JsNumber(players(i).energy),
                "heart" -> JsNumber(players(i).heart),
                "stars" -> JsNumber(players(i).stars)
              )
            )
          }
        ),
        "lapNr" -> JsNumber(playground.getLapNr),
        "rollResult" -> Json.toJson(rr),
        "kot" -> JsNumber(playground.getKOT),
        "state" -> Json.toJson(playground.getState)
      )
    )
  }
}
