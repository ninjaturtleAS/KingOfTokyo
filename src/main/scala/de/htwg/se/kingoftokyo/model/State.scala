package de.htwg.se.kingoftokyo.model

object State extends Enumeration {
  type State = Value
  val Starting, WaitForPlayerNames, WaitFor1stThrow, WaitFor2ndThrow, WaitFor3rdThrow, ThrowComplete, WaitForAttack, WaitForKotDecision = Value
}
