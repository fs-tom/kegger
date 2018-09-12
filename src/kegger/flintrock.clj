;;utils for working with flintrock
;;spark cluster management...
(ns kegger.flintrock
  (:require [clojure.java.shell :as shell]))

;;I think this returns yaml, so in theory
;;we could just use a yaml lib to parse....?
(defn trim-slave [s]
  (subs s 2))

(defn parse-cluster [xs]
  (->> xs
       (reduce (fn [[current acc] x]
                 (cond (= x "slaves:")
                       [(assoc current :slaves []) acc]
                       (:slaves current)
                       [(update current :slaves conj (trim-slave x)) acc]
                       (clojure.string/ends-with? x ":")
                       [{:cluster x} (if current (conj acc current) acc)]
                       :else (let [[k v] (clojure.string/split x #"\s")]
                               [(assoc current (keyword (apply str (butlast k))) v) acc])))
               [nil []])
       ((fn [[l xs]] (conj xs l)))))

(defn description->map [txt]
  (->> txt
       (clojure.string/split-lines)
       (filter #(not (.contains % "INFO")))
       (map clojure.string/trim )))

(defn get-clusters []
  (-> (shell/sh "flintrock" "describe")
      :out
      description->map
      parse-cluster))

(defn sparkify [host]
  (str "spark://" host ":7077"))

(defn find-spark []
  (-> (get-clusters)
      first
      :master
      sparkify))
