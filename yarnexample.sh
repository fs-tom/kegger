#IF we already have a yarn cluster running, ala EMR,
#and we can ssh into it, we can get a repl via podwerkeg
#if we have

#1: an uberjar with powerdkeg as the dependency.
#   this can be as simple as no other dep or source.
#   this is kegger.jar in the example. 

#2: we invoke - from the cluster! - the job via
#spark-submit:

spark-submit --master yarn --class powderkeg.repl kegger.jar
