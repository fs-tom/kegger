;;janky monkey patch to help us
;;prefer provided libs over resolved mvn deps.

(in-ns 'powderkeg.core)
(defn compare-by-name [system-jars spark-jars]
  (let [system-names (into {} (map #(vector (.getName %) %) system-jars))
        spark-names  (into {} (map #(vector (.getName %) %) spark-jars))]
    (->> spark-names
         keys
         (reduce dissoc system-names)
         vals
         set)))

(defn guess-all-jars-but-spark
  "Get a list (of URLs) of jars on the classpath which does not belong to Spark or its dependencies."
  []
  (let [system-jars (clj/into #{}
                      (comp
                        (map #(java.io.File. (.toURI %)))
                        (filter #(and (.isFile %) (.endsWith (.getName %) ".jar"))))
                      (.getURLs (java.lang.ClassLoader/getSystemClassLoader)))
        coords (clj/into []
                 (comp
                   (map #(.getName %))
                   (keep #(re-matches #"(spark-.*)-(\d+\.\d+\.\d+(?:-.*)?)\.jar" %))
                   (map (fn [[_ p v]] [(symbol "org.apache.spark" p) v])))
                 system-jars)
        spark-jars (set (mvn/dependency-files (mvn/resolve-dependencies :retrieve true :coordinates coords)))
        other-jars (compare-by-name system-jars spark-jars) #_(reduce disj system-jars spark-jars)]
    (when (= other-jars system-jars)
      (throw (ex-info "Can't filter out Spark jars!" {})))
    other-jars))
(in-ns 'user)
