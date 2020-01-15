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
    var playground  = injector.instance[PlayGroundInterface]
    var players = injector.instance[PlayersInterface]

    val source: String = Source.fromFile("playground.json").getLines.mkString
    val json: JsValue = Json.parse(source)

    val playersSize = (json \ "playground" \ "playersSize").as[Int]
    for (index <- 0 until playersSize) {
      val playerJ = (json \\ "player")(index)
      val name: String = (playerJ \ "name").as[String]
      val energy: Int = (playerJ \ "energy").as[Int]
      val heart: Int = (playerJ \ "heart").as[Int]
      val stars: Int = (playerJ \ "stars").as[Int]
      players = players.addNewPlayer(name, energy, heart, stars)
    }

    val lapNr: Int = (json \ "playground" \ "lapNr").as[Int]
    val rollResultVector: Vector[Int] = (json \ "playground" \ "rollResult").as[Vector[Int]]
    val rollResult: RollResultInterface = playground.getRollResult.set(rollResultVector)
    val kot: Int = (json \ "playground" \ "kot").as[Int]
    val stateString: String = (json \ "playground" \ "state").as[String]
    val state: State.GameState = State.mapStringtoState(stateString)

    playground.set(players, lapNr, rollResult, kot, state)
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
          for {i <- players.indices} yield {
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
        "state" -> Json.toJson(playground.getState.toString)
      )
    )
  }
}
