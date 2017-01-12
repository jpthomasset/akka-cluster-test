package router

import akka.cluster.Cluster
import akka.cluster.ClusterEvent._
import akka.actor.{Actor, ActorLogging, Props}

object SimpleClusterListener {
  def props = Props(new SimpleClusterListener)
}

class SimpleClusterListener extends Actor with ActorLogging {
 
  val cluster = Cluster(context.system)
 
  // subscribe to cluster changes, re-subscribe when restart 
  override def preStart(): Unit = {
    //#subscribe
    cluster.subscribe(self, initialStateMode = InitialStateAsEvents,
      classOf[MemberEvent], classOf[UnreachableMember])
    //#subscribe
  }
  override def postStop(): Unit = cluster.unsubscribe(self)
 
  def receive = {
    case MemberUp(member) =>
      log.debug("Member is Up: {}", member.address)
    case UnreachableMember(member) =>
      log.debug("Member detected as unreachable: {}", member)
    case MemberRemoved(member, previousStatus) =>
      log.debug("Member is Removed: {} after {}",
        member.address, previousStatus)
    case _: MemberEvent => // ignore
  }
}
