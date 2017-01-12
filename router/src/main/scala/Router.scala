package router

import akka.cluster.Cluster
import akka.cluster.ClusterEvent._
import akka.actor.{Actor, ActorLogging, ActorRef, Props, Terminated}
import messages._


object Router {
  def props = Props(new Router)
}

class Router extends Actor with ActorLogging {

  def receive = {
    case WorkerRegistration =>
      log.info("Registrating new worker")
      context watch sender()
      context.become(withWorker(sender()))
  }

  def withWorker(worker: ActorRef) : Receive  = {
    case job: WorkerJob => worker forward job
    case WorkerRegistration => log.error("A Worker is already registered")
    case Terminated(a) if a == worker =>
      log.warning(s"Worker $a is terminated")
      context.become(receive)
  }
}
