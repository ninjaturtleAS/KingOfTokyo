package de.htwg.se.kingoftokyo.model.fileIoComponent.fileIoJsonImpl

import com.google.inject.Guice
import com.google.inject.name.Names
import net.codingwell.scalaguice.InjectorExtensions._
import de.htwg.se.kingoftokyo.KingOfTokyoModule
import de.htwg.se.kingoftokyo.model.fileIoComponent.FileIoInterface
import de.htwg.se.kingoftokyo.model.playGroundComp.PlayGroundInterface
import play.api.libs.json._

class FileIO extends FileIoInterface {
  override def load: PlayGroundInterface = ???

  override def save(grid: PlayGroundInterface): Unit = ???
}
