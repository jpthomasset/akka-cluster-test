package router

import akka.actor.ActorSystem
import scala.io.StdIn

object Main {

  def main(args: Array[String]): Unit = {
    implicit val system = ActorSystem("ClusterSystem")

    println(s"\u001B[32mStarting Router, Ctrl+D to stop and go back to the console...\u001B[0m")

    system.actorOf(Router.props, "router")

    while(StdIn.readLine() != null) {}
    system.terminate()
  }
}
