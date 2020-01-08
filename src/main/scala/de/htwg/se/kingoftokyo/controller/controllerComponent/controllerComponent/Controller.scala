package de.htwg.se.kingoftokyo.controller.controllerComponent.controllerComponent

import com.google.inject.name.{Named, Names}
import com.google.inject.{Guice, Inject}
import net.codingwell.scalaguice.InjectorExtensions._
import de.htwg.se.kingoftokyo.KingOfTokyoModule
import de.htwg.se.kingoftokyo.controller.controllerComponent.State._
import de.htwg.se.kingoftokyo.controller.controllerComponent._
import de.htwg.se.kingoftokyo.model.playGroundComp.PlayGroundInterface
import de.htwg.se.kingoftokyo.model.playGroundComp.playGroundBaseComponent.PlayGround
import de.htwg.se.kingoftokyo.model.playersComp.playersBaseComponent.Players
import de.htwg.se.kingoftokyo.model.rollResultComp.rollResultBaseComponent.RollResult
import de.htwg.se.kingoftokyo.util._

import scala.swing.Publisher
import scala.util.Try

class Controller @Inject()(/*@Named("initCont")*/ var playGround: PlayGroundInterface) extends ControllerInterface with Publisher {
  var state: GameState = WaitForPlayerNames
  private val undoManager = new UndoManager
  val injector = Guice.createInjector(new KingOfTokyoModule)

  override def newGame: PlayGroundInterface = {
    playGround = injector.instance[PlayGroundInterface]
    playGround = PlayGround(new Players(),0 , RollResult(Vector.empty), 0)
    state = WaitForPlayerNames
    publish(new PlaygroundChanged)
    playGround
  }

  override def createPlayers(playerNames: Option[String]): PlayGroundInterface = {
    playerNames match {
      case None =>
        publish(new PlaygroundChanged)
        playGround
      case Some(playerNames) =>
        undoManager.doStep (new CreatePlayersCommand (playerNames, this) )
        playGround = playGround.createPlayerInRandomOrder (playerNames)
        .throwDies ()
        state = WaitFor1stThrow
        publish(new PlaygroundChanged)
        playGround
    }
  }

  override def evaluateThrow(): PlayGroundInterface = {
    playGround = playGround.getGood(playGround.getRollResult)
    playGround = playGround.attack(playGround.getRollResult)
    publish(new PlaygroundChanged)
    playGround = nextTurn()
    publish(new PlaygroundChanged)
    playGround
  }

  override def completeThrow(): PlayGroundInterface = {
    playGround = playGround.completeThrow()
    state = ThrowComplete
    publish(new PlaygroundChanged)
    playGround
  }

  override def incLapNr: PlayGroundInterface = {
    playGround = playGround.incLapNr()
    publish(new PlaygroundChanged)
    playGround
  }

  override def throwDies():PlayGroundInterface = {
    playGround = playGround.throwDies()
    publish(new PlaygroundChanged)
    playGround
  }

  override def filterThrowResult(filter: String):PlayGroundInterface = {
    val selection = filter.split(",").toVector
    val list = Try(selection.map(x => x.toInt))
    if (list.isSuccess) {
      playGround = playGround.filterThrowResult(filter)
        .throwDies()
      state = if (state==WaitFor1stThrow) {WaitFor2ndThrow}
      else  {ThrowComplete}
      publish(new PlaygroundChanged)
      playGround
    }
    else {
      publish(new PlaygroundChanged)
      this.playGround
    }

  }

  override def playGroundToString(): String = {
    playGround.toString()
  }

  override def nextTurn():PlayGroundInterface = {
      playGround = PlayGround(playGround.getPlayers, playGround.getLapNr + 1,
        RollResult(playGround.getRollResult.throwOne()), playGround.getKOT)
    state = WaitFor1stThrow
    playGround
  }

  override def undo: Unit = {
    undoManager.undoStep
    publish(new PlaygroundChanged)
  }

  override def redo: Unit = {
    undoManager.redoStep
    publish(new PlaygroundChanged)
  }

  override def getState(): GameState = state

  override def getPlayground(): PlayGroundInterface = playGround
}
