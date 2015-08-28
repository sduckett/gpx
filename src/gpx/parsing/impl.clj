(ns gpx.parsing.impl
  (:require [clj-time.format :as time.format]
            [clojure.xml :refer :all]))

(defn parse-gpx
  "Gets the raw XML structure built from a file. Reads the whole dang thing."
  [path]
  (parse (java.io.ByteArrayInputStream. (.getBytes (slurp path)))))

(defn tag? [tag m]
  (when (= tag (:tag m)) m))

(defn find-tag [coll tag]
  (some (partial tag? tag) coll))

(defn transform-trkpt [p]
  (let [t   (find-tag (:content p) :time)
        ts  (time.format/parse (first (:content t)))
        e   (find-tag (:content p) :ele)
        es  (first (:content e))
        lat (read-string (get-in p [:attrs :lat]))
        lon (read-string (get-in p [:attrs :lon]))]
    {:time ts
     :elevation es
     :lat lat
     :lon lon}))

(defn get-data [xml]
  (:content (find-tag (:content xml) :trk)))
