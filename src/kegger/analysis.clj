(ns kegger.analysis
  (:require [powderkeg.core :as keg]
            [net.cgrand.xforms :as x]
            [marathon.analysis :as analysis]
            [marathon.ces.sampledata :as sd]))

;;assumes we have a spark master running on localhost.
(keg/connect! "spark://127.0.0.1:7077")

;;our initial context for marathon....
(def default-tables sd/sample-tables)

;;we should be able to port this to use a cluster
;;if a connection exists, rather than run locally.
;;

#_(defn requirements-run
  "Primary function to compute  requirements analysis.  Reads requirements 
   project from inpath, computes requirement, and spits results to a tsv 
   table in the same root folder as inpath, requirements.txt"
  [inpath]
  (let [inpath (clojure.string/replace inpath #"\\" "/")
        base (->> (clojure.string/split inpath #"/")
                  (butlast)
                  (clojure.string/join "/"))
        outpath (str base "/requirements.txt")]
    (do (println ["Analyzing requirements for" inpath])
        (->> (-> (a/load-requirements-project inpath)
                 (:tables)
                 (tables->requirements-async  :search bisecting-convergence)
                 (requirements->table)
                 (tbl/table->tabdelimited))
             (spit outpath))
        (println ["Spit requirements to " outpath]))))




