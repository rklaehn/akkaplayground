import akka.actor._
import akka.pattern.ask
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global
import akka.util.Timeout

case class Print(s: String)
case object PrintAck

class Printer extends Actor {
  def receive = {
    case Print(s) => {
      println(s)
      sender ! PrintAck
    }
  }
}

object HelloWorld {
  def main(args: Array[String]) {
    val system = ActorSystem("HelloWorld")
    val printer = system.actorOf(Props[Printer])
    implicit val timeout = Timeout(5 seconds)
    (printer ? Print("Hello world")) onComplete {
      _ => system.shutdown()
    }
  }
}