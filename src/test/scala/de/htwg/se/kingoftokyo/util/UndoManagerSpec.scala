package de.htwg.se.kingoftokyo.util

import de.htwg.se.kingoftokyo.controller.controllerComponent.controllerComponent.{Controller, CreatePlayersCommand}
import de.htwg.se.kingoftokyo.model.playGroundComp.playGroundBaseComponent.PlayGround
import de.htwg.se.kingoftokyo.model.playersComp.playersBaseComponent.{Player, Players}
import de.htwg.se.kingoftokyo.model.rollResultComp.rollResultBaseComponent.RollResult
import org.scalatest._

class UndoManagerSpec extends WordSpec with Matchers {

  "UndoManager" when {
    "new" should {
      val lapNr = 0
      val kot = 1
      val initHeart =  10
      val alex = Player("Alex")
      val simon = Player("Simon")
      val marco = Player("Marco")
      val testString = "Alex, Simon, Marco"
      val playersEmpty = Players(Vector())
      val players = Players(Vector(alex, simon, marco))
      val testResult =  RollResult(Vector(1, 2, 3, 4, 5, 6))
      val badResult = RollResult(Vector(6, 6, 6, 6, 6, 6))

      var playGroundWaitPlayers = PlayGround(players, lapNr, testResult, kot)
      var playGroundEmpty = PlayGround(playersEmpty, lapNr, testResult, kot)

      val controller = new Controller(playGroundWaitPlayers)
      val controllerEmpty = new Controller(playGroundEmpty)
      val createPlayersCommand = new CreatePlayersCommand("Simon,Alex", controller)


      val udm = new UndoManager

      "do undo step" in {
          udm.doStep(createPlayersCommand)
          udm.undoStep
          udm.getRedoStack should be (List(createPlayersCommand))
      }
      "do redo step" in {
        udm.redoStep
        udm.getUndoStack should be (List(createPlayersCommand))

      }
    }
  }
}
