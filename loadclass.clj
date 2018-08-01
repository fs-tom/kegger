;;janky script to help us get JRE working, mostly
;;for windows 10, but should be portable...
(require '[spork.util.io :as io])
(require '[clojure.java.io :as jio])

(def classes (->> (-> (slurp "classes.txt")
                      (clojure.string/split #";"))
                  (map clojure.string/trim)))

(doseq [jarfile classes]
  (let [fn       (io/fname jarfile)
        new-path (jio/file (io/file-path "lib" fn))] 
    (println [:copying fn :to (io/fname new-path)])
    (jio/copy (io/file fn) new-path)))
             
