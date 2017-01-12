package messages

case class WorkerJob(text: String)
case class WorkerResult(text: String)
case class WorkerJobFailed(reason: String, job: WorkerJob)
