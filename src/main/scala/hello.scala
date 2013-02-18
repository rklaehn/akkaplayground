import akka.actor._

import rklaehn._
import akka.pattern.ask
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global
import akka.util.Timeout

case class Print(s: String)
case object PrintAck

class InverseCalculator(dispatcher:ActorRef) extends Actor {

  dispatcher ! MessageDiscarder.WorkerAvailable

  def receive = {
    case value:Double =>
      Thread.sleep(1000)
      val result = 1.0/value
      println(result)
      // Calculation completed. Make ourselves available again!
      dispatcher ! MessageDiscarder.WorkerAvailable
    case msg =>
      // Message not understood. Make ourselves available anyway!
      dispatcher ! MessageDiscarder.WorkerAvailable
  }
}

object HelloWorld {
  def main(args: Array[String]) {
    val system = ActorSystem("HelloWorld")
    val discarder = system.actorOf(Props[MessageDiscarder])
    val worker = system.actorOf(Props(new InverseCalculator(discarder)))
    for(i<-1 to 10) {
    	discarder ! i.toDouble
    	Thread.sleep(300)
    }
    Thread.sleep(10000)
    system.shutdown()
  }
}