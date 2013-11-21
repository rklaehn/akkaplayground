package sample.remote.calculator

object Main extends App {
  try {
    val choice = args(0)
    choice.trim match {
      case "calc" => sample.remote.calculator.CalcApp.main(Array.empty)
      case "create" => sample.remote.calculator.CreationApp.main(Array.empty)
      case "lookup" => sample.remote.calculator.LookupApp.main(Array.empty)
    }
  } catch {
    case e:MatchError =>
      println("calc for calculation server, create for creating remote calculator, lookup for lookup remote calculator")
  }
}