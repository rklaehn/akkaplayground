package rklaehn
import akka.actor._

object MessageDiscarder {

  object WorkerAvailable

  private sealed trait State
  private case class Forwarding(to:ActorRef) extends State
  private case object Queueing extends State
  private case class Queued(msg:Any) extends State
}

class MessageDiscarder extends Actor {
  import MessageDiscarder._

  private var state:State = Queueing

  def receive = {
    case WorkerAvailable =>
      state = state match {
        case Queueing => Forwarding(sender)
        case Queued(msg) =>
          // to use forward here we would have to "fake the return address"
          sender ! msg
          Queueing
        case Forwarding(old) =>
          Forwarding(sender)
      }

    case msg =>
      state = state match {
        case Forwarding(to) =>
          // use forward here?
          to ! msg
          Queueing
        case Queueing =>
          Queued(msg)
        case Queued(old) =>
          // send old to deadletters?
          context.system.deadLetters ! old
          Queued(msg)
      }
  }
}