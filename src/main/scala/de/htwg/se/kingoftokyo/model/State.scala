package de.htwg.se.kingoftokyo.model

object State extends Enumeration {
  type State = Value
  val WaitForPlayerNames, WaitFor1stThrow, WaitFor2ndThrow, ThrowComplete, WaitForAttack, WaitForKotDecision = Value
}
