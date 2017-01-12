# Akka cluster test

Simple project for testing Akka Cluster behavior (WIP).

The basic idea is to have a router node wich will route request to worker nodes based on their capabilities.
Worker nodes register themselves on the router node.
At this time, there is only one worker but I plan to add some strategy to route message to the correct node.

## Project layout
* Shared messages are stored in the `messages` subproject
* Router process is in the `router` subproject
* Worker process is in the `router` subproject


# Running

Inside `sbt`, run the following command to start the router:
```
router/run
```

And the following command to start the worker:
```
worker/run
```

Press `CTRL+D` to exit from the underlying process.
