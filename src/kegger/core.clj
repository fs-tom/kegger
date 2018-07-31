(ns kegger.core
  (:require [powderkeg.core :as keg]
            [net.cgrand.xforms :as x])
  )

#_(keg/connect! "spark://macbook-pro.home:7077") ; change uri, "local[2]" can do but that's no fun...

(keg/connect! "spark://127.0.0.1:7077")

                                        ; sample lifted from sparkling
(defn sample []
  (into [] ; no collect, plain Clojure
        (keg/rdd ["This is a firest line"  ; here we provide data from a clojure collection.
                  "Testing spark"
                  "and powderkeg"
                  "Happy hacking!"]
                 (filter #(.contains % "spark"))))) ; plain standard transducer, no new API

(keg/rdd (range 100)     ; source
         (filter odd?)          ; 1st transducer to apply
         (map inc)              ; 2nd transducer
         :partitions 2)



