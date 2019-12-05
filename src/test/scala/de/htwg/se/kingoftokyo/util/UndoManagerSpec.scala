package de.htwg.se.kingoftokyo.util

import de.htwg.se.kingoftokyo.model._
import org.scalatest._

class UndoManagerSpec extends WordSpec with Matchers {

  "UndoManager" when {
    "new" should {
      val udm = new UndoManager
      "do Step" in {
        udm.undoStep should be (udm.redoStep)
      }
    }
  }
}
