(defproject kegger "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [marathon "4.1.6-SNAPSHOT"]
                 ;;powderkeg dependencies
                 [hcadatalab/powderkeg "0.5.1"]
                 [com.esotericsoftware/kryo-shaded "4.0.0"]  ;; For Spark 2.x support
                 [org.apache.spark/spark-core_2.11 "2.3.1"  :exclusions [commons-codec]]
                 [org.apache.spark/spark-streaming_2.11 "2.3.1"  :exclusions [commons-codec]]]
  ;;windows hacks...
  :eval-in :classloader
  :resource-paths ["lib/*"])
