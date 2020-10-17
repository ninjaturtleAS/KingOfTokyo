package de.htwg.se.kingoftokyo.controller.controllerComponent.controllerComponent

import com.google.inject.{Guice, Inject}
import net.codingwell.scalaguice.InjectorExtensions._
import de.htwg.se.kingoftokyo.KingOfTokyoModule
import de.htwg.se.kingoftokyo.controller.controllerComponent.State._
import de.htwg.se.kingoftokyo.controller.controllerComponent._
import de.htwg.se.kingoftokyo.model.playGroundComp.PlayGroundInterface
import de.htwg.se.kingoftokyo.model.fileIoComponent.FileIoInterface
import de.htwg.se.kingoftokyo.util._

import scala.swing.Publisher
import scala.util.Try

class Controller @Inject()(var playGround: PlayGroundInterface) extends ControllerInterface with Publisher {
  var state: GameState = WaitForPlayerNames
  playGround.setState(state)
  private val undoManager = new UndoManager
  val injector = Guice.createInjector(new KingOfTokyoModule)
  val fileIo = injector.instance[FileIoInterface]

  var winnerString: String = ""

  override def getWinnerString(): String = this.winnerString

  def save: Unit = {
    playGround.setState(state)
    fileIo.save(playGround)
    publish(new PlaygroundChanged)
  }

  def load: Unit = {
    playGround = fileIo.load
    state = playGround.getState
    publish(new PlaygroundChanged)
  }


  override def newGame: PlayGroundInterface = {
    playGround = injector.instance[PlayGroundInterface]//(Names.named("initPG"))
    state = WaitForPlayerNames
    playGround.setState(state)
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
        playGround.setState(state)
        publish(new PlaygroundChanged)
        playGround
    }
  }

  override def evaluateThrow(): PlayGroundInterface = {
    playGround = playGround.getGood(playGround.getRollResult)
    val tuplePg1kotWasAttacked2 = playGround.attack(playGround.getRollResult)
    playGround = tuplePg1kotWasAttacked2._1
    val kotWasAttacked = tuplePg1kotWasAttacked2._2
    val finished = playGround.checkFinish
    if (finished._1) {
      winnerString = finished._2
      state = End
      playGround.setState(state)
    }
    else if (kotWasAttacked) {
      state = WaitForKotDecision
      playGround.setState(state)
    }
    else {
      playGround = nextTurn()
    }
    publish(new PlaygroundChanged)
    playGround
  }

  override def completeThrow(): PlayGroundInterface = {
    playGround = playGround.completeThrow()
    state = ThrowComplete
    playGround.setState(state)
    publish(new PlaygroundChanged)
    playGround
  }

  //0 = Heart, 1 = Star, 2 = None
  override def buy(x: Int): PlayGroundInterface = {
    x match {
      case 0 => playGround = playGround.buyHeart
      case 1 => playGround = playGround.buyStar
      case _ =>
    }
    if (x == 0 || x == 1) {
      playGround = nextTurn()
    } else {
      playGround = playGround.nextTurn
      state = WaitFor1stThrow
      playGround.setState(state)
    }
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
    if (list.isSuccess || filter == "") {
      playGround = playGround.filterThrowResult(filter)
        .throwDies()
      state = if (state==WaitFor1stThrow) WaitFor2ndThrow else  ThrowComplete
      playGround.setState(state)
      publish(new PlaygroundChanged)
      playGround
    }
    else {
      publish(new PlaygroundChanged)
      this.playGround
    }

  }

  override def playGroundToString(): String = {
    playGround.toString
  }

  override def nextTurn():PlayGroundInterface = {
    if (playGround.checkEnergy) {
      state = WaitForBuy
      playGround.setState(state)
    } else {
      playGround = playGround.nextTurn
      state = WaitFor1stThrow
      playGround.setState(state)
    }
    playGround
  }

  override def kotStay(): PlayGroundInterface = {
    playGround = nextTurn()
    publish(new PlaygroundChanged)
    playGround
  }

  override def kotLeave(): PlayGroundInterface = {
    playGround = playGround.setKOT(playGround.getLapNr % playGround.getPlayers.getLength)
    playGround = nextTurn()
    publish(new PlaygroundChanged)
    playGround
  }

  override def contStay(): ControllerInterface = {
    publish(new PlaygroundChanged)
    this
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
