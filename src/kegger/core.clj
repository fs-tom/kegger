(ns kegger.core
  (:require [powderkeg.core :as keg]
            [net.cgrand.xforms :as x]))

#_(keg/connect! "spark://macbook-pro.home:7077") ; change uri, "local[2]" can do but that's no fun...

#_(keg/connect! "spark://ip-172-31-27-41.us-gov-west-1.compute.internal:7077")

; sample lifted from sparkling
(comment (into [] ; no collect, plain Clojure
           (keg/rdd ["This is a firest line"  ; here we provide data from a clojure collection.
                     "Testing spark"
                     "and powderkeg"
                     "Happy hacking!"]
             (filter #(.contains % "spark"))))) ; plain standard transducer, no new API


