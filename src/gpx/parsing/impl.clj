(ns gpx.parsing.impl
  (:require [clj-time.format :as time.format]
            [clojure.xml :refer :all]))

(defn parse-gpx
  "Gets the raw XML structure built from a file. Reads the whole dang thing."
  [path]
  (parse (java.io.ByteArrayInputStream. (.getBytes (slurp path)))))

;; Seems like this should be something we shouldn't need to do directly....
;; more to a higher level?
(defn tag? [tag m]
  (when (= tag (:tag m)) m))

(defn find-tag [coll tag]
  (some (partial tag? tag) coll))

(defn transform-trackpoint [p]
  (let [time-node   (find-tag (:content p) :time)
        timestamp  (time.format/parse (first (:content time-node)))
        elevation-node   (find-tag (:content p) :ele)
        elevation-string  (first (:content elevation-node))
        lat (read-string (get-in p [:attrs :lat]))
        lon (read-string (get-in p [:attrs :lon]))]
    {:time timestamp
     :elevation elevation-string
     :lat lat
     :lon lon}))

(defn get-data [xml]
  (:content (find-tag (:content xml) :trk)))
