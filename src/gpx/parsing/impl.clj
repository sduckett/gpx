(ns gpx.parsing.impl
  (:require [clj-time.format :as time.format]
            [clojure.xml :refer :all]))

(defn- ->dbl
  "TODO Convert a string to double if we can."
  [v]
  (try (-> v clojure.string/trim Double/parseDouble)))

(defn- slurp-gpx
  "TODO Gets the raw XML structure built from a file.
  Reads the whole dang thing."
  [path]
  (parse (java.io.ByteArrayInputStream. (.getBytes (slurp path)))))

(defn- tag?
  "TODO Is this a tag? What exactly are we looking for here?"
  [tag m]
  (when (= tag (:tag m)) m))

(defn find-tag
  "Get a tag by name from coll"
  [coll tag]
  (some (partial tag? tag) coll))

(defn get-data
  "Get the first GPX track represented path"
  [path]
  (let [xml (slurp-gpx path)]
    (:content (find-tag (:content xml) :trk))))

(defn transform-trackpoint
  "TODO Convert an XML trackpoint into a map that I can use.
  This might be a little funky sometimes..."
  [p]
  (let [time-node        (find-tag (:content p) :time)
        timestamp        (time.format/parse (first (:content time-node)))
        elevation-node   (find-tag (:content p) :ele)
        elevation        (->dbl (first (:content elevation-node)))
        lat (read-string (get-in p [:attrs :lat]))
        lon (read-string (get-in p [:attrs :lon]))]
    {:time timestamp
     :elevation elevation
     :lat lat
     :lon lon}))
