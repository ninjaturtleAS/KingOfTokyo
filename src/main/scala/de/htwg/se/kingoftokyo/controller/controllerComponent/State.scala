package de.htwg.se.kingoftokyo.controller.controllerComponent

object State extends Enumeration {
  type GameState = Value
  val WaitForPlayerNames, WaitFor1stThrow, WaitFor2ndThrow, ThrowComplete, WaitForKotDecision, End = Value

  val map = Map[GameState, String] (
    WaitForPlayerNames -> "Bitte Spielernamen kommagetrennt eingeben",
    WaitFor1stThrow -> "Ihre Auswahl",
    WaitFor2ndThrow -> "Ihre Auswahl",
    ThrowComplete -> "Wurf wird ausgewertet",
    WaitForKotDecision -> "Wollen Sie King of Tokyo bleiben (yes/no)",
    End -> "Spiel beendet"
  )

  def message(state: GameState): String = {
    map(state)
  }

  def mapStringtoState(string: String): GameState = string match {
      case "WaitFor1stThrow" => WaitFor1stThrow
      case "WaitForPlayerNames" => WaitForPlayerNames
      case "WaitFor2ndThrow" => WaitFor2ndThrow
      case "ThrowComplete" => ThrowComplete
      case "WaitForKotDecision" => WaitForKotDecision
      case "End" => End
    }
}