package de.htwg.se.kingoftokyo.model.fileIoComponent

import de.htwg.se.kingoftokyo.model.playGroundComp.PlayGroundInterface
trait FileIoInterface {

  def load: PlayGroundInterface
  def save(grid: PlayGroundInterface): Unit
}
