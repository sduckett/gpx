(ns gpx.time
  (:require [clj-time.core :as time]))

(defn elapsed-time
  "The time, in seconds, between the earliest and latest trackpoints"
  [trackpoints]
  (let [points (sort-by :time trackpoints)]
    (time/in-seconds
     (time/interval (:time (first points))
                    (:time (last points))))))
