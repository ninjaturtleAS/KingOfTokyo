package de.htwg.se.kingoftokyo


import com.google.inject.AbstractModule
import net.codingwell.scalaguice.ScalaModule
import net.codingwell.scalaguice.InjectorExtensions._
import de.htwg.se.kingoftokyo.controller.controllerComponent.ControllerInterface
import de.htwg.se.kingoftokyo.controller.controllerComponent._
import de.htwg.se.kingoftokyo.model.playGroundComp.PlayGroundInterface
import de.htwg.se.kingoftokyo.model.playGroundComp.playGroundBaseComponent.PlayGround
import de.htwg.se.kingoftokyo.model.playersComp.playersBaseComponent.Players
import de.htwg.se.kingoftokyo.model.rollResultComp.rollResultBaseComponent.RollResult
import com.google.inject.name.Names
import de.htwg.se.kingoftokyo.model.fileIoComponent.FileIoInterface
import de.htwg.se.kingoftokyo.model.fileIoComponent.fileIoXmlImpl
import de.htwg.se.kingoftokyo.model.playersComp.PlayersInterface
import de.htwg.se.kingoftokyo.model.rollResultComp.RollResultInterface

class KingOfTokyoModule extends AbstractModule with ScalaModule {

    val initRR = RollResult(Vector.empty)
    val zero = 0
    val initPG = PlayGround(new Players(),zero , initRR, zero)
    //val initCont = new controllerComponent.Controller(initPG)

    override def configure(): Unit = {
        bindConstant().annotatedWith(Names.named("Zero")).to(zero)
        bind[RollResultInterface].to[RollResult]
        bind[PlayersInterface].to[Players]
        bind[PlayGroundInterface].to[PlayGround]
        bind[ControllerInterface].to[controllerComponent.Controller]
        bind[PlayGroundInterface].annotatedWith(Names.named("initPG")).toInstance(initPG)
        //bind[RollResultInterface].annotatedWith(Names.named("initRR")).toInstance(initRR)
        //bind[ControllerInterface].annotatedWith(Names.named("initCont")).toInstance(initCont)

        bind[FileIoInterface].to[fileIoXmlImpl.FileIO]
    }
}

