package de.htwg.se.kingoftokyo.controller

object State extends Enumeration {
  type State = Value
  val WaitForPlayerNames, WaitForThrow, WaitForAttack, WaitForKotDecision = Value
}
