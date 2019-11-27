package de.htwg.se.kingoftokyo.controller

object State extends Enumeration {
  type GameState = Value
  val WaitForPlayerNames, WaitFor1stThrow, WaitFor2ndThrow, ThrowComplete, WaitForKotDecision = Value

  val map = Map[GameState, String] (
    WaitForPlayerNames -> "Bitte Spielernamen kommagetrennt eingeben",
    WaitFor1stThrow -> "Ihre Auswahl",
    WaitFor2ndThrow -> "Ihre Auswahl",
    ThrowComplete -> "Wurf wird ausgewertet",
    WaitForKotDecision -> "Wollen Sie King of Tokyo bleiben (J/N)"
  )

  def message(state: GameState): String = {
    map(state)
  }
}