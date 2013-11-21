import scala.util.control.NonFatal

object Main extends App {
  try {
    val choice = args(0)
    choice.trim match {
      case "1" => sample.remote.calculator.CalcApp.main(Array.empty)
      case "2" => sample.remote.calculator.CreationApp.main(Array.empty)
      case "3" => sample.remote.calculator.LookupApp.main(Array.empty)
    }
  } catch {
    case NonFatal(e) => println("1 for calculator, 2 for creation, 3 for lookup")
  }
}