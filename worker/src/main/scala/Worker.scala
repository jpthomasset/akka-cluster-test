package worker

import akka.cluster.Cluster
import akka.cluster.ClusterEvent._
import akka.actor.{Actor, ActorLogging, Props, RootActorPath}
import akka.cluster.ClusterEvent.MemberUp
import akka.cluster.{Member, MemberStatus}
import messages._

object Worker {
  def props = Props(new Worker)
}

class Worker extends Actor with ActorLogging {

  val cluster = Cluster(context.system)

  override def preStart(): Unit = cluster.subscribe(self, classOf[MemberUp])
  override def postStop(): Unit = cluster.unsubscribe(self)

  def receive = {
    case WorkerJob(t) => sender() ! WorkerResult(s"Received '$t'")
    case MemberUp(m) if m.hasRole("router") =>
      log.info("Found router !")
      context.actorSelection(RootActorPath(m.address) / "user" / "router") ! WorkerRegistration
  }
}
