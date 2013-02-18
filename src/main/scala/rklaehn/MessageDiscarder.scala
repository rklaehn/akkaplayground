package rklaehn
import akka.actor._

object MessageDiscarder {

  object WorkerAvailable

  private sealed trait State
  private case class Forwarding(to:ActorRef) extends State
  private case object Queueing extends State
  private case class Queued(msg:Any, from:ActorRef) extends State
}

class MessageDiscarder extends Actor {
  import MessageDiscarder._

  private var state:State = Queueing

  def receive = {
    case WorkerAvailable =>
      state = state match {
        case Queueing => Forwarding(sender)
        case Queued(msg, from) =>
          // use remembered from so we're out of the loop!
          sender.tell(msg, from)
          Queueing
        case Forwarding(old) =>
          Forwarding(sender)
      }

    case msg =>
      state = state match {
        case Forwarding(to) =>
          to forward msg
          Queueing
        case Queueing =>
          // remember sender as well as message
          Queued(msg, sender)
        case Queued(oldMsg, oldSender) =>
          // send oldMsg to deadletters?
          context.system.deadLetters ! oldMsg
          // remember sender as well as message
          Queued(msg, sender)
      }
  }
}