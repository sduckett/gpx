(ns gpx.parsing
  (:require [gpx.geometry :refer [haversine rround]]
            [gpx.parsing.impl :refer :all]))

(defn get-points [path]
  (let [raw (get-data (parse-gpx path))
        trks (:content (find-tag raw :trkseg))]
    (sort-by :time (map transform-trkpt trks))))

(defn track->distance
  "Calculate the distance, in meters, of a sequence of track points

  SAMPLE DATA

  Hancock Pass: 21.4 miles
  Davis Meadows: 19.8 miles"
  [coll]
  (letfn [(kapow [coll acc]
            (if (< (count coll) 2)
              acc
              (recur (rest coll)
                     (+ (haversine (first coll) (second coll))
                        acc))))]
    (kapow coll 0)))
