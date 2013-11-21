package sample.remote.calculator

import scala.util.control.NonFatal

object Main extends App {
  try {
    val choice = args(0)
    choice.trim match {
      case "calc" => sample.remote.calculator.CalcApp.main(Array.empty)
      case "create" => sample.remote.calculator.CreationApp.main(Array.empty)
      case "lookup" => sample.remote.calculator.LookupApp.main(Array.empty)
    }
  } catch {
    case NonFatal(e) => println("calc for calculator, create for creation, lookup for lookup")
  }
}